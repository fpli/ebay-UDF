package com.ebay.risk.normalize.enums;

import com.ebay.kernel.BaseEnum;
import com.ebay.kernel.util.JdkUtil;
import java.util.List;
/**
 *  Source code recreated from com.ebay.pgw.data.constants.AddresscCountryEnum
 *  <!--      com.ebay.pgw.data.constants.AddressCountryEnum  -->
 *     <dependency>
 *       <groupId>com.ebay.pgw</groupId>
 *       <artifactId>PGWSecureCommon</artifactId>
 *       <version>8.2.37-RELEASE</version>
 *       <exclusions>
 *         <exclusion>
 *           <artifactId>Base</artifactId>
 *           <groupId>com.ebay.cos.type.v3</groupId>
 *         </exclusion>
 *       </exclusions>
 *     </dependency>*/


public class PGWAddressCountryEnum extends BaseEnum {

  public static final PGWAddressCountryEnum NONE = new PGWAddressCountryEnum("NONE", 0, "XX");
  public static final PGWAddressCountryEnum US = new PGWAddressCountryEnum("US", 1, "US");
  public static final PGWAddressCountryEnum CA = new PGWAddressCountryEnum("CA", 2, "CA");
  public static final PGWAddressCountryEnum UK = new PGWAddressCountryEnum("UK", 3, "UK");
  public static final PGWAddressCountryEnum AF = new PGWAddressCountryEnum("AF", 4, "AF");
  public static final PGWAddressCountryEnum AL = new PGWAddressCountryEnum("AL", 5, "AL");
  public static final PGWAddressCountryEnum DZ = new PGWAddressCountryEnum("DZ", 6, "DZ");
  public static final PGWAddressCountryEnum AS = new PGWAddressCountryEnum("AS", 7, "AS");
  public static final PGWAddressCountryEnum AD = new PGWAddressCountryEnum("AD", 8, "AD");
  public static final PGWAddressCountryEnum AO = new PGWAddressCountryEnum("AO", 9, "AO");
  public static final PGWAddressCountryEnum AI = new PGWAddressCountryEnum("AI", 10, "AI");
  public static final PGWAddressCountryEnum AG = new PGWAddressCountryEnum("AG", 11, "AG");
  public static final PGWAddressCountryEnum AR = new PGWAddressCountryEnum("AR", 12, "AR");
  public static final PGWAddressCountryEnum AM = new PGWAddressCountryEnum("AM", 13, "AM");
  public static final PGWAddressCountryEnum AW = new PGWAddressCountryEnum("AW", 14, "AW");
  public static final PGWAddressCountryEnum AU = new PGWAddressCountryEnum("AU", 15, "AU");
  public static final PGWAddressCountryEnum AT = new PGWAddressCountryEnum("AT", 16, "AT");
  public static final PGWAddressCountryEnum AZ = new PGWAddressCountryEnum("AZ", 17, "AZ");
  public static final PGWAddressCountryEnum BS = new PGWAddressCountryEnum("BS", 18, "BS");
  public static final PGWAddressCountryEnum BH = new PGWAddressCountryEnum("BH", 19, "BH");
  public static final PGWAddressCountryEnum BD = new PGWAddressCountryEnum("BD", 20, "BD");
  public static final PGWAddressCountryEnum BB = new PGWAddressCountryEnum("BB", 21, "BB");
  public static final PGWAddressCountryEnum BY = new PGWAddressCountryEnum("BY", 22, "BY");
  public static final PGWAddressCountryEnum BE = new PGWAddressCountryEnum("BE", 23, "BE");
  public static final PGWAddressCountryEnum BZ = new PGWAddressCountryEnum("BZ", 24, "BZ");
  public static final PGWAddressCountryEnum BJ = new PGWAddressCountryEnum("BJ", 25, "BJ");
  public static final PGWAddressCountryEnum BM = new PGWAddressCountryEnum("BM", 26, "BM");
  public static final PGWAddressCountryEnum BT = new PGWAddressCountryEnum("BT", 27, "BT");
  public static final PGWAddressCountryEnum BO = new PGWAddressCountryEnum("BO", 28, "BO");
  public static final PGWAddressCountryEnum BA = new PGWAddressCountryEnum("BA", 29, "BA");
  public static final PGWAddressCountryEnum BW = new PGWAddressCountryEnum("BW", 30, "BW");
  public static final PGWAddressCountryEnum BR = new PGWAddressCountryEnum("BR", 31, "BR");
  public static final PGWAddressCountryEnum VG = new PGWAddressCountryEnum("VG", 32, "VG");
  public static final PGWAddressCountryEnum BN = new PGWAddressCountryEnum("BN", 33, "BN");
  public static final PGWAddressCountryEnum BG = new PGWAddressCountryEnum("BG", 34, "BG");
  public static final PGWAddressCountryEnum BF = new PGWAddressCountryEnum("BF", 35, "BF");
  public static final PGWAddressCountryEnum BU = new PGWAddressCountryEnum("BU", 36, "BU");
  public static final PGWAddressCountryEnum BI = new PGWAddressCountryEnum("BI", 37, "BI");
  public static final PGWAddressCountryEnum KH = new PGWAddressCountryEnum("KH", 38, "KH");
  public static final PGWAddressCountryEnum CM = new PGWAddressCountryEnum("CM", 39, "CM");
  public static final PGWAddressCountryEnum CV = new PGWAddressCountryEnum("CV", 40, "CV");
  public static final PGWAddressCountryEnum KY = new PGWAddressCountryEnum("KY", 41, "KY");
  public static final PGWAddressCountryEnum CF = new PGWAddressCountryEnum("CF", 42, "CF");
  public static final PGWAddressCountryEnum TD = new PGWAddressCountryEnum("TD", 43, "TD");
  public static final PGWAddressCountryEnum CL = new PGWAddressCountryEnum("CL", 44, "CL");
  public static final PGWAddressCountryEnum CN = new PGWAddressCountryEnum("CN", 45, "CN");
  public static final PGWAddressCountryEnum CO = new PGWAddressCountryEnum("CO", 46, "CO");
  public static final PGWAddressCountryEnum KM = new PGWAddressCountryEnum("KM", 47, "KM");
  public static final PGWAddressCountryEnum ZR = new PGWAddressCountryEnum("ZR", 48, "ZR");
  public static final PGWAddressCountryEnum CG = new PGWAddressCountryEnum("CG", 49, "CG");
  public static final PGWAddressCountryEnum CK = new PGWAddressCountryEnum("CK", 50, "CK");
  public static final PGWAddressCountryEnum CR = new PGWAddressCountryEnum("CR", 51, "CR");
  public static final PGWAddressCountryEnum CI = new PGWAddressCountryEnum("CI", 52, "CI");
  public static final PGWAddressCountryEnum HR = new PGWAddressCountryEnum("HR", 53, "HR");
  public static final PGWAddressCountryEnum CU = new PGWAddressCountryEnum("CU", 54, "CU");
  public static final PGWAddressCountryEnum CY = new PGWAddressCountryEnum("CY", 55, "CY");
  public static final PGWAddressCountryEnum CZ = new PGWAddressCountryEnum("CZ", 56, "CZ");
  public static final PGWAddressCountryEnum DK = new PGWAddressCountryEnum("DK", 57, "DK");
  public static final PGWAddressCountryEnum DJ = new PGWAddressCountryEnum("DJ", 58, "DJ");
  public static final PGWAddressCountryEnum DM = new PGWAddressCountryEnum("DM", 59, "DM");
  public static final PGWAddressCountryEnum DO = new PGWAddressCountryEnum("DO", 60, "DO");
  public static final PGWAddressCountryEnum EC = new PGWAddressCountryEnum("EC", 61, "EC");
  public static final PGWAddressCountryEnum EG = new PGWAddressCountryEnum("EG", 62, "EG");
  public static final PGWAddressCountryEnum SV = new PGWAddressCountryEnum("SV", 63, "SV");
  public static final PGWAddressCountryEnum GQ = new PGWAddressCountryEnum("GQ", 64, "GQ");
  public static final PGWAddressCountryEnum QQ = new PGWAddressCountryEnum("QQ", 65, "QQ");
  public static final PGWAddressCountryEnum EE = new PGWAddressCountryEnum("EE", 66, "EE");
  public static final PGWAddressCountryEnum ET = new PGWAddressCountryEnum("ET", 67, "ET");
  public static final PGWAddressCountryEnum FK = new PGWAddressCountryEnum("FK", 68, "FK");
  public static final PGWAddressCountryEnum FJ = new PGWAddressCountryEnum("FJ", 69, "FJ");
  public static final PGWAddressCountryEnum FI = new PGWAddressCountryEnum("FI", 70, "FI");
  public static final PGWAddressCountryEnum FR = new PGWAddressCountryEnum("FR", 71, "FR");
  public static final PGWAddressCountryEnum GF = new PGWAddressCountryEnum("GF", 72, "GF");
  public static final PGWAddressCountryEnum PF = new PGWAddressCountryEnum("PF", 73, "PF");
  public static final PGWAddressCountryEnum GA = new PGWAddressCountryEnum("GA", 74, "GA");
  public static final PGWAddressCountryEnum GM = new PGWAddressCountryEnum("GM", 75, "GM");
  public static final PGWAddressCountryEnum GE = new PGWAddressCountryEnum("GE", 76, "GE");
  public static final PGWAddressCountryEnum DE = new PGWAddressCountryEnum("DE", 77, "DE");
  public static final PGWAddressCountryEnum GH = new PGWAddressCountryEnum("GH", 78, "GH");
  public static final PGWAddressCountryEnum GI = new PGWAddressCountryEnum("GI", 79, "GI");
  public static final PGWAddressCountryEnum GR = new PGWAddressCountryEnum("GR", 80, "GR");
  public static final PGWAddressCountryEnum GL = new PGWAddressCountryEnum("GL", 81, "GL");
  public static final PGWAddressCountryEnum GD = new PGWAddressCountryEnum("GD", 82, "GD");
  public static final PGWAddressCountryEnum GP = new PGWAddressCountryEnum("GP", 83, "GP");
  public static final PGWAddressCountryEnum GU = new PGWAddressCountryEnum("GU", 84, "GU");
  public static final PGWAddressCountryEnum GT = new PGWAddressCountryEnum("GT", 85, "GT");
  public static final PGWAddressCountryEnum GS = new PGWAddressCountryEnum("GS", 86, "GS");
  public static final PGWAddressCountryEnum GN = new PGWAddressCountryEnum("GN", 87, "GN");
  public static final PGWAddressCountryEnum GW = new PGWAddressCountryEnum("GW", 88, "GW");
  public static final PGWAddressCountryEnum GY = new PGWAddressCountryEnum("GY", 89, "GY");
  public static final PGWAddressCountryEnum HT = new PGWAddressCountryEnum("HT", 90, "HT");
  public static final PGWAddressCountryEnum HN = new PGWAddressCountryEnum("HN", 91, "HN");
  public static final PGWAddressCountryEnum HK = new PGWAddressCountryEnum("HK", 92, "HK");
  public static final PGWAddressCountryEnum HU = new PGWAddressCountryEnum("HU", 93, "HU");
  public static final PGWAddressCountryEnum IS = new PGWAddressCountryEnum("IS", 94, "IS");
  public static final PGWAddressCountryEnum IN = new PGWAddressCountryEnum("IN", 95, "IN");
  public static final PGWAddressCountryEnum ID = new PGWAddressCountryEnum("ID", 96, "ID");
  public static final PGWAddressCountryEnum IR = new PGWAddressCountryEnum("IR", 97, "IR");
  public static final PGWAddressCountryEnum IQ = new PGWAddressCountryEnum("IQ", 98, "IQ");
  public static final PGWAddressCountryEnum IE = new PGWAddressCountryEnum("IE", 99, "IE");
  public static final PGWAddressCountryEnum IL = new PGWAddressCountryEnum("IL", 100, "IL");
  public static final PGWAddressCountryEnum IT = new PGWAddressCountryEnum("IT", 101, "IT");
  public static final PGWAddressCountryEnum JM = new PGWAddressCountryEnum("JM", 102, "JM");
  public static final PGWAddressCountryEnum SJ = new PGWAddressCountryEnum("SJ", 103, "SJ");
  public static final PGWAddressCountryEnum JP = new PGWAddressCountryEnum("JP", 104, "JP");
  public static final PGWAddressCountryEnum JS = new PGWAddressCountryEnum("JS", 105, "JS");
  public static final PGWAddressCountryEnum JO = new PGWAddressCountryEnum("JO", 106, "JO");
  public static final PGWAddressCountryEnum KZ = new PGWAddressCountryEnum("KZ", 107, "KZ");
  public static final PGWAddressCountryEnum KE = new PGWAddressCountryEnum("KE", 108, "KE");
  public static final PGWAddressCountryEnum KI = new PGWAddressCountryEnum("KI", 109, "KI");
  public static final PGWAddressCountryEnum KP = new PGWAddressCountryEnum("KP", 110, "KP");
  public static final PGWAddressCountryEnum KR = new PGWAddressCountryEnum("KR", 111, "KR");
  public static final PGWAddressCountryEnum KW = new PGWAddressCountryEnum("KW", 112, "KW");
  public static final PGWAddressCountryEnum KG = new PGWAddressCountryEnum("KG", 113, "KG");
  public static final PGWAddressCountryEnum LA = new PGWAddressCountryEnum("LA", 114, "LA");
  public static final PGWAddressCountryEnum LV = new PGWAddressCountryEnum("LV", 115, "LV");
  public static final PGWAddressCountryEnum LB = new PGWAddressCountryEnum("LB", 116, "LB");
  public static final PGWAddressCountryEnum LS = new PGWAddressCountryEnum("LS", 117, "LS");
  public static final PGWAddressCountryEnum LR = new PGWAddressCountryEnum("LR", 118, "LR");
  public static final PGWAddressCountryEnum LY = new PGWAddressCountryEnum("LY", 119, "LY");
  public static final PGWAddressCountryEnum LI = new PGWAddressCountryEnum("LI", 120, "LI");
  public static final PGWAddressCountryEnum LT = new PGWAddressCountryEnum("LT", 121, "LT");
  public static final PGWAddressCountryEnum LU = new PGWAddressCountryEnum("LU", 122, "LU");
  public static final PGWAddressCountryEnum MO = new PGWAddressCountryEnum("MO", 123, "MO");
  public static final PGWAddressCountryEnum MK = new PGWAddressCountryEnum("MK", 124, "MK");
  public static final PGWAddressCountryEnum MG = new PGWAddressCountryEnum("MG", 125, "MG");
  public static final PGWAddressCountryEnum MW = new PGWAddressCountryEnum("MW", 126, "MW");
  public static final PGWAddressCountryEnum MY = new PGWAddressCountryEnum("MY", 127, "MY");
  public static final PGWAddressCountryEnum MV = new PGWAddressCountryEnum("MV", 128, "MV");
  public static final PGWAddressCountryEnum ML = new PGWAddressCountryEnum("ML", 129, "ML");
  public static final PGWAddressCountryEnum MT = new PGWAddressCountryEnum("MT", 130, "MT");
  public static final PGWAddressCountryEnum MH = new PGWAddressCountryEnum("MH", 131, "MH");
  public static final PGWAddressCountryEnum MQ = new PGWAddressCountryEnum("MQ", 132, "MQ");
  public static final PGWAddressCountryEnum MR = new PGWAddressCountryEnum("MR", 133, "MR");
  public static final PGWAddressCountryEnum MU = new PGWAddressCountryEnum("MU", 134, "MU");
  public static final PGWAddressCountryEnum YT = new PGWAddressCountryEnum("YT", 135, "YT");
  public static final PGWAddressCountryEnum MX = new PGWAddressCountryEnum("MX", 136, "MX");
  public static final PGWAddressCountryEnum MD = new PGWAddressCountryEnum("MD", 137, "MD");
  public static final PGWAddressCountryEnum MC = new PGWAddressCountryEnum("MC", 138, "MC");
  public static final PGWAddressCountryEnum MN = new PGWAddressCountryEnum("MN", 139, "MN");
  public static final PGWAddressCountryEnum MS = new PGWAddressCountryEnum("MS", 140, "MS");
  public static final PGWAddressCountryEnum MA = new PGWAddressCountryEnum("MA", 141, "MA");
  public static final PGWAddressCountryEnum MZ = new PGWAddressCountryEnum("MZ", 142, "MZ");
  public static final PGWAddressCountryEnum NA = new PGWAddressCountryEnum("NA", 143, "NA");
  public static final PGWAddressCountryEnum NR = new PGWAddressCountryEnum("NR", 144, "NR");
  public static final PGWAddressCountryEnum NP = new PGWAddressCountryEnum("NP", 145, "NP");
  public static final PGWAddressCountryEnum NL = new PGWAddressCountryEnum("NL", 146, "NL");
  public static final PGWAddressCountryEnum AN = new PGWAddressCountryEnum("AN", 147, "AN");
  public static final PGWAddressCountryEnum NC = new PGWAddressCountryEnum("NC", 148, "NC");
  public static final PGWAddressCountryEnum NZ = new PGWAddressCountryEnum("NZ", 149, "NZ");
  public static final PGWAddressCountryEnum NI = new PGWAddressCountryEnum("NI", 150, "NI");
  public static final PGWAddressCountryEnum NE = new PGWAddressCountryEnum("NE", 151, "NE");
  public static final PGWAddressCountryEnum NG = new PGWAddressCountryEnum("NG", 152, "NG");
  public static final PGWAddressCountryEnum NU = new PGWAddressCountryEnum("NU", 153, "NU");
  public static final PGWAddressCountryEnum NO = new PGWAddressCountryEnum("NO", 154, "NO");
  public static final PGWAddressCountryEnum OM = new PGWAddressCountryEnum("OM", 155, "OM");
  public static final PGWAddressCountryEnum PK = new PGWAddressCountryEnum("PK", 156, "PK");
  public static final PGWAddressCountryEnum PW = new PGWAddressCountryEnum("PW", 157, "PW");
  public static final PGWAddressCountryEnum PA = new PGWAddressCountryEnum("PA", 158, "PA");
  public static final PGWAddressCountryEnum PG = new PGWAddressCountryEnum("PG", 159, "PG");
  public static final PGWAddressCountryEnum PY = new PGWAddressCountryEnum("PY", 160, "PY");
  public static final PGWAddressCountryEnum PE = new PGWAddressCountryEnum("PE", 161, "PE");
  public static final PGWAddressCountryEnum PH = new PGWAddressCountryEnum("PH", 162, "PH");
  public static final PGWAddressCountryEnum PL = new PGWAddressCountryEnum("PL", 163, "PL");
  public static final PGWAddressCountryEnum PT = new PGWAddressCountryEnum("PT", 164, "PT");
  public static final PGWAddressCountryEnum PR = new PGWAddressCountryEnum("PR", 165, "PR");
  public static final PGWAddressCountryEnum QA = new PGWAddressCountryEnum("QA", 166, "QA");
  public static final PGWAddressCountryEnum RO = new PGWAddressCountryEnum("RO", 167, "RO");
  public static final PGWAddressCountryEnum RU = new PGWAddressCountryEnum("RU", 168, "RU");
  public static final PGWAddressCountryEnum RW = new PGWAddressCountryEnum("RW", 169, "RW");
  public static final PGWAddressCountryEnum SH = new PGWAddressCountryEnum("SH", 170, "SH");
  public static final PGWAddressCountryEnum ST = new PGWAddressCountryEnum("ST", 171, "ST");
  public static final PGWAddressCountryEnum LC = new PGWAddressCountryEnum("LC", 172, "LC");
  public static final PGWAddressCountryEnum PM = new PGWAddressCountryEnum("PM", 173, "PM");
  public static final PGWAddressCountryEnum VC = new PGWAddressCountryEnum("VC", 174, "VC");
  public static final PGWAddressCountryEnum SM = new PGWAddressCountryEnum("SM", 175, "SM");
  public static final PGWAddressCountryEnum SA = new PGWAddressCountryEnum("SA", 176, "SA");
  public static final PGWAddressCountryEnum SN = new PGWAddressCountryEnum("SN", 177, "SN");
  public static final PGWAddressCountryEnum SC = new PGWAddressCountryEnum("SC", 178, "SC");
  public static final PGWAddressCountryEnum SL = new PGWAddressCountryEnum("SL", 179, "SL");
  public static final PGWAddressCountryEnum SG = new PGWAddressCountryEnum("SG", 180, "SG");
  public static final PGWAddressCountryEnum SK = new PGWAddressCountryEnum("SK", 181, "SK");
  public static final PGWAddressCountryEnum SI = new PGWAddressCountryEnum("SI", 182, "SI");
  public static final PGWAddressCountryEnum SB = new PGWAddressCountryEnum("SB", 183, "SB");
  public static final PGWAddressCountryEnum SO = new PGWAddressCountryEnum("SO", 184, "SO");
  public static final PGWAddressCountryEnum ZA = new PGWAddressCountryEnum("ZA", 185, "ZA");
  public static final PGWAddressCountryEnum ES = new PGWAddressCountryEnum("ES", 186, "ES");
  public static final PGWAddressCountryEnum LK = new PGWAddressCountryEnum("LK", 187, "LK");
  public static final PGWAddressCountryEnum SD = new PGWAddressCountryEnum("SD", 188, "SD");
  public static final PGWAddressCountryEnum SR = new PGWAddressCountryEnum("SR", 189, "SR");
  public static final PGWAddressCountryEnum SVL = new PGWAddressCountryEnum("SVL", 190, "SVL");
  public static final PGWAddressCountryEnum SZ = new PGWAddressCountryEnum("SZ", 191, "SZ");
  public static final PGWAddressCountryEnum SE = new PGWAddressCountryEnum("SE", 192, "SE");
  public static final PGWAddressCountryEnum CH = new PGWAddressCountryEnum("CH", 193, "CH");
  public static final PGWAddressCountryEnum SY = new PGWAddressCountryEnum("SY", 194, "SY");
  public static final PGWAddressCountryEnum TA = new PGWAddressCountryEnum("TA", 195, "TA");
  public static final PGWAddressCountryEnum TW = new PGWAddressCountryEnum("TW", 196, "TW");
  public static final PGWAddressCountryEnum TJ = new PGWAddressCountryEnum("TJ", 197, "TJ");
  public static final PGWAddressCountryEnum TZ = new PGWAddressCountryEnum("TZ", 198, "TZ");
  public static final PGWAddressCountryEnum TH = new PGWAddressCountryEnum("TH", 199, "TH");
  public static final PGWAddressCountryEnum TG = new PGWAddressCountryEnum("TG", 200, "TG");
  public static final PGWAddressCountryEnum TO = new PGWAddressCountryEnum("TO", 201, "TO");
  public static final PGWAddressCountryEnum TT = new PGWAddressCountryEnum("TT", 202, "TT");
  public static final PGWAddressCountryEnum TN = new PGWAddressCountryEnum("TN", 203, "TN");
  public static final PGWAddressCountryEnum TR = new PGWAddressCountryEnum("TR", 204, "TR");
  public static final PGWAddressCountryEnum TM = new PGWAddressCountryEnum("TM", 205, "TM");
  public static final PGWAddressCountryEnum TC = new PGWAddressCountryEnum("TC", 206, "TC");
  public static final PGWAddressCountryEnum TV = new PGWAddressCountryEnum("TV", 207, "TV");
  public static final PGWAddressCountryEnum UG = new PGWAddressCountryEnum("UG", 208, "UG");
  public static final PGWAddressCountryEnum UA = new PGWAddressCountryEnum("UA", 209, "UA");
  public static final PGWAddressCountryEnum UAE = new PGWAddressCountryEnum("AE", 210, "AE");
  public static final PGWAddressCountryEnum UY = new PGWAddressCountryEnum("UY", 211, "UY");
  public static final PGWAddressCountryEnum UZ = new PGWAddressCountryEnum("UZ", 212, "UZ");
  public static final PGWAddressCountryEnum VU = new PGWAddressCountryEnum("VU", 213, "VU");
  public static final PGWAddressCountryEnum VA = new PGWAddressCountryEnum("VA", 214, "VA");
  public static final PGWAddressCountryEnum VE = new PGWAddressCountryEnum("VE", 215, "VE");
  public static final PGWAddressCountryEnum VN = new PGWAddressCountryEnum("VN", 216, "VN");
  public static final PGWAddressCountryEnum VI = new PGWAddressCountryEnum("VI", 217, "VI");
  public static final PGWAddressCountryEnum WF = new PGWAddressCountryEnum("WF", 218, "WF");
  public static final PGWAddressCountryEnum EH = new PGWAddressCountryEnum("EH", 219, "EH");
  public static final PGWAddressCountryEnum WS = new PGWAddressCountryEnum("WS", 220, "WS");
  public static final PGWAddressCountryEnum YE = new PGWAddressCountryEnum("YE", 221, "YE");
  public static final PGWAddressCountryEnum YU = new PGWAddressCountryEnum("YU", 222, "YU");
  public static final PGWAddressCountryEnum ZM = new PGWAddressCountryEnum("ZM", 223, "ZM");
  public static final PGWAddressCountryEnum ZW = new PGWAddressCountryEnum("ZW", 224, "ZW");
  public static final PGWAddressCountryEnum APO_FPO = new PGWAddressCountryEnum("APO/FPO", 225,
      "XPO");
  public static final PGWAddressCountryEnum MI = new PGWAddressCountryEnum("MI", 226, "MI");
  public static final PGWAddressCountryEnum RE = new PGWAddressCountryEnum("RE", 227, "RE");
  public static final PGWAddressCountryEnum ME = new PGWAddressCountryEnum("ME", 228, "ME");
  public static final PGWAddressCountryEnum RS = new PGWAddressCountryEnum("RS", 229, "RS");
  public static final PGWAddressCountryEnum GB = new PGWAddressCountryEnum("GB", 230, "GB");
  public static final PGWAddressCountryEnum KN = new PGWAddressCountryEnum("KN", 231, "KN");
  private static final long serialVersionUID = 1913607964771439139L;
  private String m_dbValue;

  public String getDbValue() {
    return this.m_dbValue;
  }

  private PGWAddressCountryEnum(String name, int intValue, String value) {
    super(intValue, name);
    this.m_dbValue = value;
  }

  public static PGWAddressCountryEnum get(int intVal) {
    return (PGWAddressCountryEnum) getElseReturnEnum(
        JdkUtil.forceInit(PGWAddressCountryEnum.class), intVal, US);
  }

  public static PGWAddressCountryEnum findByName(String name) {
    List list = getList(PGWAddressCountryEnum.class);

    for (int i = 0; i < list.size(); ++i) {
      PGWAddressCountryEnum country = (PGWAddressCountryEnum) list.get(i);
      if (country.getName().equals(name)) {
        return country;
      }
    }

    return NONE;
  }

  public static PGWAddressCountryEnum findByDbValue(String value) {
    List list = getList(PGWAddressCountryEnum.class);

    for (int i = 0; i < list.size(); ++i) {
      PGWAddressCountryEnum country = (PGWAddressCountryEnum) list.get(i);
      if (country.getDbValue().equals(value)) {
        return country;
      }
    }

    return NONE;
  }
}
