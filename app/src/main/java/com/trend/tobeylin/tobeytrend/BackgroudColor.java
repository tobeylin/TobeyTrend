package com.trend.tobeylin.tobeytrend;

import android.graphics.Color;

/**
 * Created by tobeylin on 15/6/15.
 */
public enum BackgroudColor {

    Yellow(Color.rgb(244, 180, 0)),
    Red(Color.rgb(219, 68, 55)),
    Blue(Color.rgb(66, 133, 244)),
    Green(Color.rgb(15, 157, 88));

    private int colorInt;

    BackgroudColor(int colorInt) {
        this.colorInt = colorInt;
    }

    public int getInt() {
        return this.colorInt;
    }

    public static int[] getAll(){

        BackgroudColor[] colors = BackgroudColor.values();
        int[] colorInt = new int[4];
        for(int i = 0; i < colorInt.length; ++i){
            colorInt[i] = colors[i].getInt();
        }
        return colorInt;

    }

}
