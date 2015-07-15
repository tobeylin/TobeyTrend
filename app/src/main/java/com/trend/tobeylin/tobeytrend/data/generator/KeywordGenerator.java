package com.trend.tobeylin.tobeytrend.data.generator;

import android.content.Context;

import com.trend.tobeylin.tobeytrend.Region;
import com.trend.tobeylin.tobeytrend.data.generator.api.KeywordApiService;
import com.trend.tobeylin.tobeytrend.entity.RegionTopSearchEntity;

import java.util.List;

/**
 * Created by tobeylin on 15/6/15.
 */
public class KeywordGenerator {

    public static final String TAG = KeywordGenerator.class.getSimpleName();

    private KeywordGeneratorSyncListener listener = null;
    private RegionTopSearchEntity topSearchEntity = null;
    private String country = Region.getDefaultCountry();
    private KeywordApiService keywordApiService = null;
    private boolean isSync = false;

    public interface KeywordGeneratorSyncListener {
        void onSyncSuccess(RegionTopSearchEntity keywordResponseEntity);
        void onSyncFail();
    }

    public KeywordGenerator(Context context){
        keywordApiService = new KeywordApiService(context);
    }

    public KeywordGenerator(KeywordApiService keywordApiService) {
        this.keywordApiService = keywordApiService;
    }

    public void setListener(KeywordGeneratorSyncListener listener) {
        this.listener = listener;
    }

    public void removeListener() {
        this.listener = null;
    }

    public void setTopSearchEntity(RegionTopSearchEntity topSearchEntity) {
        this.topSearchEntity = topSearchEntity;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry(){

        return country;

    }

    public void sync(){
        keywordApiService.start(new KeywordApiService.ApiCallback() {
            @Override
            public void onSuccess(RegionTopSearchEntity response) {
                isSync = true;
                topSearchEntity = response;
                if (listener != null) {
                    listener.onSyncSuccess(topSearchEntity);
                }
            }

            @Override
            public void onFail() {
                isSync = false;
                if (listener != null) {
                    listener.onSyncFail();
                }
            }
        });
    }

    public boolean isSync(){
        return isSync;
    }

    public List<String> getKeywords() {

        return getKeywords(country);

    }

    public List<String> getKeywords(String country) {

        List<String> keywords;
        if (Region.isAllRegion(country)) {
            keywords = topSearchEntity.getAllCountryKeywords();
        } else {
            keywords = topSearchEntity.getCountryKeywords(Region.getCountryShortName(country));
        }
        return keywords;

    }

}
