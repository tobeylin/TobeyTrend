package com.trend.tobeylin.tobeytrend.ui.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.trend.tobeylin.tobeytrend.BackgroudColor;
import com.trend.tobeylin.tobeytrend.R;
import com.trend.tobeylin.tobeytrend.data.generator.KeywordGenerator;
import com.trend.tobeylin.tobeytrend.ui.custom.KeywordCard;

/**
 * Created by tobeylin on 15/6/23.
 */
public class KeywordCardAdapter extends RecyclerView.Adapter<KeywordCardAdapter.ViewHolder> {

    private final int DEFAULT_GRID_WIDTH_COUNT = 1;
    private final int DEFAULT_GRID_HEIGHT_COUNT = 1;
    private final long DEFAULT_SHOW_KEYWORD_DURATION = 2000;
    private int gridWidthCount = DEFAULT_GRID_WIDTH_COUNT;
    private int gridHeightCount = DEFAULT_GRID_HEIGHT_COUNT;
    private long show_keyword_duration = DEFAULT_SHOW_KEYWORD_DURATION;
    private KeywordGenerator keywordGenerator;
    private Context context;

    public KeywordCardAdapter(Context context, KeywordGenerator keywordGenerator){
        this.context = context;
        this.keywordGenerator = keywordGenerator;
    }

    public KeywordCardAdapter(Context context, KeywordGenerator keywordGenerator, int widthCount, int heightCount){
        this.context = context;
        this.keywordGenerator = keywordGenerator;
        this.gridWidthCount = widthCount;
        this.gridHeightCount = heightCount;
    }

    public void setKeywordGenerator(KeywordGenerator keywordGenerator){
        this.keywordGenerator = keywordGenerator;
    }

    public void setGridWidthCount(int gridWidthCount) {
        this.gridWidthCount = gridWidthCount;
    }

    public void setGridHeightCount(int gridHeightCount){
        this.gridHeightCount = gridHeightCount;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View keywordCardView = layoutInflater.inflate(R.layout.keyword_card_item, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(keywordCardView);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getItemHeight(viewGroup));
        keywordCardView.setLayoutParams(layoutParams);
        viewHolder.keywordCard.setKeywordTextSize(getTextSize());
        viewHolder.keywordCard.setBackgroundColors(BackgroudColor.getAll());
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
        return viewHolder;

    }

    private int getItemHeight(ViewGroup parent){

        return parent.getMeasuredHeight() / gridHeightCount;

    }

    private void updateKeywordCard(ViewHolder viewHolder){

        viewHolder.keywordCard.setKeyword(keywordGenerator.getRandomKeyword());

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        updateKeywordCard(viewHolder);

    }


    @Override
    public int getItemCount() {
        return gridWidthCount * gridHeightCount;
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

    private float getTextSize(){

        int total = gridWidthCount * gridHeightCount;
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

}
