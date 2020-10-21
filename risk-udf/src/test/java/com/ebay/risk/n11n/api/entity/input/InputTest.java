package com.ebay.risk.n11n.api.entity.input;

import static org.junit.Assert.assertEquals;

import com.ebay.risk.n11n.data.AddressData;
import com.ebay.risk.normalize.enums.DataTypeEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.junit.Test;

public class InputTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  private final Input emailInput;
  private final Input addressInput;
  private final Input phoneInput;

  public InputTest() {
    EmailInput e = new EmailInput();
    e.setEmail("123@a.coM");
    e.setSubscriptionKey("LMS");
    emailInput = e;

    AddressInput a = new AddressInput();
    a.setAddress(AddressData.builder().addressLine("aaa").country("US").build());
    addressInput = a;

    PhoneInput p = new PhoneInput();
    p.setCountryId(11);
    p.setPhoneNumber("1234567890Aa");
    phoneInput = p;
  }

  @Test
  public void testInputKey() {
    assertEquals("PHONE:11╏1234567890Aa", phoneInput.getInputKey());
    assertEquals("EMAIL:123@a.coM", emailInput.getInputKey());
    assertEquals("ADDRESS:aaa╏╏╏╏US", addressInput.getInputKey());
  }

  @Test
  public void testToUniformedInputKey() {
    assertEquals("PHONE:11╏1234567890aa", phoneInput.convertToUniformedInput().getInputKey());
    assertEquals("EMAIL:123@a.com", emailInput.convertToUniformedInput().getInputKey());
    assertEquals("ADDRESS:AAA╏╏╏╏US", addressInput.convertToUniformedInput().getInputKey());
  }

  @Test
  public void testSerializeEmail() throws JsonProcessingException {
    assertEquals("{\"dataType\":\"EMAIL\",\"email\":\"123@a.coM\"}",
        objectMapper.writeValueAsString(emailInput));
  }

  @Test
  public void testSerializePhone() throws JsonProcessingException {
    assertEquals("{\"dataType\":\"PHONE\",\"phoneNumber\":\"1234567890Aa\",\"countryId\":11}",
        objectMapper.writeValueAsString(phoneInput));
  }

  @Test
  public void testSerializeAddress() throws JsonProcessingException {
    assertEquals("{\"dataType\":\"ADDRESS\",\"address\":{\"addressLine\":\"aaa\",\"locality\":null,\"province\":null,\"postalCode\":null,\"country\":\"US\"}}",
        objectMapper.writeValueAsString(addressInput));
  }

  @Test
  public void testDeserializeEmail() throws IOException {
    Input input = objectMapper.readValue("{\"dataType\":\"EMAIL\",\"email\":\"123@a.coM\"}",
        Input.class);
    assertEquals(DataTypeEnum.EMAIL, input.getDataType());
    assertEquals(emailInput.getInputKey(), input.getInputKey());
  }

  @Test
  public void testDeserializePhone() throws IOException {
    Input input = objectMapper.readValue("{\"dataType\":\"PHONE\",\"phoneNumber\":\"1234567890Aa\",\"countryId\":11}",
        Input.class);
    assertEquals(DataTypeEnum.PHONE, input.getDataType());
    assertEquals(phoneInput.getInputKey(), input.getInputKey());
  }

  @Test
  public void testDeserializeAddress() throws IOException {
    Input input = objectMapper.readValue("{\"dataType\":\"ADDRESS\",\"address\":{\"rawAddressLine\":null,\"addressLine\":\"aaa\",\"locality\":null,\"province\":null,\"postalCode\":null,\"country\":\"US\"}}",
        Input.class);
    assertEquals(DataTypeEnum.ADDRESS, input.getDataType());
    assertEquals(addressInput.getInputKey(), input.getInputKey());
  }

}