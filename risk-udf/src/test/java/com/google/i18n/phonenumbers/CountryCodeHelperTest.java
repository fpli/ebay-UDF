package com.google.i18n.phonenumbers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CountryCodeHelperTest {

  @Test
  public void getFirstCountryForCountryCode() {
    assertEquals("US", CountryCodeHelper.getFirstCountryForCountryCode(1).orElse(""));
    assertEquals("CN", CountryCodeHelper.getFirstCountryForCountryCode(86).orElse(""));
  }

  @Test
  public void getCountryCodeToRegionCodeMap() {
    // defined by the lib
    assertEquals(215, CountryCodeHelper.getCountryCodeToRegionCodeMap().size());
  }
}