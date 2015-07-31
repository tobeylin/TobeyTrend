package com.trend.tobeylin.tobeytrend;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.trend.tobeylin.tobeytrend.manager.Manager;

/**
 * Created by tobeylin on 15/7/6.
 */
public class VolleyRequestQueue implements Manager {

    private static VolleyRequestQueue instance = null;
    private RequestQueue requestQueue = null;

    public VolleyRequestQueue(Context context){
        requestQueue = Volley.newRequestQueue(context);
    }

    public VolleyRequestQueue(RequestQueue requestQueue){
        this.requestQueue = requestQueue;
    }

    public void sendRequest(Request request){
        requestQueue.add(request);
    }

}
