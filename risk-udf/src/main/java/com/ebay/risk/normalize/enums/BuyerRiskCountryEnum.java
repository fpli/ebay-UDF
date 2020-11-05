package com.ebay.risk.normalize.enums;

import com.ebay.kernel.BaseEnum;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;

/**
 * Source code recreated from com.ebay.domain.core.rbiz.rbo.CountryEnum
 *  <!--      com.ebay.domain.core.rbiz.rbo.CountryEnum  -->
 *    <dependency>
 * 			<groupId>com.ebay.rm.tns</groupId>
 * 			<artifactId>RBO</artifactId>
 * 		  <version>5.2.42-SNAPSHOT</version>
 * 		</dependency>
 * */

public class BuyerRiskCountryEnum extends BaseEnum {

  private static final long serialVersionUID = 1L;

  public static final BuyerRiskCountryEnum UNITED_STATES = new BuyerRiskCountryEnum("United States", 1, "US");
  public static final BuyerRiskCountryEnum CANADA = new BuyerRiskCountryEnum("Canada", 2, "CA");
  public static final BuyerRiskCountryEnum UNITED_KINGDOM = new BuyerRiskCountryEnum("United Kingdom", 3, "GB");
  public static final BuyerRiskCountryEnum AFGHANISTAN = new BuyerRiskCountryEnum("Afghanistan", 4, "AF");
  public static final BuyerRiskCountryEnum ALBANIA = new BuyerRiskCountryEnum("Albania", 5, "AL");
  public static final BuyerRiskCountryEnum ALGERIA = new BuyerRiskCountryEnum("Algeria", 6, "DZ");
  public static final BuyerRiskCountryEnum AMERICAN_SAMOA = new BuyerRiskCountryEnum("American Samoa", 7, "AS");
  public static final BuyerRiskCountryEnum ANDORRA = new BuyerRiskCountryEnum("Andorra", 8, "AD");
  public static final BuyerRiskCountryEnum ANGOLA = new BuyerRiskCountryEnum("Angola", 9, "AO");
  public static final BuyerRiskCountryEnum ANGUILLA = new BuyerRiskCountryEnum("Anguilla", 10, "AI");
  public static final BuyerRiskCountryEnum ANTIGUA_AND_BARBUDA = new BuyerRiskCountryEnum("Antigua and Barbuda", 11,
      "AG");
  public static final BuyerRiskCountryEnum ARGENTINA = new BuyerRiskCountryEnum("Argentina", 12, "AR");
  public static final BuyerRiskCountryEnum ARMENIA = new BuyerRiskCountryEnum("Armenia", 13, "AM");
  public static final BuyerRiskCountryEnum ARUBA = new BuyerRiskCountryEnum("Aruba", 14, "AW");
  public static final BuyerRiskCountryEnum AUSTRALIA = new BuyerRiskCountryEnum("Australia", 15, "AU");
  public static final BuyerRiskCountryEnum AUSTRIA = new BuyerRiskCountryEnum("Austria", 16, "AT");
  public static final BuyerRiskCountryEnum AZERBAIJAN_REPUBLIC = new BuyerRiskCountryEnum("Azerbaijan Republic", 17,
      "AZ");
  public static final BuyerRiskCountryEnum BAHAMAS = new BuyerRiskCountryEnum("Bahamas", 18, "BS");
  public static final BuyerRiskCountryEnum BAHRAIN = new BuyerRiskCountryEnum("Bahrain", 19, "BH");
  public static final BuyerRiskCountryEnum BANGLADESH = new BuyerRiskCountryEnum("Bangladesh", 20, "BD");
  public static final BuyerRiskCountryEnum BARBADOS = new BuyerRiskCountryEnum("Barbados", 21, "BB");
  public static final BuyerRiskCountryEnum BELARUS = new BuyerRiskCountryEnum("Belarus", 22, "BY");
  public static final BuyerRiskCountryEnum BELGIUM = new BuyerRiskCountryEnum("Belgium", 23, "BE");
  public static final BuyerRiskCountryEnum BELIZE = new BuyerRiskCountryEnum("Belize", 24, "BZ");
  public static final BuyerRiskCountryEnum BENIN = new BuyerRiskCountryEnum("Benin", 25, "BJ");
  public static final BuyerRiskCountryEnum BERMUDA = new BuyerRiskCountryEnum("Bermuda", 26, "BM");
  public static final BuyerRiskCountryEnum BHUTAN = new BuyerRiskCountryEnum("Bhutan", 27, "BT");
  public static final BuyerRiskCountryEnum BOLIVIA = new BuyerRiskCountryEnum("Bolivia", 28, "BO");
  public static final BuyerRiskCountryEnum BOSNIA_AND_HERZEGOVINA = new BuyerRiskCountryEnum("Bosnia and Herzegovina",
      29, "BA");
  public static final BuyerRiskCountryEnum BOTSWANA = new BuyerRiskCountryEnum("Botswana", 30, "BW");
  public static final BuyerRiskCountryEnum BRAZIL = new BuyerRiskCountryEnum("Brazil", 31, "BR");
  public static final BuyerRiskCountryEnum BRITISH_VIRGIN_ISLANDS = new BuyerRiskCountryEnum("British Virgin Islands",
      32, "VG");
  public static final BuyerRiskCountryEnum BRUNEI_DARUSSALAM = new BuyerRiskCountryEnum("Brunei Darussalam", 33,
      "BN");
  public static final BuyerRiskCountryEnum BULGARIA = new BuyerRiskCountryEnum("Bulgaria", 34, "BG");
  public static final BuyerRiskCountryEnum BURKINA_FASO = new BuyerRiskCountryEnum("Burkina Faso", 35, "BF");
  public static final BuyerRiskCountryEnum BURMA = new BuyerRiskCountryEnum("Burma", 36,
      "Burma"); // no ISO Country code found
  public static final BuyerRiskCountryEnum BURUNDI = new BuyerRiskCountryEnum("Burundi", 37, "BI");
  public static final BuyerRiskCountryEnum CAMBODIA = new BuyerRiskCountryEnum("Cambodia", 38, "KH");
  public static final BuyerRiskCountryEnum CAMEROON = new BuyerRiskCountryEnum("Cameroon", 39, "CM");
  public static final BuyerRiskCountryEnum CAPE_VERDE_ISLANDS = new BuyerRiskCountryEnum("Cape Verde Islands", 40,
      "CV");
  public static final BuyerRiskCountryEnum CAYMAN_ISLANDS = new BuyerRiskCountryEnum("Cayman Islands", 41, "KY");
  public static final BuyerRiskCountryEnum CENTRAL_AFRICAN_REPUBLIC = new BuyerRiskCountryEnum(
      "Central African Republic", 42, "CF");
  public static final BuyerRiskCountryEnum CHAD = new BuyerRiskCountryEnum("Chad", 43, "TD");
  public static final BuyerRiskCountryEnum CHILE = new BuyerRiskCountryEnum("Chile", 44, "CL");
  public static final BuyerRiskCountryEnum CHINA = new BuyerRiskCountryEnum("China", 45, "CN");
  public static final BuyerRiskCountryEnum COLOMBIA = new BuyerRiskCountryEnum("Colombia", 46, "CO");
  public static final BuyerRiskCountryEnum COMOROS = new BuyerRiskCountryEnum("Comoros", 47, "KM");
  public static final BuyerRiskCountryEnum CONGO_DEMOCRATIC_REPUBLIC_OF_THE = new BuyerRiskCountryEnum(
      "Congo, Democratic Republic of the", 48, "CD");
  public static final BuyerRiskCountryEnum CONGO_REPUBLIC_OF_THE = new BuyerRiskCountryEnum("Congo, Republic of the",
      49, "CG");
  public static final BuyerRiskCountryEnum COOK_ISLANDS = new BuyerRiskCountryEnum("Cook Islands", 50, "CK");
  public static final BuyerRiskCountryEnum COSTA_RICA = new BuyerRiskCountryEnum("Costa Rica", 51, "CR");
  public static final BuyerRiskCountryEnum COTE_D_IVOIRE_IVORY_COAST = new BuyerRiskCountryEnum(
      "Cote d Ivoire (Ivory Coast)", 52, "CI");
  public static final BuyerRiskCountryEnum CROATIA_DEMOCRATIC_REPUBLIC_OF_THE = new BuyerRiskCountryEnum(
      "Croatia, Democratic Republic of the", 53, "HR");
  public static final BuyerRiskCountryEnum CUBA = new BuyerRiskCountryEnum("Cuba", 54, "CU");
  public static final BuyerRiskCountryEnum CYPRUS = new BuyerRiskCountryEnum("Cyprus", 55, "CY");
  public static final BuyerRiskCountryEnum CZECH_REPUBLIC = new BuyerRiskCountryEnum("Czech Republic", 56, "CZ");
  public static final BuyerRiskCountryEnum DENMARK = new BuyerRiskCountryEnum("Denmark", 57, "DK");
  public static final BuyerRiskCountryEnum DJIBOUTI = new BuyerRiskCountryEnum("Djibouti", 58, "DJ");
  public static final BuyerRiskCountryEnum DOMINICA = new BuyerRiskCountryEnum("Dominica", 59, "DM");
  public static final BuyerRiskCountryEnum DOMINICAN_REPUBLIC = new BuyerRiskCountryEnum("Dominican Republic", 60,
      "DO");
  public static final BuyerRiskCountryEnum ECUADOR = new BuyerRiskCountryEnum("Ecuador", 61, "EC");
  public static final BuyerRiskCountryEnum EGYPT = new BuyerRiskCountryEnum("Egypt", 62, "EG");
  public static final BuyerRiskCountryEnum EL_SALVADOR = new BuyerRiskCountryEnum("El Salvador", 63, "SV");
  public static final BuyerRiskCountryEnum EQUATORIAL_GUINEA = new BuyerRiskCountryEnum("Equatorial Guinea", 64,
      "GQ");
  public static final BuyerRiskCountryEnum ERITREA = new BuyerRiskCountryEnum("Eritrea", 65, "ER");
  public static final BuyerRiskCountryEnum ESTONIA = new BuyerRiskCountryEnum("Estonia", 66, "EE");
  public static final BuyerRiskCountryEnum ETHIOPIA = new BuyerRiskCountryEnum("Ethiopia", 67, "ET");
  public static final BuyerRiskCountryEnum FALKLAND_ISLANDS_ISLAS_MAKVINAS = new BuyerRiskCountryEnum(
      "Falkland Islands (Islas Makvinas)", 68, "FK");
  public static final BuyerRiskCountryEnum FIJI = new BuyerRiskCountryEnum("Fiji", 69, "FJ");
  public static final BuyerRiskCountryEnum FINLAND = new BuyerRiskCountryEnum("Finland", 70, "FI");
  public static final BuyerRiskCountryEnum FRANCE = new BuyerRiskCountryEnum("France", 71, "FR");
  public static final BuyerRiskCountryEnum FRENCH_GUIANA = new BuyerRiskCountryEnum("French Guiana", 72, "GF");
  public static final BuyerRiskCountryEnum FRENCH_POLYNESIA = new BuyerRiskCountryEnum("French Polynesia", 73, "PF");
  public static final BuyerRiskCountryEnum GABON_REPUBLIC = new BuyerRiskCountryEnum("Gabon Republic", 74, "GA");
  public static final BuyerRiskCountryEnum GAMBIA = new BuyerRiskCountryEnum("Gambia", 75, "GM");
  public static final BuyerRiskCountryEnum GEORGIA = new BuyerRiskCountryEnum("Georgia", 76, "GE");
  public static final BuyerRiskCountryEnum GERMANY = new BuyerRiskCountryEnum("Germany", 77, "DE");
  public static final BuyerRiskCountryEnum GHANA = new BuyerRiskCountryEnum("Ghana", 78, "GH");
  public static final BuyerRiskCountryEnum GIBRALTAR = new BuyerRiskCountryEnum("Gibraltar", 79, "GI");
  public static final BuyerRiskCountryEnum GREECE = new BuyerRiskCountryEnum("Greece", 80, "GR");
  public static final BuyerRiskCountryEnum GREENLAND = new BuyerRiskCountryEnum("Greenland", 81, "GL");
  public static final BuyerRiskCountryEnum GRENADA = new BuyerRiskCountryEnum("Grenada", 82, "GD");
  public static final BuyerRiskCountryEnum GUADELOUPE = new BuyerRiskCountryEnum("Guadeloupe", 83, "GP");
  public static final BuyerRiskCountryEnum GUAM = new BuyerRiskCountryEnum("Guam", 84, "GU");
  public static final BuyerRiskCountryEnum GUATEMALA = new BuyerRiskCountryEnum("Guatemala", 85, "GT");
  public static final BuyerRiskCountryEnum GUERNSEY = new BuyerRiskCountryEnum("Guernsey", 86, "GG");
  public static final BuyerRiskCountryEnum GUINEA = new BuyerRiskCountryEnum("Guinea", 87, "GN");
  public static final BuyerRiskCountryEnum GUINEA_BISSAU = new BuyerRiskCountryEnum("Guinea-Bissau", 88, "GW");
  public static final BuyerRiskCountryEnum GUYANA = new BuyerRiskCountryEnum("Guyana", 89, "GY");
  public static final BuyerRiskCountryEnum HAITI = new BuyerRiskCountryEnum("Haiti", 90, "HT");
  public static final BuyerRiskCountryEnum HONDURAS = new BuyerRiskCountryEnum("Honduras", 91, "HN");
  public static final BuyerRiskCountryEnum HONG_KONG = new BuyerRiskCountryEnum("Hong Kong", 92, "HK");
  public static final BuyerRiskCountryEnum HUNGARY = new BuyerRiskCountryEnum("Hungary", 93, "HU");
  public static final BuyerRiskCountryEnum ICELAND = new BuyerRiskCountryEnum("Iceland", 94, "IS");
  public static final BuyerRiskCountryEnum INDIA = new BuyerRiskCountryEnum("India", 95, "IN");
  public static final BuyerRiskCountryEnum INDONESIA = new BuyerRiskCountryEnum("Indonesia", 96, "ID");
  public static final BuyerRiskCountryEnum IRAN = new BuyerRiskCountryEnum("Iran", 97, "IR");
  public static final BuyerRiskCountryEnum IRAQ = new BuyerRiskCountryEnum("Iraq", 98, "IQ");
  public static final BuyerRiskCountryEnum IRELAND = new BuyerRiskCountryEnum("Ireland", 99, "IE");
  public static final BuyerRiskCountryEnum ISRAEL = new BuyerRiskCountryEnum("Israel", 100, "IL");
  public static final BuyerRiskCountryEnum ITALY = new BuyerRiskCountryEnum("Italy", 101, "IT");
  public static final BuyerRiskCountryEnum JAMAICA = new BuyerRiskCountryEnum("Jamaica", 102, "JM");
  public static final BuyerRiskCountryEnum JAN_MAYEN = new BuyerRiskCountryEnum("Jan Mayen", 103,
      "Jan Mayen"); // no ISO Country Code found
  public static final BuyerRiskCountryEnum JAPAN = new BuyerRiskCountryEnum("Japan", 104, "JP");
  public static final BuyerRiskCountryEnum JERSEY = new BuyerRiskCountryEnum("Jersey", 105, "JE");
  public static final BuyerRiskCountryEnum JORDAN = new BuyerRiskCountryEnum("Jordan", 106, "JO");
  public static final BuyerRiskCountryEnum KAZAKHSTAN = new BuyerRiskCountryEnum("Kazakhstan", 107, "KZ");
  public static final BuyerRiskCountryEnum KENYA_COAST_REPUBLIC = new BuyerRiskCountryEnum("Kenya Coast Republic",
      108, "KE");
  public static final BuyerRiskCountryEnum KIRIBATI = new BuyerRiskCountryEnum("Kiribati", 109, "KI");
  public static final BuyerRiskCountryEnum KOREA_NORTH = new BuyerRiskCountryEnum("Korea, North", 110, "KP");
  public static final BuyerRiskCountryEnum KOREA_SOUTH = new BuyerRiskCountryEnum("Korea, South", 111, "KR");
  public static final BuyerRiskCountryEnum KUWAIT = new BuyerRiskCountryEnum("Kuwait", 112, "KW");
  public static final BuyerRiskCountryEnum KYRGYZSTAN = new BuyerRiskCountryEnum("Kyrgyzstan", 113, "KG");
  public static final BuyerRiskCountryEnum LAOS = new BuyerRiskCountryEnum("Laos", 114, "LA	");
  public static final BuyerRiskCountryEnum LATVIA = new BuyerRiskCountryEnum("Latvia", 115, "LV");
  public static final BuyerRiskCountryEnum LEBANON_SOUTH = new BuyerRiskCountryEnum("Lebanon, South", 116, "LB");
  public static final BuyerRiskCountryEnum LESOTHO = new BuyerRiskCountryEnum("Lesotho", 117, "LS");
  public static final BuyerRiskCountryEnum LIBERIA = new BuyerRiskCountryEnum("Liberia", 118, "LR");
  public static final BuyerRiskCountryEnum LIBYA = new BuyerRiskCountryEnum("Libya", 119, "LY");
  public static final BuyerRiskCountryEnum LIECHTENSTEIN = new BuyerRiskCountryEnum("Liechtenstein", 120, "LI");
  public static final BuyerRiskCountryEnum LITHUANIA = new BuyerRiskCountryEnum("Lithuania", 121, "LT");
  public static final BuyerRiskCountryEnum LUXEMBOURG = new BuyerRiskCountryEnum("Luxembourg", 122, "LU");
  public static final BuyerRiskCountryEnum MACAU = new BuyerRiskCountryEnum("Macau", 123, "MO");
  public static final BuyerRiskCountryEnum MACEDONIA = new BuyerRiskCountryEnum("Macedonia", 124,
      "Macedonia"); // MK is for Yugoslavia
  public static final BuyerRiskCountryEnum MADAGASCAR = new BuyerRiskCountryEnum("Madagascar", 125, "MG");
  public static final BuyerRiskCountryEnum MALAWI = new BuyerRiskCountryEnum("Malawi", 126, "MW");
  public static final BuyerRiskCountryEnum MALAYSIA = new BuyerRiskCountryEnum("Malaysia", 127, "MY");
  public static final BuyerRiskCountryEnum MALDIVES = new BuyerRiskCountryEnum("Maldives", 128, "MV");
  public static final BuyerRiskCountryEnum MALI = new BuyerRiskCountryEnum("Mali", 129, "ML");
  public static final BuyerRiskCountryEnum MALTA = new BuyerRiskCountryEnum("Malta", 130, "MT");
  public static final BuyerRiskCountryEnum MARSHALL_ISLANDS = new BuyerRiskCountryEnum("Marshall Islands", 131, "MH");
  public static final BuyerRiskCountryEnum MARTINIQUE = new BuyerRiskCountryEnum("Martinique", 132, "MQ");
  public static final BuyerRiskCountryEnum MAURITANIA = new BuyerRiskCountryEnum("Mauritania", 133, "MR");
  public static final BuyerRiskCountryEnum MAURITIUS = new BuyerRiskCountryEnum("Mauritius", 134, "MU");
  public static final BuyerRiskCountryEnum MAYOTTE = new BuyerRiskCountryEnum("Mayotte", 135, "YT");
  public static final BuyerRiskCountryEnum MEXICO = new BuyerRiskCountryEnum("Mexico", 136, "MX");
  public static final BuyerRiskCountryEnum MOLDOVA = new BuyerRiskCountryEnum("Moldova", 137, "MD");
  public static final BuyerRiskCountryEnum MONACO = new BuyerRiskCountryEnum("Monaco", 138, "MC");
  public static final BuyerRiskCountryEnum MONGOLIA = new BuyerRiskCountryEnum("Mongolia", 139, "MN");
  public static final BuyerRiskCountryEnum MONTSERRAT = new BuyerRiskCountryEnum("Montserrat", 140, "MS");
  public static final BuyerRiskCountryEnum MOROCCO = new BuyerRiskCountryEnum("Morocco", 141, "MA");
  public static final BuyerRiskCountryEnum MOZAMBIQUE = new BuyerRiskCountryEnum("Mozambique", 142, "MZ");
  public static final BuyerRiskCountryEnum NAMIBIA = new BuyerRiskCountryEnum("Namibia", 143, "NA");
  public static final BuyerRiskCountryEnum NAURU = new BuyerRiskCountryEnum("Nauru", 144, "NR");
  public static final BuyerRiskCountryEnum NEPAL = new BuyerRiskCountryEnum("Nepal", 145, "NP");
  public static final BuyerRiskCountryEnum NETHERLANDS = new BuyerRiskCountryEnum("Netherlands", 146, "NL");
  public static final BuyerRiskCountryEnum NETHERLANDS_ANTILLES = new BuyerRiskCountryEnum("Netherlands Antilles",
      147, "Netherlands Antilles"); // Iso Country code not found
  public static final BuyerRiskCountryEnum NEW_CALEDONIA = new BuyerRiskCountryEnum("New Caledonia", 148, "NC");
  public static final BuyerRiskCountryEnum NEW_ZEALAND = new BuyerRiskCountryEnum("New Zealand", 149, "NZ");
  public static final BuyerRiskCountryEnum NICARAGUA = new BuyerRiskCountryEnum("Nicaragua", 150, "NI");
  public static final BuyerRiskCountryEnum NIGER = new BuyerRiskCountryEnum("Niger", 151, "NE");
  public static final BuyerRiskCountryEnum NIGERIA = new BuyerRiskCountryEnum("Nigeria", 152, "NG");
  public static final BuyerRiskCountryEnum NIUE = new BuyerRiskCountryEnum("Niue", 153, "NU");
  public static final BuyerRiskCountryEnum NORWAY = new BuyerRiskCountryEnum("Norway", 154, "NO");
  public static final BuyerRiskCountryEnum OMAN = new BuyerRiskCountryEnum("Oman", 155, "OM");
  public static final BuyerRiskCountryEnum PAKISTAN = new BuyerRiskCountryEnum("Pakistan", 156, "PK");
  public static final BuyerRiskCountryEnum PALAU = new BuyerRiskCountryEnum("Palau", 157, "PW");
  public static final BuyerRiskCountryEnum PANAMA = new BuyerRiskCountryEnum("Panama", 158, "PA");
  public static final BuyerRiskCountryEnum PAPUA_NEW_GUINEA = new BuyerRiskCountryEnum("Papua New Guinea", 159, "PG");
  public static final BuyerRiskCountryEnum PARAGUAY = new BuyerRiskCountryEnum("Paraguay", 160, "PY");
  public static final BuyerRiskCountryEnum PERU = new BuyerRiskCountryEnum("Peru", 161, "PE");
  public static final BuyerRiskCountryEnum PHILIPPINES = new BuyerRiskCountryEnum("Philippines", 162, "PH");
  public static final BuyerRiskCountryEnum POLAND = new BuyerRiskCountryEnum("Poland", 163, "PL");
  public static final BuyerRiskCountryEnum PORTUGAL = new BuyerRiskCountryEnum("Portugal", 164, "PT");
  public static final BuyerRiskCountryEnum PUERTO_RICO = new BuyerRiskCountryEnum("Puerto Rico", 165, "PR");
  public static final BuyerRiskCountryEnum QATAR = new BuyerRiskCountryEnum("Qatar", 166, "QA");
  public static final BuyerRiskCountryEnum ROMANIA = new BuyerRiskCountryEnum("Romania", 167, "RO");
  public static final BuyerRiskCountryEnum RUSSIAN_FEDERATION = new BuyerRiskCountryEnum("Russian Federation", 168,
      "RU");
  public static final BuyerRiskCountryEnum RWANDA = new BuyerRiskCountryEnum("Rwanda", 169, "RW");
  public static final BuyerRiskCountryEnum SAINT_HELENA = new BuyerRiskCountryEnum("Saint Helena", 170, "SH");
  public static final BuyerRiskCountryEnum SAINT_KITTS_NEVIS = new BuyerRiskCountryEnum("Saint Kitts-Nevis", 171,
      "KN");
  public static final BuyerRiskCountryEnum SAINT_LUCIA = new BuyerRiskCountryEnum("Saint Lucia", 172, "LC");
  public static final BuyerRiskCountryEnum SAINT_PIERRE_AND_MIQUELON = new BuyerRiskCountryEnum(
      "Saint Pierre and Miquelon", 173, "PM");
  public static final BuyerRiskCountryEnum SAINT_VINCENT_AND_THE_GRENADINES = new BuyerRiskCountryEnum(
      "Saint Vincent and the Grenadines", 174, "VC");
  public static final BuyerRiskCountryEnum SAN_MARINO = new BuyerRiskCountryEnum("San Marino", 175, "SM");
  public static final BuyerRiskCountryEnum SAUDI_ARABIA = new BuyerRiskCountryEnum("Saudi Arabia", 176, "SA");
  public static final BuyerRiskCountryEnum SENEGAL = new BuyerRiskCountryEnum("Senegal", 177, "SN");
  public static final BuyerRiskCountryEnum SEYCHELLES = new BuyerRiskCountryEnum("Seychelles", 178, "SC");
  public static final BuyerRiskCountryEnum SIERRA_LEONE = new BuyerRiskCountryEnum("Sierra Leone", 179, "SL");
  public static final BuyerRiskCountryEnum SINGAPORE = new BuyerRiskCountryEnum("Singapore", 180, "SG");
  public static final BuyerRiskCountryEnum SLOVAKIA = new BuyerRiskCountryEnum("Slovakia", 181, "SK");
  public static final BuyerRiskCountryEnum SLOVENIA = new BuyerRiskCountryEnum("Slovenia", 182, "SI");
  public static final BuyerRiskCountryEnum SOLOMON_ISLANDS = new BuyerRiskCountryEnum("Solomon Islands", 183, "SB");
  public static final BuyerRiskCountryEnum SOMALIA = new BuyerRiskCountryEnum("Somalia", 184, "SO");
  public static final BuyerRiskCountryEnum SOUTH_AFRICA = new BuyerRiskCountryEnum("South Africa", 185, "ZA");
  public static final BuyerRiskCountryEnum SPAIN = new BuyerRiskCountryEnum("Spain", 186, "ES");
  public static final BuyerRiskCountryEnum SRI_LANKA = new BuyerRiskCountryEnum("Sri Lanka", 187, "LK");
  public static final BuyerRiskCountryEnum SUDAN = new BuyerRiskCountryEnum("Sudan", 188, "SD");
  public static final BuyerRiskCountryEnum SURINAME = new BuyerRiskCountryEnum("Suriname", 189, "SR");
  public static final BuyerRiskCountryEnum SVALBARD = new BuyerRiskCountryEnum("Svalbard", 190, "SJ");
  public static final BuyerRiskCountryEnum SWAZILAND = new BuyerRiskCountryEnum("Swaziland", 191, "SZ");
  public static final BuyerRiskCountryEnum SWEDEN = new BuyerRiskCountryEnum("Sweden", 192, "SE");
  public static final BuyerRiskCountryEnum SWITZERLAND = new BuyerRiskCountryEnum("Switzerland", 193, "CH");
  public static final BuyerRiskCountryEnum SYRIA = new BuyerRiskCountryEnum("Syria", 194, "SY");
  public static final BuyerRiskCountryEnum TAHITI = new BuyerRiskCountryEnum("Tahiti", 195,
      "Tahiti"); // ISO Country code not found
  public static final BuyerRiskCountryEnum TAIWAN = new BuyerRiskCountryEnum("Taiwan", 196, "TW");
  public static final BuyerRiskCountryEnum TAJIKISTAN = new BuyerRiskCountryEnum("Tajikistan", 197, "TJ");
  public static final BuyerRiskCountryEnum TANZANIA = new BuyerRiskCountryEnum("Tanzania", 198, "TZ");
  public static final BuyerRiskCountryEnum THAILAND = new BuyerRiskCountryEnum("Thailand", 199, "TH");
  public static final BuyerRiskCountryEnum TOGO = new BuyerRiskCountryEnum("Togo", 200, "TG");
  public static final BuyerRiskCountryEnum TONGA = new BuyerRiskCountryEnum("Tonga", 201, "TO");
  public static final BuyerRiskCountryEnum TRINIDAD_AND_TOBAGO = new BuyerRiskCountryEnum("Trinidad and Tobago", 202,
      "TT");
  public static final BuyerRiskCountryEnum TUNISIA = new BuyerRiskCountryEnum("Tunisia", 203, "TN");
  public static final BuyerRiskCountryEnum TURKEY = new BuyerRiskCountryEnum("Turkey", 204, "TR");
  public static final BuyerRiskCountryEnum TURKMENISTAN = new BuyerRiskCountryEnum("Turkmenistan", 205, "TM");
  public static final BuyerRiskCountryEnum TURKS_AND_CAICOS_ISLANDS = new BuyerRiskCountryEnum(
      "Turks and Caicos Islands", 206, "TC");
  public static final BuyerRiskCountryEnum TUVALU = new BuyerRiskCountryEnum("Tuvalu", 207, "TV");
  public static final BuyerRiskCountryEnum UGANDA = new BuyerRiskCountryEnum("Uganda", 208, "UG");
  public static final BuyerRiskCountryEnum UKRAINE = new BuyerRiskCountryEnum("Ukraine", 209, "UA");
  public static final BuyerRiskCountryEnum UNITED_ARAB_EMIRATES = new BuyerRiskCountryEnum("United Arab Emirates",
      210, "AE");
  public static final BuyerRiskCountryEnum URUGUAY = new BuyerRiskCountryEnum("Uruguay", 211, "UY");
  public static final BuyerRiskCountryEnum UZBEKISTAN = new BuyerRiskCountryEnum("Uzbekistan", 212, "UZ");
  public static final BuyerRiskCountryEnum VANUATU = new BuyerRiskCountryEnum("Vanuatu", 213, "VU");
  public static final BuyerRiskCountryEnum VATICAN_CITY_STATE = new BuyerRiskCountryEnum("Vatican City State", 214,
      "VA");
  public static final BuyerRiskCountryEnum VENEZUELA = new BuyerRiskCountryEnum("Venezuela", 215, "VE");
  public static final BuyerRiskCountryEnum VIETNAM = new BuyerRiskCountryEnum("Vietnam", 216, "VN");
  public static final BuyerRiskCountryEnum VIRGIN_ISLANDS_US = new BuyerRiskCountryEnum("Virgin Islands (U.S.)", 217,
      "VI");
  public static final BuyerRiskCountryEnum WALLIS_AND_FUTUNA = new BuyerRiskCountryEnum("Wallis and Futuna", 218,
      "WF");
  public static final BuyerRiskCountryEnum WESTERN_SAHARA = new BuyerRiskCountryEnum("Western Sahara", 219, "EH");
  public static final BuyerRiskCountryEnum WESTERN_SAMOA = new BuyerRiskCountryEnum("Western Samoa", 220, "WS");
  public static final BuyerRiskCountryEnum YEMEN = new BuyerRiskCountryEnum("Yemen", 221, "YE");
  public static final BuyerRiskCountryEnum YUGOSLAVIA = new BuyerRiskCountryEnum("Yugoslavia", 222, "MK");
  public static final BuyerRiskCountryEnum ZAMBIA = new BuyerRiskCountryEnum("Zambia", 223, "ZM");
  public static final BuyerRiskCountryEnum ZIMBABWE = new BuyerRiskCountryEnum("Zimbabwe", 224, "ZW");
  public static final BuyerRiskCountryEnum APO_FPO = new BuyerRiskCountryEnum("APO/FPO", 225, "APO/FPO");
  public static final BuyerRiskCountryEnum MICRONESIA = new BuyerRiskCountryEnum("Micronesia", 226, "FM");
  public static final BuyerRiskCountryEnum REUNION = new BuyerRiskCountryEnum("Reunion", 227, "RE");
  public static final BuyerRiskCountryEnum MONTENEGRO = new BuyerRiskCountryEnum("Montenegro", 228, "ME");
  public static final BuyerRiskCountryEnum SERBIA = new BuyerRiskCountryEnum("Serbia", 229, "RS");
  public static final BuyerRiskCountryEnum GREAT_BRITAIN = new BuyerRiskCountryEnum("Great Britain", 230,
      "Great Britain");

