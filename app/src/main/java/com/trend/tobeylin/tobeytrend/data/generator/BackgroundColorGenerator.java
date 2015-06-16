package com.trend.tobeylin.tobeytrend.data.generator;

import com.trend.tobeylin.tobeytrend.BackgroudColor;

/**
 * Created by tobeylin on 15/6/16.
 */
public class BackgroundColorGenerator {

    private static BackgroundColorGenerator instance = null;

    private BackgroudColor[] colors = {};
    private int currentColorIndex = 0;

    private BackgroundColorGenerator(){

        colors = BackgroudColor.values();

    }

    public static BackgroundColorGenerator getInstance(){

        if(instance == null){
            instance = new BackgroundColorGenerator();
        }
        return instance;

    }

    public int getColor(){

        BackgroudColor color = colors[currentColorIndex];
        int colorInt = color.getInt();
        currentColorIndex += 1;
        currentColorIndex %= colors.length;
        return colorInt;

    }

}
