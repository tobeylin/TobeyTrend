package com.trend.tobeylin.tobeytrend.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.trend.tobeylin.tobeytrend.R;

import java.text.Bidi;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by tobeylin on 15/6/30.
 */
public class TypeTextView extends RelativeLayout {

    public static final String TAG = TypeTextView.class.getSimpleName();
    private static final int DEFAULT_CURSOR_WIDTH = 1;
    private static final int DEFAULT_TEXT_COLOR = Color.BLACK;
    private static final float DEFAULT_TEXT_SIZE = 12;
    private static final int DEFAULT_TEXT_SHADOW_RADIUS = 1;
    private static final int DEFAULT_TEXT_SHADOW_COLOR = Color.GRAY;
    private static final int DEFAULT_TEXT_SHADOW_DX = 0;
    private static final int DEFAULT_TEXT_SHADOW_DY = 0;
    private static final int DEFAULT_CURSOR_COLOR = Color.BLACK;
    private static long TYPE_DELAY_TIME = 0;
    private static long DEFAULT_TYPE_SPEED = 150;
    private static long TEXT_CURSOR_BLINK_DELAY_TIME = 0;
    private static long DEFAULT_TEXT_CURSOR_BLINK_SPEED = 700;

    private TextView textView;
    private Layout textViewLayout;
    private View textCursorView;
    private String fullText = "";
    private TypeTimer typeTimer = null;
    private CursorBlinkTimer cursorBlinkTimer = null;
    private OnTypeListener typeListener = null;

    private int cursorWidth = DEFAULT_CURSOR_WIDTH;
    private int cursorColor = DEFAULT_CURSOR_COLOR;
    private int textColor = DEFAULT_TEXT_COLOR;
    private float textSize = DEFAULT_TEXT_SIZE;
    private int textShadowRadius = DEFAULT_TEXT_SHADOW_RADIUS;
    private int textShadowColor = DEFAULT_TEXT_SHADOW_COLOR;
    private int textShadowDx = DEFAULT_TEXT_SHADOW_DX;
    private int textShadowDy = DEFAULT_TEXT_SHADOW_DY;

    public interface OnTypeListener {
        void onTypeStart();

        void onTypeFinish();
    }

    public TypeTextView(Context context) {
        super(context);
        init(context);
    }

