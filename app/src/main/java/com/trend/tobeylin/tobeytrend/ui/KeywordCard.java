package com.trend.tobeylin.tobeytrend.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

    public static final String TAG = KeywordCard.class.getSimpleName();

    private Context context = null;
    private List<TypeEditText> keywordTypeEditTexts = null;
    private List<LinearLayout> backgroundLinearLayouts = null;
    private OnStateChangeListener listener = null;

    private final int DEFAULT_VIEW_BUFFER_SIZE = 2;
    private final long DEFAULT_ANIMATION_DURATION = 600;
    private List<View> keywordCardViews = null;
    private int currentViewIndex = 0;
    private long animationDuration = 0;
    private String keyword = "keyword";
    private int cardWidth = 0;
    private int cardHeight = 0;


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
        animationDuration = DEFAULT_ANIMATION_DURATION;
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
            int visibility = (i != 0)? View.INVISIBLE: View.VISIBLE;
            keywordCard.setVisibility(visibility);

        }

    }

    public void setOnStateChangeListener(OnStateChangeListener listener){
        this.listener = listener;
    }

    public void removeOnStateChangeListener(){
        this.listener = null;
    }

    public void setKeyword(String keyword, int backgroundColor) {

        this.keyword = keyword;
        int nextViewIndex = (currentViewIndex + 1) % DEFAULT_VIEW_BUFFER_SIZE;
        View currentView = keywordCardViews.get(currentViewIndex);
        View nextView = keywordCardViews.get(nextViewIndex);

        keywordTypeEditTexts.get(nextViewIndex).setText("");
        backgroundLinearLayouts.get(nextViewIndex).setBackgroundColor(backgroundColor);

        transition(currentView, nextView);

    }

    private void transition(final View currentView, final View nextView){

        currentView.setVisibility(View.VISIBLE);
        nextView.setVisibility(View.VISIBLE);

        List<Animation> moveAnimations = getRandomDirectionAnimation();
        Animation moveInAnimation = moveAnimations.get(0);
        Animation moveOutAnimation = moveAnimations.get(1);

        moveInAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                Log.i(TAG, "move in end");

                View previosView = keywordCardViews.get(currentViewIndex);
                keywordTypeEditTexts.get(currentViewIndex).removeOnTypeListener();
                previosView.setVisibility(View.GONE);

                currentViewIndex = (currentViewIndex + 1) % DEFAULT_VIEW_BUFFER_SIZE;
                View currentView = keywordCardViews.get(currentViewIndex);
                currentView.setVisibility(VISIBLE);
                keywordTypeEditTexts.get(currentViewIndex).setOnTypeListener(KeywordCard.this);
                keywordTypeEditTexts.get(currentViewIndex).startTypeText(keyword);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        currentView.startAnimation(moveOutAnimation);
        nextView.startAnimation(moveInAnimation);

    }
    private List<Animation> getRandomDirectionAnimation(){

        AnimationDirection[] directions = AnimationDirection.values();
        Random random = new Random();
        int randomDirectionIndex = random.nextInt(directions.length);
        AnimationDirection randomDirection = directions[randomDirectionIndex];
        return getDirectionAnimation(randomDirection);

    }

    private List<Animation> getDirectionAnimation(AnimationDirection direction) {

        Animation moveInAnimation;
        Animation moveOutAnimation;
        switch (direction){
            case Right:
                moveInAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_right_move_in);
                moveOutAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_right_move_out);
                break;
            case Left:
                moveInAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_left_move_in);
                moveOutAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_left_move_out);
                break;
            case Top:
                moveInAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_top_move_in);
                moveOutAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_top_move_out);
                break;
            case Bottom:
                moveInAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_bottom_move_in);
                moveOutAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_bottom_move_out);
                break;
            default:
                moveInAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_right_move_in);
                moveOutAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_right_move_out);
        }
        List<Animation> moveAnimations = new ArrayList<>();
        moveAnimations.add(moveInAnimation);
        moveAnimations.add(moveOutAnimation);
        return moveAnimations;

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

        backgroundLinearLayouts.get((currentViewIndex + 1) % DEFAULT_VIEW_BUFFER_SIZE).setBackgroundColor(color);

    }
}
