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
public class KeywordApiService implements Response.Listener<String>, Response.ErrorListener {

    private static final String TOP_SEARCH_REQUEST_URL = "http://hawttrends.appspot.com/api/terms/";

    private VolleyRequestQueue requestQueue = null;
    private RegionTopSearchEntity topSearchEntity = null;
    private ApiCallback callback = null;

    public interface ApiCallback {
        void onSuccess();
        void onFail();
    }

    public KeywordApiService(Context context, ApiCallback callback){
        requestQueue = VolleyRequestQueue.getInstance(context);
        this.callback = callback;
    }

    public RegionTopSearchEntity getTopSearchEntity() {
        return topSearchEntity;
    }

    public void start(){
        requestQueue.sendGetRequest(TOP_SEARCH_REQUEST_URL, this, this);
    }

    @Override
    public void onResponse(String response) {
        Gson gson = new Gson();
        try {
            topSearchEntity = gson.fromJson(response, RegionTopSearchEntity.class);
            callback.onSuccess();
        } catch (JsonSyntaxException e){
            callback.onFail();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
       callback.onFail();
    }

}
