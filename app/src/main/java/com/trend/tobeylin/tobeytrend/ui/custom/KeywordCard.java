package com.trend.tobeylin.tobeytrend.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.trend.tobeylin.tobeytrend.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by tobeylin on 15/6/16.
 */
public class KeywordCard extends RelativeLayout implements TypeTextView.OnTypeListener, View.OnClickListener {

    public static final String TAG = KeywordCard.class.getSimpleName();

    private static final int DEFAULT_VIEW_WIDTH = 300;
    private static final int DEFAULT_VIEW_HEIGHT = 300;
    private static final int DEFAULT_BACKGROUND_COLOR_YELLOW_RES = R.color.keyword_card_default_background_yellow;
    private static final int DEFAULT_BACKGROUND_COLOR_RED_RES = R.color.keyword_card_default_background_red;
    private static final int DEFAULT_BACKGROUND_COLOR_BLUE_RES = R.color.keyword_card_default_background_blue;
    private static final int DEFAULT_BACKGROUND_COLOR_GREEN_RES = R.color.keyword_card_default_background_green;
    private Context context = null;
    private List<TypeTextView> keywordTypeTextViews = null;
    private List<RelativeLayout> backgroundLinearLayouts = null;
    private OnStateChangeListener listener = null;
    private OnKeywordClickListener keywordClickListener = null;

    private final int DEFAULT_VIEW_BUFFER_SIZE = 2;
    private List<View> keywordCardViews = null;
    private int currentViewIndex = 0;
    private String keyword = "keyword";
    private int[] backgroundColors;
    private int backgroundColorIndex = 0;

    public enum AnimationDirection {
        Right, Left, Top, Bottom;
    }

    public interface OnStateChangeListener{

        void onKeywordTypeStart();
        void onKeywordTypeFinish();

    }

    public interface OnKeywordClickListener{

        void onKeywordClick();

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
        keywordTypeTextViews = new ArrayList<>();
        backgroundLinearLayouts = new ArrayList<>();
        backgroundColors = getDefaultBackgroundColors();
        initCardView();

    }

    private int[] getDefaultBackgroundColors(){

        int[] defaultColors = new int[4];
        defaultColors[0] = context.getResources().getColor(DEFAULT_BACKGROUND_COLOR_YELLOW_RES);
        defaultColors[1] = context.getResources().getColor(DEFAULT_BACKGROUND_COLOR_RED_RES);
        defaultColors[2] = context.getResources().getColor(DEFAULT_BACKGROUND_COLOR_BLUE_RES);
        defaultColors[3] = context.getResources().getColor(DEFAULT_BACKGROUND_COLOR_GREEN_RES);
        return defaultColors;

    }

    private void initCardView(){

        for(int i = 0; i < DEFAULT_VIEW_BUFFER_SIZE; ++i) {

            View keywordCard = LayoutInflater.from(this.context).inflate(R.layout.keyword_card, this, false);
            keywordCardViews.add(keywordCard);

            TypeTextView keywordTypeTextView = (TypeTextView) keywordCard.findViewById(R.id.keywordCard_keywordTypeTextView);
            keywordTypeTextView.setTag("keywordTypeTextView_" + i);
            keywordTypeTextView.setOnTypeListener(this);
            keywordTypeTextView.setOnClickListener(this);
            keywordTypeTextViews.add(keywordTypeTextView);

            RelativeLayout backgroundRelativeLayout = (RelativeLayout) keywordCard.findViewById(R.id.keywordCard_backgrounLinearLayout);
            backgroundRelativeLayout.setOnClickListener(this);
            backgroundLinearLayouts.add(backgroundRelativeLayout);

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

    public void setOnKeywordClickListener(OnKeywordClickListener listener){
        this.keywordClickListener = listener;
    }

    public void removeOnKeywordClickListener(){
        this.keywordClickListener = null;
    }

    public String getKeyword(){
        return this.keyword;
    }

    public void setKeyword(String keyword) {

        this.keyword = keyword;
        int nextViewIndex = (currentViewIndex + 1) % DEFAULT_VIEW_BUFFER_SIZE;
        View currentView = keywordCardViews.get(currentViewIndex);
        View nextView = keywordCardViews.get(nextViewIndex);

        keywordTypeTextViews.get(nextViewIndex).clearText();
        backgroundLinearLayouts.get(nextViewIndex).setBackgroundColor(getBackgroundColor());

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

                View previosView = keywordCardViews.get(currentViewIndex);
                keywordTypeTextViews.get(currentViewIndex).removeOnTypeListener();
                previosView.setVisibility(View.GONE);

                currentViewIndex = (currentViewIndex + 1) % DEFAULT_VIEW_BUFFER_SIZE;
                View currentView = keywordCardViews.get(currentViewIndex);
                currentView.setVisibility(VISIBLE);
                keywordTypeTextViews.get(currentViewIndex).setOnTypeListener(KeywordCard.this);
                keywordTypeTextViews.get(currentViewIndex).startTypeText(keyword);

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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.keywordCard_keywordTypeTextView:
                Log.i(TAG, "keywordCard_keywordTypeTextView click");
                if(keywordClickListener != null){
                    keywordClickListener.onKeywordClick();
                }
                break;
            case R.id.keywordCard_backgrounLinearLayout:
                Log.i(TAG, "keywordCard_backgrounLinearLayout click");
                break;
            default:
        }

    }

    /**
     * Set a background color list. The background color of keyword card will be set according to the order of this list
     * @param backgroundColors
     */
    public void setBackgroundColors(int[] backgroundColors){
        this.backgroundColors = backgroundColors;
        Random random  = new Random();
        backgroundColorIndex = random.nextInt(this.backgroundColors.length);
    }

    private int getBackgroundColor(){

        backgroundColorIndex = (backgroundColorIndex == backgroundColors.length)? 0: backgroundColorIndex;
        return backgroundColors[backgroundColorIndex++];

    }

    public void setKeywordTextSize(float textSizeSp){
        keywordTypeTextViews.get(0).setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSp);
        keywordTypeTextViews.get(1).setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSp);
    }
}
