package com.trend.tobeylin.tobeytrend.ui.custom;

import android.content.Context;
import android.graphics.Point;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

    private TextView textView;
    private Layout textViewLayout;
    private View textCursorView;
    private String fullText = "";
    private Timer typeTimer = null;
    private Timer cursorBlinkTimer = null;
    private static long TYPE_DELAY_TIME = 0;
    private static long DEFAULT_TYPE_SPEED = 150;
    private static long TEXT_CURSOR_BLINK_DELAY_TIME = 0;
    private static long DEFAULT_TEXT_CURSOR_BLINK_SPEED = 700;
    private OnTypeListener typeListener = null;
    private boolean isCursorVisible = true;
    private int cursorWidth = DEFAULT_CURSOR_WIDTH;

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
        init(context);
    }

    public TypeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.type_text_view, this, true);
        textView = (TextView) findViewById(R.id.typeTextView_textView);
        textView.setText("");
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 70);
        textCursorView = findViewById(R.id.typeTextView_textCursorView);
        textCursorView.setVisibility(View.GONE);
    }

    public void setOnTypeListener(OnTypeListener listener){
        typeListener = listener;
    }

    public void removeOnTypeListener(){
        typeListener = null;
    }

    public void setTextSize(int unit, float size){
        textView.setTextSize(unit, size);
    }

    public void setCursorVisibility(boolean isVisible){
        isCursorVisible = isVisible;
    }

    private void startCursorBlink(){
        prepareCursorTimer();
        textViewLayout = textView.getLayout();
        cursorBlinkTimer.schedule(new CursorBlinkTimerTask(), TEXT_CURSOR_BLINK_DELAY_TIME, DEFAULT_TEXT_CURSOR_BLINK_SPEED);
    }

    private void cancelCursorBlink(){
        if(cursorBlinkTimer != null) {
            cursorBlinkTimer.cancel();
        }
        hideTextCursor();
    }

    private void prepareCursorTimer(){
        if(cursorBlinkTimer != null) {
            cursorBlinkTimer.cancel();
        }
        cursorBlinkTimer = new Timer();
    }

    public String getCurrentText(){
        return textView.getText().toString();
    }

    public void clearText(){
        textView.setText("");
    }

    public String getTypeText(){
        return fullText;
    }

    public void startTypeText(String text){
        cancelCursorBlink();
        clearText();
        fullText = text;
        prepareTypeTimer();
        invalidate();
        typeTimer.schedule(new TypeTimerTask(), TYPE_DELAY_TIME, DEFAULT_TYPE_SPEED);
        if(typeListener != null) {
            typeListener.onTypeStart();
        }
    }

    private void prepareTypeTimer(){
        cancelTypeText();
        typeTimer = new Timer();
    }

    public void cancelTypeText(){
        if(typeTimer != null){
            typeTimer.cancel();
        }
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

    private void toggleTextCursor(){
        int cursorVisibility = textCursorView.getVisibility();
        switch (cursorVisibility){
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

    private void hideTextCursor(){
        textCursorView.setVisibility(View.GONE);
    }

    private void showTextCursor() {
        LayoutParams cursorLayoutParams = getCursorLayoutParams();
        textCursorView.setLayoutParams(cursorLayoutParams);
        textCursorView.setVisibility(View.VISIBLE);
    }

    private LayoutParams getCursorLayoutParams(){
        float xOffset = getCursorXOffset();
        float yOffset = getCursorYOffset();
        int cursorHeight = textView.getLineHeight();
        RelativeLayout.LayoutParams cursorLayoutParams = new LayoutParams(cursorWidth, cursorHeight);
        cursorLayoutParams.setMargins((int) xOffset, (int) yOffset, 0, 0);
        return cursorLayoutParams;
    }

    private float getCursorXOffset(){
        float xOffset = 0;
        if(textViewLayout != null) {
            int lineCount = textViewLayout.getLineCount();
            boolean textDirection = getDirection();
            if (textDirection){
                xOffset = textViewLayout.getLineWidth(lineCount - 1);
            } else {
                xOffset = textViewLayout.getWidth() - textViewLayout.getLineWidth(lineCount - 1);
            }
        }
        return xOffset;
    }

    private float getCursorYOffset(){
        float yOffset = 0;
        if(textViewLayout != null) {
            int lineCount = textViewLayout.getLineCount();
            yOffset = textView.getLineHeight() * (lineCount - 1);
        }
        return yOffset;
    }

    /**
     * Get the text direction.
     * @return True, left to right; False, right to left
     */
    private boolean getDirection(){
        Bidi bidi = new Bidi(textView.getText().toString(), Bidi.DIRECTION_DEFAULT_LEFT_TO_RIGHT);
        return bidi.baseIsLeftToRight();
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if(VISIBLE != visibility){
            cancelTypeText();
            cancelCursorBlink();
        }
    }
}
