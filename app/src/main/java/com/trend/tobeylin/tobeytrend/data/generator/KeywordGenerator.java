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
import com.google.gson.JsonSyntaxException;
import com.trend.tobeylin.tobeytrend.Country;
import com.trend.tobeylin.tobeytrend.entity.RegionTopSearchEntity;

import java.util.List;

/**
 * Created by tobeylin on 15/6/15.
 */
public class KeywordGenerator {

    public static final String TAG = KeywordGenerator.class.getSimpleName();
    private static final String TOP_SEARCH_REQUEST_URL = "http://hawttrends.appspot.com/api/terms/";

    private static KeywordGenerator instance = null;
    private KeywordGeneratorListener listener = null;
    private RegionTopSearchEntity topSearchEntity = null;
    private RequestQueue requestQueue = null;
    private Country country = Country.All;

    public interface KeywordGeneratorListener {
        void onSyncSuccess();
        void onSyncFail();
    }

    private KeywordGenerator(Context context) {

        requestQueue = Volley.newRequestQueue(context.getApplicationContext());

    }

    public static KeywordGenerator getInstance(Context context) {

        if (instance == null) {
            instance = new KeywordGenerator(context);
        }
        return instance;

    }

    public void setListener(KeywordGeneratorListener listener) {

        this.listener = listener;

    }

    public void removeListener() {

        this.listener = null;

    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Country getCountry(){

        return country;

    }

    public void sync() {

        StringRequest getTopSearchRequest = new StringRequest(Request.Method.GET, TOP_SEARCH_REQUEST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i(TAG, "response = " + response);

                Gson gson = new Gson();
                try {
                    topSearchEntity = gson.fromJson(response, RegionTopSearchEntity.class);

                    if (listener != null) {
                        listener.onSyncSuccess();
                    }
                } catch (JsonSyntaxException e){
                    if (listener != null) {
                        listener.onSyncFail();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error");
                if (listener != null) {
                    listener.onSyncFail();
                }
            }
        });
        requestQueue.add(getTopSearchRequest);

    }

    public List<String> getKeywords() {

        return getKeywords(country);

    }

    public List<String> getKeywords(Country country) {

        List<String> keywords;
        if (country == Country.All) {
            keywords = topSearchEntity.getAllCountryKeywords();
        } else {
            keywords = topSearchEntity.getCountryKeywords(country.getSimpleName());
        }
        return keywords;

    }

}
