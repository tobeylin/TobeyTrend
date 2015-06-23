package com.trend.tobeylin.tobeytrend;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.trend.tobeylin.tobeytrend.data.generator.KeywordGenerator;
import com.trend.tobeylin.tobeytrend.ui.KeywordCardAdapter;

import java.util.List;


public class MainActivity extends Activity implements KeywordGenerator.KeywordGeneratorListener, AdapterView.OnItemSelectedListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView keywordCardRecycleView = null;
    private KeywordCardAdapter keywordCardAdapter = null;
    private RecyclerView.LayoutManager keywordCardLayoutManager = null;
    private ProgressBar progressBar = null;
    private TextView showCountryTextView = null;

    private KeywordGenerator keywordGenerator = null;
    private Country country = Country.All;

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
        keywordCardLayoutManager = new GridLayoutManager(this, 2);
        keywordCardRecycleView.setLayoutManager(keywordCardLayoutManager);
        progressBar = (ProgressBar) findViewById(R.id.main_progressBar);
        progressBar.setVisibility(View.VISIBLE);
        showCountryTextView = (TextView) findViewById(R.id.main_showTextView);
        setShowCountry(country.getFullName());

    }

    private void initCountrySpinner(){

        View actionBarView = LayoutInflater.from(this).inflate(R.layout.actionbar, null, false);
        actionBarView.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT));
        Spinner countrySpinner = (Spinner) actionBarView.findViewById(R.id.actionbar_selectCountrySpinner);
        SpinnerAdapter countrySpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Country.getAllCountriesFullName());
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
        setShowCountry(country.getFullName());

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
        showCountryTextView.setText(getString(R.string.main_show_region, country));
    }

    @Override
    public void onSyncFinish() {

        progressBar.setVisibility(View.INVISIBLE);
        initCountrySpinner();
        keywordCardAdapter = new KeywordCardAdapter(keywordGenerator);
        keywordCardAdapter.setKeywordGenerator(keywordGenerator);
        keywordCardRecycleView.setAdapter(keywordCardAdapter);

    }

}