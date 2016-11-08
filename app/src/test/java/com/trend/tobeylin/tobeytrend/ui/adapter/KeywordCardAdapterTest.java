package com.trend.tobeylin.tobeytrend.ui.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.trend.tobeylin.tobeytrend.BuildConfig;
import com.trend.tobeylin.tobeytrend.ui.custom.KeywordCard;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static com.trend.tobeylin.tobeytrend.ui.adapter.KeywordCardAdapter.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by tobeylin on 15/7/16.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class KeywordCardAdapterTest {

    private KeywordCardAdapter keywordCardAdapter;
    private List<String> testKeywordList;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        // Prepare the test data.
        testKeywordList = new ArrayList<>();
        testKeywordList.add("Country_1");
        testKeywordList.add("Country_2");
        testKeywordList.add("Country_3");
        testKeywordList.add("Country_4");
        testKeywordList.add("Country_5");
    }

    @Test
    public void testOnCreateViewHolder() throws Exception {

        //TODO

//        int testColumnCount = 1;
//        int testRowCount = 1;
//        int testKeywordTextSize = 20;
//        keywordCardAdapter = spy(new KeywordCardAdapter(testKeywordList, testColumnCount, testRowCount));
//        doReturn(testKeywordTextSize).when(keywordCardAdapter).getKeywordTextSize(any(Context.class), anyInt(), anyInt());
//
//        ViewGroup mockViewGroup = mock(ViewGroup.class);
//        when(mockViewGroup.getContext()).thenReturn(RuntimeEnvironment.application);
//
//        ViewHolder viewHolder = keywordCardAdapter.onCreateViewHolder(mockViewGroup, 0);
//
//        assertNotNull(viewHolder.keywordCard);
//        assertEquals(testKeywordTextSize, viewHolder.keywordCard.);

    }

    @Test
    public void testOnBindViewHolder() throws Exception {
        int testColumnCount = 1;
        int testRowCount = 1;
        keywordCardAdapter = spy(new KeywordCardAdapter(testKeywordList, testColumnCount, testRowCount));
        doNothing().when(keywordCardAdapter).updateKeywordCard(any(ViewHolder.class));
        ViewHolder testViewHolder = new ViewHolder(mock(KeywordCard.class));

        keywordCardAdapter.onBindViewHolder(testViewHolder, 0);

        ArgumentCaptor<ViewHolder> viewHolderCaptor = ArgumentCaptor.forClass(ViewHolder.class);
        verify(keywordCardAdapter).updateKeywordCard(viewHolderCaptor.capture());
        ViewHolder actualViewHolder = viewHolderCaptor.getValue();
        assertSame(testViewHolder, actualViewHolder);
    }

    @Test
    public void testUpdateKeywordCard() throws Exception {
        String testKeyword = "test keyword";
        int testColumnCount = 1;
        int testRowCount = 1;
        KeywordCard mockKeywordCard = mock(KeywordCard.class);
        when(mockKeywordCard.getKeyword()).thenReturn(testKeyword);
        ViewHolder testViewHolder = new ViewHolder(mockKeywordCard);
        keywordCardAdapter = new KeywordCardAdapter(testKeywordList, testColumnCount, testRowCount);

        keywordCardAdapter.updateKeywordCard(testViewHolder);

        ArgumentCaptor<String> keywordCaptor = ArgumentCaptor.forClass(String.class);
        verify(mockKeywordCard).setKeyword(keywordCaptor.capture());
        String expectedKeyword = keywordCaptor.getValue();
        assertTrue(testKeywordList.contains(expectedKeyword));
    }

    @Test
    public void testGetItemCount() throws Exception {
        int testColumnCount = 1;
        int testRowCount = 1;
        keywordCardAdapter = new KeywordCardAdapter(testKeywordList, testColumnCount, testRowCount);

        int expectedGetItemCount = testColumnCount * testRowCount;
        int actualGetItemCount = keywordCardAdapter.getItemCount();
        assertEquals(expectedGetItemCount, actualGetItemCount);
    }

    @Test
    public void testGetKeywordTextSize_withOneItem() throws Exception {
        int testColumnCount = 1;
        int testRowCount = 1;
        keywordCardAdapter = new KeywordCardAdapter(testKeywordList, testColumnCount, testRowCount);

        float actualTextSize = keywordCardAdapter.getKeywordTextSize(RuntimeEnvironment.application, testColumnCount, testRowCount);
        float expectedTextSize = 20;
        assertEquals(expectedTextSize, actualTextSize, 0);
    }

    @Test
    public void testGetKeywordTextSize_withBigColumnCount() throws Exception {
        int testColumnCount = 5;
        int testRowCount = 1;
        keywordCardAdapter = new KeywordCardAdapter(testKeywordList, testColumnCount, testRowCount);

        float actualTextSize = keywordCardAdapter.getKeywordTextSize(RuntimeEnvironment.application, testColumnCount, testRowCount);
        float expectedTextSize = 8;
        assertEquals(expectedTextSize, actualTextSize, 0);
    }

    @Test
    public void testGetKeywordTextSize_withBigRowCount() throws Exception {
        int testColumnCount = 1;
        int testRowCount = 5;
        keywordCardAdapter = new KeywordCardAdapter(testKeywordList, testColumnCount, testRowCount);

        float actualTextSize = keywordCardAdapter.getKeywordTextSize(RuntimeEnvironment.application, testColumnCount, testRowCount);
        float expectedTextSize = 8;
        assertEquals(expectedTextSize, actualTextSize, 0);
    }

    @Test
    public void testGetKeywordTextSize_withMaxNumberItem() throws Exception {
        int testColumnCount = 5;
        int testRowCount = 5;
        keywordCardAdapter = new KeywordCardAdapter(testKeywordList, testColumnCount, testRowCount);

        float actualTextSize = keywordCardAdapter.getKeywordTextSize(RuntimeEnvironment.application, testColumnCount, testRowCount);
        float expectedTextSize = 3;
        assertEquals(expectedTextSize, actualTextSize, 0);
    }

}