package com.trend.tobeylin.tobeytrend;

import android.app.Application;

import com.android.volley.RequestQueue;

/**
 * Created by tobeylin on 15/7/6.
 */
public class TobeyTrendApplication extends Application {

    private static TobeyTrendApplication instance = null;
    private VolleyRequestQueue volleyRequestQueue = null;

    @Override
    public void onCreate() {
        super.onCreate();
        volleyRequestQueue = VolleyRequestQueue.getInstance(this);
    }

}
