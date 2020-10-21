package com.ebay.risk.n11n.api.entity.result;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.ebay.risk.n11n.data.AddressData;
import com.ebay.risk.normalize.enums.DataTypeEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.junit.Test;

public class ResultTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  private final Result emailResult;
  private final Result addressResult;
  private final Result phoneResult;


  public ResultTest() {
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    emailResult = EmailResult.builder().normalizedEmail("1x@a.com").localPart("1x")
        .domainPart("a.com").isValidEmail(true).build();

    addressResult = AddressResult.builder()
        .address(AddressData.builder().addressLine("aaa").country("US").build()).build();

    phoneResult = PhoneResult.builder().countryCode(86).countryId(45)
        .nationalNumber("15900000000").phoneNumberE164Format("+8615900000000").build();
  }


  @Test
  public void testSerializeEmail() throws JsonProcessingException {
    assertEquals(
        "{\"dataType\":\"EMAIL\",\"normalizedEmail\":\"1x@a.com\",\"localPart\":\"1x\",\"domainPart\":\"a.com\","
            + "\"validEmail\":true}",
        objectMapper.writeValueAsString(emailResult));
  }

  @Test
  public void testSerializePhone() throws JsonProcessingException {
    assertEquals(
        "{\"dataType\":\"PHONE\",\"countryId\":45,\"nationalNumber\":\"15900000000\",\"countryCode\":86,\"phoneNumberE164Format\":\"+8615900000000\"}",
        objectMapper.writeValueAsString(phoneResult));
  }

  @Test
  public void testSerializeAddress() throws JsonProcessingException {
    assertEquals(
        "{\"dataType\":\"ADDRESS\",\"address\":{\"addressLine\":\"aaa\",\"locality\":null,\"province\":null,\"postalCode\":null,\"country\":\"US\"},\"metadata\":{\"normalizationLevel\":0,\"scores\":{},\"ansStructure\":{\"number\":\"\",\"street\":\"\",\"subBuilding\":\"\",\"building\":\"\",\"organization\":\"\",\"deliveryService\":\"\",\"locality\":\"\",\"province\":\"\",\"postalCode\":\"\",\"country\":\"\",\"completeAddress\":\"\",\"addressType\":\"\",\"languageISO3\":\"\",\"mailabilityScore\":\"\"},\"callANSCount\":0,\"pobox\":false}}",
        objectMapper.writeValueAsString(addressResult));
  }

  @Test
  public void testDeserializeEmail() throws IOException {
    Result result = objectMapper
        .readValue("{\"dataType\":\"EMAIL\",\"normalizedEmail\":\"123@a.com\"}",
            Result.class);
    assertEquals(DataTypeEnum.EMAIL, result.getDataType());
    assertTrue(result instanceof EmailResult);
    assertEquals("123@a.com", ((EmailResult)result).getNormalizedEmail());
  }

  @Test
  public void testDeserializePhone() throws IOException {
    Result result = objectMapper
        .readValue("{\"dataType\":\"PHONE\",\"nationalNumber\":\"1234567890\",\"countryId\":11}",
            Result.class);
    assertEquals(DataTypeEnum.PHONE, result.getDataType());
    assertTrue(result instanceof PhoneResult);
    assertEquals("1234567890", ((PhoneResult)result).getNationalNumber());
  }

  @Test
  public void testDeserializeAddress() throws IOException {
    Result result = objectMapper.readValue(
        "{\"dataType\":\"ADDRESS\",\"address\":{\"rawAddressLine\":null,\"addressLine\":\"aaa\",\"locality\":null,\"province\":null,\"postalCode\":null,\"country\":\"US\"}}",
        Result.class);
    assertEquals(DataTypeEnum.ADDRESS, result.getDataType());
    assertTrue(result instanceof AddressResult);
    assertEquals("US", ((AddressResult)result).getAddress().getCountry());
  }

}