  // special geo location codes returned by third-party (ThreatMetrix)
//  public static final BuyerRiskCountryEnum ANONYMOUS_PROXY = new BuyerRiskCountryEnum("A1", 1000, "A1");
//  public static final BuyerRiskCountryEnum SATELLITE_PROXY = new BuyerRiskCountryEnum("A2", 1001, "A2");
//  public static final BuyerRiskCountryEnum EUROPE = new BuyerRiskCountryEnum("EU", 1002, "EU");
//  public static final BuyerRiskCountryEnum OTHER_COUNTRY = new BuyerRiskCountryEnum("O1", 1003, "O1");
//  public static final BuyerRiskCountryEnum ASIA_PACIFIC_REGION = new BuyerRiskCountryEnum("AP", 1004, "AP");

  //	@EnumMetadata(VisibilityLevel=EnumMetadata.Visibility.HIDE)
  public static final BuyerRiskCountryEnum INVALID = new BuyerRiskCountryEnum("Invalid", 0, "Invalid");

  // ------- Type specific interfaces -------------------------------//

  /**
   * Get the enumeration instance for a given value or null
   */
  public static BuyerRiskCountryEnum get(int key) {
    return (BuyerRiskCountryEnum) getEnum(BuyerRiskCountryEnum.class, key);
  }

