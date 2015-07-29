package com.trend.tobeylin.tobeytrend.main.view;

import android.content.Context;
import android.content.Intent;
import android.support.test.espresso.contrib.CountingIdlingResource;
import android.support.test.espresso.intent.Intents;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;

import com.trend.tobeylin.tobeytrend.R;
import com.trend.tobeylin.tobeytrend.data.generator.api.KeywordApiService;
import com.trend.tobeylin.tobeytrend.entity.RegionTopSearchEntity;
import com.trend.tobeylin.tobeytrend.main.agent.HomeAgent;

import org.hamcrest.core.AllOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import custom.action.NumberPickerAction;
import custom.matcher.NumberPickerViewMatcher;
import custom.matcher.RecyclerViewMatcher;
import custom.rule.BaseTestRule;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasData;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.intent.matcher.UriMatchers.hasHost;
import static android.support.test.espresso.intent.matcher.UriMatchers.hasParamWithName;
import static android.support.test.espresso.intent.matcher.UriMatchers.hasPath;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Created by tobeylin on 15/7/20.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class HomeActivityTest {

    @Rule
    public BaseTestRule<HomeActivity, DecoratedHomeAgent> homeActivityTestRule = new BaseTestRule<>(HomeActivity.class, DecoratedHomeAgent.class);

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
        return homeActivityTestRule.getActivity().getString(resourceId);
    }

    public static class DecoratedHomeAgent extends HomeAgent {

        public final String TAG = DecoratedHomeAgent.class.getSimpleName();

        private CountingIdlingResource countingIdlingResource;

        public DecoratedHomeAgent(Context context, HomeView homeView, CountingIdlingResource countingIdlingResource) {
            super(context, homeView);
            Log.i(TAG, "DecoratedHomeAgent construct.");
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