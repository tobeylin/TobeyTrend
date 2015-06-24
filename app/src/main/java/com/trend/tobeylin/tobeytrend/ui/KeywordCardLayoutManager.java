package com.trend.tobeylin.tobeytrend.ui;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

/**
 * Created by tobeylin on 15/6/24.
 */
public class KeywordCardLayoutManager extends GridLayoutManager{

    private static final int DEFAULT_WIDTH_COUNT = 1;
    private static final int DEFAULT_HEIGHT_COUNT = 1;
    private int widthCount = DEFAULT_WIDTH_COUNT;
    private int heightCount = DEFAULT_HEIGHT_COUNT;

    public KeywordCardLayoutManager(Context context){
        super(context, DEFAULT_WIDTH_COUNT);
    }

    public KeywordCardLayoutManager(Context context, int widthCount, int heightCount){
        super(context, widthCount);
        this.widthCount = widthCount;
        this.heightCount = heightCount;
    }

    public void setWidthCount(int widthCount) {
        this.widthCount = widthCount;
        setSpanCount(widthCount);
    }

    public void setHeightCount(int heightCount){
        this.heightCount = heightCount;
    }

    @Override
    public int getDecoratedMeasuredHeight(View child) {

        int parentHeight = getHeight();
        int childHeight = getItemHeight(parentHeight);
        return childHeight;

    }

    private int getItemHeight(int parentHeight){

        return parentHeight / heightCount;

    }

}
