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
public class KeywordCard extends RelativeLayout implements TypeEditText.OnTypeListener {

    public static final String TAG = KeywordCard.class.getSimpleName();

    private static final int DEFAULT_VIEW_WIDTH = 300;
    private static final int DEFAULT_VIEW_HEIGHT = 300;
    private Context context = null;
    private List<TypeEditText> keywordTypeEditTexts = null;
    private List<RelativeLayout> backgroundLinearLayouts = null;
    private OnStateChangeListener listener = null;

    private final int DEFAULT_VIEW_BUFFER_SIZE = 2;
    private final long DEFAULT_ANIMATION_DURATION = 600;
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
        initCardView();

    }

    private void initCardView(){

        for(int i = 0; i < DEFAULT_VIEW_BUFFER_SIZE; ++i) {

            View keywordCard = LayoutInflater.from(this.context).inflate(R.layout.keyword_card, null, false);
            keywordCardViews.add(keywordCard);

            TypeEditText keywordTypeEditText = (TypeEditText) keywordCard.findViewById(R.id.keywordCard_keywordTypeTextView);
            keywordTypeEditText.setOnTypeListener(this);
            keywordTypeEditTexts.add(keywordTypeEditText);

            RelativeLayout backgroundRelativeLayout = (RelativeLayout) keywordCard.findViewById(R.id.keywordCard_backgrounLinearLayout);
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

    public void setKeyword(String keyword) {

        this.keyword = keyword;
        int nextViewIndex = (currentViewIndex + 1) % DEFAULT_VIEW_BUFFER_SIZE;
        View currentView = keywordCardViews.get(currentViewIndex);
        View nextView = keywordCardViews.get(nextViewIndex);

        keywordTypeEditTexts.get(nextViewIndex).setText("");
        backgroundLinearLayouts.get(nextViewIndex).setBackgroundColor(getBackgroundColor());

        transition(currentView, nextView);

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

    /**
     * Set the background color of next keyword.
     * @param color
     */
    public void setBackgroundColor(int color) {

        backgroundLinearLayouts.get((currentViewIndex + 1) % DEFAULT_VIEW_BUFFER_SIZE).setBackgroundColor(color);

    }

    /**
     * Set a background color list. The background color of keyword card will be set according to the order of this list
     * @param backgroundColors
     */
    public void setBackgroundColors(int[] backgroundColors){
        this.backgroundColors = backgroundColors;
    }

    private int getBackgroundColor(){

        backgroundColorIndex = (backgroundColorIndex == backgroundColors.length)? 0: backgroundColorIndex;
        return backgroundColors[backgroundColorIndex++];

    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//
//        int width  = measureDimension(DEFAULT_VIEW_WIDTH, widthMeasureSpec);
//        int height = measureDimension(DEFAULT_VIEW_HEIGHT, heightMeasureSpec);
//        super.setMeasuredDimension(width, height);
//
//
//        //Log.i(TAG, "Width = " + getMeasuredWidth() + " Height = " + getMeasuredHeight());
//    }

    private int measureDimension(int defaultSize, int measureSpec){

        int result = 0;

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
            Log.i(TAG, "EXACTLY " + result);
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(defaultSize, specSize);
            Log.i(TAG, "AT_MOST " + result);
        } else {
            result = defaultSize;
        }

        return result;
    }

    public void setKeywordTextSize(float textSizeSp){
        keywordTypeEditTexts.get(0).setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSp);
        keywordTypeEditTexts.get(1).setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSp);
    }
}
