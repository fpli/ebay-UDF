package com.ebay.hadoop.udf.tokenizer;

import com.ebay.hadoop.udf.tokenizer.util.TokenizerUtils;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RetryableMicroVaultInstance implements InvocationHandler {
  private static final Logger LOG = LoggerFactory.getLogger(RetryableMicroVaultInstance.class);

  public static String POD_ENV_KEY = "POD_ENV";
  public static String TOKENIZER_SERVICE_HOST_KEY = "TOKENIZER_SERVICE_HOST";
  public static String TOKENIZER_SERVICE_PORT_KEY = "TOKENIZER_SERVICE_PORT";
  public static String TOKENIZER_SERVICE_TIMEOUT_KEY = "TOKENIZER_SERVICE_TIMEOUT"; // in ms
  public static String PROD_ENV = "production";
  public static String DEFAULT_POD_ENV = PROD_ENV;
  public static String DEFAULT_PROD_TOKENIZER_SERVICE_HOST = "tokenizersvc.vip.ebay.com";
  public static int DEFAULT_PROD_TOKENIZER_SERVICE_PORT = 443;
  public static int DEFAULT_TOKENIZER_SERVICE_TIMEOUT = 3 * 60 * 1000; // 3 minutes

  public static String TOKENIZER_MAX_ATTEMPTS_KEY = "TOKENIZER_MAX_ATTEMPTS";
  public static String TOKENIZER_ATTEMPT_WAIT_KEY = "TOKENIZER_ATTEMPT_WAIT";
  public static int DEFAULT_TOKENIZER_MAX_ATTEMPTS = 2;
  public static int DEFAULT_TOKENIZER_ATTEMPT_WAIT = 50; // in ms

  private static final String CONTEXT_DEADLINE_EXCEEDED = "context deadline exceeded";

  private final String podEnv;
  private final String svcHost;
  private final int svcPort;
  private final long svcTimeout;
  private final int rpcMaxAttempts;
  private final int rpcAttemptWait;
  private volatile boolean serviceReachable;

  private static volatile IMicroVaultInstance singleTon;
  private volatile IMicroVaultInstance _microVaultInstance;

  private RetryableMicroVaultInstance() {
    podEnv = Optional.ofNullable(System.getenv(POD_ENV_KEY)).orElse(DEFAULT_POD_ENV);
    svcHost =
        Optional.ofNullable(System.getenv(TOKENIZER_SERVICE_HOST_KEY))
            .orElse(DEFAULT_PROD_TOKENIZER_SERVICE_HOST);
    svcPort =
        Optional.ofNullable(System.getenv(TOKENIZER_SERVICE_PORT_KEY))
            .map(i -> Integer.valueOf(i))
            .orElse(DEFAULT_PROD_TOKENIZER_SERVICE_PORT);
    svcTimeout =
        Optional.ofNullable(System.getenv(TOKENIZER_SERVICE_TIMEOUT_KEY))
            .map(i -> Integer.valueOf(i))
            .orElse(DEFAULT_TOKENIZER_SERVICE_TIMEOUT);
    rpcMaxAttempts =
        Optional.ofNullable(System.getenv(TOKENIZER_MAX_ATTEMPTS_KEY))
            .map(i -> Integer.valueOf(i))
            .orElse(DEFAULT_TOKENIZER_MAX_ATTEMPTS);
    rpcAttemptWait =
        Optional.ofNullable(System.getenv(TOKENIZER_ATTEMPT_WAIT_KEY))
            .map(i -> Integer.valueOf(i))
            .orElse(DEFAULT_TOKENIZER_ATTEMPT_WAIT);
    _microVaultInstance = new MicroVaultInstance();
  }

  public static IMicroVaultInstance getSingleton() {
    if (singleTon == null) {
      synchronized (RetryableMicroVaultInstance.class) {
        if (singleTon == null) {
          RetryableMicroVaultInstance microVaultInstance = new RetryableMicroVaultInstance();
          singleTon =
              (IMicroVaultInstance)
                  Proxy.newProxyInstance(
                      Thread.currentThread().getContextClassLoader(),
                      new Class[] {IMicroVaultInstance.class},
                      microVaultInstance);
        }
      }
    }
    return singleTon;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    int retryTimes = 0;
    while (true) {
      try {
        if (!serviceReachable) {
          waitProdTokenizerServiceReachable();
        }
        return method.invoke(_microVaultInstance, args);
      } catch (IllegalAccessException ignored) {
      } catch (InvocationTargetException e) {
        if (e.getCause() == null) {
          throw e;
        } else {
          if (TokenizerUtils.stringifyException(e.getCause()).contains(CONTEXT_DEADLINE_EXCEEDED)) {
            serviceReachable = isTokenizerServiceReachable();
          }
          retryTimes++;
          if (retryTimes < rpcMaxAttempts) {
            Thread.sleep(rpcAttemptWait);
          } else {
            LOG.error("Attempt over {} times", rpcMaxAttempts, e.getCause());
            if (serviceReachable) {
              throw e.getCause();
            } else {
              throw new TokenizerException(
                  String.format("Tokenizer service %s:%d is not reachable", svcHost, svcPort),
                  e.getCause());
            }
          }
        }
      }
    }
  }

  private boolean needToCheckTokenizerServiceReachable() {
    if (!TokenizerUtils.isTesting() && PROD_ENV.equalsIgnoreCase(podEnv)) {
      return true;
    } else {
      LOG.info(
          "Skip to check [testing ? {}] {} tokenizer service {}:{} reachable.",
          TokenizerUtils.isTesting(),
          podEnv,
          svcHost,
          svcPort);
      return false;
    }
  }

  private boolean isTokenizerServiceReachable() {
    return needToCheckTokenizerServiceReachable() ? isHostPortReachable(svcHost, svcPort) : true;
  }

  /** Wait tokenizer service reachable for production environment. */
  private void waitProdTokenizerServiceReachable() {
    if (!needToCheckTokenizerServiceReachable()) {
      serviceReachable = true;
    } else {
      long startTime = System.currentTimeMillis();
      serviceReachable = isHostPortReachable(svcHost, svcPort);
      while (!serviceReachable && System.currentTimeMillis() - startTime < svcTimeout) {
        try {
          LOG.info(
              "Sleeping 1 second to wait the tokenizer service {}:{} reachable.", svcHost, svcPort);
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
          // do nothing
        }
        serviceReachable = isHostPortReachable(svcHost, svcPort);
      }

      long elapsedTime = System.currentTimeMillis() - startTime;

      if (serviceReachable) {
        LOG.info(
            "Tokenizer service {}:{} is reachable after {} ms.", svcHost, svcPort, elapsedTime);
      } else {
        LOG.info(
            "Tokenizer service {}:{} is still not reachable after {} ms.",
            svcHost,
            svcPort,
            elapsedTime);
      }
    }
  }

  private boolean isHostPortReachable(String host, int port) {
    try (Socket socket = new Socket()) {
      InetSocketAddress socketAddress = new InetSocketAddress(host, port);
      socket.connect(socketAddress, 1000); // connect timeout 1s
      LOG.info("{}:{} is reachable.", host, port);
      return true;
    } catch (IOException e) {
      LOG.warn("{}:{} is not reachable.", host, port);
      return false;
    }
  }
}
