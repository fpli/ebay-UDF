package com.ebay.cos.type.v3.base;

/*
 * Author: Cheng
 * Date: 12/6/2013
 * This enum processes a country designation based on a list of the ISO3166-1 country codes.
 * */

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonCreator;

import com.ebay.cos.raptor.service.annotations.ApiDescription;

/**
 * This defines the list of valid country codes, adapted from <a href="http://www.iso.org/iso/country_codes">ISO 3166-1 country code</a>.
 * List elements take the following form to identify a two-letter code with a short name in English, a three-digit code, and a three-letter code:
 * {@code alpha-2("English short name", "numeric", "alpha-3", "legacy Country Id")}.
 * For example, the entry for Japan follows:
 * <p>
 * {@code JP("Japan", "392", "JPN", 104)}
 * <p>
 * Short codes provide uniform recognition, avoiding language-dependent country names.
 * The number code is helpful where Latin script may be problematic.
 * Not all listed codes are universally recognized as countries, for example:
 * {@code AQ("Antarctica", "010", "ATA")}
 * <p>
 *
 */
@ApiDescription("This defines the list of valid country codes, adapted from http://www.iso.org/iso/country_codes, ISO 3166-1 country code. "
    + "List elements take the following form to identify a two-letter code with a short name in English, a three-digit code, and a three-letter code: "
    + "For example, the entry for Japan includes Japan, 392, JPN. Short codes provide uniform recognition, avoiding language-dependent country names. "
    + "The number code is helpful where Latin script may be problematic. Not all listed codes are universally recognized as countries, for example: "
    + "code AQ is Antarctica, 010, ATA")
@XmlRootElement
public enum CountryCodeEnum {

