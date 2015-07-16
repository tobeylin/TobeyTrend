package com.trend.tobeylin.tobeytrend.main.agent;

import com.trend.tobeylin.tobeytrend.BuildConfig;
import com.trend.tobeylin.tobeytrend.data.generator.KeywordGenerator;
import com.trend.tobeylin.tobeytrend.main.view.HomeView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import static com.trend.tobeylin.tobeytrend.data.generator.KeywordGenerator.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by tobeylin on 15/7/15.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class HomeAgentTest {

    private HomeAgent homeAgent;

    @Mock
    private HomeView mockHomeView;

    @Mock
    private KeywordGenerator mockKeywordGenerator;

    @Mock
    private KeywordGeneratorSyncListener mockSyncListener;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        homeAgent = new HomeAgent(mockHomeView, mockKeywordGenerator);
    }

    public void testConstructor() throws Exception {

    }

    @Test
    public void testInit() throws Exception {
        homeAgent.init();

        verify(mockKeywordGenerator).sync();
        verify(mockHomeView).showCountry(anyString());
    }

    @Test
    public void testOnSyncSuccess() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                homeAgent.onSyncSuccess(null);
                return null;
            }
        }).when(mockKeywordGenerator).sync();
        List mockKeywordList = mock(List.class);
        doReturn(mockKeywordList).when(mockKeywordGenerator).getKeywords();

        homeAgent.init();

        verify(mockKeywordGenerator).sync();
        verify(mockHomeView).showCountry(anyString());
        verify(mockHomeView).hideProgress();
        verify(mockHomeView).showGridImageView();
        verify(mockHomeView).showCountrySpinner();
        ArgumentCaptor<List> keywordListCaptor = ArgumentCaptor.forClass(List.class);
        verify(mockHomeView).updateKeywordGrid(keywordListCaptor.capture(), eq(homeAgent.getColumnCount()), eq(homeAgent.getRowCount()));
        List actualKeywordList = keywordListCaptor.getValue();
        String assertMessage = "First argument in updateKeywordGrid should be KeywordGenerator.getKeywords()";
        assertSame(assertMessage, mockKeywordList, actualKeywordList);
    }

    @Test
    public void testOpenSelectViewDialog() throws Exception {
        homeAgent.openSelectViewDialog();
        verify(mockHomeView).showSelectViewDialog(homeAgent.getColumnCount(), homeAgent.getRowCount());
    }

    @Test
    public void testClickKeyword() throws Exception {
        String testKeyword = "test";
        homeAgent.clickKeyword(testKeyword);
        verify(mockHomeView).showKeywordSearchPage("http://www.google.com/search?q=" + testKeyword);
    }

    @Test
    public void testSelectCountry() throws Exception {
        String testCountryName = "Country Name";
        List mockKeywordList = mock(List.class);
        doReturn(mockKeywordList).when(mockKeywordGenerator).getKeywords();

        homeAgent.selectCountry(testCountryName);

        verify(mockKeywordGenerator).setCountry(testCountryName);
        ArgumentCaptor<List> keywordListCaptor = ArgumentCaptor.forClass(List.class);
        verify(mockHomeView).updateKeywordGrid(keywordListCaptor.capture(), eq(homeAgent.getColumnCount()), eq(homeAgent.getRowCount()));
        List actualKeywordList = keywordListCaptor.getValue();
        String assertMessage = "First argument in updateKeywordGrid should be KeywordGenerator.getKeywords()";
        assertSame(assertMessage, mockKeywordList, actualKeywordList);
        verify(mockHomeView).showCountry(testCountryName);
    }

    @Test
    public void testUpdateGrid_withValidInput() throws Exception {
        List mockKeywordList = mock(List.class);
        doReturn(mockKeywordList).when(mockKeywordGenerator).getKeywords();

        int testColumnCount = 2;
        int testRowCount = 2;
        homeAgent.updateGrid(testColumnCount, testRowCount);

        ArgumentCaptor<List> keywordListCaptor = ArgumentCaptor.forClass(List.class);
        verify(mockHomeView).updateKeywordGrid(keywordListCaptor.capture(), eq(testColumnCount), eq(testRowCount));
        List actualKeywordList = keywordListCaptor.getValue();
        String assertMessage = "First argument in updateKeywordGrid should be KeywordGenerator.getKeywords()";
        assertSame(assertMessage, mockKeywordList, actualKeywordList);
    }

    @Test
    public void testUpdateGrid_withInvalidColumnCount() throws Exception {
        List mockKeywordList = mock(List.class);
        doReturn(mockKeywordList).when(mockKeywordGenerator).getKeywords();

        int testColumnCount = -1;
        int testRowCount = 2;
        homeAgent.updateGrid(testColumnCount, testRowCount);

        ArgumentCaptor<List> keywordListCaptor = ArgumentCaptor.forClass(List.class);
        int defaultColumnCount = 1;
        verify(mockHomeView).updateKeywordGrid(keywordListCaptor.capture(), eq(defaultColumnCount), eq(testRowCount));
        List actualKeywordList = keywordListCaptor.getValue();
        String assertMessage = "First argument in updateKeywordGrid should be KeywordGenerator.getKeywords()";
        assertSame(assertMessage, mockKeywordList, actualKeywordList);
    }

    @Test
    public void testUpdateGrid_withInvalidRowCount() throws Exception {
        List mockKeywordList = mock(List.class);
        doReturn(mockKeywordList).when(mockKeywordGenerator).getKeywords();

        int testColumnCount = 2;
        int testRowCount = -1;
        homeAgent.updateGrid(testColumnCount, testRowCount);

        ArgumentCaptor<List> keywordListCaptor = ArgumentCaptor.forClass(List.class);
        int defaultRowCount = 1;
        verify(mockHomeView).updateKeywordGrid(keywordListCaptor.capture(), eq(testColumnCount), eq(defaultRowCount));
        List actualKeywordList = keywordListCaptor.getValue();
        String assertMessage = "First argument in updateKeywordGrid should be KeywordGenerator.getKeywords()";
        assertSame(assertMessage, mockKeywordList, actualKeywordList);
    }

}