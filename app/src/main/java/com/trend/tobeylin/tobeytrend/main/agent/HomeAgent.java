package com.trend.tobeylin.tobeytrend.main.agent;

import android.content.Context;
import android.util.Log;

import com.trend.tobeylin.tobeytrend.Country;
import com.trend.tobeylin.tobeytrend.data.generator.KeywordGenerator;
import com.trend.tobeylin.tobeytrend.main.view.HomeView;

import java.util.List;

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
        keywordGenerator.setListener(this);
        keywordGenerator.sync();
        homeView.showCountry(keywordGenerator.getCountry().getFullName());
    }

    @Override
    public void onSyncSuccess() {
        homeView.hideProgress();
        homeView.showActionbar();
        Log.i(TAG, "Sync Success");
        homeView.updateKeywordGrid(keywordGenerator.getKeywords(), columnCount, rowCount);
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

    public void selectCountry(String countryName){
        Country country = Country.getCountryByFullName(countryName);
        keywordGenerator.setCountry(country);
        List<String> keywords = keywordGenerator.getKeywords();
        homeView.updateKeywordGrid(keywords, columnCount, rowCount);
        homeView.showCountry(country.getFullName());
    }

    public void updateGrid(int newColumnCount, int newRowCount){
        columnCount = (newColumnCount < 1)? 1: newColumnCount;
        rowCount = (newRowCount < 1)? 1: newRowCount;
        List<String> keywords = keywordGenerator.getKeywords();
        homeView.updateKeywordGrid(keywords, columnCount, rowCount);
    }

}
