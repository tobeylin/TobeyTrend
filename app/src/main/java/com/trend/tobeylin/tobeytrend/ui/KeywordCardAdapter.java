package com.trend.tobeylin.tobeytrend.ui;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.trend.tobeylin.tobeytrend.BackgroudColor;
import com.trend.tobeylin.tobeytrend.R;
import com.trend.tobeylin.tobeytrend.data.generator.KeywordGenerator;

/**
 * Created by tobeylin on 15/6/23.
 */
public class KeywordCardAdapter extends RecyclerView.Adapter<KeywordCardAdapter.ViewHolder> {

    private final int DEFAULT_GRID_WIDTH_COUNT = 2;
    private final int DEFAULT_GRID_HEIGHT_COUNT = 2;
    private final long DEFAULT_SHOW_KEYWORD_DURATION = 2000;
    private int gridWidthCount = DEFAULT_GRID_WIDTH_COUNT;
    private int gridHeightCount = DEFAULT_GRID_HEIGHT_COUNT;
    private long show_keyword_duration = DEFAULT_SHOW_KEYWORD_DURATION;
    private KeywordGenerator keywordGenerator;

    public KeywordCardAdapter(KeywordGenerator keywordGenerator){
        this.keywordGenerator = keywordGenerator;
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

        public KeywordCard keywordCard;

        public ViewHolder(View keywordCardView){
            super(keywordCardView);
            this.keywordCard = (KeywordCard) keywordCardView.findViewById(R.id.item_keywordCard);
        }

    }


}