  AD("Andorra", "020", "AND",8), AE("United Arab Emirates", "784", "ARE", 210), AF(
      "Afghanistan", "004", "AFG",4), AG("Antigua and Barbuda", "028",
      "ATG",11), AI("Anguilla", "660", "AIA",10), AL("Albania", "008", "ALB",5), AM(
      "Armenia", "051", "ARM",13), AN("Netherlands Antilles", "530", "ANT", 147), AO(
      "Angola", "024", "AGO",9), AQ("Antarctica", "010", "ATA", 4000), AR(
      "Argentina", "032", "ARG",12), AS("American Samoa", "016", "ASM",7), AT(
      "Austria", "040", "AUT",16), AU("Australia", "036", "AUS",15), AW(
      "Aruba", "533", "ABW",14), AX("Åland Islands", "248", "ALA", 4001), AZ(
      "Azerbaijan", "031", "AZE",17), BA("Bosnia and Herzegovina", "070",
      "BIH",29), BB("Barbados", "052", "BRB",21), BD("Bangladesh", "050", "BGD",20), BE(
      "Belgium", "056", "BEL",23), BF("Burkina Faso", "854", "BFA", 35), BG(
      "Bulgaria", "100", "BGR", 34), BH("Bahrain", "048", "BHR",19), BI(
      "Burundi", "108", "BDI", 37), BJ("Benin", "204", "BEN",25), BL(
      "Saint Barthélemy", "652", "BLM", 4002), BM("Bermuda", "060", "BMU",26), BN(
      "Brunei", "096", "BRN", 33), BO("Bolivia", "068", "BOL",28), BQ(
      "Bonaire,Sint Eustatius and Saba", "535", "BES", 4003), BR("Brazil",
      "076", "BRA",31), BS("Bahamas", "044", "BHS",18), BT("Bhutan", "064",
      "BTN",27), BV("Bouvet Island", "074", "BVT", 4004), BW("Botswana", "072",
      "BWA",30), BY("Belarus", "112", "BLR",22), BZ("Belize", "084", "BLZ",24), CA(
      "Canada", "124", "CAN",2), CC("Cocos Islands", "166", "CCK", 4005), CD(
      "The Democratic Republic Of Congo", "180", "COD", 48), CF(
      "Central African Republic", "140", "CAF", 42), CG("Congo", "178", "COG", 49), CH(
      "Switzerland", "756", "CHE", 193), CI("Côte d'Ivoire", "384", "CIV", 52), CK(
      "Cook Islands", "184", "COK", 50), CL("Chile", "152", "CHL", 44), CM(
      "Cameroon", "120", "CMR", 39), CN("China", "156", "CHN", 45), CO(
      "Colombia", "170", "COL", 46), CR("Costa Rica", "188", "CRI", 51), CU(
      "Cuba", "192", "CUB", 54), CV("Cape Verde", "132", "CPV", 40), CW(
      "Curaçao", "531", "CUW", 4007), CX("Christmas Island", "162", "CXR",4008), CY(
      "Cyprus", "196", "CYP", 55), CZ("Czech Republic", "203", "CZE", 56), DE(
      "Germany", "276", "DEU", 77), DJ("Djibouti", "262", "DJI", 58), DK(
      "Denmark", "208", "DNK", 57), DM("Dominica", "212", "DMA", 59), DO(
      "Dominican Republic", "214", "DOM", 60), DZ("Algeria", "012", "DZA",6), EC(
      "Ecuador", "218", "ECU", 61), EE("Estonia", "233", "EST", 66), EG("Egypt",
      "818", "EGY", 62), EH("Western Sahara", "732", "ESH", 219), ER("Eritrea",
      "232", "ERI", 65), ES("Spain", "724", "ESP", 186), ET("Ethiopia", "231",
      "ETH", 67), FI("Finland", "246", "FIN", 70), FJ("Fiji", "242", "FJI", 69), FK(
      "Falkland Islands", "238", "FLK", 68), FM("Micronesia", "583", "FSM", 226), FO(
      "Faroe Islands", "234", "FRO", 4009), FR("France", "250", "FRA", 71), GA(
      "Gabon", "266", "GAB", 74), GB("United Kingdom", "826", "GBR", 3), GD(
      "Grenada", "308", "GRD", 82), GE("Georgia", "268", "GEO", 76), GF(
      "French Guiana", "254", "GUF", 72), GG("Guernsey", "831", "GGY", 86), GH(
      "Ghana", "288", "GHA", 78), GI("Gibraltar", "292", "GIB", 79), GL(
      "Greenland", "304", "GRL", 81), GM("Gambia", "270", "GMB", 75), GN(
      "Guinea", "324", "GIN", 87), GP("Guadeloupe", "312", "GLP", 83), GQ(
      "Equatorial Guinea", "226", "GNQ", 64), GR("Greece", "300", "GRC", 80), GS(
      "South Georgia And The South Sandwich Islands", "239", "SGS", 4010), GT(
      "Guatemala", "320", "GTM", 85), GU("Guam", "316", "GUM", 84), GW(
      "Guinea-Bissau", "624", "GNB", 88), GY("Guyana", "328", "GUY", 89), HK(
      "Hong Kong", "344", "HKG", 92), HM("Heard Island And McDonald Islands",
      "334", "HMD", 4011), HN("Honduras", "340", "HND", 91), HR("Croatia", "191",
      "HRV", 53), HT("Haiti", "332", "HTI", 90), HU("Hungary", "348", "HUN", 93), ID(
      "Indonesia", "360", "IDN", 96), IE("Ireland", "372", "IRL", 99), IL(
      "Israel", "376", "ISR", 100), IM("Isle Of Man", "833", "IMN", 4012), IN(
      "India", "356", "IND", 95), IO("British Indian Ocean Territory", "086",
      "IOT", 4013), IQ("Iraq", "368", "IRQ", 98), IR("Iran", "364", "IRN", 97), IS(
      "Iceland", "352", "ISL", 94), IT("Italy", "380", "ITA", 101), JE("Jersey",
      "832", "JEY", 105), JM("Jamaica", "388", "JAM", 102), JO("Jordan", "400",
      "JOR", 106), JP("Japan", "392", "JPN", 104), KE("Kenya", "404", "KEN", 108), KG(
      "Kyrgyzstan", "417", "KGZ", 113), KH("Cambodia", "116", "KHM", 38), KI(
      "Kiribati", "296", "KIR", 109), KM("Comoros", "174", "COM", 47), KN(
      "Saint Kitts And Nevis", "659", "KNA", 171), KP("North Korea", "408",
      "PRK", 110), KR("South Korea", "410", "KOR", 111), KW("Kuwait", "414", "KWT", 112), KY(
      "Cayman Islands", "136", "CYM", 41), KZ("Kazakhstan", "398", "KAZ", 107), LA(
      "Lao People's Democratic Republic", "418", "LAO", 114), LB("Lebanon",
      "422", "LBN", 116), LC("Saint Lucia", "662", "LCA", 172), LI("Liechtenstein",
      "438", "LIE", 120), LK("Sri Lanka", "144", "LKA", 187), LR("Liberia", "430",
      "LBR", 118), LS("Lesotho", "426", "LSO", 117), LT("Lithuania", "440", "LTU", 121), LU(
      "Luxembourg", "442", "LUX", 122), LV("Latvia", "428", "LVA", 115), LY(
      "Libya", "434", "LBY", 119), MA("Morocco", "504", "MAR", 141), MC("Monaco",
      "492", "MCO", 138), MD("Moldova", "498", "MDA", 137), ME("Montenegro", "499",
      "MNE", 228), MF("Saint Martin", "663", "MAF", 4014), MG("Madagascar", "450",
      "MDG", 125), MH("Marshall Islands", "584", "MHL", 131), MK("Macedonia",
      "807", "MKD", 124), ML("Mali", "466", "MLI", 129), MM("Myanmar", "104", "MMR", 36), MN(
      "Mongolia", "496", "MNG", 139), MO("Macao", "446", "MAC", 123), MP(
      "Northern Mariana Islands", "580", "MNP", 4016), MQ("Martinique", "474",
      "MTQ", 132), MR("Mauritania", "478", "MRT", 133), MS("Montserrat", "500",
      "MSR", 140), MT("Malta", "470", "MLT", 130), MU("Mauritius", "480", "MUS", 134), MV(
      "Maldives", "462", "MDV", 128), MW("Malawi", "454", "MWI", 126), MX("Mexico",
      "484", "MEX", 136), MY("Malaysia", "458", "MYS", 127), MZ("Mozambique",
      "508", "MOZ", 142), NA("Namibia", "516", "NAM", 143), NC("New Caledonia",
      "540", "NCL", 148), NE("Niger", "562", "NER", 151), NF("Norfolk Island",
      "574", "NFK", 4017), NG("Nigeria", "566", "NGA", 152), NI("Nicaragua", "558",
      "NIC", 150), NL("Netherlands", "528", "NLD", 146), NO("Norway", "578", "NOR", 154), NP(
      "Nepal", "524", "NPL", 145), NR("Nauru", "520", "NRU", 144), NU("Niue",
      "570", "NIU", 153), NZ("New Zealand", "554", "NZL", 149), OM("Oman", "512",
      "OMN", 155), PA("Panama", "591", "PAN", 158), PE("Peru", "604", "PER", 161), PF(
      "French Polynesia", "258", "PYF", 73), PG("Papua New Guinea", "598",
      "PNG", 159), PH("Philippines", "608", "PHL", 162), PK("Pakistan", "586",
      "PAK", 156), PL("Poland", "616", "POL", 163), PM("Saint Pierre And Miquelon",
      "666", "SPM", 173), PN("Pitcairn", "612", "PCN", 4018), PR("Puerto Rico",
      "630", "PRI", 165), PS("Palestine", "275", "PSE", 4019), PT("Portugal", "620",
      "PRT", 164), PW("Palau", "585", "PLW", 157), PY("Paraguay", "600", "PRY", 160), QA(
      "Qatar", "634", "QAT", 166), RE("Reunion", "638", "REU", 227), RO("Romania",
      "642", "ROU", 167), RS("Serbia", "688", "SRB", 229), RU("Russian Federation",
      "643", "RUS", 168), RW("Rwanda", "646", "RWA", 169), SA("Saudi Arabia",
      "682", "SAU", 176), SB("Solomon Islands", "090", "SLB", 183), SC(
      "Seychelles", "690", "SYC", 178), SD("Sudan", "729", "SDN", 188), SE(
      "Sweden", "752", "SWE", 192), SG("Singapore", "702", "SGP", 180), SH(
      "Saint Helena", "654", "SHN", 170), SI("Slovenia", "705", "SVN", 182), SJ(
      "Svalbard And Jan Mayen", "744", "SJM", 103), SK("Slovakia", "703",
      "SVK", 181), SL("Sierra Leone", "694", "SLE", 179), SM("San Marino", "674",
      "SMR", 175), SN("Senegal", "686", "SEN", 177), SO("Somalia", "706", "SOM", 184), SR(
      "Suriname", "740", "SUR", 189), ST("Sao Tome And Principe", "728", "STP", 4020), SV(
      "El Salvador", "678", "SLV", 63), SX("Sint Maarten (Dutch part)",
      "222", "SXM", 4021), SY("Syrian Arab Republic", "760", "SYR", 194), SZ(
      "Swaziland", "748", "SWZ", 191), TC("Turks And Caicos Islands", "796",
      "TCA", 206), TD("Chad", "148", "TCD", 43), TF("French Southern Territories",
      "148", "ATF", 4022), TG("Togo", "768", "TGO", 200), TH("Thailand", "764",
      "THA", 199), TJ("Tajikistan", "762", "TJK", 197), TK("Tokelau", "772", "TKL", 4023), TL(
      "Timor-Leste", "626", "TLS", 4024), TM("Turkmenistan", "795", "TKM", 205), TN(
      "Tunisia", "788", "TUN", 203), TO("Tonga", "776", "TON", 201), TR("Turkey",
      "792", "TUR", 204), TT("Trinidad and Tobago", "780", "TTO", 202), TV(
      "Tuvalu", "798", "TUV", 207), TW("Taiwan", "158", "TWN", 196), TZ("Tanzania",
      "834", "TZA", 198), UA("Ukraine", "804", "UKR", 209), UG("Uganda", "800",
      "UGA", 208), UM("United States Minor Outlying Islands", "581", "UMI", 4025), US(
      "United States", "840", "USA", 1), UY("Uruguay", "858", "URY", 211), UZ(
      "Uzbekistan", "860", "UZB", 212), VA("Vatican", "336", "VAT", 214), VC(
      "Saint Vincent And The Grenadines", "670", "VCT", 174), VE("Venezuela",
      "862", "VEN", 215), VG("British Virgin Islands", "092", "VGB", 32), VI(
      "U.S. Virgin Islands", "850", "VIR", 217), VN("Vietnam", "704", "VNM", 216), VU(
      "Vanuatu", "548", "VUT", 213), WF("Wallis And Futuna", "876", "WLF", 218), WS(
      "Samoa", "882", "WSM", 220), YE("Yemen", "887", "YEM", 221), YT("Mayotte",
      "175", "MYT", 135), ZA("South Africa", "710", "ZAF", 185), ZM("Zambia",
      "894", "ZMB", 223), ZW("Zimbabwe", "716", "ZWE", 224);


