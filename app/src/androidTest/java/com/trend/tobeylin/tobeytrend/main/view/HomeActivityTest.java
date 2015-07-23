package com.trend.tobeylin.tobeytrend.main.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.GeneralClickAction;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Tap;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.CountingIdlingResource;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleCallback;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;

import com.trend.tobeylin.tobeytrend.R;
import com.trend.tobeylin.tobeytrend.entity.RegionTopSearchEntity;
import com.trend.tobeylin.tobeytrend.main.agent.HomeAgent;

import org.hamcrest.Matchers;
import org.hamcrest.core.AllOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import custom.matcher.RecyclerViewMatcher;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

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
        onView(withId(R.id.home_progressBar)).check(matches(Matchers.not(isDisplayed())));
        onView(withId(R.id.home_showTextView)).check(matches(withText("Showing the latest hot searches in All Regions.")));
        onView(withId(R.id.home_keywordCardRecycleView)).check(matches(isDisplayed()));
    }

    @Test
    public void checkClickSelectGridView() {
        onView(withId(R.id.actionbar_gridImageView)).check(matches(isDisplayed()));
        onView(withId(R.id.actionbar_gridImageView)).perform(click());
        onView(withId(R.id.selectViewDialog_widthNumberPicker)).check(matches(isDisplayed()));
    }
    @Test
    public void checkSelectCountry(){
        onView(withId(R.id.actionbar_selectCountrySpinner)).check(matches(isDisplayed()));
        onView(withId(R.id.actionbar_selectCountrySpinner)).perform(click());
    }

    public void checkClickKeyword() {
        //check if have data
        onView(withId(R.id.actionbar_gridImageView)).check(matches(isDisplayed()));
        onView(withId(R.id.home_keywordCardRecycleView)).check(matches(isDisplayed()));

        HomeActivity homeActivity = homeActivityIntentsTestRule.getActivity();

        //yes, click
        //TODO: click the keyword typeTextView
        RecyclerViewMatcher recyclerViewMatcher = new RecyclerViewMatcher(R.id.home_keywordCardRecycleView);
        //click 1
        ViewAction customClick = actionWithAssertions(new GeneralClickAction(Tap.SINGLE, GeneralLocation.CENTER_LEFT, Press.FINGER));
        onView(AllOf.allOf(recyclerViewMatcher.atPositionOnView(0, R.id.keywordCard_keywordTypeTextView), isDisplayed())).check(matches(isDisplayed()));

        //Check if the intent is match
        Intents.intended(IntentMatchers.hasAction(Intent.ACTION_VIEW));
    }

    private class HomeAgentInjector implements ActivityLifecycleCallback {

        private DecoratedHomeAgent decoratedHomeAgent;
        private CountingIdlingResource homeAgentCounting;
        @Override
        public void onActivityLifecycleChanged(Activity activity, Stage stage) {
            HomeActivity homeActivity = (HomeActivity) activity;

            switch (stage) {
                case PRE_ON_CREATE:
                    Log.i("Test", "PRE ON CREATE");
                    homeAgentCounting = new CountingIdlingResource(HomeAgent.TAG);
                    homeAgentCounting.increment();
                    decoratedHomeAgent = new DecoratedHomeAgent(homeActivity, homeActivity, homeAgentCounting);
                    homeActivity.setAgent(decoratedHomeAgent);
                    registerIdlingResources(homeAgentCounting);
                    break;
                case STOPPED:
                    Log.i("Test", "DESTROYED");
                    registerIdlingResources(homeAgentCounting);
                    break;
                default:
            }
        }
    }

    private class DecoratedHomeAgent extends HomeAgent {

        private CountingIdlingResource countingIdlingResource;

        public DecoratedHomeAgent(Context context, HomeView homeView, CountingIdlingResource countingIdlingResource) {
            super(context, homeView);
            this.countingIdlingResource = countingIdlingResource;
            this.countingIdlingResource.decrement();
        }

        @Override
        public void init() {
            Log.i("Test", "busy");
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