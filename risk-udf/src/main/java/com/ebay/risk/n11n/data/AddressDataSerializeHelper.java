package com.ebay.risk.n11n.data;

import java.util.Locale;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

/**
 * Serialize/deserialize address data.
 */
public final class AddressDataSerializeHelper {

  public static final String ADDR_SEP = "╏";

  private static final int NUM_SEGS = 5;

  /**
   * Please use static methods instead.
   */
  private AddressDataSerializeHelper() {}

  /**
   * Serialize the AddressData fields, with processing:
   * 1. If null, set to ""
   * 2. Remove ╏ in the original text
   * 3. Trim spaces.
   * @return the concatenated string with seperator "╏".
   */
  public static String serialize(AddressData addressData) {
    if (addressData == null) {
      return "╏╏╏╏";
    }
    String[] segs = new String[] {
      StringUtils.isEmpty(addressData.getAddressLine()) ? "": addressData.getAddressLine().replaceAll("╏", " "),
      StringUtils.isEmpty(addressData.getLocality()) ? "": addressData.getLocality().replaceAll("╏", " "),
      StringUtils.isEmpty(addressData.getProvince()) ? "": addressData.getProvince().replaceAll("╏", " "),
      StringUtils.isEmpty(addressData.getPostalCode()) ? "": addressData.getPostalCode().replaceAll("╏", " "),
      StringUtils.isEmpty(addressData.getCountry()) ? "": addressData.getCountry().replaceAll("╏", " "),
    };
    return String.join(ADDR_SEP, segs);
  }

  public static Optional<AddressData> deserialize(String str) {
    if (StringUtils.isEmpty(str)) {
      return Optional.empty();
    }
    String[] segs = str.split(ADDR_SEP, -1);
    if (segs.length != NUM_SEGS) {
      return Optional.empty();
    }
    return Optional.of(AddressData.builder()
        .addressLine(segs[0])
        .locality(segs[1])
        .province(segs[2])
        .postalCode(segs[3])
        .country(segs[4])
        .build());
  }

  /**
   * Uniform the serialized string, make them: 1. upper cased, 2. trimmed for each field.
   */
  public static String uniform(String serialized) {
    if (StringUtils.isEmpty(serialized)) {
      return "";
    }
    String[] segs = serialized.split(ADDR_SEP);
    for (int i = 0; i < segs.length; i++) {
      segs[i] = uniformField(segs[i]);
    }
    return String.join(ADDR_SEP, segs);
  }

  public static String uniformField(String field) {
    if (StringUtils.isEmpty(field)) {
      return "";
    }
    return field.trim().toUpperCase(Locale.ENGLISH);
  }
}
