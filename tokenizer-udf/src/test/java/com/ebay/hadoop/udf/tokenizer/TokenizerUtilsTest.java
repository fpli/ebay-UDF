package com.ebay.hadoop.udf.tokenizer;

import com.ebay.hadoop.udf.tokenizer.util.TokenizerUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class TokenizerUtilsTest {
  @Test
  public void testFilterData() {
    String tokenizerRef1 = "phone-fax-home-tw";
    String tokenizerRef2 = "other";

    TokenizerRef phoneRef = TokenizerRef.apply(tokenizerRef1);
    TokenizerRef otherRef = TokenizerRef.apply(tokenizerRef2);

    assert phoneRef == TokenizerRef.PHONE_FAX_HOME_TW;
    assert otherRef == TokenizerRef.OTHER_TOKENIZER_REF;

    Map<String, String> dataFilterMap = new HashMap<>();
    dataFilterMap.put("86 1234567", "861234567");
    dataFilterMap.put("+86 1234567", "+861234567");
    dataFilterMap.put("+(86) 1234567", "+(86)1234567");
    dataFilterMap.put("+(86 1234567", "+(861234567");
    dataFilterMap.put("+86) 1234567", "+86)1234567");
    dataFilterMap.put("-86 1234567", "-861234567");
    dataFilterMap.put("+-86 1234567", "+-861234567");
    dataFilterMap.put("abc+-86 1234567", "+-861234567");
    dataFilterMap.put("abc+-86 1234567abc", "+-861234567");
    dataFilterMap.put("04264", "004264");
    dataFilterMap.put("abc", "");

    List<String> dataList = new ArrayList<>(dataFilterMap.size());
    List<String> expectedDataList = new ArrayList<>(dataFilterMap.size());

    for (Map.Entry<String, String> mapping : dataFilterMap.entrySet()) {
      String data = mapping.getKey();
      String expectedData = mapping.getValue();

      dataList.add(data);
      expectedDataList.add(expectedData);

      assert TokenizerUtils.filterData(phoneRef, data).equals(expectedData);
      assert TokenizerUtils.filterData(otherRef, data).equals(data);

      assert TokenizerUtils.filterData(phoneRef, expectedData).equals(expectedData);
      assert TokenizerUtils.filterData(otherRef, expectedData).equals(expectedData);
    }

    List<String> filteredPhoneDataList = TokenizerUtils.filterData(phoneRef, dataList);
    List<String> filteredOtherDataList = TokenizerUtils.filterData(otherRef, dataList);

    int index = 0;
    for (String data : dataList) {
      assert filteredPhoneDataList.get(index).equals(expectedDataList.get(index));
      assert filteredOtherDataList.get(index).equals(data);
      index++;
    }
  }
}
