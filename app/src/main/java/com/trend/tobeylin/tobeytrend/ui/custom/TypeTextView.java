package com.trend.tobeylin.tobeytrend.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.trend.tobeylin.tobeytrend.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by tobeylin on 15/6/30.
 */
public class TypeTextView extends LinearLayout {

    private TextView textView;
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
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.type_text_view, this, true);
        textView = (TextView) findViewById(R.id.typeTextView_textView);
        textView.setText("");
        textCursorView = findViewById(R.id.typeTextView_textCursorView);
        textCursorView.setVisibility(View.INVISIBLE);
    }

    public void setCursorVisibility(boolean isVisible){
        isCursorVisible = isVisible;
    }

    private void startCursorBlink(){
        prepareCursorTimer();
        cursorBlinkTimer.schedule(new CursorBlinkTimerTask(), TEXT_CURSOR_BLINK_DELAY_TIME, DEFAULT_TEXT_CURSOR_BLINK_SPEED);
    }

    private void cancelCursorBlink(){
        cursorBlinkTimer.cancel();
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
        clearText();
        fullText = text;
        prepareTypeTimer();
        typeTimer.schedule(new TypeTimerTask(), TYPE_DELAY_TIME, DEFAULT_TYPE_SPEED);
        if(typeListener != null) {
            typeListener.onTypeStart();
        }
    }

    private void prepareTypeTimer(){
        if(typeTimer != null){
            typeTimer.cancel();
        }
        typeTimer = new Timer();
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
        textCursorView.setVisibility(View.INVISIBLE);
    }

    private void showTextCursor() {
        textCursorView.setVisibility(View.VISIBLE);
    }

}
