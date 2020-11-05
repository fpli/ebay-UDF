package com.ebay.risk.normalize;

import com.ebay.cos.type.v3.base.CountryCodeEnum;
import com.ebay.risk.normalize.enums.BuyerRiskCountryEnum;
import com.ebay.risk.normalize.enums.PGWAddressCountryEnum;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

/**
 * cntryMap includes: 1. GDW_TABLES.DW_COUNTRIES example: 3->United Kingdom; United Kingdom->United
 * Kingdom; gb->United Kingdom; uk->United Kingdom; Korea, South->South Korea...
 *
 * 2. XID cntry2cntryCode.csv example: MEI GUO->United States;VEREINIGTE STAATEN VON AMERIKA->United
 * States...
 *
 * 3. BuyerRiskCountryEnum example: Yugoslavia->Macedonia;APO/FPO->United States;Great
 * Britain->United Kingdom...
 *
 * 4. PGWAddressCountryEnum example: XPO->United States;
 *
 * 5. CountryCodeEnum example: South Korea->South Korea;840->United States;USA->United States...
 *
 * 6. HandMap example: 臺灣->Taiwan...
 *
 * no need to add this enum <!--    com.ebay.integ.account.common.CountryEnum-->
 * <dependency>
 * <groupId>com.ebay.v3project.v3dal</groupId>
 * <artifactId>DALAccount</artifactId>
 * <version>1.0.1086-E1157_DEV_BASE</version>
 * </dependency>
 */
@Slf4j
public class CountryNormalizer {

  /**
   * source: https://zeta.corp.ebay.com/zeta/#/ cluster: apolloRno; user: b_riskmgmt_insights SQL:
   * select cntry_id,cntry_desc,cntry_code,iso_cntry_code from GDW_TABLES.DW_COUNTRIES
   */
  private static final String DW_COUNTRIES_CSV = "/cntryMapping/DW_COUNTRIES.csv";
  private static final String XID_COUNTRY_MAPPING_CSV = "/cntryMapping/XIDCntryMapping.csv";
  private Map<String, String> cntryMap = new HashMap<>();

  public static CountryNormalizer getInstance() {
    return EnumFactory.INSTANCE.getInstance();
  }

  public String normalize(String rawCntry) {
    String fullCntry = null;
    if (!StringUtils.isBlank(rawCntry)) {
      fullCntry = cntryMap.getOrDefault(processInput(rawCntry), null);
    }
    return StringUtils.equals(fullCntry, "Invalid") ? null : fullCntry;
  }


  private CountryNormalizer() {
    try {
      putFromCsvFile();
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    putFromEnum();
  }

  private enum EnumFactory {

    INSTANCE;

    private CountryNormalizer instance;

    EnumFactory() {
      instance = new CountryNormalizer();
    }

    public CountryNormalizer getInstance() {
      return instance;
    }

  }

  private static String processInput(String input) {
    return input.toUpperCase().trim();
  }

  private static CountryCodeEnum findFullCntrybyEbayId(int id) {
    return id == 222 ? CountryCodeEnum.findByLegacyCountryId(124)
        : CountryCodeEnum.findByLegacyCountryId(id);
  }

  private static void putTmpEntry(String key, String value, Map<String, String> tmp) {
    if (!StringUtils.isBlank(key) && !StringUtils.isBlank(value) &&
        !tmp.containsKey(processInput(key))) {
      tmp.put(processInput(key), value);
    }
  }

  private void putMap(Map<String, String> tmp) {
    for (String key : tmp.keySet()) {
      String value = tmp.get(key);
      if (cntryMap.containsKey(key)
          && !StringUtils.equals(value, cntryMap.get(key))) {
        log.debug("For key - {}: {} => {}", key, cntryMap.get(key), value);
        cntryMap.replace(key, value);
      }

      if (!cntryMap.containsKey(key)) {
        cntryMap.put(key, value);
      }
    }
  }

  private Map<String, String> putFromDWCountriesCSV() throws IOException {
    String readCsvFilePath = CountryNormalizer.class.getResource(DW_COUNTRIES_CSV).getPath();
    Reader reader = null;
    Map<String, String> tmp = new HashMap<>();

    reader = Files.newBufferedReader(Paths.get(readCsvFilePath));
    CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());

    for (CSVRecord csvRecord : csvParser) {
      // preValue.getName() is fullCountry
      CountryCodeEnum preValue = findFullCntrybyEbayId(Integer.valueOf(csvRecord.get(0)));
      if (preValue != null) {
        putTmpEntry(csvRecord.get(0), preValue.getName(), tmp);
        putTmpEntry(csvRecord.get(1), preValue.getName(), tmp);
        putTmpEntry(csvRecord.get(3), preValue.getName(), tmp);
        putTmpEntry(csvRecord.get(2), preValue.getName(), tmp);
      }
    }
    return tmp;
  }

  private Map<String, String> putFromXIDCountriesCSV() throws IOException {
    String readCsvFilePath = CountryNormalizer.class.getResource(XID_COUNTRY_MAPPING_CSV).getPath();
    Reader reader = null;
    Map<String, String> tmp = new HashMap<>();

    reader = Files.newBufferedReader(Paths.get(readCsvFilePath));
    CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());

