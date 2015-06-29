package com.trend.tobeylin.tobeytrend.main;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.trend.tobeylin.tobeytrend.Country;
import com.trend.tobeylin.tobeytrend.R;
import com.trend.tobeylin.tobeytrend.data.generator.KeywordGenerator;
import com.trend.tobeylin.tobeytrend.ui.adapter.CountrySpinnerAdapter;
import com.trend.tobeylin.tobeytrend.ui.adapter.KeywordCardAdapter;
import com.trend.tobeylin.tobeytrend.ui.adapter.KeywordCardLayoutManager;
import com.trend.tobeylin.tobeytrend.ui.custom.KeywordCard;

import java.util.List;


public class MainActivity extends FragmentActivity implements KeywordGenerator.KeywordGeneratorListener, AdapterView.OnItemSelectedListener, View.OnClickListener, SelectViewDialogFragment.SelectViewDialogListener, KeywordCardAdapter.ViewHolder.OnItemClickListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView keywordCardRecycleView = null;
    private KeywordCardAdapter keywordCardAdapter = null;
    private KeywordCardLayoutManager keywordCardLayoutManager = null;
    private ProgressBar progressBar = null;
    private TextView showCountryTextView = null;

    private KeywordGenerator keywordGenerator = null;
    private Country country = Country.All;
    private int gridWidth = 1;
    private int gridHeight = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLayout();
        initGenerator();

    }

    private void initLayout() {

        getActionBar().setTitle("");
        keywordCardRecycleView = (RecyclerView) findViewById(R.id.main_keywordCardRecycleView);
        keywordCardRecycleView.setHasFixedSize(true);
        keywordCardLayoutManager = new KeywordCardLayoutManager(this);
        keywordCardRecycleView.setLayoutManager(keywordCardLayoutManager);
        progressBar = (ProgressBar) findViewById(R.id.main_progressBar);
        progressBar.setVisibility(View.VISIBLE);
        showCountryTextView = (TextView) findViewById(R.id.main_showTextView);
        setShowCountry(country.getFullName());

    }

    private void initCountrySpinner(){

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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        List<String> countries = Country.getAllCountriesFullName();
        String selectCountryFullName = countries.get(position);
        country = Country.getCountryByFullName(selectCountryFullName);
        keywordGenerator.setCountry(country);
        keywordCardAdapter = new KeywordCardAdapter(this, keywordGenerator, gridWidth, gridHeight);
        keywordCardAdapter.setOnItemClickListener(this);
        keywordCardRecycleView.setAdapter(keywordCardAdapter);
        setShowCountry(country.getFullName());

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        showCountryTextView.setVisibility(View.VISIBLE);
    }

    private void initGenerator() {

        keywordGenerator = KeywordGenerator.getInstance(this);
        keywordGenerator.setListener(this);
        keywordGenerator.sync();

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        keywordGenerator.removeListener();

    }

    private void setShowCountry(String country){
        showCountryTextView.setVisibility(View.VISIBLE);
        showCountryTextView.setText(getString(R.string.main_show_region, country));
    }

    @Override
    public void onSyncFinish() {

        progressBar.setVisibility(View.INVISIBLE);
        initCountrySpinner();
        keywordCardAdapter = new KeywordCardAdapter(this, keywordGenerator);
        keywordCardAdapter.setOnItemClickListener(this);
        keywordCardAdapter.setKeywordGenerator(keywordGenerator);
        keywordCardRecycleView.setAdapter(keywordCardAdapter);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.actionbar_gridImageView:
                showSelectViewDialog();
                break;
            default:
        }

    }

    private void showSelectViewDialog(){

        FragmentManager fragmentManager = getSupportFragmentManager();
        SelectViewDialogFragment selectViewDialogFragment = new SelectViewDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(SelectViewDialogFragment.BUNDLE_CURRENT_WIDTH, gridWidth);
        bundle.putInt(SelectViewDialogFragment.BUNDLE_CURRENT_HEIGHT, gridHeight);
        selectViewDialogFragment.setArguments(bundle);
        selectViewDialogFragment.show(fragmentManager, SelectViewDialogFragment.TAG);

    }

    @Override
    public void onConfirmClick(int oldWidth, int oldHeight, int newWidth, int newHeight) {

        if(oldWidth != newWidth || oldHeight != newHeight) {
            updateGridSize(newWidth, newHeight);
        }

    }

    @Override
    public void onCancelClick() {

    }

    private void updateGridSize(final int width, final int height){

        keywordCardLayoutManager.setHeightCount(height);
        keywordCardLayoutManager.setWidthCount(width);
        keywordCardAdapter = new KeywordCardAdapter(this, keywordGenerator, width, height);
        keywordCardAdapter.setOnItemClickListener(this);
        keywordCardRecycleView.setAdapter(keywordCardAdapter);
        gridWidth = width;
        gridHeight = height;

    }

    private void openKeywordSearch(String keyword) {
        String url = "http://www.google.com/search?q=" + keyword;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @Override
    public void onItemClick(KeywordCard keywordCard) {
    }

    @Override
    public void onKeywordClick(String keyword) {
        openKeywordSearch(keyword);
    }
}