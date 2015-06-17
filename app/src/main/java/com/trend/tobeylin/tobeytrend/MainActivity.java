package com.trend.tobeylin.tobeytrend;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.trend.tobeylin.tobeytrend.data.generator.BackgroundColorGenerator;
import com.trend.tobeylin.tobeytrend.data.generator.KeywordGenerator;
import com.trend.tobeylin.tobeytrend.ui.KeywordCard;
import com.trend.tobeylin.tobeytrend.ui.TypeEditText;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends Activity implements KeywordGenerator.KeywordGeneratorListener, KeywordCard.OnStateChangeListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    private KeywordCard keywordCard = null;
    private ProgressBar progressBar = null;
    private KeywordGenerator keywordGenerator = null;
    private BackgroundColorGenerator backgroundColorGenerator = null;
    private Timer keywordTimer = null;
    private String[] keywords = {};

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

    }

    private void initGenerator() {

        keywordGenerator = KeywordGenerator.getInstance(this);
        //TO-DO: TW to All
        // keywordGenerator.setCountry(Country.TW);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.action_TW:
                updateKeywords(Country.TW);
                break;
            case R.id.action_US:
                updateKeywords(Country.US);
                break;
            default:
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateKeywords(Country country){

        keywords = keywordGenerator.getKeywords(country);

    }

    @Override
    public void onSyncFinish() {

        progressBar.setVisibility(View.INVISIBLE);
        keywordCard.setVisibility(View.VISIBLE);
        updateKeywords(Country.TW);
        startKeyword();

    }

    private void startKeyword() {

        keywordTimer = new Timer();
        keywordTimer.schedule(new KeywordTimerTask(), 3000);

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
            Random random = new Random();
            int randomKeywordIndex = random.nextInt(keywords.length);
            message.obj = keywords[randomKeywordIndex];
            handler.sendMessage(message);
        }
    }

    ;

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