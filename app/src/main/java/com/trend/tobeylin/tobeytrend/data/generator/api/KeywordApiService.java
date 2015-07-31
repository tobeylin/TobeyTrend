package com.trend.tobeylin.tobeytrend.data.generator.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.trend.tobeylin.tobeytrend.VolleyRequestQueue;
import com.trend.tobeylin.tobeytrend.entity.RegionTopSearchEntity;
import com.trend.tobeylin.tobeytrend.manager.AppCenter;

/**
 * Created by tobeylin on 15/7/9.
 */
public class KeywordApiService {

    private final String TOP_SEARCH_REQUEST_URL = "http://hawttrends.appspot.com/api/terms/";

    private VolleyRequestQueue requestQueue = null;
    private RegionTopSearchEntity topSearchEntity = null;

    public interface ApiCallback {
        void onSuccess(RegionTopSearchEntity topSearchEntity);

        void onFail();
    }

    public KeywordApiService() {
        requestQueue = (VolleyRequestQueue) AppCenter.getInstance().getVolleyRequestQueue();
    }

    public KeywordApiService(VolleyRequestQueue volleyRequestQueue) {
        requestQueue = volleyRequestQueue;
    }

    public String getApiUrl() {
        return TOP_SEARCH_REQUEST_URL;
    }

    public RegionTopSearchEntity getTopSearchEntity() {
        return topSearchEntity;
    }

    public void start(final ApiCallback callback) {

        Response.Listener<String> successListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    topSearchEntity = parse(response);
                    callback.onSuccess(topSearchEntity);
                } catch (JsonParseException e) {
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
        Request request = getRequest(successListener, errorListener);
        requestQueue.sendRequest(request);
    }

    public Request getRequest(Response.Listener successListener, Response.ErrorListener errorListener) {
        StringRequest request = new StringRequest(Request.Method.GET, TOP_SEARCH_REQUEST_URL, successListener, errorListener);
        return request;
    }

    public RegionTopSearchEntity parse(String data) throws JsonParseException {
        Gson gson = new Gson();
        RegionTopSearchEntity resultEntity = gson.fromJson(data, RegionTopSearchEntity.class);
        return resultEntity;
    }

}
