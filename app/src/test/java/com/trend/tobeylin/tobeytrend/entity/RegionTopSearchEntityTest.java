package com.trend.tobeylin.tobeytrend.entity;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by tobeylin on 15/7/18.
 */
public class RegionTopSearchEntityTest {

    @Test
    public void testGetCountryKeywords_withValidCountryName() throws Exception {
        RegionTopSearchEntity entity = new RegionTopSearchEntity();
        String validCountryShortName = "TW";
        int expectedResultListSize = 20;
        List<String> resultList = entity.getCountryKeywords(validCountryShortName);

        assertNotNull("getCountryKeywords(String) return null", resultList);
        assertEquals(expectedResultListSize, resultList.size());
    }

    @Test
    public void testGetCountryKeywords_withInvalidCountryName() throws Exception {
        RegionTopSearchEntity entity = new RegionTopSearchEntity();
        String validCountryShortName = "invalid";
        int expectedResultListSize = 0;
        List<String> resultList = entity.getCountryKeywords(validCountryShortName);

        assertNotNull("getCountryKeywords(String) return null", resultList);
        assertEquals(expectedResultListSize, resultList.size());
    }

    @Test
    public void testGetAllCountryKeywords() throws Exception {
        RegionTopSearchEntity entity = new RegionTopSearchEntity(){
            @Override
            public List<String> getCountryKeywords(String countryShortName) {
                List<String> dummy = new ArrayList(20);
                for(int i = 0; i < 20; ++i) {
                    dummy.add("dummy");
                }
                return dummy;
            }
        };

        List<String> resultList = entity.getAllCountryKeywords();

        int expectedResultListSize = 20 * 47;
        assertNotNull("getAllCountryKeywords()", resultList);
        assertEquals(expectedResultListSize, resultList.size());
    }

}