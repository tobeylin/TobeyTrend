package com.trend.tobeylin.tobeytrend.main.view;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.trend.tobeylin.tobeytrend.Country;
import com.trend.tobeylin.tobeytrend.R;
import com.trend.tobeylin.tobeytrend.main.agent.HomeAgent;
import com.trend.tobeylin.tobeytrend.ui.adapter.CountrySpinnerAdapter;
import com.trend.tobeylin.tobeytrend.ui.adapter.KeywordCardAdapter;
import com.trend.tobeylin.tobeytrend.ui.adapter.KeywordCardLayoutManager;
import com.trend.tobeylin.tobeytrend.ui.custom.KeywordCard;

import java.util.List;

public class HomeActivity extends FragmentActivity implements HomeView,
        View.OnClickListener,
        AdapterView.OnItemSelectedListener,
        KeywordCardAdapter.OnItemClickListener, SelectViewDialogFragment.SelectViewDialogListener {

    public static final String TAG = HomeActivity.class.getSimpleName();

    private RecyclerView keywordCardRecycleView = null;
    private KeywordCardLayoutManager keywordCardLayoutManager = null;
    private KeywordCardAdapter keywordCardAdapter = null;
    private ProgressBar progressBar = null;
    private TextView showCountryTextView = null;

    private HomeAgent homeAgent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initLayout();
        homeAgent = new HomeAgent(this, this);
    }

    public void initLayout() {
        keywordCardRecycleView = (RecyclerView) findViewById(R.id.home_keywordCardRecycleView);
        keywordCardRecycleView.setHasFixedSize(true);
        keywordCardLayoutManager = new KeywordCardLayoutManager(this);
        keywordCardRecycleView.setLayoutManager(keywordCardLayoutManager);
        progressBar = (ProgressBar) findViewById(R.id.home_progressBar);
        showCountryTextView = (TextView) findViewById(R.id.home_showTextView);
    }

    @Override
    public void showCountry(String countryName) {
        showCountryTextView.setText(getString(R.string.main_show_region, countryName));
    }

    @Override
    public void showSelectViewDialog(int columnCount, int rowCount) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        SelectViewDialogFragment selectViewDialogFragment = new SelectViewDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(SelectViewDialogFragment.BUNDLE_CURRENT_COLUMN_COUNT, columnCount);
        bundle.putInt(SelectViewDialogFragment.BUNDLE_CURRENT_ROW_COUNT, rowCount);
        selectViewDialogFragment.setArguments(bundle);
        selectViewDialogFragment.show(fragmentManager, SelectViewDialogFragment.TAG);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void updateKeywordGrid(List<String> newKeywords, int newColumnCount, int newRowCount) {
        keywordCardLayoutManager.setHeightCount(newRowCount);
        keywordCardLayoutManager.setWidthCount(newColumnCount);
        keywordCardAdapter = new KeywordCardAdapter(this, newKeywords, newColumnCount, newRowCount);
        keywordCardAdapter.setOnItemClickListener(this);
        keywordCardRecycleView.setAdapter(keywordCardAdapter);
    }

    @Override
    public void showActionbar() {
        View actionBarView = LayoutInflater.from(this).inflate(R.layout.actionbar, null, false);
        actionBarView.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));

        ImageView gridImageView = (ImageView) actionBarView.findViewById(R.id.actionbar_gridImageView);
        gridImageView.setOnClickListener(this);

        Spinner countrySpinner = (Spinner) actionBarView.findViewById(R.id.actionbar_selectCountrySpinner);
        CountrySpinnerAdapter countrySpinnerAdapter = new CountrySpinnerAdapter(Country.getAllCountriesFullName());
        countrySpinner.setAdapter(countrySpinnerAdapter);
        countrySpinner.setOnItemSelectedListener(this);

        getActionBar().setDisplayShowCustomEnabled(true);
        getActionBar().setCustomView(actionBarView);
    }

    @Override
    public void showKeywordSearchPage(String keyword) {
        String url = "http://www.google.com/search?q=" + keyword;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.actionbar_gridImageView:
                homeAgent.clickGridView();
                break;
            default:

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        List<String> countries = Country.getAllCountriesFullName();
        String selectCountryFullName = countries.get(position);
        homeAgent.selectCountry(selectCountryFullName);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemClick(KeywordCard keywordCard) {

    }

    @Override
    public void onKeywordClick(String keyword) {
        homeAgent.clickKeyword(keyword);
    }

    @Override
    public void onConfirmClick(int oldWidth, int oldHeight, int newWidth, int newHeight) {
        if(oldWidth != newWidth || oldHeight != newHeight) {
            homeAgent.updateGrid(newWidth, newHeight);
        }
    }

    @Override
    public void onCancelClick() {

    }
}
