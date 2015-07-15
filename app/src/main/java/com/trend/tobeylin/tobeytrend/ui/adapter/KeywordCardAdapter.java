package com.trend.tobeylin.tobeytrend.ui.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.trend.tobeylin.tobeytrend.BackgroundColor;
import com.trend.tobeylin.tobeytrend.R;
import com.trend.tobeylin.tobeytrend.data.generator.KeywordGenerator;
import com.trend.tobeylin.tobeytrend.ui.custom.KeywordCard;

import java.util.Collections;
import java.util.List;

/**
 * Created by tobeylin on 15/6/23.
 */
public class KeywordCardAdapter extends RecyclerView.Adapter<KeywordCardAdapter.ViewHolder> {

    private final int DEFAULT_GRID_COLUMN_COUNT = 1;
    private final int DEFAULT_GRID_ROW_COUNT = 1;
    private final long DEFAULT_SHOW_KEYWORD_DURATION = 2000;
    private int gridColumnCount = DEFAULT_GRID_COLUMN_COUNT;
    private int gridRowCount = DEFAULT_GRID_ROW_COUNT;
    private long show_keyword_duration = DEFAULT_SHOW_KEYWORD_DURATION;
    private KeywordGenerator keywordGenerator;
    private List<String> keywords;
    private Context context;
    private OnItemClickListener itemClickListener = null;
    private int keywordIndex = 0;

    public interface OnItemClickListener{

        void onItemClick(KeywordCard keywordCard);
        void onKeywordClick(String keyword);

    }

    public KeywordCardAdapter(Context context, List<String> keywords, int columnCount, int rowCount){
        this.context = context;
        this.keywords = keywords;
        this.gridColumnCount = columnCount;
        this.gridRowCount = rowCount;
    }

    public int getGridColumnCount() {
        return gridColumnCount;
    }

    public int getGridRowCount() {
        return gridRowCount;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.itemClickListener = listener;
    }

    public void removeOnItemClickListener(){
        this.itemClickListener = null;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View keywordCardView = layoutInflater.inflate(R.layout.keyword_card_item, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(keywordCardView);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getItemHeight(viewGroup));
        keywordCardView.setLayoutParams(layoutParams);
        viewHolder.keywordCard.setKeywordTextSize(getTextSize());
        viewHolder.keywordCard.setBackgroundColors(BackgroundColor.getAllColors(context));
        viewHolder.keywordCard.setOnStateChangeListener(new KeywordCard.OnStateChangeListener() {
            @Override
            public void onKeywordTypeStart() {

            }

            @Override
            public void onKeywordTypeFinish() {

                Handler handler = new Handler();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        updateKeywordCard(viewHolder);
                    }
                };
                handler.postDelayed(runnable, show_keyword_duration);
            }
        });
        viewHolder.keywordCard.setOnKeywordClickListener(new KeywordCard.OnKeywordClickListener() {
            @Override
            public void onKeywordClick() {
                if(itemClickListener != null){
                    itemClickListener.onKeywordClick(viewHolder.keywordCard.getKeyword());
                }
            }
        });

        return viewHolder;

    }

    private int getItemHeight(ViewGroup parent){
        return parent.getMeasuredHeight() / gridRowCount;
    }

    private float getTextSize(){

        int total = gridColumnCount * gridRowCount;
        float textSize;
        if (total <= 2){
            textSize = context.getResources().getDimension(R.dimen.keyword_text_size_1);
        } else if (total > 2 && total <= 4){
            textSize = context.getResources().getDimension(R.dimen.keyword_text_size_2);
        }else if (total > 4 && total <= 10){
            textSize = context.getResources().getDimension(R.dimen.keyword_text_size_3);
        } else {
            textSize = context.getResources().getDimension(R.dimen.keyword_text_size_4);
        }

        return textSize;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        updateKeywordCard(viewHolder);
    }

    private void updateKeywordCard(ViewHolder viewHolder){
        viewHolder.keywordCard.setKeyword(getKeyword());
    }

    @Override
    public int getItemCount() {
        return gridColumnCount * gridRowCount;
    }

    public int getDataCount(){
        return (keywords != null)? keywords.size(): 0;
    }

    private void shuffleKeywords() {
        Collections.shuffle(keywords);
    }

    private String getKeyword(){
        if (keywordIndex == keywords.size()) {
            shuffleKeywords();
            keywordIndex = 0;
        }
        return keywords.get(keywordIndex++);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public KeywordCard keywordCard;

        public ViewHolder(View keywordCardView){
            super(keywordCardView);
            this.view = keywordCardView;
            this.keywordCard = (KeywordCard) keywordCardView.findViewById(R.id.item_keywordCard);
        }

    }



}
