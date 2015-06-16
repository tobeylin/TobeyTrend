package com.trend.tobeylin.tobeytrend.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by tobeylin on 15/6/16.
 */
public class TypeEditText extends EditText {

    private String text = "";
    private Timer typeTimer = null;
    private long TYPE_DELAY_TIME = 300;
    private OnTypeListener listener = null;

    public interface OnTypeListener {

        void onTypeStart();
        void onTypeFinish();

    }

    public TypeEditText(Context context) {
        super(context);
    }

    public TypeEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TypeEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnTypeListener(OnTypeListener listener){
        this.listener = listener;
    }

    public void removeOnTypeListener(){
        this.listener = null;
    }

    public void startTypeText(String text) {

        this.text = text;
        setText("");
        typeTimer = new Timer();
        typeTimer.schedule(new TypeTimerTask(), TYPE_DELAY_TIME, TYPE_DELAY_TIME);
        if(listener != null) {
            listener.onTypeStart();
        }

    }

    private class TypeTimerTask extends TimerTask {
        @Override
        public void run() {
            post(new Runnable() {
                @Override
                public void run() {
                    String currentText = getText().toString();
                    if(currentText.length() < text.length()) {
                        String newText = text.substring(0, currentText.length() + 1);
                        setText(newText);
                        setSelection(newText.length());
                    } else {
                        if(listener != null){
                            listener.onTypeFinish();
                        }
                        typeTimer.cancel();
                        typeTimer = null;
                    }
                }
            });
        }
    };

}
