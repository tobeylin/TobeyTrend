package com.trend.tobeylin.tobeytrend.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.trend.tobeylin.tobeytrend.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by tobeylin on 15/6/16.
 */
public class KeywordCard extends RelativeLayout implements TypeEditText.OnTypeListener {

    private Context context = null;
    private List<TypeEditText> keywordTypeEditTexts = null;
    private List<LinearLayout> backgroundLinearLayouts = null;
    private OnStateChangeListener listener = null;

    private final int DEFAULT_VIEW_BUFFER_SIZE = 2;
    private List<View> keywordCardViews = null;
    private int currentViewIndex = 0;
    private int animationDuration = 0;

    public enum AnimationDirection {
        Right, Left, Top, Bottom;
    }

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
        keywordCardViews = new ArrayList<>();
        keywordTypeEditTexts = new ArrayList<>();
        backgroundLinearLayouts = new ArrayList<>();
        animationDuration = 600;
        initCardView();

    }

    private void initCardView(){

        for(int i = 0; i < DEFAULT_VIEW_BUFFER_SIZE; ++i) {

            View keywordCard = LayoutInflater.from(this.context).inflate(R.layout.keyword_card, null, false);
            keywordCardViews.add(keywordCard);

            TypeEditText keywordTypeEditText = (TypeEditText) keywordCard.findViewById(R.id.keywordCard_keywordTypeTextView);
            keywordTypeEditText.setOnTypeListener(this);
            keywordTypeEditTexts.add(keywordTypeEditText);

            LinearLayout backgroundLinearLayout = (LinearLayout) keywordCard.findViewById(R.id.keywordCard_backgrounLinearLayout);
            backgroundLinearLayouts.add(backgroundLinearLayout);

            LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            addView(keywordCard, layoutParams);
            int visibility = (i != 0)? View.GONE: View.VISIBLE;
            keywordCard.setVisibility(visibility);

        }

    }

    public void setOnStateChangeListener(OnStateChangeListener listener){
        this.listener = listener;
    }

    public void removeOnStateChangeListener(){
        this.listener = null;
    }

    public void setKeyword(String keyword) {

        int nextViewIndex = (currentViewIndex + 1) % DEFAULT_VIEW_BUFFER_SIZE;

        View currentView = keywordCardViews.get(currentViewIndex);
        View nextView = keywordCardViews.get(nextViewIndex);
        nextView.setVisibility(View.GONE);

        keywordTypeEditTexts.get(nextViewIndex).startTypeText(keyword);
        transition(currentView, nextView);
        currentViewIndex = nextViewIndex;
    }

    private void transition(final View currentView, final View nextView){

        nextView.setVisibility(View.VISIBLE);
        ObjectAnimator transitionAnimator = getRandomAnimator(currentView, nextView);
        transitionAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                currentView.setVisibility(View.GONE);
                //TO-DO: start type next view.
            }
        });

        transitionAnimator.setDuration(animationDuration);
        transitionAnimator.start();

    }

    private ObjectAnimator getRandomAnimator(final View currentView, final View nextView){

        AnimationDirection[] directions = AnimationDirection.values();
        Random random = new Random();
        int randomDirectionIndex = random.nextInt(directions.length);
        AnimationDirection randomDirection = directions[randomDirectionIndex];

        return  createAnimator(randomDirection, currentView, nextView);
    }

    private ObjectAnimator createAnimator(AnimationDirection direction, final View currentView, final View nextView){

        ObjectAnimator objectAnimator;
        switch (direction) {
            case Right:
                objectAnimator = ObjectAnimator.ofFloat(nextView, "translationX", getWidth(), 0f);
                break;
            case Left:
                objectAnimator = ObjectAnimator.ofFloat(nextView, "translationX", 0 - getWidth(), 0f);
                break;
            case Top:
                objectAnimator = ObjectAnimator.ofFloat(nextView, "translationY", 0 - getHeight(), 0f);
                break;
            case Bottom:
                objectAnimator = ObjectAnimator.ofFloat(nextView, "translationY", getHeight(), 0f);
                break;
            default:
                objectAnimator = ObjectAnimator.ofFloat(nextView, "translationX", getWidth(), 0f);
        }

        return objectAnimator;

    }

    private void crossfade(final View currentView, final View nextView){

        nextView.setAlpha(0f);
        nextView.setVisibility(View.VISIBLE);
        nextView.animate()
                .alpha(1f)
                .setDuration(animationDuration)
                .setListener(null);

        currentView.animate()
                .alpha(0f)
                .setDuration(animationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        currentView.setVisibility(View.GONE);
                    }
                });

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

        backgroundLinearLayouts.get(currentViewIndex).setBackgroundColor(color);

    }
}
