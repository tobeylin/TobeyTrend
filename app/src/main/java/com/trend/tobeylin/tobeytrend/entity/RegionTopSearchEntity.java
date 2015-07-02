package com.trend.tobeylin.tobeytrend.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tobeylin on 15/6/15.
 */
public class RegionTopSearchEntity implements Serializable {

    private static final long serialVersionUID = 5033680397645094936L;

    @SerializedName("40")
    String[] ZA = new String[20];

    @SerializedName("15")
    String[] DE = new String[20];

    @SerializedName("36")
    String[] SA = new String[20];

    @SerializedName("30")
    String[] AR = new String[20];

    @SerializedName("8")
    String[] AU = new String[20];

    @SerializedName("44")
    String[] AT = new String[20];

    @SerializedName("41")
    String[] BE = new String[20];

    @SerializedName("18")
    String[] BR = new String[20];

    @SerializedName("13")
    String[] CA = new String[20];
    
    @SerializedName("38")
    String[] CL = new String[20];

    @SerializedName("32")
    String[] CO = new String[20];

    @SerializedName("23")
    String[] KR = new String[20];
    
    @SerializedName("49")
    String[] DK = new String[20];
    
    @SerializedName("29")
    String[] EG = new String[20];

    @SerializedName("26")
    String[] ES = new String[20];

    @SerializedName("1")
    String[] US = new String[20];

    @SerializedName("50")
    String[] FI = new String[20];

    @SerializedName("16")
    String[] FR = new String[20];

    @SerializedName("48")
    String[] GR = new String[20];

    @SerializedName("10")
    String[] HK = new String[20];

    @SerializedName("45")
    String[] HU = new String[20];

    @SerializedName("3")
    String[] IN = new String[20];

    @SerializedName("19")
    String[] ID = new String[20];

    @SerializedName("6")
    String[] IL = new String[20];

    @SerializedName("27")
    String[] IT = new String[20];

    @SerializedName("4")
    String[] JP = new String[20];

    @SerializedName("37")
    String[] KE = new String[20];

    @SerializedName("34")
    String[] MY = new String[20];

    @SerializedName("21")
    String[] MX = new String[20];

    @SerializedName("52")
    String[] NG = new String[20];

    @SerializedName("51")
    String[] NO = new String[20];

    @SerializedName("17")
    String[] NL = new String[20];

    @SerializedName("25")
    String[] PH = new String[20];

    @SerializedName("31")
    String[] PL = new String[20];

    @SerializedName("47")
    String[] PT = new String[20];

    @SerializedName("43")
    String[] CZ = new String[20];

    @SerializedName("39")
    String[] RO = new String[20];

    @SerializedName("9")
    String[] GB = new String[20];

    @SerializedName("14")
    String[] RU = new String[20];

    @SerializedName("5")
    String[] SG = new String[20];

    @SerializedName("42")
    String[] SE = new String[20];

    @SerializedName("46")
    String[] CH = new String[20];

    @SerializedName("12")
    String[] TW = new String[20];

    @SerializedName("33")
    String[] TH = new String[20];

    @SerializedName("24")
    String[] TR = new String[20];

    @SerializedName("35")
    String[] UA = new String[20];

    @SerializedName("28")
    String[] VN = new String[20];

    public List<String> getCountryKeywords(String countryName){

        Object keywords = null;
        try {

            Field countryField = RegionTopSearchEntity.class.getDeclaredField(countryName);
            keywords = countryField.get(this);


        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return Arrays.asList((String[])keywords);

    }

    public List<String> getAllCountryKeywords(){

        List<String> allKeywords = new ArrayList<>();

        try {
            Field[] countryFields = RegionTopSearchEntity.class.getDeclaredFields();
            for (Field countryFiled : countryFields) {
                if(!countryFiled.getName().equals("serialVersionUID")) {
                    String[] countryKeywords = (String[]) countryFiled.get(this);
                    allKeywords.addAll(Arrays.asList(countryKeywords));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return allKeywords;

    }

}
