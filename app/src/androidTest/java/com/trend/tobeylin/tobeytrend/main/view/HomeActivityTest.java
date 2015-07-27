package com.trend.tobeylin.tobeytrend.main.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.test.espresso.contrib.CountingIdlingResource;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleCallback;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;

import com.trend.tobeylin.tobeytrend.R;
import com.trend.tobeylin.tobeytrend.data.generator.api.KeywordApiService;
import com.trend.tobeylin.tobeytrend.entity.RegionTopSearchEntity;
import com.trend.tobeylin.tobeytrend.main.agent.HomeAgent;

import org.hamcrest.core.AllOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import custom.action.NumberPickerAction;
import custom.matcher.KeywordCardViewMatcher;
import custom.matcher.NumberPickerViewMatcher;
import custom.matcher.RecyclerViewMatcher;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.intent.matcher.IntentMatchers.*;
import static android.support.test.espresso.intent.matcher.UriMatchers.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.*;

/**
 * Created by tobeylin on 15/7/20.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class HomeActivityTest {

    @Rule
    public IntentsTestRule<HomeActivity> homeActivityIntentsTestRule = new IntentsTestRule<HomeActivity>(HomeActivity.class){

        private HomeAgentInjector homeAgentInjector;

        @Override
        protected void beforeActivityLaunched() {
            Log.i("Test", "beforeActivityLaunched");
            super.beforeActivityLaunched();
            homeAgentInjector = new HomeAgentInjector();
            ActivityLifecycleMonitorRegistry.getInstance().addLifecycleCallback(homeAgentInjector);
        }

        @Override
        protected void afterActivityFinished() {
            Log.i("Test", "afterActivityFinished");
            homeAgentInjector.destroy();
            ActivityLifecycleMonitorRegistry.getInstance().removeLifecycleCallback(homeAgentInjector);
            super.afterActivityFinished();
        }
    };

    @Before
    public void setUp() throws Exception {
        Log.i("Test", "setUp");
    }

    @After
    public void tearDown() throws Exception {
        Log.i("Test", "tearDown");
    }

    @Test
    public void checkOnCreate() {
        onView(withId(R.id.actionbar_gridImageView)).check(matches(isDisplayed()));
        onView(withId(R.id.actionbar_selectCountrySpinner)).check(matches(isDisplayed()));
        onView(withId(R.id.home_progressBar)).check(matches(not(isDisplayed())));
        onView(withId(R.id.home_showTextView)).check(matches(withText("Showing the latest hot searches in All Regions.")));
        onView(withId(R.id.home_keywordCardRecycleView)).check(matches(isDisplayed()));
    }

    @Test
    public void checkClickSelectGridView() {
        onView(withId(R.id.actionbar_gridImageView)).check(matches(isDisplayed()));
        onView(withId(R.id.actionbar_gridImageView)).perform(click());

        // Check the content of the dialog is correct
        onView(withText(getString(R.string.select_view_dialog_title))).check(matches(isDisplayed()));
        onView(withId(R.id.selectViewDialog_widthNumberPicker)).check(matches(isDisplayed()));
        onView(NumberPickerViewMatcher.withValue(R.id.selectViewDialog_widthNumberPicker, "1")).check(matches(isDisplayed()));
        onView(withId(R.id.selectViewDialog_heightNumberPicker)).check(matches(isDisplayed()));
        onView(NumberPickerViewMatcher.withValue(R.id.selectViewDialog_heightNumberPicker, "1")).check(matches(isDisplayed()));
        onView(withId(R.id.selectViewDialog_widthTextView)).check(matches(withText(containsString(getString(R.string.select_view_dialog_width)))));
        onView(withId(R.id.selectViewDialog_heightTextView)).check(matches(withText(containsString(getString(R.string.select_view_dialog_height)))));
        onView(withText(getString(R.string.select_view_confirm_button))).check(matches(isDisplayed()));
        onView(withText(getString(R.string.select_view_cancel_button))).check(matches(isDisplayed()));
    }

    @Test
    public void checkChangeColumn() {
        onView(withId(R.id.actionbar_gridImageView)).check(matches(isDisplayed()));
        onView(withId(R.id.actionbar_gridImageView)).perform(click());

        // Check the content of the dialog is correct
        onView(withText(getString(R.string.select_view_dialog_title))).check(matches(isDisplayed()));
        onView(withId(R.id.selectViewDialog_widthNumberPicker)).check(matches(isDisplayed()));
        onView(NumberPickerViewMatcher.withValue(R.id.selectViewDialog_widthNumberPicker, "1")).check(matches(isDisplayed()));
        onView(withId(R.id.selectViewDialog_heightNumberPicker)).check(matches(isDisplayed()));
        onView(NumberPickerViewMatcher.withValue(R.id.selectViewDialog_heightNumberPicker, "1")).check(matches(isDisplayed()));
        onView(withId(R.id.selectViewDialog_widthTextView)).check(matches(withText(containsString(getString(R.string.select_view_dialog_width)))));
        onView(withId(R.id.selectViewDialog_heightTextView)).check(matches(withText(containsString(getString(R.string.select_view_dialog_height)))));
        onView(withText(getString(R.string.select_view_confirm_button))).check(matches(isDisplayed()));
        onView(withText(getString(R.string.select_view_cancel_button))).check(matches(isDisplayed()));

        //Change the column count from 1 to 2
        int testColumnCount = 2;
        int testRowCount = 1;
        onView(withId(R.id.selectViewDialog_widthNumberPicker)).perform(NumberPickerAction.setValue(testColumnCount));
        onView(withText(getString(R.string.select_view_confirm_button))).perform(click());

        //Check the items in the recycler view
        onView(withId(R.id.home_keywordCardRecycleView)).check(matches(allOf(isDisplayed(), RecyclerViewMatcher.withColumnCount(testColumnCount))));
        onView(withId(R.id.home_keywordCardRecycleView)).check(matches(allOf(isDisplayed(), RecyclerViewMatcher.withRowCount(testRowCount))));
    }

    @Test
    public void checkSelectCountry(){
        onView(withId(R.id.actionbar_selectCountrySpinner)).check(matches(isDisplayed()));
        onView(withId(R.id.actionbar_selectCountrySpinner)).perform(click());
        onData(AllOf.allOf(is(instanceOf(String.class)), is("Taiwan"))).perform(click());
        onView(withId(R.id.home_showTextView)).check(matches(withText(containsString("Taiwan"))));
    }

    @Test
    public void checkClickKeyword() {
        //check if have data
        onView(withId(R.id.actionbar_gridImageView)).check(matches(isDisplayed()));
        onView(withId(R.id.home_keywordCardRecycleView)).check(matches(isDisplayed()));

        //Click the item
        int testClickPosition = 0;
        onView(RecyclerViewMatcher.atPositionOnKeywordText(
                R.id.home_keywordCardRecycleView,
                testClickPosition,
                R.id.keywordCard_keywordTypeTextView))
                .perform(click());

        //Check if the intent is match
        String expectedUriHost = "www.google.com";
        String expectedUriPath = "/search";
        String expectedUriParam = "q";
        Intents.intended(allOf(
                hasAction(equalTo(Intent.ACTION_VIEW)),
                hasData(allOf(hasHost(expectedUriHost), hasPath(expectedUriPath), hasParamWithName(expectedUriParam))),
                toPackage("com.android.browser")));
    }

    public String getString(int resourceId){
        return homeActivityIntentsTestRule.getActivity().getString(resourceId);
    }

    private class HomeAgentInjector implements ActivityLifecycleCallback {

        public final String TAG = HomeAgentInjector.class.getSimpleName();

        private DecoratedHomeAgent decoratedHomeAgent;
        private CountingIdlingResource homeAgentCounting;
        @Override
        public void onActivityLifecycleChanged(Activity activity, Stage stage) {
            HomeActivity homeActivity = (HomeActivity) activity;

            switch (stage) {
                case PRE_ON_CREATE:
                    Log.i(TAG, "PRE ON CREATE: " + activity);
                    homeAgentCounting = new CountingIdlingResource(activity + HomeAgent.TAG);
                    homeAgentCounting.increment();
                    decoratedHomeAgent = new DecoratedHomeAgent(homeActivity, homeActivity, homeAgentCounting);
                    homeActivity.setAgent(decoratedHomeAgent);
                    registerIdlingResources(homeAgentCounting);
                    Log.i(TAG, "PRE ON CREATE: " + homeAgentCounting);
                    break;
                case STOPPED:
                    Log.i(TAG, "STOPPED: " + activity);
                    break;
                default:
            }
        }

        public void destroy(){
            Log.i(TAG, this + " destroy");
            unregisterIdlingResources(homeAgentCounting);
        }
    }

    private class DecoratedHomeAgent extends HomeAgent {

        private CountingIdlingResource countingIdlingResource;

        public DecoratedHomeAgent(Context context, HomeView homeView, CountingIdlingResource countingIdlingResource) {
            super(context, homeView);
            mock();
            this.countingIdlingResource = countingIdlingResource;
            this.countingIdlingResource.decrement();
        }

        private void mock(){
            KeywordApiService mockKeywordApiService = Mockito.spy(new KeywordApiService(Mockito.mock(Context.class)));
            Mockito.doAnswer(new Answer() {
                @Override
                public Object answer(InvocationOnMock invocation) throws Throwable {
                    KeywordApiService mock = (KeywordApiService) invocation.getMock();
                    KeywordApiService.ApiCallback callback = (KeywordApiService.ApiCallback) invocation.getArguments()[0];
                    callback.onSuccess(mock.parse(KeywordApiServiceTestData.SUCCESS_RES));
                    return null;
                }
            }).when(mockKeywordApiService).start(Mockito.any(KeywordApiService.ApiCallback.class));
            keywordGenerator.setKeywordApiService(mockKeywordApiService);
        }

        @Override
        public void init() {
            countingIdlingResource.increment();
            super.init();
        }

        @Override
        public void onSyncSuccess(RegionTopSearchEntity keywordResponseEntity) {
            super.onSyncSuccess(keywordResponseEntity);
            countingIdlingResource.decrement();
        }

        @Override
        public void onSyncFail() {
            super.onSyncFail();
            countingIdlingResource.decrement();
        }
    }
}