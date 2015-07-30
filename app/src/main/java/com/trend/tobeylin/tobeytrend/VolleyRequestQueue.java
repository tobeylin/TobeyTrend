package com.trend.tobeylin.tobeytrend;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
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

    public void sendRequest(Request request){
        requestQueue.add(request);
    }

}
