package com.trend.tobeylin.tobeytrend.main.view;

import java.util.List;

/**
 * Created by tobeylin on 15/7/1.
 */
public interface HomeView {

    void showCountry(String countryName);
    void showSelectViewDialog(int columnCount, int rowCount);
    void showProgress();
    void hideProgress();
    void updateKeywordGrid(List<String> newKeywords, int newColumnCount, int newRowCount);
    void showGridImageView();
    void showCountrySpinner();
    void showKeywordSearchPage(String keyword);

}