  /** The country's English short name. */
  @ApiDescription("The country's English short name.")
  private String name;

  /** The numeric country code, which must be 3 digits. */
  @ApiDescription("The numeric country code, which must be 3 digits.")
  private String countryId;

  /** The 3-character alphabetic country code. */
  @ApiDescription("The 3-character alphabetic country code.")
  private String Alpha3IsoCountryCode;

  /** The legacy ebay defined numeric country code. */
  @ApiDescription("The legacy ebay country id which is stored in database.")
  private int legacyCountryId;

  /**
   * Gets a country by numeric ISO 3166-1 country code.
   * For example, "124" represents Canada.
   * Codes contain three digits. Pad with leading zeroes if necessary.
   * For example, "020" represents Andorra.
   *
   * @param countryId  the numeric country code (must be 3 digits)
   */
  @ApiDescription("Gets a country by numeric ISO 3166-1 country code. For example, 124 represents Canada. Codes contain three digits. Pad with leading zeroes if necessary. "
      + "For example, 020 represents Andorra.")
  public static CountryCodeEnum  findByCountryId(String countryId){

    for(CountryCodeEnum countryEnum:CountryCodeEnum.values()){
      if(countryEnum.getCountryId().equals(countryId))
        return countryEnum;
    }
    return null;

  }

