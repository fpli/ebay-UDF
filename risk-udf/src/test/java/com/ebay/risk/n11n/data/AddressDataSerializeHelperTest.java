package com.ebay.risk.n11n.data;

import static com.ebay.risk.n11n.data.AddressDataSerializeHelper.deserialize;
import static com.ebay.risk.n11n.data.AddressDataSerializeHelper.serialize;

import java.util.Locale;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;

public class AddressDataSerializeHelperTest {

  @Test
  public void testSerialize() {
    // input null
    assertEquals("╏╏╏╏", serialize(null));
    // With null fields
    assertEquals("╏╏╏╏", serialize(
        AddressData.builder().build()
    ));
    // with empty fields (v1.1: won't trim)
    assertEquals("  ╏╏╏╏", serialize(
        AddressData.builder().addressLine("  ").country("").build()
    ));

    // with fields
    assertEquals("Hello  world╏SH╏Shanghai╏200240╏China!", serialize(
        AddressData.builder().addressLine("Hello  world").locality("SH").province("Shanghai")
            .postalCode("200240").country("China!").build()
    ));

    // with fields ╏
    assertEquals("Hello  world    ╏SH╏Shanghai╏200240╏China!", serialize(
        AddressData.builder().addressLine("Hello  world╏╏╏╏").locality("SH").province("Shanghai")
            .postalCode("200240").country("China!").build()
    ));

    // with emoji
    assertEquals("\uD83D\uDE00╏SH╏Shanghai╏200240╏China!", serialize(
        AddressData.builder().addressLine("\uD83D\uDE00").locality("SH").province("Shanghai")
            .postalCode("200240").country("China!").build()
    ));
  }

  @Test
  public void testDeserialize() {
    // Trim works for TAB
    assertEquals("1", "\t \t  \t1\t   \t   ".trim());

    // empty string
    Assert.assertEquals(Optional.empty(), deserialize(""));
    Assert.assertEquals(Optional.empty(), deserialize(null));
    // wrong seps
    Assert.assertEquals(Optional.empty(), deserialize("GC|SH|Shanghai|200240|CN"));
    Assert.assertEquals(Optional.empty(), deserialize("GC╏╏SH╏Shanghai╏200240╏CN"));
    Assert.assertEquals(Optional.empty(), deserialize("GC SH╏Shanghai╏200240╏CN"));

    // normal case
    Assert.assertEquals(
        AddressData.builder().addressLine("Hello  world").locality("SH").province("Shanghai")
            .postalCode("200240").country("\t \tChina!\t  \t").build(),
        deserialize("Hello  world╏SH╏Shanghai╏200240╏\t \tChina!\t  \t").get()
    );
    Assert.assertEquals(AddressData.builder().addressLine("GC").locality("SH").province("Shanghai")
        .postalCode("200240").country("").build(), deserialize("GC╏SH╏Shanghai╏200240╏").get());
  }

  private void assertEquals(String expected, String actual) {
    Assert.assertEquals(expected.toUpperCase(Locale.ENGLISH), actual.toUpperCase(Locale.ENGLISH));
  }
}