    for (CSVRecord csvRecord : csvParser) {
      if (BuyerRiskCountryEnum.getCountry(csvRecord.get(1)) != null) {
        CountryCodeEnum preValue =
            findFullCntrybyEbayId(BuyerRiskCountryEnum.getCountry(csvRecord.get(1)).getId());
        if (preValue != null) {
          putTmpEntry(csvRecord.get(0), preValue.getName(), tmp);
          putTmpEntry(csvRecord.get(1), preValue.getName(), tmp);
        }
      }
    }
    return tmp;
  }

  private void putFromCsvFile() throws IOException {
    cntryMap.putAll(putFromDWCountriesCSV());
    putMap(putFromXIDCountriesCSV());
  }

  private static Map<String, String> genMapFromBuyerRiskCountryEnum() {
    Map<String, String> tmp = new HashMap<>();

    for (Object object : BuyerRiskCountryEnum.getList(BuyerRiskCountryEnum.class)) {
      BuyerRiskCountryEnum buyerRiskCountryEnum = (BuyerRiskCountryEnum) object;
      // preValue.getName() is fullCountry
      CountryCodeEnum preValue = findFullCntrybyEbayId(buyerRiskCountryEnum.getId());

      // name as key
      String name = buyerRiskCountryEnum.getName();
      // id as key
      String id = String.valueOf(buyerRiskCountryEnum.getId());
      // countrycode as key
      String cntryCode = buyerRiskCountryEnum.getCountryCode();

      // build map
      if (preValue != null) {
        putTmpEntry(name, preValue.getName(), tmp);
        putTmpEntry(id, preValue.getName(), tmp);
        putTmpEntry(cntryCode, preValue.getName(), tmp);
      }
    }
    return tmp;
  }

  private static Map<String, String> genMapFromPGWAddressCountryEnum() {
    Map<String, String> tmp = new HashMap<>();

    for (Object object : PGWAddressCountryEnum.getList(PGWAddressCountryEnum.class)) {
      PGWAddressCountryEnum addrCntryEnum = (PGWAddressCountryEnum) object;
      // preValue.getName() is fullCountry
      CountryCodeEnum preValue = findFullCntrybyEbayId(addrCntryEnum.getId());

      // dbValue as key
      String dbValue = addrCntryEnum.getDbValue();
      // name as key
      String addrCntryEnumName = addrCntryEnum.getName();
      // intValue as key
      String intValue = String.valueOf(addrCntryEnum.getId());

      // build map
      if (preValue != null) {
        putTmpEntry(dbValue, preValue.getName(), tmp);
        putTmpEntry(addrCntryEnumName, preValue.getName(), tmp);
        putTmpEntry(intValue, preValue.getName(), tmp);
      }
    }
    return tmp;
  }

  private static Map<String, String> genMapFromCountryCodeEnum() {
    Map<String, String> tmp = new HashMap<>();

    for (CountryCodeEnum cntryEnum : CountryCodeEnum.values()) {
      // cntryEnum.getName() is fullCntry
      // enumName as key
      String iso2 = cntryEnum.name();
      // LegacyCountryId as key
      String legacyCountryId = String.valueOf(cntryEnum.getLegacyCountryId());
      // Alpha3IsoCountryCode as key
      String alpha3Iso = cntryEnum.getAlpha3IsoCountryCode();
      // CountryName as key
      String name = cntryEnum.getName();

      // build map
      putTmpEntry(iso2, cntryEnum.getName(), tmp);
      putTmpEntry(legacyCountryId, cntryEnum.getName(), tmp);
      putTmpEntry(alpha3Iso, cntryEnum.getName(), tmp);
      putTmpEntry(name, cntryEnum.getName(), tmp);
    }
    return tmp;
  }

  private static Map<String, String> genMapFromHandMadeMapping() {
    Map<String, String> tmp = new HashMap<>();

    putTmpEntry("YU", "Macedonia", tmp);
    putTmpEntry("Islas Makvinas", "Falkland Islands", tmp);
    putTmpEntry("Ivory Coast", "Côte d'Ivoire", tmp);
    putTmpEntry("Cabo Verde", "Cape Verde", tmp);
    putTmpEntry("Réunion", findFullCntrybyEbayId(227).getName(), tmp);
    putTmpEntry("臺灣", "Taiwan", tmp);
    putTmpEntry("台灣", "Taiwan", tmp);
    putTmpEntry("中國", "China", tmp);
    putTmpEntry("香港", "Hong Kong", tmp);
    putTmpEntry("德國", "Germany", tmp);
    putTmpEntry("France métropolitaine", "France", tmp);
    putTmpEntry("Österreich", "Austria", tmp);
    putTmpEntry("España", "Spain", tmp);
    putTmpEntry("België", "Belgium", tmp);
    putTmpEntry("Ungarn", "Hungary", tmp);
    putTmpEntry("Dänemark", "Denmark", tmp);

    return tmp;
  }

  private void putFromEnum() {
    // insert BuyerRiskCountryEnum
    putMap(genMapFromBuyerRiskCountryEnum());
    // insert AddressCountryEnum
    putMap(genMapFromPGWAddressCountryEnum());
    // insert CountryCodeEnum
    putMap(genMapFromCountryCodeEnum());
    // insert some hand made key-value
    putMap(genMapFromHandMadeMapping());
  }

}
