package com.trend.tobeylin.tobeytrend.ui;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by tobeylin on 15/6/23.
 */
public class KeywordCardLayoutManager extends LinearLayoutManager{

    private static final int DEFAULT_HEIGHT_COUNT = 3;
    private int heightCount = DEFAULT_HEIGHT_COUNT;
    private static final int DEFAULT_WIDTH_COUNT = 2;
    private int widthCount = DEFAULT_WIDTH_COUNT;

    public KeywordCardLayoutManager(Context context) {
        super(context);
    }

    public KeywordCardLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public KeywordCardLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setWidthCount(int widthCount){
        this.widthCount = widthCount;
    }

    public void setHeightCount(int heightCount) {
        this.heightCount = heightCount;
    }

    @Override
    public void measureChild(View child, int widthUsed, int heightUsed) {

        final LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) child.getLayoutParams();
        final int widthSpec = getChildMeasureSpec(getItemWidth(), getPaddingLeft() + getPaddingRight() + widthUsed, layoutParams.width,
                canScrollHorizontally());
        final int heightSpec = getChildMeasureSpec(getItemHeight(),
                getPaddingTop() + getPaddingBottom() + heightUsed, layoutParams.height,
                canScrollVertically());
        child.measure(widthSpec, heightSpec);

    }

    private int getItemHeight(){

        int parentHeight = getHeight();
        int itemHeight = parentHeight / heightCount;
        return itemHeight;

    }

    private int getItemWidth(){

        int parentWidth = getWidth();
        int itemWidth = parentWidth / widthCount;
        return itemWidth;

    }

}
