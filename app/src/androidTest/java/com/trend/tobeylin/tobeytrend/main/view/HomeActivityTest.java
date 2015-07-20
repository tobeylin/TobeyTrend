package com.trend.tobeylin.tobeytrend.main.view;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.lifecycle.ActivityLifecycleCallback;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.test.ActivityInstrumentationTestCase2;

import com.trend.tobeylin.tobeytrend.R;
import com.trend.tobeylin.tobeytrend.entity.RegionTopSearchEntity;
import com.trend.tobeylin.tobeytrend.main.agent.HomeAgent;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by tobeylin on 15/7/20.
 */
public class HomeActivityTest extends ActivityInstrumentationTestCase2<HomeActivity> {

    private HomeActivity homeActivity;

    public HomeActivityTest() {
        super("com.trend.tobeylin.tobeytrend.main.view", HomeActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());

        HomeAgentInjector homeAgentInjector = new HomeAgentInjector();
        ActivityLifecycleMonitorRegistry.getInstance().addLifecycleCallback(homeAgentInjector);
        homeActivity = getActivity();
    }


    @Test
    public void testOnCreate() {

        Espresso.onView(ViewMatchers.withId(R.id.actionbar_gridImageView)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

    }

    private class HomeAgentInjector implements ActivityLifecycleCallback {

        private HomeIdlingResource homeIdlingResource;

        @Override
        public void onActivityLifecycleChanged(Activity activity, Stage stage) {
            switch (stage) {
                case PRE_ON_CREATE:
                    HomeActivity homeActivity = (HomeActivity) activity;
                    homeIdlingResource = new HomeIdlingResource(homeActivity, homeActivity);
                    homeIdlingResource.setHomeView(homeActivity);
                    homeActivity.setAgent(homeIdlingResource);
                    Espresso.registerIdlingResources(homeIdlingResource);
                    break;
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
            callback.onTransitionToIdle();
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