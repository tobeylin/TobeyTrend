package com.trend.tobeylin.tobeytrend;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tobeylin on 15/6/15.
 */
public enum Country {

    All("0", "All Regions"),
    AR("30", "Argentina"),
    AU("8", "Australia"),
    AT("44", "Austria"),
    BE("41", "Belgium"),
    BR("18", "Brazil"),
    CA("13", "Canada"),
    CL("38", "Chile"),
    CO("32", "Colombia"),
    CZ("43", "Czech Republic"),
    DK("49", "Denmark"),
    EG("29", "Egypt"),
    FI("50", "Finland"),
    FR("16", "France"),
    DE("15", "Germany"),
    GR("48", "Greece"),
    HK("10", "Hong Kong"),
    HU("45", "Hungary"),
    IN("3", "India"),
    ID("19", "Indonesia"),
    IL("6", "Israel"),
    IT("27", "Italy"),
    JP("4", "Japan"),
    KE("37", "Kenya"),
    MY("34", "Malaysia"),
    MX("21", "Mexico"),
    NL("17", "Netherlands"),
    NG("52", "Nigeria"),
    NO("51", "Norway"),
    PH("25", "Philippines"),
    PL("31", "Poland"),
    PT("47", "Portugal"),
    RO("39", "Romania"),
    RU("14", "Russia"),
    SA("36", "Saudi Arabia"),
    SG("5", "Singapore"),
    ZA("40", "South Africa"),
    KR("23", "South Korea"),
    ES("26","Spain"),
    SE("42", "Sweden"),
    CH("46", "Switzerland"),
    TW("12", "Taiwan"),
    TH("33", "Thailand"),
    TR("24", "Turkey"),
    UA("35", "Ukraine"),
    GB("9", "United Kingdom"),
    US("1", "United States"),
    VN("28", "Vietnam");

    private String code;
    private String fullName;

    Country(String code, String fullName) {

        this.code = code;
        this.fullName = fullName;

    }

    public static Country getCountryByCode(String countryCode) {

        Country[] countries = Country.values();
        Country result = Country.TW;

        for (int i = 0; i < countries.length; ++i) {
            if(countries[i].getCode().equals(countryCode)) {
                result = countries[i];
                break;
            }
        }
        return result;
    }

    public static Country getCountryBySimpleName(String countryName) {

        return valueOf(countryName);

    }

    public static Country getCountryByFullName(String fullName) {

        Country[] countries = Country.values();
        Country result = Country.All;

        for (int i = 0; i < countries.length; ++i) {
            if(countries[i].getFullName().equals(fullName)) {
                result = countries[i];
                break;
            }
        }
        return result;

    }

    public String getCode() {

        return this.code;

    }

    public String getSimpleName() {

        return this.name();

    }

    public String getFullName(){

        return this.fullName;

    }

    public static List<String> getAllCountriesFullName(){

        Country[] countries = Country.values();
        List<String> countryFullNames = new ArrayList<>();
        for(Country country: countries){
            countryFullNames.add(country.getFullName());
        }
        return countryFullNames;

    }

}
