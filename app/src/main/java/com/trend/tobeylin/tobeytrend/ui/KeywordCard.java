package com.trend.tobeylin.tobeytrend.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
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

        nextView.setVisibility(View.VISIBLE);
        keywordTypeEditTexts.get(nextViewIndex).setText("");
        backgroundLinearLayouts.get(nextViewIndex).setBackgroundColor(backgroundColor);

        currentView.setVisibility(View.VISIBLE);

        transition(currentView, nextView);

    }

    private void transition(final View currentView, final View nextView){

        currentView.setVisibility(View.VISIBLE);
        nextView.setVisibility(View.VISIBLE);

        List<ObjectAnimator> moveAnimators = getRandomAnimator(currentView, nextView);
        ObjectAnimator moveInAnimator = moveAnimators.get(0);
        ObjectAnimator moveOutAnimator = moveAnimators.get(1);

        moveInAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {

                Log.i(TAG, "move in end");

                View previosView = keywordCardViews.get(currentViewIndex);
                previosView.setVisibility(View.GONE);
                Log.i(TAG, "previous view = (" + previosView.getTop() + ", " + previosView.getLeft() + ")");

                currentViewIndex = (currentViewIndex + 1) % DEFAULT_VIEW_BUFFER_SIZE;
                View currentView = keywordCardViews.get(currentViewIndex);
                currentView.setVisibility(VISIBLE);

                keywordTypeEditTexts.get(currentViewIndex).startTypeText(keyword);
                Log.i(TAG, "current view = (" + currentView.getTop() + ", " + currentView.getLeft() + ") " + keyword);



            }
        });

        moveOutAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });

        moveInAnimator.setDuration(animationDuration);
        moveOutAnimator.setDuration(animationDuration);
        moveInAnimator.start();
        moveOutAnimator.start();

    }

    private List<ObjectAnimator> getRandomAnimator(final View moveOutView, final View moveInView){

        AnimationDirection[] directions = AnimationDirection.values();
        Random random = new Random();
        int randomDirectionIndex = random.nextInt(2);
        AnimationDirection randomDirection = directions[randomDirectionIndex];

        List<ObjectAnimator> moveAnimators = createMoveAnimator(randomDirection, moveOutView, moveInView);
        return moveAnimators;

    }

    private List<ObjectAnimator> createMoveAnimator(AnimationDirection direction, final View moveOutView, final View moveInView){

        ObjectAnimator moveInAnimator;
        ObjectAnimator moveOutAnimator;
        switch (direction) {
            case Right:
                moveInAnimator = ObjectAnimator.ofFloat(moveInView, "translationX", getWidth(), 0f);
                moveOutAnimator = ObjectAnimator.ofFloat(moveOutView, "translationX", 0f, 0 - getWidth());
                break;
            case Left:
                moveInAnimator = ObjectAnimator.ofFloat(moveInView, "translationX", 0 - getWidth(), 0f);
                moveOutAnimator = ObjectAnimator.ofFloat(moveOutView, "translationX", 0f, getWidth());
                break;
            case Top:
                moveInAnimator = ObjectAnimator.ofFloat(moveInView, "translationY", 0 - getHeight(), 0f);
                moveOutAnimator = ObjectAnimator.ofFloat(moveOutView, "translationY", 0f, getHeight());
                break;
            case Bottom:
                moveInAnimator = ObjectAnimator.ofFloat(moveInView, "translationY", getHeight(), 0f);
                moveOutAnimator = ObjectAnimator.ofFloat(moveOutView, "translationY", 0f, 0 - getHeight());
                break;
            default:
                moveInAnimator = ObjectAnimator.ofFloat(moveInView, "translationX", getWidth(), 0f);
                moveOutAnimator = ObjectAnimator.ofFloat(moveOutView, "translationX", 0f, 0 - getWidth());
        }
        List<ObjectAnimator> moveAnimators = new ArrayList<>();
        moveAnimators.add(moveInAnimator);
        moveAnimators.add(moveOutAnimator);

        return moveAnimators;

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
