package com.trend.tobeylin.tobeytrend.main.agent;

import android.content.Context;
import android.util.Log;

import com.trend.tobeylin.tobeytrend.Country;
import com.trend.tobeylin.tobeytrend.data.generator.KeywordGenerator;
import com.trend.tobeylin.tobeytrend.main.view.HomeView;

/**
 * Created by tobeylin on 15/7/1.
 */
public class HomeAgent implements KeywordGenerator.KeywordGeneratorListener{

    public static final String TAG = HomeAgent.class.getSimpleName();

    private HomeView homeView = null;
    private KeywordGenerator keywordGenerator = null;
    private int columnCount = 1;
    private int rowCount = 1;

    public HomeAgent(Context context, HomeView homeView){
        this.homeView = homeView;
        keywordGenerator = KeywordGenerator.getInstance(context);
        keywordGenerator.setCountry(Country.TW);
        keywordGenerator.setListener(this);
        keywordGenerator.sync();
        homeView.showCountry(keywordGenerator.getCountry().getFullName());
    }

    @Override
    public void onSyncSuccess() {
        homeView.hideProgress();
        homeView.showCountrySpinner();
        Log.i(TAG, "Sync Success");
        homeView.updateGridSize(keywordGenerator.getKeywords(), columnCount, rowCount);
    }

    @Override
    public void onSyncFail() {
        Log.i(TAG, "Sync Fail");
    }

    public void clickGridView(){
        homeView.showSelectViewDialog(columnCount, rowCount);
    }

    public void clickKeyword(String keyword) {
        homeView.showKeywordSearchPage(keyword);
    }

    public void selectCountry(){

    }

}
