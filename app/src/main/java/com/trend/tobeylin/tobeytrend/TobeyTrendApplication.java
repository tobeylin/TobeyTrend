package com.trend.tobeylin.tobeytrend;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.trend.tobeylin.tobeytrend.manager.AppCenter;

/**
 * Created by tobeylin on 15/7/6.
 */
public class TobeyTrendApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppCenter appCenter = AppCenter.getInstance();
        appCenter.init(this);
    }

}
