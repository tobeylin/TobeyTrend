package com.trend.tobeylin.tobeytrend.main.view;

import android.app.Activity;
import android.content.Context;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.lifecycle.ActivityLifecycleCallback;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.test.suitebuilder.annotation.LargeTest;

import com.trend.tobeylin.tobeytrend.R;
import com.trend.tobeylin.tobeytrend.entity.RegionTopSearchEntity;
import com.trend.tobeylin.tobeytrend.main.agent.HomeAgent;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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
    public ActivityTestRule<HomeActivity> homeActivityTestRule = new ActivityTestRule<>(HomeActivity.class);

    private HomeAgentInjector homeAgentInjector;

    @Before
    public void setUp() throws Exception {
        homeAgentInjector = new HomeAgentInjector();
        ActivityLifecycleMonitorRegistry.getInstance().addLifecycleCallback(homeAgentInjector);
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
    public void testClickKeyword() {

        onView(withId(R.id.actionbar_gridImageView)).check(matches(isDisplayed()));
        onView(withId(R.id.actionbar_gridImageView)).perform(click());

//        //check if have data
//        onView(withId(R.id.home_keywordCardRecycleView)).check(matches(isDisplayed()));
//        //yes, click
//        onView(withId(R.id.home_keywordCardRecycleView)).perform(RecyclerViewActions.actionOnItem(withChild(withId(R.id.keywordCard_keywordTypeTextView)), click()).atPosition(0));

    }

    private class HomeAgentInjector implements ActivityLifecycleCallback {

        private HomeIdlingResource homeIdlingResource;

        @Override
        public void onActivityLifecycleChanged(Activity activity, Stage stage) {
            HomeActivity homeActivity = (HomeActivity) activity;
            switch (stage) {
                case PRE_ON_CREATE:
                    homeIdlingResource = new HomeIdlingResource(homeActivity, homeActivity);
                    homeIdlingResource.setHomeView(homeActivity);
                    homeActivity.setAgent(homeIdlingResource);
                    registerIdlingResources(homeIdlingResource);
                    break;
                default:
            }
        }
    }

    private class HomeIdlingResource extends HomeAgent implements IdlingResource {

        private boolean isSync;
        private ResourceCallback callback;

        public HomeIdlingResource(Context context, HomeView homeView) {
            super(context, homeView);
            isSync = false;
        }

        @Override
        public void onSyncSuccess(RegionTopSearchEntity keywordResponseEntity) {
            super.onSyncSuccess(keywordResponseEntity);
            isSync = true;
            if (callback != null) {
                callback.onTransitionToIdle();
            }
        }

        @Override
        public void onSyncFail() {
            super.onSyncFail();
            isSync = false;
        }

        @Override
        public String getName() {
            return "HomeIdlingResource";
        }

        @Override
        public boolean isIdleNow() {
            return isSync;
        }

        @Override
        public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
            this.callback = resourceCallback;
        }
    }
}