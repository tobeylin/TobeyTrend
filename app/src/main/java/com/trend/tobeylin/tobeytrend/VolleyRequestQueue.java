package com.trend.tobeylin.tobeytrend;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by tobeylin on 15/7/6.
 */
public class VolleyRequestQueue {

    private static VolleyRequestQueue instance = null;
    private RequestQueue requestQueue = null;

    private VolleyRequestQueue(Context context){
        requestQueue = Volley.newRequestQueue(context);
    }

    public static VolleyRequestQueue getInstance(Context context) {
        if(instance == null){
            instance = new VolleyRequestQueue(context);
        }
        return instance;
    }

    public VolleyRequestQueue(RequestQueue requestQueue){
        this.requestQueue = requestQueue;
    }

    public RequestQueue getQueue(){
        return requestQueue;
    }

    public boolean r = false;
    public void sendGetRequest(String url, Response.Listener successListener, Response.ErrorListener errorListener){
        r = true;
        requestQueue.add(new MyRequest(Request.Method.GET, url, successListener, errorListener));
    }

    public void add(StringRequest request) {
        if(request != null){
            requestQueue.add(request);
        }
    }

    private class MyRequest extends StringRequest {

        public MyRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            super(method, url, listener, errorListener);
        }

        public MyRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            super(url, listener, errorListener);
        }

        @Override
        protected Response<String> parseNetworkResponse(NetworkResponse response) {

            String data = new String(response.data);

            return super.parseNetworkResponse(response);
        }
    }

}
