package com.trend.tobeylin.tobeytrend;

import android.app.ActionBar;
import android.app.Activity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.trend.tobeylin.tobeytrend.data.generator.BackgroundColorGenerator;
import com.trend.tobeylin.tobeytrend.data.generator.KeywordGenerator;
import com.trend.tobeylin.tobeytrend.ui.KeywordCard;

import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends Activity implements KeywordGenerator.KeywordGeneratorListener, KeywordCard.OnStateChangeListener, AdapterView.OnItemSelectedListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    private KeywordCard keywordCard = null;
    private ProgressBar progressBar = null;
    private TextView showCountryTextView = null;
    private KeywordGenerator keywordGenerator = null;
    private BackgroundColorGenerator backgroundColorGenerator = null;
    private Timer keywordTimer = null;
    private List<String> keywords = null;
    private final long SHOW_KEYWORD_DURATION = 3000;
    private Country country = Country.All;
    private int keywordIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initLayout();
        initGenerator();

    }

    private void initLayout() {

        getActionBar().setTitle("");
        keywordCard = (KeywordCard) findViewById(R.id.main_keywordCard);
        keywordCard.setVisibility(View.INVISIBLE);
        keywordCard.setOnStateChangeListener(this);
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
        updateKeywords();
        setShowCountry(country.getFullName());

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void initGenerator() {

        keywordGenerator = KeywordGenerator.getInstance(this);
        keywordGenerator.setCountry(Country.All);
        keywordGenerator.setListener(this);
        keywordGenerator.sync();

        backgroundColorGenerator = BackgroundColorGenerator.getInstance();

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        keywordGenerator.removeListener();
        keywordCard.removeOnStateChangeListener();

    }

    private void updateKeywords(){
        keywords = keywordGenerator.getKeywords(country);
        Collections.shuffle(keywords);
    }

    private void setShowCountry(String country){
        showCountryTextView.setText(getString(R.string.main_show_region, country));
    }

    @Override
    public void onSyncFinish() {

        progressBar.setVisibility(View.INVISIBLE);
        keywordCard.setVisibility(View.VISIBLE);
        initCountrySpinner();
        updateKeywords();
        startKeyword();

    }

    private void startKeyword() {

        keywordTimer = new Timer();
        keywordTimer.schedule(new KeywordTimerTask(), SHOW_KEYWORD_DURATION);

    }

    private void stopKeyword() {
        keywordTimer.purge();
        keywordTimer = null;
    }

    private class KeywordTimerTask extends TimerTask {
        @Override
        public void run() {
            Message message = new Message();
            message.what = KEYWORD_MESSAGE;
            message.obj = getKeyword();
            handler.sendMessage(message);
        }
    }
    private String getKeyword(){

        if(keywordIndex == keywords.size()) {
            updateKeywords();
            keywordIndex = 0;
        }
        String keyword = keywords.get(keywordIndex++);
        return keyword;
    }

    public static final int KEYWORD_MESSAGE = 1;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
            switch (msg.what) {
                case KEYWORD_MESSAGE:

                    String keyword = (String) msg.obj;
                    int backgroundColorInt = backgroundColorGenerator.getColor();
                    keywordCard.setKeyword(keyword, backgroundColorInt);
                    break;

                default:
            }
        }
    };

    @Override
    public void onKeywordTypeStart() {
        stopKeyword();
    }

    @Override
    public void onKeywordTypeFinish() {
        startKeyword();
    }
}