  /**
   * Get the enumeration instance for a given value or return the elseEnum default.
   */
  public static BuyerRiskCountryEnum getElseReturn(int key, BuyerRiskCountryEnum elseEnum) {
    return (BuyerRiskCountryEnum) getElseReturnEnum(BuyerRiskCountryEnum.class, key, elseEnum);
  }

  /**
   * Return an bidirectional iterator that traverses the enumeration instances in the order they
   * were defined.
   */
  @SuppressWarnings("rawtypes")
  public static ListIterator iterator() {
    return getIterator(BuyerRiskCountryEnum.class);
  }

  protected String m_iso3166CountryCode;

  public String getCountryCode() {
    return m_iso3166CountryCode;
  }

  protected BuyerRiskCountryEnum(String name, int id, String countryCode) {
    super(id, name);
    m_iso3166CountryCode = countryCode;
  }

  private static final Map<String, BuyerRiskCountryEnum> s_countryCodeMap =
      new HashMap<String, BuyerRiskCountryEnum>();

  static {
    for (@SuppressWarnings("rawtypes")
    Iterator it = iterator(); it.hasNext(); ) {
      BuyerRiskCountryEnum e = (BuyerRiskCountryEnum) it.next();
      s_countryCodeMap.put(e.getCountryCode(), e);
    }
  }

  public static BuyerRiskCountryEnum getCountry(String countryCode) {
    return s_countryCodeMap.get(countryCode);
  }
}
