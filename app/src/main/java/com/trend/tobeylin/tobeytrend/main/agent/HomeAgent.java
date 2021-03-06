package com.trend.tobeylin.tobeytrend.main.agent;

import android.content.Context;
import android.util.Log;

import com.trend.tobeylin.tobeytrend.data.generator.KeywordGenerator;
import com.trend.tobeylin.tobeytrend.entity.RegionTopSearchEntity;
import com.trend.tobeylin.tobeytrend.main.view.HomeView;
import com.trend.tobeylin.tobeytrend.util.UrlUtil;

import java.util.List;

/**
 * Created by tobeylin on 15/7/1.
 */
public class HomeAgent implements BaseAgent, KeywordGenerator.KeywordGeneratorSyncListener {

    public static final String TAG = HomeAgent.class.getSimpleName();

    protected HomeView homeView = null;
    protected KeywordGenerator keywordGenerator = null;
    private int columnCount = 1;
    private int rowCount = 1;

    public HomeAgent(HomeView homeView){
        this.homeView = homeView;
        keywordGenerator = new KeywordGenerator();
        keywordGenerator.setListener(this);
    }

    public HomeAgent(HomeView homeView, KeywordGenerator keywordGenerator){
        this.homeView = homeView;
        this.keywordGenerator = keywordGenerator;
        this.keywordGenerator.setListener(this);
    }

    public void setHomeView(HomeView homeView) {
        this.homeView = homeView;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void init(){
        keywordGenerator.sync();
        homeView.showCountry(keywordGenerator.getCountry());
    }

    @Override
    public void onSyncSuccess(RegionTopSearchEntity keywordResponseEntity) {
        homeView.hideProgress();
        homeView.showGridImageView();
        homeView.showCountrySpinner();
        Log.i(TAG, "Sync Success");
        homeView.updateKeywordGrid(keywordGenerator.getKeywords(), columnCount, rowCount);
    }

    @Override
    public void onSyncFail() {
        Log.i(TAG, "Sync Fail");
    }

    public void openSelectViewDialog(){
        homeView.showSelectViewDialog(columnCount, rowCount);
    }

    public void clickKeyword(String keyword) {
        String url = UrlUtil.getGoogleSearchUrl(keyword);
        homeView.showKeywordSearchPage(url);
    }

    public void selectCountry(String countryName){
        keywordGenerator.setCountry(countryName);
        List<String> keywords = keywordGenerator.getKeywords();
        homeView.updateKeywordGrid(keywords, columnCount, rowCount);
        homeView.showCountry(countryName);
    }

    public void updateGrid(int newColumnCount, int newRowCount){
        columnCount = (newColumnCount < 1)? 1: newColumnCount;
        rowCount = (newRowCount < 1)? 1: newRowCount;
        List<String> keywords = keywordGenerator.getKeywords();
        homeView.updateKeywordGrid(keywords, columnCount, rowCount);
    }

}
