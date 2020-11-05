package com.ebay.risk.normalize;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CountryNormalizerTest {

  public void assertCntry(String rawCntry, String fullCntry) {
    assertEquals(fullCntry, countryNormalizer.normalize(rawCntry));
  }

  CountryNormalizer countryNormalizer = CountryNormalizer.getInstance();

  @Test
  public void test() {

    assertCntry(null, null);
    assertCntry("", null);
    assertCntry("  ", null);
    assertCntry("usa,", null);

    assertCntry("usa", "United States");
    assertCntry("USA", "United States");
    assertCntry("UsA", "United States");
    assertCntry(" UsA ", "United States");
    assertCntry("UniTed States", "United States");

    // test address country enum
    assertCntry("APO/FPO", "United States");
    assertCntry("XPO", "United States");

    // test country code enum
    assertCntry("190", "Svalbard And Jan Mayen");
    assertCntry("195", "French Polynesia");
    assertCntry("225", "United States");
    assertCntry("230", "United Kingdom");
    assertCntry("4023", "Tokelau");
    assertCntry("4009", "Faroe Islands");

    assertCntry("na", "Namibia");
    assertCntry("nam", "Namibia");
    assertCntry("uk", "United Kingdom");
    assertCntry("gb", "United Kingdom");

    // test invalid
    assertCntry("xx", null);
    assertCntry("0", null);
    assertCntry("-999", null);
    assertCntry("-997", null);
    assertCntry("-888", null);
    assertCntry("-1", null);
    assertCntry("231", null);

    //test dw map
    assertCntry("臺灣", "Taiwan");
    assertCntry("台灣", "Taiwan");
    assertCntry("中國", "China");
    assertCntry("香港", "Hong Kong");
    assertCntry("德國", "Germany");
    assertCntry("France métropolitaine", "France");
    assertCntry("Österreich", "Austria");
    assertCntry("España", "Spain");
    assertCntry("België", "Belgium");
    assertCntry("Ungarn", "Hungary");
    assertCntry("Dänemark", "Denmark");

    //test buyer risk cntry enum
    assertCntry("Burma", "Myanmar");
    assertCntry("Congo, Democratic Republic of the", "The Democratic Republic Of Congo");
    assertCntry("Congo, Republic of the", "Congo");
    assertCntry("Ivory Coast", "Côte d'Ivoire");
    assertCntry("Kenya Coast Republic", "Kenya");
    assertCntry("Korea, North", "North Korea");
    assertCntry("Lebanon, South", "Lebanon");
    assertCntry("Macau", "Macao");
    assertCntry("GREAT BRITAIN", "United Kingdom");
    assertCntry("Laos", "Lao People's Democratic Republic");
    assertCntry("Falkland Islands (Islas Makvinas)", "Falkland Islands");
    assertCntry("Croatia, Democratic Republic of the", "Croatia");
    assertCntry("Islas Makvinas", "Falkland Islands");
    assertCntry("222", "Macedonia");
    assertCntry("Yugoslavia", "Macedonia");
    assertCntry("Yu", "Macedonia");


  }
}
