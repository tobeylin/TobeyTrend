package com.trend.tobeylin.tobeytrend;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.trend.tobeylin.tobeytrend.entity.RegionTopSearchEntity;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends Activity implements KeywordGenerator.KeywordGeneratorListener {

    private TextView keywordTextView = null;
    private KeywordGenerator keywordGenerator = null;
    private String[] keywords = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLayout();
        initKeywordGenerator();
    }

    private void initLayout() {

        keywordTextView = (TextView) findViewById(R.id.main_keywordTextView);

    }

    private void initKeywordGenerator() {

        keywordGenerator = KeywordGenerator.getInstance(this);
        keywordGenerator.setCountry(Country.TW);
        keywordGenerator.setListener(this);
        keywordGenerator.sync();

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        keywordGenerator.removeListener();

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSyncFinish() {

        //TO-DO: Timer
        Timer keywordTimer = new Timer();
        keywords = keywordGenerator.getKeywords();
        keywordTimer.schedule(keywordTimerTask, 1000, 3000);

    }

    private TimerTask keywordTimerTask = new TimerTask() {
        @Override
        public void run() {
            Message message = new Message();
            message.what = KEYWORD_MESSAGE;
            Random random = new Random();
            int randomKeywordIndex = random.nextInt(keywords.length);
            message.obj = keywords[randomKeywordIndex];
            handler.sendMessage(message);
        }
    };

    public static final int KEYWORD_MESSAGE = 1;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
            switch (msg.what) {
                case KEYWORD_MESSAGE:

                    String keyword = (String) msg.obj;
                    keywordTextView.setText(keyword);
                    break;
                
                default:
            }
        }
    };
}