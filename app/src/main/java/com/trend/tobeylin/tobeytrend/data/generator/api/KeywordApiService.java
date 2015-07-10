package com.trend.tobeylin.tobeytrend.data.generator.api;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.trend.tobeylin.tobeytrend.VolleyRequestQueue;
import com.trend.tobeylin.tobeytrend.entity.RegionTopSearchEntity;

/**
 * Created by tobeylin on 15/7/9.
 */
public class KeywordApiService {

    private static final String TOP_SEARCH_REQUEST_URL = "http://hawttrends.appspot.com/api/terms/";

    private VolleyRequestQueue requestQueue = null;
    private RegionTopSearchEntity topSearchEntity = null;

    public interface ApiCallback {
        void onSuccess(RegionTopSearchEntity topSearchEntity);
        void onFail();
    }

    public KeywordApiService(Context context){
        requestQueue = VolleyRequestQueue.getInstance(context);
    }

    public KeywordApiService(VolleyRequestQueue volleyRequestQueue){
        requestQueue = volleyRequestQueue;
    }

    public void setVolleyRequestQueue(VolleyRequestQueue volleyRequestQueue) {
        this.requestQueue = volleyRequestQueue;
    }

    public RegionTopSearchEntity getTopSearchEntity() {
        return topSearchEntity;
    }

    public void start(final ApiCallback callback){

        Response.Listener<String> successListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    topSearchEntity = parse(response);
                    callback.onSuccess(topSearchEntity);
                } catch (JsonSyntaxException e){
                    callback.onFail();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail();
            }
        };

        requestQueue.sendGetRequest(TOP_SEARCH_REQUEST_URL, successListener, errorListener);
    }

    private RegionTopSearchEntity parse(String data){
        Gson gson = new Gson();
        RegionTopSearchEntity resultEntity = gson.fromJson(data, RegionTopSearchEntity.class);
        return resultEntity;
    }

}
