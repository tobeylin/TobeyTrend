package com.trend.tobeylin.tobeytrend.main.view;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.trend.tobeylin.tobeytrend.BuildConfig;
import com.trend.tobeylin.tobeytrend.R;
import com.trend.tobeylin.tobeytrend.main.agent.HomeAgent;
import com.trend.tobeylin.tobeytrend.ui.adapter.KeywordCardAdapter;
import com.trend.tobeylin.tobeytrend.ui.adapter.KeywordCardLayoutManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.assertj.android.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by tobeylin on 15/7/13.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class HomeActivityUnitTest {

    private HomeActivity homeActivity;
    @Mock
    private HomeAgent mockHomeAgent;

    private TextView showCountryTextView;
    private ProgressBar progressBar;
    private ImageView gridImageView;
    private Spinner countrySpinner;
    private RecyclerView keywordCardRecycleView;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        homeActivity = Robolectric.buildActivity(HomeActivity.class).create().get();
        homeActivity.setAgent(mockHomeAgent);

        showCountryTextView = (TextView) homeActivity.findViewById(R.id.home_showTextView);
        progressBar = (ProgressBar) homeActivity.findViewById(R.id.home_progressBar);
        gridImageView = (ImageView) homeActivity.getActionBar().getCustomView().findViewById(R.id.actionbar_gridImageView);
        countrySpinner = (Spinner) homeActivity.getActionBar().getCustomView().findViewById(R.id.actionbar_selectCountrySpinner);
        keywordCardRecycleView = (RecyclerView) homeActivity.findViewById(R.id.home_keywordCardRecycleView);
    }

    public void testOnCreate() {

        //TODO: test activity's life cycle
        assertNotNull(showCountryTextView);
        assertNotNull(progressBar);
        assertNotNull(gridImageView);
        assertNotNull(countrySpinner);
        assertNotNull(keywordCardRecycleView);

    }

    @Test
    public void testShowCountry() {

        String expectedCountryName = "Taiwan";
        homeActivity.showCountry(expectedCountryName);

        String expectedShowCountry = "Showing the latest hot searches in " + expectedCountryName + ".";
        String actualShowCountry = showCountryTextView.getText().toString();
        String assertMessage = "ShowCountryTextView does not show correctly.\n" +
                "Expect: " + expectedShowCountry + "\n" +
                "Actual: " + actualShowCountry;
        assertTrue(assertMessage, expectedShowCountry.equals(actualShowCountry));

    }

    @Test
    public void testShowProgressBar() {

        homeActivity.showProgress();

        int expectedProgressVisibility = View.VISIBLE;
        int actualProgressVisibility = progressBar.getVisibility();
        String assertMessage = "Progress's visibility expected visible, but invisible or gone";
        assertEquals(assertMessage, expectedProgressVisibility, actualProgressVisibility);

    }

    @Test
    public void testHideProgressBar() {
        homeActivity.hideProgress();

        assertThat(progressBar).isInvisible();
    }

    @Test
    public void testShowSelectViewDialog() {
        int dummyColumnCount = 1;
        int dummyRowCount = 1;
        homeActivity.showSelectViewDialog(dummyColumnCount, dummyRowCount);

        SelectViewDialogFragment selectViewDialogFragment = (SelectViewDialogFragment) homeActivity.getSupportFragmentManager().findFragmentByTag(SelectViewDialogFragment.TAG);
        assertNotNull(selectViewDialogFragment);
    }

    @Test
    public void testShowGridImageView() {
        homeActivity.showGridImageView();

        assertThat(gridImageView).isVisible();
    }

    @Test
    public void testShowCountrySpinner() {
        homeActivity.showCountrySpinner();

        assertThat(countrySpinner).isVisible();

        Adapter countrySpinnerAdapter = countrySpinner.getAdapter();
        String assertAdapterMessage = "CountrySpinnerAdapter should not be null.";
        assertNotNull(assertAdapterMessage, countrySpinnerAdapter);
    }

    @Test
    public void testUpdateKeywordGrid() {
        KeywordCardAdapter oldAdapter = (KeywordCardAdapter) keywordCardRecycleView.getAdapter();
        KeywordCardLayoutManager mockLayoutManager = mock(KeywordCardLayoutManager.class);
        keywordCardRecycleView.setLayoutManager(mockLayoutManager);
        int testKeywordCount = 10;
        List<String> testKeywordList = new ArrayList<>();
        for (int i = 0; i < testKeywordCount; ++i) {
            testKeywordList.add("Keyword_" + i);
        }
        int testColumnCount = 1;
        int testRowCount = 1;

        homeActivity.updateKeywordGrid(testKeywordList, testColumnCount, testRowCount);

        verify(mockLayoutManager).setWidthCount(anyInt());
        verify(mockLayoutManager).setHeightCount(anyInt());
        KeywordCardAdapter adapter = (KeywordCardAdapter) keywordCardRecycleView.getAdapter();
        String assertAdapterMessage = "KeywordCardAdapter should not be null";
        assertNotNull(assertAdapterMessage, adapter);
        assertNotSame(oldAdapter, adapter);
    }

    @Test
    public void testShowKeywordSearchPage() {
        String testUrl = "http://www.google.com/search?q=test";
        homeActivity.showKeywordSearchPage(testUrl);

        Intent expectedIntent = new Intent(Intent.ACTION_VIEW);
        expectedIntent.setData(Uri.parse(testUrl));
        assertThat(Shadows.shadowOf(homeActivity).getNextStartedActivity()).isEqualTo(expectedIntent);
    }

    @Test
    public void testOnClickGridImageView() {
        gridImageView.setVisibility(View.VISIBLE);
        gridImageView.setOnClickListener(homeActivity);
        gridImageView.performClick();

        verify(mockHomeAgent).openSelectViewDialog();
    }

    @Test
    public void testOnCountrySpinnerItemSelected() {
        int testDataPosition = 0;
        String[] testData = new String[]{"data_1", "data_2", "data_3"};
        countrySpinner.setVisibility(View.VISIBLE);
        ArrayAdapter<String> testSpinnerAdapter = new ArrayAdapter<>(mock(Context.class), android.R.layout.simple_expandable_list_item_1, testData);
        countrySpinner.setAdapter(testSpinnerAdapter);
        countrySpinner.setOnItemSelectedListener(homeActivity);

        countrySpinner.setSelection(testDataPosition);

        verify(mockHomeAgent).selectCountry(eq(testData[testDataPosition]));
    }

    @Test
    public void testOnKeywordClick() {
        int testColumnCount = 1;
        int testRowCount = 1;
        List<String> testKeywordList = new ArrayList<>();
        testKeywordList.add("Keyword_1");
        testKeywordList.add("Keyword_2");
        testKeywordList.add("Keyword_3");
        testKeywordList.add("Keyword_4");
        testKeywordList.add("Keyword_5");
        KeywordCardLayoutManager keywordCardLayoutManager = (KeywordCardLayoutManager) keywordCardRecycleView.getLayoutManager();
        keywordCardLayoutManager.setHeightCount(testColumnCount);
        keywordCardLayoutManager.setWidthCount(testRowCount);
        KeywordCardAdapter keywordCardAdapter = new KeywordCardAdapter(testKeywordList, testColumnCount, testRowCount);
        keywordCardAdapter.setOnItemClickListener(homeActivity);
        keywordCardRecycleView.setAdapter(keywordCardAdapter);
        keywordCardRecycleView.measure(0, 0);
        keywordCardRecycleView.layout(0, 0, 100, 10000);

        View firstView = keywordCardRecycleView.getChildAt(0);
        KeywordCardAdapter.ViewHolder firstViewHolder = (KeywordCardAdapter.ViewHolder) keywordCardRecycleView.getChildViewHolder(firstView);
        firstViewHolder.keywordCard.findViewById(R.id.keywordCard_keywordTypeTextView).performClick();

        verify(mockHomeAgent).clickKeyword(Matchers.anyString());
    }

    @Test
    public void testOnSelectViewDialogConfirmClick_withDifferentColumnRow() {

        SelectViewDialogFragment selectViewDialogFragment = spy(new SelectViewDialogFragment());
        Bundle bundle = new Bundle();
        int testOldColumnCount = 1;
        int testOldRowCount = 1;
        int testNewColumnCount = 2;
        int testNewRowCount = 2;
        bundle.putInt(SelectViewDialogFragment.BUNDLE_CURRENT_COLUMN_COUNT, testOldColumnCount);
        bundle.putInt(SelectViewDialogFragment.BUNDLE_CURRENT_ROW_COUNT, testOldRowCount);
        selectViewDialogFragment.setArguments(bundle);
        SupportFragmentTestUtil.startFragment(selectViewDialogFragment, HomeActivity.class);
        selectViewDialogFragment.setListener(homeActivity);
        Dialog dialogView = selectViewDialogFragment.getDialog();
        NumberPicker columnNumberPicker = (NumberPicker) dialogView.findViewById(R.id.selectViewDialog_widthNumberPicker);
        NumberPicker rowNumberPicker = (NumberPicker) dialogView.findViewById(R.id.selectViewDialog_heightNumberPicker);
        columnNumberPicker.setValue(testNewColumnCount);
        rowNumberPicker.setValue(testNewRowCount);

        Button confirmButton = ((AlertDialog) selectViewDialogFragment.getDialog()).getButton(AlertDialog.BUTTON_POSITIVE);
        confirmButton.performClick();

        verify(mockHomeAgent).updateGrid(anyInt(), anyInt());
    }

    @Test
    public void testOnSelectViewDialogConfirmClick_withSameColumnRow() {

        SelectViewDialogFragment selectViewDialogFragment = spy(new SelectViewDialogFragment());
        Bundle bundle = new Bundle();
        int testOldColumnCount = 1;
        int testOldRowCount = 1;
        int testNewColumnCount = 1;
        int testNewRowCount = 1;
        bundle.putInt(SelectViewDialogFragment.BUNDLE_CURRENT_COLUMN_COUNT, testOldColumnCount);
        bundle.putInt(SelectViewDialogFragment.BUNDLE_CURRENT_ROW_COUNT, testOldRowCount);
        selectViewDialogFragment.setArguments(bundle);
        SupportFragmentTestUtil.startFragment(selectViewDialogFragment, HomeActivity.class);
        selectViewDialogFragment.setListener(homeActivity);
        Dialog dialogView = selectViewDialogFragment.getDialog();
        NumberPicker columnNumberPicker = (NumberPicker) dialogView.findViewById(R.id.selectViewDialog_widthNumberPicker);
        NumberPicker rowNumberPicker = (NumberPicker) dialogView.findViewById(R.id.selectViewDialog_heightNumberPicker);
        columnNumberPicker.setValue(testNewColumnCount);
        rowNumberPicker.setValue(testNewRowCount);

        Button confirmButton = ((AlertDialog) selectViewDialogFragment.getDialog()).getButton(AlertDialog.BUTTON_POSITIVE);
        confirmButton.performClick();

        verify(mockHomeAgent, never()).updateGrid(anyInt(), anyInt());
    }

}