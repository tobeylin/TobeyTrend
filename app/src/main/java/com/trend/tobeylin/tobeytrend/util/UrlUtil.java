package com.trend.tobeylin.tobeytrend.util;

/**
 * Created by tobeylin on 15/7/14.
 */
public class UrlUtil {

    public static String getGoogleSearchUrl(String keyword) {

        String googleSearchBaseUrl = "http://www.google.com/search?q=";
        String resultUrl = googleSearchBaseUrl;
        if(keyword != null && !keyword.isEmpty()) {
            resultUrl += keyword;
        }
        return resultUrl;
    }

}
