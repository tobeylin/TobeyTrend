package com.trend.tobeylin.tobeytrend.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.trend.tobeylin.tobeytrend.R;

/**
 * Created by tobeylin on 15/6/16.
 */
public class KeywordCard extends LinearLayout implements TypeEditText.OnTypeListener {

    private Context context = null;
    private TypeEditText keywordTypeEditText = null;
    private LinearLayout backgroundLinearLayout = null;
    private OnStateChangeListener listener = null;

    public interface OnStateChangeListener{

        void onKeywordTypeStart();
        void onKeywordTypeFinish();

    }

    public KeywordCard(Context context) {
        super(context);
        init(context);
    }

    public KeywordCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public KeywordCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){

        this.context = context;
        initCardView();

    }

    private void initCardView(){

        View keywordCardView = LayoutInflater.from(this.context).inflate(R.layout.keyword_card, null, false);
        keywordTypeEditText = (TypeEditText) keywordCardView.findViewById(R.id.keywordCard_keywordTypeTextView);
        keywordTypeEditText.setOnTypeListener(this);
        backgroundLinearLayout = (LinearLayout) keywordCardView.findViewById(R.id.keywordCard_backgrounLinearLayout);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(keywordCardView, layoutParams);

    }

    public void setOnStateChangeListener(OnStateChangeListener listener){
        this.listener = listener;
    }

    public void removeOnStateChangeListener(){
        this.listener = null;
    }

    public void setKeyword(String keyword) {

        keywordTypeEditText.startTypeText(keyword);

    }

    @Override
    public void onTypeStart() {

        if(listener != null){
            listener.onKeywordTypeStart();
        }

    }

    @Override
    public void onTypeFinish() {

        if(listener != null){
            listener.onKeywordTypeFinish();
        }

    }

    public void setBackgroundColor(int color) {

        backgroundLinearLayout.setBackgroundColor(color);

    }
}