  /**
   * Gets a country by three-letter (alpha-3) ISO 3166-1 country code.
   * For example, CAN represents Canada.
   *
   * @param isoCode  the alpha-3 country code (must be 3 alphabetic characters)
   */
  @ApiDescription("Gets a country by three-letter (alpha-3) ISO 3166-1 country code. For example, CAN represents Canada.")
  public static CountryCodeEnum findByAlpha3IsoCountryCode(String isoCode) {

    for(CountryCodeEnum countryEnum:CountryCodeEnum.values()){
      if(countryEnum.getAlpha3IsoCountryCode().equals(isoCode))
        return countryEnum;
    }
    return null;
  }

  /**
   * Gets a country by ISO 3166-1 English short name.
   * For example United Kingdom represents the United Kingdom of Great Britain and Northern Ireland.
   * @param countryName  the country's English short name
   */
  @ApiDescription("Gets a country by ISO 3166-1 English short name. For example United Kingdom represents the United Kingdom of Great Britain and Northern Ireland.")
  public static CountryCodeEnum findByCountryName(String countryName) {

    for(CountryCodeEnum countryEnum:CountryCodeEnum.values()){
      if(countryEnum.getName().equals(countryName))
        return countryEnum;
    }
    return null;
  }

  /**
   * Gets the country's ISO 3166-1 three-character (alpha-3) country code.
   * @return Alpha3IsoCountryCode  the country's alpha-3 code (must be 3 alphabetic characters)
   */
  @ApiDescription("Gets the country's ISO 3166-1 three-character (alpha-3) country code.")
  public String getAlpha3IsoCountryCode() {
    return Alpha3IsoCountryCode;
  }

