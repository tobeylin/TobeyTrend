package com.trend.tobeylin.tobeytrend;

import android.content.Context;

/**
 * Created by tobeylin on 15/6/15.
 */
public enum BackgroundColor {

    Yellow(R.color.background_yellow),
    Red(R.color.background_red),
    Blue(R.color.background_blue),
    Green(R.color.background_green);

    private int colorResId;

    BackgroundColor(int colorResId) {
        this.colorResId = colorResId;
    }

    public int getResourceId() {
        return this.colorResId;
    }

    public int getColorInt(Context context){
        return context.getResources().getColor(this.colorResId);
    }

    public static int[] getAllColors(Context context){

        BackgroundColor[] colors = BackgroundColor.values();
        int[] colorInt = new int[4];
        for(int i = 0; i < colorInt.length; ++i){
            colorInt[i] = colors[i].getColorInt(context);
        }
        return colorInt;

    }

}
