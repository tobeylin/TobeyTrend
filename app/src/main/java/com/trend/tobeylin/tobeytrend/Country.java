package com.trend.tobeylin.tobeytrend;

/**
 * Created by tobeylin on 15/6/15.
 */
public enum Country {

    All("0"), ZA("40"), DE("15"), SA("36"), AR("30"), AU("8"), AT("44"), BE("41"), BR("18"), CA("13"), CL("38"), CO("32"), KR("23"), DK("49"), EG("29"), ES("26"), US("1"), FI("50"), FR("16"), GR("48"), HK("10"), HU("45"), IN("3"), ID("19"), IL("6"), IT("27"), JP("4"), KE("37"), MY("34"), MX("21"), NG("52"), NO("51"), NL("17"), PH("25"), PL("31"), PT("47"), CZ("43"), RO("39"), GB("9"), RU("14"), SG("5"), SE("42"), CH("46"), TW("12"), TH("33"), TR("24"), UA("35"), VN("28");

    private String countryCode;

    Country(String countryCode) {

        this.countryCode = countryCode;

    }

    public static Country getCountryByCode(String countryCode) {

        Country[] countries = Country.values();
        Country result = Country.TW;

        for (int i = 0; i < countries.length; ++i) {
            if(countries[i].getCountryCode().equals(countryCode)) {
                result = countries[i];
                break;
            }
        }
        return result;
    }

    public static Country getCountryByName(String countryName) {

        return valueOf(countryName);

    }

    public String getCountryCode() {

        return this.countryCode;

    }

    public String getCountryName() {

        return this.name();

    }

}