    public TypeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        init(context);
    }

    public TypeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        init(context);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TypeTextView, 0, 0);
        try {
            textSize = a.getDimension(R.styleable.TypeTextView_textSize, DEFAULT_TEXT_SIZE);
            textColor = a.getColor(R.styleable.TypeTextView_textColor, DEFAULT_TEXT_COLOR);
            cursorColor = a.getColor(R.styleable.TypeTextView_cursorColor, DEFAULT_CURSOR_COLOR);
            textShadowRadius = a.getInteger(R.styleable.TypeTextView_shadowRadius, DEFAULT_TEXT_SHADOW_RADIUS);
            textShadowColor = a.getColor(R.styleable.TypeTextView_shadowColor, DEFAULT_TEXT_SHADOW_COLOR);
            textShadowDx = a.getInteger(R.styleable.TypeTextView_shadowDx, DEFAULT_TEXT_SHADOW_DX);
            textShadowDy = a.getInteger(R.styleable.TypeTextView_shadowDy, DEFAULT_TEXT_SHADOW_DY);
        } finally {
            a.recycle();
        }
    }

    private void init(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.type_text_view, this, true);
        textView = (TextView) findViewById(R.id.typeTextView_textView);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        textView.setTextColor(textColor);
        textView.setShadowLayer(textShadowRadius, textShadowDx, textShadowDy, textShadowColor);
        textCursorView = findViewById(R.id.typeTextView_textCursorView);
        textCursorView.setBackgroundColor(cursorColor);
        textCursorView.setVisibility(View.GONE);
    }

    public void setOnTypeListener(OnTypeListener listener) {
        typeListener = listener;
    }

    public void removeOnTypeListener() {
        typeListener = null;
    }

    public void setTextSize(int unit, float size) {
        textView.setTextSize(unit, size);
    }

    private void startCursorBlink() {
        prepareCursorTimer();
        textViewLayout = textView.getLayout();
        cursorBlinkTimer.startBlink();
    }

    private void prepareCursorTimer() {
        resetCursor();
        cursorBlinkTimer = new CursorBlinkTimer(DEFAULT_TEXT_CURSOR_BLINK_SPEED);
    }

    private void resetCursor() {
        if (cursorBlinkTimer != null) {
            cursorBlinkTimer.cancel();
            cursorBlinkTimer.purge();
        }
        hideTextCursor();
    }

    public String getCurrentText() {
        return textView.getText().toString();
    }

    public void clearText() {
        textView.setText("");
    }

    public void startTypeText(String text) {
        resetCursor();
        clearText();
        fullText = text;
        prepareTypeTimer();
        typeTimer.startTyping();
    }

    public void prepareTypeTimer() {
        resetTypeTimer();
        typeTimer = new TypeTimer(DEFAULT_TYPE_SPEED);
    }

    private void resetTypeTimer() {
        if (typeTimer != null) {
            typeTimer.cancel();
            typeTimer.purge();
        }
    }

    private class TypeTimer extends Timer {

        private long typeSpeed;

        public TypeTimer(long typeSpeed) {
            this.typeSpeed = typeSpeed;
        }

        private void startTyping() {
            schedule(new TypeTimerTask(), 0, typeSpeed);
        }

        private class TypeTimerTask extends TimerTask {
            @Override
            public void run() {
                post(new Runnable() {
                    @Override
                    public void run() {
                        String currentText = getCurrentText();
                        int currentTextLength = currentText.length();
                        if(currentText.length() < fullText.length()) {
                            String appendText = fullText.substring(currentTextLength, currentTextLength + 1);
                            textView.append(appendText);
                        } else {
                            if(typeListener != null){
                                typeListener.onTypeFinish();
                            }
                            typeTimer.cancel();
                            startCursorBlink();
                        }
                    }
                });
            }
        }

    }

    private class CursorBlinkTimer extends Timer {

        private CursorBlinkTimerTask cursorBlinkTimerTask = null;
        private long cursorBlinkSpeed;

        public CursorBlinkTimer(long cursorBlinkSpeed) {
            super();
            cursorBlinkTimerTask = new CursorBlinkTimerTask();
            this.cursorBlinkSpeed = cursorBlinkSpeed;
        }

        public void startBlink() {
            schedule(cursorBlinkTimerTask, 0, cursorBlinkSpeed);
        }

        @Override
        public void cancel() {
            cursorBlinkTimerTask = null;
            super.cancel();
        }
    }

    private class CursorBlinkTimerTask extends TimerTask {
        @Override
        public void run() {
            post(new Runnable() {
                @Override
                public void run() {
                    toggleTextCursor();
                }
            });
        }
    }

    private void toggleTextCursor() {
        int cursorVisibility = textCursorView.getVisibility();
        switch (cursorVisibility) {
            case View.VISIBLE:
                hideTextCursor();
                break;
            case View.GONE:
            case View.INVISIBLE:
                showTextCursor();
                break;
            default:
        }
    }

    private void hideTextCursor() {
        textCursorView.setVisibility(View.GONE);
    }

    private void showTextCursor() {
        LayoutParams cursorLayoutParams = getCursorLayoutParams();
        textCursorView.setLayoutParams(cursorLayoutParams);
        textCursorView.setVisibility(View.VISIBLE);
    }

    private LayoutParams getCursorLayoutParams() {
        float xOffset = getCursorXOffset();
        float yOffset = getCursorYOffset();
        int cursorHeight = textView.getLineHeight();
        RelativeLayout.LayoutParams cursorLayoutParams = new LayoutParams(cursorWidth, cursorHeight);
        cursorLayoutParams.setMargins((int) xOffset, (int) yOffset, 0, 0);
        return cursorLayoutParams;
    }

    private float getCursorXOffset() {
        float xOffset = 0;
        if (textViewLayout != null) {
            int lineCount = textViewLayout.getLineCount();
            boolean textDirection = getDirection();
            if (textDirection) {
                xOffset = textViewLayout.getLineWidth(lineCount - 1);
            } else {
                xOffset = textViewLayout.getWidth() - textViewLayout.getLineWidth(lineCount - 1);
            }
        }
        return xOffset;
    }

    private float getCursorYOffset() {
        float yOffset = 0;
        if (textViewLayout != null) {
            int lineCount = textViewLayout.getLineCount();
            yOffset = textView.getLineHeight() * (lineCount - 1);
        }
        return yOffset;
    }

    /**
     * Get the text direction.
     *
     * @return True, left to right; False, right to left
     */
    private boolean getDirection() {
        Bidi bidi = new Bidi(textView.getText().toString(), Bidi.DIRECTION_DEFAULT_LEFT_TO_RIGHT);
        return bidi.baseIsLeftToRight();
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (VISIBLE != visibility) {
            resetTypeTimer();
            resetCursor();
        }
    }

}
