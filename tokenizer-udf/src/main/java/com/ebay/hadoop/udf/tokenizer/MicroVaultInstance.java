package com.ebay.hadoop.udf.tokenizer;

import com.ebay.platform.security.tokenizer.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MicroVaultInstance {
  public static String MICRO_VAULT_HOST_KEY = "MICRO_VAULT_HOST";
  public static String MICRO_VAULT_PORT_KEY = "MICRO_VAULT_PORT";
  public static String GRPC_BUFFER_SIZE_KEY = "GRPC_BUFFER_SIZE"; // in MB
  public static String DEFAULT_MICRO_VAULT_HOST = "localhost";
  public static int DEFAULT_MICRO_VAULT_PORT = 10000;
  public static int DEFAULT_GRPC_BUFFER_SIZE = 16 * 1024 * 1024; // 16MB

  private final TokenizerGrpc.TokenizerBlockingStub tokenizerBlockingStub;
  private final MetadataGrpc.MetadataBlockingStub metadataBlockingStub;

  private static volatile MicroVaultInstance singleton;

  public static MicroVaultInstance getSingleton() {
    if (singleton == null) {
      synchronized (MicroVaultInstance.class) {
        if (singleton == null) {
          singleton = new MicroVaultInstance();
        }
      }
    }
    return singleton;
  }

  private MicroVaultInstance() {
    this(
        Optional.ofNullable(System.getenv(MICRO_VAULT_HOST_KEY)).orElse(DEFAULT_MICRO_VAULT_HOST),
        Optional.ofNullable(System.getenv(MICRO_VAULT_PORT_KEY))
            .map(i -> Integer.valueOf(i))
            .orElse(DEFAULT_MICRO_VAULT_PORT),
        Optional.ofNullable(System.getenv(GRPC_BUFFER_SIZE_KEY))
            .map(i -> Integer.valueOf(i) * 1024 * 1024)
            .orElse(DEFAULT_GRPC_BUFFER_SIZE));
  }

  private MicroVaultInstance(String host, int port, int grpcBuffer) {
    this(
        ManagedChannelBuilder.forAddress(host, port)
            .usePlaintext()
            .maxInboundMessageSize(grpcBuffer));
  }

  private MicroVaultInstance(ManagedChannelBuilder<?> channelBuilder) {
    ManagedChannel channel = channelBuilder.build();
    tokenizerBlockingStub = TokenizerGrpc.newBlockingStub(channel);
    metadataBlockingStub = MetadataGrpc.newBlockingStub(channel);
  }

  /** message TokenizeResponse { string token = 1; string keyVersion = 2; bool keyEmbedded = 3; } */
  public TokenizeResponse tokenize(String tokenizerRef, String data) {
    try {
      TokenizeRequest request =
          TokenizeRequest.newBuilder().setTokenizerRef(tokenizerRef).setData(data).build();
      return tokenizerBlockingStub.tokenize(request);
    } catch (Exception e) {
      throw new TokenizerException(
          String.format("Error tokenizing the data[%s] with tokenizerRef[%s]", data, tokenizerRef),
          e);
    }
  }

  public List<TokenizeResponse> batchTokenize(String tokenizerRef, List<String> dataList) {
    try {
      List<TokenizeRequest> requests =
          dataList.stream()
              .map(
                  data -> {
                    return TokenizeRequest.newBuilder()
                        .setTokenizerRef(tokenizerRef)
                        .setData(data)
                        .build();
                  })
              .collect(Collectors.toList());
      BatchTokenizeRequest batchRequest =
          BatchTokenizeRequest.newBuilder()
              .setTokenizerRef(tokenizerRef)
              .addAllRequests(requests)
              .build();
      BatchTokenizeResponse batchResponse = tokenizerBlockingStub.batchTokenize(batchRequest);
      if (batchResponse.getHasError()) {
        List<String> errorList =
            batchResponse.getResultsList().stream()
                .filter(
                    r -> {
                      return r.hasError();
                    })
                .map(
                    r -> {
                      return r.getError();
                    })
                .collect(Collectors.toList());
        throw new TokenizerException("Error: " + String.join(",", errorList));
      }
      return batchResponse.getResultsList().stream()
          .map(
              res -> {
                return res.getResponse();
              })
          .collect(Collectors.toList());
    } catch (TokenizerException e) {
      throw e;
    } catch (Exception e) {
      throw new TokenizerException(
          String.format(
              "Error batch tokenizing the data list[%s] with tokenizerRef[%s]",
              dataList, tokenizerRef),
          e);
    }
  }

  /** message DetokenizeResponse { string data = 1; } */
  public DetokenizeResponse deTokenize(String tokenizerRef, String token) {
    try {
      DetokenizeRequest request =
          DetokenizeRequest.newBuilder().setTokenizerRef(tokenizerRef).setToken(token).build();
      return tokenizerBlockingStub.detokenize(request);
    } catch (Exception e) {
      throw new TokenizerException(
          String.format(
              "Error deTokenizing the token[%s] with tokenizerRef[%s]", token, tokenizerRef),
          e);
    }
  }

  public List<DetokenizeResponse> batchDeTokenize(String tokenizerRef, List<String> tokenList) {
    try {
      List<DetokenizeRequest> requests =
          tokenList.stream()
              .map(
                  token -> {
                    return DetokenizeRequest.newBuilder()
                        .setTokenizerRef(tokenizerRef)
                        .setToken(token)
                        .build();
                  })
              .collect(Collectors.toList());
      BatchDetokenizeRequest batchRequest =
          BatchDetokenizeRequest.newBuilder()
              .setTokenizerRef(tokenizerRef)
              .addAllRequests(requests)
              .build();
      BatchDetokenizeResponse batchResponse = tokenizerBlockingStub.batchDetokenize(batchRequest);
      if (batchResponse.getHasError()) {
        List<String> errorList =
            batchResponse.getResultsList().stream()
                .filter(
                    r -> {
                      return r.hasError();
                    })
                .map(
                    r -> {
                      return r.getError();
                    })
                .collect(Collectors.toList());
        throw new TokenizerException("Error: " + String.join(",", errorList));
      }
      return batchResponse.getResultsList().stream()
          .map(
              res -> {
                return res.getResponse();
              })
          .collect(Collectors.toList());
    } catch (TokenizerException e) {
      throw e;
    } catch (Exception e) {
      throw new TokenizerException(
          String.format(
              "Error batch deTokenizing the tokens[%s] with tokenizerRef[%s]",
              tokenList, tokenizerRef),
          e);
    }
  }

  /**
   * message TokenizerMetadata { string name = 1; string id = 2; bool reversible = 3; string
   * description = 4; }
   */
  public List<TokenizerMetadata> findTokenizerMetadata(
      String storeType, String storeName, String tableName, String columnName) {
    try {
      FindTokenizerMetadataRequest request =
          FindTokenizerMetadataRequest.newBuilder()
              .setStoreType(storeType)
              .setStoreName(storeName)
              .setTableName(tableName)
              .setColumnName(columnName)
              .build();
      FindTokenizerMetadataResponse response = metadataBlockingStub.findTokenizerMetadata(request);
      return response.getTokenizersList();
    } catch (Exception e) {
      throw new TokenizerException(
          String.format(
              "Error finding tokenizer metadata with storeType[%s] storeName[%s] tableName[%s] columnName[%s]",
              storeType, storeName, tableName, columnName),
          e);
    }
  }
}
