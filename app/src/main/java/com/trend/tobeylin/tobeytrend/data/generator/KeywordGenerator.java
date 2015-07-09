package com.trend.tobeylin.tobeytrend.data.generator;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.trend.tobeylin.tobeytrend.Region;
import com.trend.tobeylin.tobeytrend.VolleyRequestQueue;
import com.trend.tobeylin.tobeytrend.entity.RegionTopSearchEntity;

import java.util.List;

/**
 * Created by tobeylin on 15/6/15.
 */
public class KeywordGenerator {

    public static final String TAG = KeywordGenerator.class.getSimpleName();
    private static final String TOP_SEARCH_REQUEST_URL = "http://hawttrends.appspot.com/api/terms/";

    private static KeywordGenerator instance = null;
    private KeywordGeneratorSyncListener listener = null;
    private RegionTopSearchEntity topSearchEntity = null;
    private String country = Region.getDefaultCountry();
    private VolleyRequestQueue requestQueue = null;

    public interface KeywordGeneratorSyncListener {
        void onSyncSuccess(RegionTopSearchEntity keywordResponseEntity);
        void onSyncFail();
    }

    private KeywordGenerator(Context context) {
        requestQueue = VolleyRequestQueue.getInstance(context);
    }

    public static KeywordGenerator getInstance(Context context) {

        if (instance == null) {
            instance = new KeywordGenerator(context);
        }
        return instance;

    }

    public void setVolleyRequestQueue(VolleyRequestQueue volleyRequestQueue){
        this.requestQueue = volleyRequestQueue;
    }

    public void setTopSearchEntity(RegionTopSearchEntity topSearchEntity){
        this.topSearchEntity = topSearchEntity;
    }

    public void setListener(KeywordGeneratorSyncListener listener) {

        this.listener = listener;

    }

    public void removeListener() {

        this.listener = null;

    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry(){

        return country;

    }
    String res = "";

    public String getRes(){
        return res;
    }

    public void sync() {

        Response.Listener successListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                try {

                    topSearchEntity = gson.fromJson(response, RegionTopSearchEntity.class);

                    if (listener != null) {
                        listener.onSyncSuccess(topSearchEntity);
                    }
                } catch (JsonSyntaxException e){
                    if (listener != null) {
                        listener.onSyncFail();
                    }
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error");
                if (listener != null) {
                    listener.onSyncFail();
                }
            }
        };
        requestQueue.sendGetRequest(TOP_SEARCH_REQUEST_URL, successListener, errorListener);

    }

    public boolean isSync(){
        return (topSearchEntity != null)? true: false;
    }

    public List<String> getKeywords() {

        return getKeywords(country);

    }

    public List<String> getKeywords(String country) {

        List<String> keywords;
        if (Region.isAllRegion(country)) {
            keywords = topSearchEntity.getAllCountryKeywords();
        } else {
            keywords = topSearchEntity.getCountryKeywords(Region.getCountryShortName(country));
        }
        return keywords;

    }

}
