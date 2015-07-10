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
    public String[] ZA = new String[20];

    @SerializedName("15")
    public String[] DE = new String[20];

    @SerializedName("36")
    public String[] SA = new String[20];

    @SerializedName("30")
    public String[] AR = new String[20];

    @SerializedName("8")
    public String[] AU = new String[20];

    @SerializedName("44")
    public String[] AT = new String[20];

    @SerializedName("41")
    public String[] BE = new String[20];

    @SerializedName("18")
    public String[] BR = new String[20];

    @SerializedName("13")
    public String[] CA = new String[20];
    
    @SerializedName("38")
    public String[] CL = new String[20];

    @SerializedName("32")
    public String[] CO = new String[20];

    @SerializedName("23")
    public String[] KR = new String[20];
    
    @SerializedName("49")
    public String[] DK = new String[20];
    
    @SerializedName("29")
    public String[] EG = new String[20];

    @SerializedName("26")
    public String[] ES = new String[20];

    @SerializedName("1")
    public String[] US = new String[20];

    @SerializedName("50")
    public String[] FI = new String[20];

    @SerializedName("16")
    public String[] FR = new String[20];

    @SerializedName("48")
    public String[] GR = new String[20];

    @SerializedName("10")
    public String[] HK = new String[20];

    @SerializedName("45")
    public String[] HU = new String[20];

    @SerializedName("3")
    public String[] IN = new String[20];

    @SerializedName("19")
    public String[] ID = new String[20];

    @SerializedName("6")
    public String[] IL = new String[20];

    @SerializedName("27")
    public String[] IT = new String[20];

    @SerializedName("4")
    public String[] JP = new String[20];

    @SerializedName("37")
    public String[] KE = new String[20];

    @SerializedName("34")
    public String[] MY = new String[20];

    @SerializedName("21")
    public String[] MX = new String[20];

    @SerializedName("52")
    public String[] NG = new String[20];

    @SerializedName("51")
    public String[] NO = new String[20];

    @SerializedName("17")
    public String[] NL = new String[20];

    @SerializedName("25")
    public String[] PH = new String[20];

    @SerializedName("31")
    public String[] PL = new String[20];

    @SerializedName("47")
    public String[] PT = new String[20];

    @SerializedName("43")
    public String[] CZ = new String[20];

    @SerializedName("39")
    public String[] RO = new String[20];

    @SerializedName("9")
    public String[] GB = new String[20];

    @SerializedName("14")
    public String[] RU = new String[20];

    @SerializedName("5")
    public String[] SG = new String[20];

    @SerializedName("42")
    public String[] SE = new String[20];

    @SerializedName("46")
    public String[] CH = new String[20];

    @SerializedName("12")
    public String[] TW = new String[20];

    @SerializedName("33")
    public String[] TH = new String[20];

    @SerializedName("24")
    public String[] TR = new String[20];

    @SerializedName("35")
    public String[] UA = new String[20];

    @SerializedName("28")
    public String[] VN = new String[20];

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
