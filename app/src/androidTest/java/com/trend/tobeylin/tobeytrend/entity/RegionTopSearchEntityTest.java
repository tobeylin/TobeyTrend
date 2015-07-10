package com.trend.tobeylin.tobeytrend.entity;

import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by tobeylin on 15/7/10.
 */
@RunWith(MockitoJUnitRunner.class)
public class RegionTopSearchEntityTest extends TestCase{

    public void testGetCountryKeywords_withValidCountryName() throws Exception {

        RegionTopSearchEntity entity = new RegionTopSearchEntity();
        String validCountryShortName = "TW";
        int expectedResultListSize = 20;
        List<String> resultList = entity.getCountryKeywords(validCountryShortName);

        assertNotNull("getCountryKeywords(String) return null", resultList);
        assertEquals(expectedResultListSize, resultList.size());

    }

    public void testGetCountryKeywords_withInvalidCountryName() throws Exception {

        RegionTopSearchEntity entity = new RegionTopSearchEntity();
        String validCountryShortName = "invalid";
        int expectedResultListSize = 0;
        List<String> resultList = entity.getCountryKeywords(validCountryShortName);

        assertNotNull("getCountryKeywords(String) return null", resultList);
        assertEquals(expectedResultListSize, resultList.size());

    }

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