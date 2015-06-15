package com.trend.tobeylin.tobeytrend.entity;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * Created by tobeylin on 15/6/15.
 */
public class RegionTopSearchEntity implements Serializable {

    String[] ZA = new String[20];
    String[] DE = new String[20];
    String[] SA = new String[20];
    String[] AR = new String[20];
    String[] AU = new String[20];
    String[] AT = new String[20];
    String[] BE = new String[20];
    String[] BR = new String[20];
    String[] CA = new String[20];
    String[] CL = new String[20];
    String[] CO = new String[20];
    String[] KR = new String[20];
    String[] DK = new String[20];
    String[] EG = new String[20];
    String[] ES = new String[20];
    String[] US = new String[20];
    String[] FI = new String[20];
    String[] FR = new String[20];
    String[] GR = new String[20];
    String[] HK = new String[20];
    String[] HU = new String[20];
    String[] IN = new String[20];
    String[] ID = new String[20];
    String[] IL = new String[20];
    String[] IT = new String[20];
    String[] JP = new String[20];
    String[] KE = new String[20];
    String[] MY = new String[20];
    String[] MX = new String[20];
    String[] NG = new String[20];
    String[] NO = new String[20];
    String[] NL = new String[20];
    String[] PH = new String[20];
    String[] PL = new String[20];
    String[] PT = new String[20];
    String[] CZ = new String[20];
    String[] RO = new String[20];
    String[] GB = new String[20];
    String[] RU = new String[20];
    String[] SG = new String[20];
    String[] SE = new String[20];
    String[] CH = new String[20];
    String[] TW = new String[20];
    String[] TH = new String[20];
    String[] TR = new String[20];
    String[] UA = new String[20];
    String[] VN = new String[20];

    public String[] getCountryKeywords(String countryName){

        Object keywords = null;
        try {

            Field countryField = RegionTopSearchEntity.class.getDeclaredField(countryName);
            keywords = countryField.get(this);


        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return (String[])keywords;

    }

    public void getAllCountries(){

    }

}
