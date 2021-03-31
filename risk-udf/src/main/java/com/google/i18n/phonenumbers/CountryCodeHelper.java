package com.google.i18n.phonenumbers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * This is a proxy calling CountryCodeToRegionCodeMap.getCountryCodeToRegionCodeMap, which is
 * default access level.
 */
public class CountryCodeHelper {

  public static Optional<String> getFirstCountryForCountryCode(Integer countryCode) {
    List<String> list = getCountryCodeToRegionCodeMap().get(countryCode);
    if (list == null || list.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(list.get(0));
  }

  public static Map<Integer, List<String>> getCountryCodeToRegionCodeMap() {
    return CountryCodeToRegionCodeMap.getCountryCodeToRegionCodeMap();
  }
}
