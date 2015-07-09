package com.trend.tobeylin.tobeytrend;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tobeylin on 15/6/15.
 */
public class Region {

    private static final Map<String, String> countryMap;
    
    static {
        countryMap = new HashMap<>();
        countryMap.put("All Regions", "All");
        countryMap.put("Argentina", "AR");
        countryMap.put("Australia", "AU");
        countryMap.put("Austria", "AT");
        countryMap.put("Belgium", "BE");
        countryMap.put("Brazil", "BR");
        countryMap.put("Canada", "CA");
        countryMap.put("Chile", "CL");
        countryMap.put("Colombia", "CO");
        countryMap.put("Czech Republic", "CZ");
        countryMap.put("Denmark", "DK");
        countryMap.put("Egypt", "EG");
        countryMap.put("Finland", "FI");
        countryMap.put("Germany", "DE");
        countryMap.put("Greece", "GR");
        countryMap.put("Greece", "GR");
        countryMap.put("Hong Kong", "HK");
        countryMap.put("Hungary", "HU");
        countryMap.put("India", "IN");
        countryMap.put("Indonesia", "ID");
        countryMap.put("Israel", "IL");
        countryMap.put("Italy", "IT");
        countryMap.put("Japan", "JP");
        countryMap.put("Kenya", "KE");
        countryMap.put("Malaysia", "MY");
        countryMap.put("Mexico", "MX");
        countryMap.put("Netherlands", "NL");
        countryMap.put("Nigeria", "NG");
        countryMap.put("Norway", "NO");
        countryMap.put("Philippines", "PH");
        countryMap.put("Poland", "PL");
        countryMap.put("Portugal", "PT");
        countryMap.put("Romania", "RO");
        countryMap.put("Russia", "RU");
        countryMap.put("Saudi Arabia", "SA");
        countryMap.put("Singapore", "SG");
        countryMap.put("South Africa", "ZA");
        countryMap.put("South Korea", "KR");
        countryMap.put("Spain", "ES");
        countryMap.put("Sweden", "SE");
        countryMap.put("Switzerland", "CH");
        countryMap.put("Taiwan", "TW");
        countryMap.put("Thailand", "TH");
        countryMap.put("Turkey", "TR");
        countryMap.put("Ukraine", "UA");
        countryMap.put("United Kingdom", "GB");
        countryMap.put("United States", "US");
        countryMap.put("Vietnam", "VN");
    }

    public static List<String> getAllCountriesFullName(){
        List<String> countryFullNames = new ArrayList<>();
        countryFullNames.addAll(countryMap.keySet());
        Collections.sort(countryFullNames);
        return countryFullNames;
    }

    public static String getDefaultCountry(){
        return "All Regions";
    }

    public static boolean isAllRegion(String target){
        return "All Regions".equals(target);
    }

    public static  String getCountryShortName(String countryFullName){
        String shortName = countryMap.get(countryFullName);
        shortName = (shortName == null)? countryMap.get(getDefaultCountry()): shortName;
        return shortName;
    }

}
