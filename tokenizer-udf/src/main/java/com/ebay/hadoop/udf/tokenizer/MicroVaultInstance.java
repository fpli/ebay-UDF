package com.ebay.hadoop.udf.tokenizer;

import com.ebay.hadoop.udf.tokenizer.util.TokenizerUtils;
import com.ebay.platform.security.tokenizer.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

public class MicroVaultInstance implements IMicroVaultInstance {
  public static String MICRO_VAULT_HOST_KEY = "MICRO_VAULT_HOST";
  public static String MICRO_VAULT_PORT_KEY = "MICRO_VAULT_PORT";
  public static String GRPC_BUFFER_SIZE_KEY = "GRPC_BUFFER_SIZE"; // in MB
  public static String DEFAULT_MICRO_VAULT_HOST = "localhost";
  public static int DEFAULT_MICRO_VAULT_PORT = 10000;
  public static int DEFAULT_GRPC_BUFFER_SIZE = 16 * 1024 * 1024; // 16MB

  private final TokenizerGrpc.TokenizerBlockingStub tokenizerBlockingStub;
  private final MetadataGrpc.MetadataBlockingStub metadataBlockingStub;

  protected MicroVaultInstance() {
    this(
        Optional.ofNullable(System.getenv(MICRO_VAULT_HOST_KEY)).orElse(DEFAULT_MICRO_VAULT_HOST),
        Optional.ofNullable(System.getenv(MICRO_VAULT_PORT_KEY))
            .map(i -> Integer.valueOf(i))
            .orElse(DEFAULT_MICRO_VAULT_PORT),
        Optional.ofNullable(System.getenv(GRPC_BUFFER_SIZE_KEY))
            .map(i -> Integer.valueOf(i) * 1024 * 1024)
            .orElse(DEFAULT_GRPC_BUFFER_SIZE));
  }

  protected MicroVaultInstance(String host, int port, int grpcBuffer) {
    this(
        ManagedChannelBuilder.forAddress(host, port)
            .usePlaintext()
            .maxInboundMessageSize(grpcBuffer));
  }

  protected MicroVaultInstance(ManagedChannelBuilder<?> channelBuilder) {
    ManagedChannel channel = channelBuilder.build();
    tokenizerBlockingStub = TokenizerGrpc.newBlockingStub(channel);
    metadataBlockingStub = MetadataGrpc.newBlockingStub(channel);
  }

  @Override
  public TokenizeResponse tokenize(String tokenizerRef, String data) {
    TokenizerRef TOKENIZER_REF = TokenizerRef.apply(tokenizerRef);
    String originalData = data;
    data = TokenizerUtils.filterData(TOKENIZER_REF, data);
    if (StringUtils.isBlank(data)) return null;
    try {
      TokenizeRequest request =
          TokenizeRequest.newBuilder().setTokenizerRef(tokenizerRef).setData(data).build();
      return tokenizerBlockingStub.tokenize(request);
    } catch (Exception e) {
      throw new TokenizerException(
          String.format(
              "Error tokenizing the data[%s] with tokenizerRef[%s], original data[%s]",
              data, tokenizerRef, originalData),
          e);
    }
  }

  @Override
  public List<TokenizeResponse> batchTokenize(String tokenizerRef, List<String> dataList) {
    TokenizerRef TOKENIZER_REF = TokenizerRef.apply(tokenizerRef);
    List<String> originalDataList = dataList;
    dataList = TokenizerUtils.filterData(TOKENIZER_REF, dataList);

    List<String> nonBlankDataList =
        dataList.stream().filter(data -> StringUtils.isNotBlank(data)).collect(Collectors.toList());

    try {
      List<TokenizeRequest> requests =
          nonBlankDataList.stream()
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
      List<TokenizeResponse> nonBlankDataResult =
          batchResponse.getResultsList().stream()
              .map(
                  res -> {
                    return res.getResponse();
                  })
              .collect(Collectors.toList());

      List<TokenizeResponse> allResult = new ArrayList<>(dataList.size());
      int nonBlankDataResultIndex = 0;
      for (String data : dataList) {
        if (StringUtils.isNotBlank(data)) {
          allResult.add(nonBlankDataResult.get(nonBlankDataResultIndex));
          nonBlankDataResultIndex++;
        } else {
          // add null result on blank data
          allResult.add(null);
        }
      }
      return allResult;
    } catch (TokenizerException e) {
      throw e;
    } catch (Exception e) {
      throw new TokenizerException(
          String.format(
              "Error batch tokenizing the data list[%s] with tokenizerRef[%s], original data list[%s]",
              dataList, tokenizerRef, originalDataList),
          e);
    }
  }

  @Override
  public DetokenizeResponse deTokenize(String tokenizerRef, String token) {
    if (StringUtils.isBlank(token)) return null;
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

  @Override
  public List<DetokenizeResponse> batchDeTokenize(String tokenizerRef, List<String> tokenList) {
    List<String> nonBlankTokenList =
        tokenList.stream()
            .filter(token -> StringUtils.isNotBlank(token))
            .collect(Collectors.toList());

    try {
      List<DetokenizeRequest> requests =
          nonBlankTokenList.stream()
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

      List<DetokenizeResponse> nonBlankTokenResult =
          batchResponse.getResultsList().stream()
              .map(
                  res -> {
                    return res.getResponse();
                  })
              .collect(Collectors.toList());

      List<DetokenizeResponse> allResult = new ArrayList<>(tokenList.size());
      int nonBlankTokenResultIndex = 0;
      for (String token : tokenList) {
        if (StringUtils.isNotBlank(token)) {
          allResult.add(nonBlankTokenResult.get(nonBlankTokenResultIndex));
          nonBlankTokenResultIndex++;
        } else {
          // add null result on blank token
          allResult.add(null);
        }
      }
      return allResult;
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

  @Override
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