  /**
   * Sets the country's ISO 3166-1 three-character (alpha-3) country code.
   * @param alpha3IsoCountryCode  the country's alpha-3 code (must be 3 alphabetic characters)
   */
  public void setAlpha3IsoCountryCode(String alpha3IsoCountryCode) {
    Alpha3IsoCountryCode = alpha3IsoCountryCode;
  }

  /**
   * Gets the country's ISO 3166-1 English short name.
   * For example, "United States" or "Germany".
   * @return name  the country's English short name
   */
  @ApiDescription("Gets the country's ISO 3166-1 English short name. For example, United States or Germany.")
  public String getName() {
    return name;
  }

  /**
   * Sets the country's ISO 3166-1 English short name.
   * For example, "United States" or "Germany".
   *
   * @param name  the country's English short name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the country's ISO 3166-1 numeric country code.
   * For example, the United States' country code is "840".
   * @return countryId  the three-digit country code (must be 3 digit characters, padded with leading zeroes if necessary)
   */
  @ApiDescription("Gets the country's ISO 3166-1 numeric country code. For example, the United States' country code is 840.")
  public String getCountryId() {
    return countryId;
  }

  /**
   * Sets the country's ISO 3166-1 numeric country code.
   * For example, the United States country code is "840".
   * @param countryId  the three-digit country code (must be 3 digit characters)
   */
  public void setCountryId(String countryId) {
    this.countryId = countryId;
  }

