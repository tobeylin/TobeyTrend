package com.trend.tobeylin.tobeytrend.data.generator;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.trend.tobeylin.tobeytrend.Country;
import com.trend.tobeylin.tobeytrend.entity.RegionTopSearchEntity;

import java.util.Collections;
import java.util.List;

/**
 * Created by tobeylin on 15/6/15.
 */
public class KeywordGenerator {

    public static final String TAG = KeywordGenerator.class.getSimpleName();
    private static final String TOP_SEARCH_REQUEST_URL = "http://hawttrends.appspot.com/api/terms/";

    private static KeywordGenerator instance = null;
    private List<String> keywords = null;
    private KeywordGeneratorListener listener = null;
    private RegionTopSearchEntity topSearchEntity = null;
    private RequestQueue requestQueue = null;
    private Country country = Country.All;
    private int keywordPointer = 0;


    public interface KeywordGeneratorListener{

        void onSyncFinish();

    }

    private KeywordGenerator(Context context) {

        requestQueue = Volley.newRequestQueue(context);

    }

    public static KeywordGenerator getInstance(Context context){

        if(instance == null){
            instance = new KeywordGenerator(context);
        }
        return instance;

    }

    public void setListener(KeywordGeneratorListener listener){

        this.listener = listener;

    }

    public void removeListener(){

        this.listener = null;

    }

    public void setCountry(Country country){

        this.country = country;
        updateKeywords();

    }

    public void sync(){

        StringRequest getTopSearchRequest = new StringRequest(Request.Method.GET, TOP_SEARCH_REQUEST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i(TAG, "response = " + response);

                response = replaceNumber(response);
                Gson gson = new Gson();
                topSearchEntity = gson.fromJson(response, RegionTopSearchEntity.class);
                keywords = getShuffleKeywords();

                if (listener != null) {
                    listener.onSyncFinish();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e(TAG, "Error");

            }
        });
        requestQueue.add(getTopSearchRequest);

    }

    private String replaceNumber(String text){

        Country[] countries = Country.values();

        for(int i = 0; i < countries.length; ++i) {
            String countryCode = countries[i].getCode();
            String countryName = countries[i].getSimpleName();
            String pattern = "\"" + countryCode + "\"";
            text = text.replaceFirst(pattern, countryName);
        }

        return text;

    }

    public List<String> getKeywords(){

        return getKeywords(country);

    }

    public List<String> getKeywords(Country country) {

        List<String> keywords;
        if(country == Country.All) {
            keywords = topSearchEntity.getAllCountryKeywords();
        } else {
            keywords = topSearchEntity.getCountryKeywords(country.getSimpleName());
        }
        return keywords;

    }

    private List<String> getShuffleKeywords(){

        List<String> keywords = getKeywords();
        Collections.shuffle(keywords);
        return keywords;

    }

    public String getRandomKeyword(){

        if(keywordPointer == keywords.size()){
            updateKeywords();
            keywordPointer = 0;
        }
        String keyword = keywords.get(keywordPointer++);

        return keyword;
    }

    private void updateKeywords(){
        keywords = getShuffleKeywords();
    }

}
