package com.trend.tobeylin.tobeytrend.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
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
public class TypeTextView extends RelativeLayout implements View.OnLayoutChangeListener {

    public static final String TAG = TypeTextView.class.getSimpleName();
    public static final int DEFAULT_CURSOR_WIDTH = 3;
    public static final int DEFAULT_TEXT_COLOR = Color.BLACK;
    public static final float DEFAULT_TEXT_SIZE = 12;
    public static final int DEFAULT_TEXT_SHADOW_RADIUS = 1;
    public static final int DEFAULT_TEXT_SHADOW_COLOR = Color.GRAY;
    public static final int DEFAULT_TEXT_SHADOW_DX = 0;
    public static final int DEFAULT_TEXT_SHADOW_DY = 0;
    public static final int DEFAULT_CURSOR_COLOR = Color.BLACK;
    public static long DEFAULT_TYPE_SPEED = 150;

    private TextView textView;
    private Layout textViewLayout;
    private CursorView cursorView;
    private String fullText = "";
    private TypeTimer typeTimer = null;
    private boolean updateCursor = false;
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
        this(context, null);
    }

    public TypeTextView(Context context, TextView textView, CursorView cursorView){
        this(context, null);
        this.textView = textView;
        this.cursorView = cursorView;
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
        } catch (Exception e) {
            e.printStackTrace();
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
        cursorView = (CursorView) findViewById(R.id.typeTextView_textCursorView);
        cursorView.setBackgroundColor(cursorColor);
        cursorView.setVisibility(View.GONE);
    }

    public int getCursorColor() {
        return cursorColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public float getTextSize() {
        return textSize;
    }

    public int getTextShadowRadius() {
        return textShadowRadius;
    }

    public int getTextShadowColor() {
        return textShadowColor;
    }

    public int getTextShadowDx() {
        return textShadowDx;
    }

    public int getTextShadowDy() {
        return textShadowDy;
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

    public String getCurrentText() {
        return textView.getText().toString();
    }

    public String getFullText(){
        return fullText;
    }

    public void clearText() {
        textView.setText("");
    }

    public void showTextCursor() {
        cursorView.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams cursorLayoutParams = getCursorLayoutParams();
        cursorView.setLayoutParams(cursorLayoutParams);
    }

    public void startTypeText(String text) {

        if(text == null || text.isEmpty()){
            fullText = "";
            return;
        }

        fullText = text;

        //Prepare cursor
        cursorView.resetCursor();

        //Prepare text view
        clearText();

        //Handle typing
        prepareTypeTimer();
        typeTimer.startTyping();

    }

    public void prepareTypeTimer() {
        resetTypeTimer();
        textView.addOnLayoutChangeListener(this);
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
                        if (currentText.length() < fullText.length()) {
                            String appendText = fullText.substring(currentTextLength, currentTextLength + 1);
                            textView.append(appendText);
                            updateCursor = true;
                        } else {
                            typeTimer.cancel();
                            typeTimer.purge();
                            cursorView.startBlink();
                            textView.removeOnLayoutChangeListener(TypeTextView.this);
                            if (typeListener != null) {
                                typeListener.onTypeFinish();
                            }
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (updateCursor) {
            textViewLayout = textView.getLayout();
            post(new Runnable() {
                @Override
                public void run() {
                    showTextCursor();
                }
            });
            updateCursor = false;
        }
    }

    public RelativeLayout.LayoutParams getCursorLayoutParams() {
        float xOffset = getCursorXOffset();
        float yOffset = getCursorYOffset();
        int cursorHeight = textView.getLineHeight();
        RelativeLayout.LayoutParams cursorLayoutParams = new RelativeLayout.LayoutParams(cursorWidth, cursorHeight);
        cursorLayoutParams.setMargins((int) xOffset, (int) yOffset, 0, 0);
        return cursorLayoutParams;
    }

    public float getCursorXOffset() {
        float xOffset = 0;
        if (textViewLayout != null) {
            int lineCount = textView.getLineCount();
            boolean textDirection = getDirection();
            if (textDirection) {
                xOffset = textViewLayout.getLineWidth(lineCount - 1);
            } else {
                xOffset = textViewLayout.getWidth() - textViewLayout.getLineWidth(lineCount - 1);
            }
        } else {
            Log.w(TAG, "textViewLayout is null");
        }
        return xOffset;
    }

    public float getCursorYOffset() {
        float yOffset = 0;
        if (textViewLayout != null) {
            int lineCount = textViewLayout.getLineCount();
            yOffset = textView.getLineHeight() * (lineCount - 1);
        } else {
            Log.w(TAG, "textViewLayout is null");
        }
        return yOffset;
    }

    /**
     * Get the text direction.
     *
     * @return True, left to right; False, right to left
     */
    public boolean getDirection() {
        Bidi bidi = new Bidi(textView.getText().toString(), Bidi.DIRECTION_DEFAULT_LEFT_TO_RIGHT);
        return bidi.baseIsLeftToRight();
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (VISIBLE != visibility) {
            resetTypeTimer();
            post(new Runnable() {
                @Override
                public void run() {
                    cursorView.resetCursor();
                }
            });
        }
    }

}
