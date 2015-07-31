package com.trend.tobeylin.tobeytrend.manager;

import android.content.Context;

import com.trend.tobeylin.tobeytrend.VolleyRequestQueue;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tobeylin on 15/7/30.
 */
public class AppCenter {

    private static AppCenter instance = new AppCenter();
    private static final int VolleyRequestQueue = 0;
    private Map<Integer, Manager> managerMap;

    private AppCenter() {
        managerMap = new HashMap<>();
    }

    public static AppCenter getInstance() {
        return instance;
    }

    public void init(Context context) {

        managerMap.put(VolleyRequestQueue, new VolleyRequestQueue(context));

    }

    public Manager getVolleyRequestQueue() {
        return managerMap.get(VolleyRequestQueue);
    }

}
