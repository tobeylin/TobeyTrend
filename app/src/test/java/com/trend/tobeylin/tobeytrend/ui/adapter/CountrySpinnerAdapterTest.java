package com.trend.tobeylin.tobeytrend.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.trend.tobeylin.tobeytrend.BuildConfig;
import com.trend.tobeylin.tobeytrend.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by tobeylin on 15/7/16.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class CountrySpinnerAdapterTest {

    private CountrySpinnerAdapter countrySpinnerAdapter;
    private List<String> testCountryList;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        // Prepare the test data.
        testCountryList = new ArrayList<>();
        testCountryList.add("Country_1");
        testCountryList.add("Country_2");
        testCountryList.add("Country_3");
        testCountryList.add("Country_4");
        testCountryList.add("Country_5");
    }

    @Test
    public void testConstructor_withNullArgument() throws Exception {
        countrySpinnerAdapter = new CountrySpinnerAdapter(null);
        assertNotNull(countrySpinnerAdapter.getCountries());
        assertEquals(0, countrySpinnerAdapter.getCountries().size());
    }

    @Test
    public void testConstructor_withNotNullArgument() throws Exception {
        countrySpinnerAdapter = new CountrySpinnerAdapter(testCountryList);
        assertSame(testCountryList, countrySpinnerAdapter.getCountries());
    }

    @Test
    public void testGetCount() throws Exception {
        countrySpinnerAdapter = new CountrySpinnerAdapter(testCountryList);
        int expectedGetCount = testCountryList.size();
        int actualGetCount = countrySpinnerAdapter.getCount();
        assertEquals(expectedGetCount, actualGetCount);
    }

    @Test
    public void testGetItem() throws Exception {
        countrySpinnerAdapter = new CountrySpinnerAdapter(testCountryList);
        int testItemPosition = 0;
        String expectedItem = testCountryList.get(testItemPosition);
        Object actualItem = countrySpinnerAdapter.getItem(testItemPosition);
        String assertMessage = "The returning object of CountrySpinnerAdapter.getItem(int) should refer to object as same as testCountryList.get(int)";
        assertSame(assertMessage, expectedItem, actualItem);
    }

    @Test
    public void testGetItemId() throws Exception {
        countrySpinnerAdapter = new CountrySpinnerAdapter(testCountryList);
        int testItemPosition = 0;
        long expectedItemId = 0;
        long actualItemId = countrySpinnerAdapter.getItemId(testItemPosition);
        assertEquals(expectedItemId, actualItemId);
    }

    @Test
    public void testGetView_withRecycleView() throws Exception {
        countrySpinnerAdapter = new CountrySpinnerAdapter(testCountryList);
        int testItemPosition = 0;
        View testConvertView = View.inflate(RuntimeEnvironment.application, R.layout.country_spinner_item, null);
        CountrySpinnerAdapter.ViewHolder testConvertViewHolder = new CountrySpinnerAdapter.ViewHolder(testConvertView);
        testConvertView.setTag(testConvertViewHolder);
        ViewGroup mockViewGroup = mock(ViewGroup.class);
        when(mockViewGroup.getContext()).thenReturn(RuntimeEnvironment.application);

        View actualItemView = countrySpinnerAdapter.getView(testItemPosition, testConvertView, null);

        assertSame(testConvertView, actualItemView);
        TextView countryNameTextView = (TextView) actualItemView.findViewById(R.id.countrySpinnerItem_countryNameTextView);
        assertNotNull(countryNameTextView);
        String expectedCountryName = testCountryList.get(testItemPosition);
        String actualCountryName = countryNameTextView.getText().toString();
        assertEquals(expectedCountryName, actualCountryName);
    }

    @Test
    public void testGetView_withNoConvertView() throws Exception {
        countrySpinnerAdapter = new CountrySpinnerAdapter(testCountryList);
        int testItemPosition = 0;

        View itemView = countrySpinnerAdapter.getView(testItemPosition, null, new Spinner(RuntimeEnvironment.application));

        assertNotNull(itemView);
        assertNotNull(itemView.getTag());
        TextView countryNameTextView = (TextView) itemView.findViewById(R.id.countrySpinnerItem_countryNameTextView);
        assertNotNull(countryNameTextView);
        String expectedCountryName = testCountryList.get(testItemPosition);
        String actualCountryName = countryNameTextView.getText().toString();
        assertEquals(expectedCountryName, actualCountryName);
    }
}