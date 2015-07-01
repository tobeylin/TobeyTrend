package com.trend.tobeylin.tobeytrend.main.view;

import java.util.List;

/**
 * Created by tobeylin on 15/7/1.
 */
public interface HomeView {

    public void showCountry(String countryName);
    public void showSelectViewDialog(int columnCount, int rowCount);
    public void showProgress();
    public void hideProgress();
    public void updateGridSize(List<String> newKeywords, int newColumnCount, int newRowCount);
    public void showCountrySpinner();
    public void showKeywordSearchPage(String keyword);

}
