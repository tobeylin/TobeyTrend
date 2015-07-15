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

import com.trend.tobeylin.tobeytrend.Region;
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
        KeywordCardAdapter.OnItemClickListener,
        SelectViewDialogFragment.SelectViewDialogListener {

    public static final String TAG = HomeActivity.class.getSimpleName();

    private RecyclerView keywordCardRecycleView = null;
    private KeywordCardAdapter keywordCardAdapter = null;
    private ProgressBar progressBar = null;
    private TextView showCountryTextView = null;
    private Spinner countrySpinner = null;

    private HomeAgent homeAgent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initLayout();
        if(homeAgent == null) {
            homeAgent = new HomeAgent(this, this);
            homeAgent.init();
        }
    }

    public void setAgent(HomeAgent homeAgent) {
        this.homeAgent = homeAgent;
    }

    public HomeAgent getAgent() {
        return homeAgent;
    }

    public void initLayout() {
        keywordCardRecycleView = (RecyclerView) findViewById(R.id.home_keywordCardRecycleView);
        keywordCardRecycleView.setHasFixedSize(true);
        keywordCardRecycleView.setLayoutManager(new KeywordCardLayoutManager(this));
        progressBar = (ProgressBar) findViewById(R.id.home_progressBar);
        showCountryTextView = (TextView) findViewById(R.id.home_showTextView);

        View actionBarView = LayoutInflater.from(this).inflate(R.layout.actionbar, null, false);
        actionBarView.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
        getActionBar().setDisplayShowCustomEnabled(true);
        getActionBar().setCustomView(actionBarView);
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
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void updateKeywordGrid(List<String> newKeywords, int newColumnCount, int newRowCount) {
        KeywordCardLayoutManager keywordCardLayoutManager = (KeywordCardLayoutManager) keywordCardRecycleView.getLayoutManager();
        keywordCardLayoutManager.setHeightCount(newRowCount);
        keywordCardLayoutManager.setWidthCount(newColumnCount);
        keywordCardAdapter = new KeywordCardAdapter(this, newKeywords, newColumnCount, newRowCount);
        keywordCardAdapter.setOnItemClickListener(this);
        keywordCardRecycleView.setAdapter(keywordCardAdapter);
    }

    @Override
    public void showGridImageView(){
        ImageView gridImageView = (ImageView) getActionBar().getCustomView().findViewById(R.id.actionbar_gridImageView);
        gridImageView.setVisibility(View.VISIBLE);
        gridImageView.setOnClickListener(this);
    }

    @Override
    public void showCountrySpinner(){
        countrySpinner = (Spinner) getActionBar().getCustomView().findViewById(R.id.actionbar_selectCountrySpinner);
        countrySpinner.setVisibility(View.VISIBLE);
        CountrySpinnerAdapter countrySpinnerAdapter = new CountrySpinnerAdapter(Region.getAllCountriesFullName());
        countrySpinner.setAdapter(countrySpinnerAdapter);
        countrySpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void showKeywordSearchPage(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.actionbar_gridImageView:
                homeAgent.openSelectViewDialog();
                break;
            default:

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectCountryFullName = (String) (parent.getAdapter().getItem(position));
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
    public void onConfirmClick(int oldColumnCount, int oldRowCount, int newColumnCount, int newRowCount) {
        if (oldColumnCount != newColumnCount || oldRowCount != newRowCount) {
            homeAgent.updateGrid(newColumnCount, newRowCount);
        }
    }

    @Override
    public void onCancelClick() {

    }
}
