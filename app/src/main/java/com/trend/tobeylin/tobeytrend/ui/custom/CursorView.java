package com.trend.tobeylin.tobeytrend.ui.custom;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by tobeylin on 15/7/17.
 */
public class CursorView extends View {

    public static long DEFAULT_TEXT_CURSOR_BLINK_SPEED = 700;

    private long blinkSpeed = DEFAULT_TEXT_CURSOR_BLINK_SPEED;

    public CursorView(Context context) {
        super(context);
    }

    public CursorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CursorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void startBlink() {
        Animation blinkAnimation = new AlphaAnimation(0.0f, 1.0f);
        blinkAnimation.setDuration(50);
        blinkAnimation.setStartOffset(blinkSpeed);
        blinkAnimation.setRepeatMode(Animation.REVERSE);
        blinkAnimation.setRepeatCount(Animation.INFINITE);
        startAnimation(blinkAnimation);
    }

    public void resetCursor() {
        clearAnimation();
        setVisibility(View.GONE);
    }

}