  /**
   * Gets the legacy countryId which can be used to construct com.ebay.integ.account.common.CountryEnum.
   * Example Usage:
   * CountryCodeEnum countryCodeEnum = CountryCodeEnum.US
   * CountryEnum countryEnum = CountryEnum.get(countryCodeEnum.getLegacyCountryId());
   * The result is CountryEnum.US
   * In the above example countryEnum will be NULL for the below CountryCodeEnums
   * AQ("Antarctica", 4000), AX("Ã?land Islands", 4001), BL("Saint BarthÃ©lemy",
   4002), BQ("Bonaire,Sint Eustatius and Saba", 4003), BV(
   "Bouvet Island", 4004), CC("Cocos Islands", 4005), CW("CuraÃ§ao", 4007), CX(
   "Christmas Island", 4008), FO(
   "Faroe Islands", 4009), GS(
   "South Georgia And The South Sandwich Islands", 4010), HM(
   "Heard Island And McDonald Islands", 4011), IM("Isle Of Man", 4012), IO(
   "British Indian Ocean Territory", 4013), MF("Saint Martin", 4014), MP("Northern Mariana Islands", 4016), NF(
   "Norfolk Island", 4017), PN("Pitcairn", 4018), PS("Palestine", 4019), ST(
   "Sao Tome And Principe", 4020), SX("Sint Maarten (Dutch part)", 4021), TF(
   "French Southern Territories", 4022), TK("Tokelau", 4023), TL(
   "Timor-Leste", 4024), UM("United States Minor Outlying Islands",
   4025);
   * @return legacyCountryId
   */
  @ApiDescription("Gets the legacy countryId which can be used to construct com.ebay.integ.account.common.CountryEnum.")
  public int getLegacyCountryId() {
    return legacyCountryId;
  }

  public void setLegacyCountryId(int legacyCountryId) {
    this.legacyCountryId = legacyCountryId;
  }

  /**
   * Gets the CountryCodeEnum given legacy countryId.
   * Example Usage:
   * int legacyCountryId = CountryEnum.US.getId();
   * CountryCodeEnum countryCodeEnum = CountryCodeEnum.findByLegacyCountryId(legacyCountryId);
   * The result is CountryCodeEnum.US
   * In the above example CountryCodeEnum will be NULL for the below legacyCountryId
   * CountryEnum.YU=222 -- Yugoslavia
   * CountryEnum.APO/FPO=225
   */
  public static CountryCodeEnum  findByLegacyCountryId(int legacyCountryId){
    if(legacyCountryId == 230) { //Great Britain
      legacyCountryId = CountryCodeEnum.GB.getLegacyCountryId();
    } else if(legacyCountryId == 190) { //Svalbard
      legacyCountryId = CountryCodeEnum.SJ.getLegacyCountryId();
    } else if(legacyCountryId == 195) { //Tahiti
      legacyCountryId = CountryCodeEnum.PF.getLegacyCountryId();
    }  else if(legacyCountryId == 225) { //APO/FPO
      legacyCountryId = CountryCodeEnum.US.getLegacyCountryId();
    }

    for(CountryCodeEnum countryEnum:CountryCodeEnum.values()){
      if(countryEnum.getLegacyCountryId() == legacyCountryId)
        return countryEnum;
    }
    return null;

  }

  /**
   * Populates the country-code enum.
   *
   * @param name  the ISO 3166-1 <em>English short name</em> (such as "United States")
   * @param countryId  the ISO 3166-1 three-digit <em>numeric country code</em> (such as "840")
   * @param Alpha3IsoCountryCode  the ISO 3166-1 alpha-3 country code (such as "USA")
   */

  private CountryCodeEnum(String name, String countryId, String Alpha3IsoCountryCode, int legacyCountryId) {
    this.name = name;
    this.countryId = countryId;
    this.Alpha3IsoCountryCode = Alpha3IsoCountryCode;
    this.legacyCountryId = legacyCountryId;
  }

  @JsonCreator
  public static CountryCodeEnum fromValue(String str) {
    if (str != null && str.trim().length() > 0) {
      try {
        return CountryCodeEnum.valueOf(str);
      } catch (IllegalArgumentException e) {
        return null;
      }
    }
    return null;
  }

}
