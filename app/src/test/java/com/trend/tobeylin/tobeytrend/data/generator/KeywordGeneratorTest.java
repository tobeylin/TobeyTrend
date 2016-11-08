package com.trend.tobeylin.tobeytrend.data.generator;

import com.trend.tobeylin.tobeytrend.data.generator.api.KeywordApiService;
import com.trend.tobeylin.tobeytrend.entity.RegionTopSearchEntity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class KeywordGeneratorTest {

    @InjectMocks
    private KeywordGenerator keywordGenerator;

    @Mock
    private KeywordApiService keywordApiService;

    @Test
    public void testSync_SuccessAndSync_ByDoAnswer(){
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                KeywordApiService.ApiCallback callback = (KeywordApiService.ApiCallback) invocation.getArguments()[0];
                callback.onSuccess(new RegionTopSearchEntity());
                return null;
            }
        }).when(keywordApiService).start(any(KeywordApiService.ApiCallback.class));
        KeywordGenerator.KeywordGeneratorSyncListener mockListener = mock(KeywordGenerator.KeywordGeneratorSyncListener.class);
        keywordGenerator.setListener(mockListener);

        keywordGenerator.sync();

        verify(keywordApiService, times(1)).start(any(KeywordApiService.ApiCallback.class));
        assertEquals(true, keywordGenerator.isSync());
        verify(mockListener).onSyncSuccess(any(RegionTopSearchEntity.class));
    }

    @Test
    public void testSync_SuccessAndSync_ByCaptor(){

        KeywordGenerator.KeywordGeneratorSyncListener mockListener = mock(KeywordGenerator.KeywordGeneratorSyncListener.class);
        keywordGenerator.setListener(mockListener);
        keywordGenerator.sync();

        ArgumentCaptor<KeywordApiService.ApiCallback> callbackCaptor = ArgumentCaptor.forClass(KeywordApiService.ApiCallback.class);
        verify(keywordApiService).start(callbackCaptor.capture());
        callbackCaptor.getValue().onSuccess(new RegionTopSearchEntity());
        assertEquals(true, keywordGenerator.isSync());
        verify(mockListener, times(1)).onSyncSuccess(any(RegionTopSearchEntity.class));
    }

    @Test
    public void testSync_FailFromApiResponse(){
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                KeywordApiService.ApiCallback callback = (KeywordApiService.ApiCallback) invocation.getArguments()[0];
                callback.onFail();
                return null;
            }
        }).when(keywordApiService).start(any(KeywordApiService.ApiCallback.class));

        keywordGenerator.sync();

        verify(keywordApiService, times(1)).start(any(KeywordApiService.ApiCallback.class));
        assertEquals(false, keywordGenerator.isSync());
    }

    @Test
    public void testGetKeywords_withCountryName(){
        RegionTopSearchEntity mockTopSearchEntity = mock(RegionTopSearchEntity.class);
        int countryKeywordCount = 20;
        List mockCountryList = mock(List.class);
        when(mockCountryList.size()).thenReturn(countryKeywordCount);
        doReturn(mockCountryList).when(mockTopSearchEntity).getCountryKeywords(anyString());
        keywordGenerator.setTopSearchEntity(mockTopSearchEntity);

        String validCountryName = "TW";
        assertEquals(20, keywordGenerator.getKeywords(validCountryName).size());

    }

    @Test
    public void testGetKeywords_withAllRegion(){
        RegionTopSearchEntity mockTopSearchEntity = mock(RegionTopSearchEntity.class);
        int countryKeywordCount = 20;
        int countryCount = 47;
        List mockAllCountryList = mock(List.class);
        when(mockAllCountryList.size()).thenReturn(countryKeywordCount * countryCount);
        doReturn(mockAllCountryList).when(mockTopSearchEntity).getCountryKeywords(anyString());
        keywordGenerator.setTopSearchEntity(mockTopSearchEntity);

        String allRegion = "all";
        assertEquals(countryKeywordCount * countryCount, keywordGenerator.getKeywords(allRegion).size());
    }

    //    @Test
    //    public void testSync_SuccessWithCorrectSyntaxApiResponse1() {
    //
    //        keywordGenerator = KeywordGenerator.getInstance(context);
    //        keywordGenerator.setVolleyRequestQueue(volleyRequestQueue);
    //        keywordGenerator.setListener(listener);
    //        keywordGenerator.sync();
    //
    //        ArgumentCaptor<Response.Listener> successCaptor = ArgumentCaptor.forClass(Response.Listener.class);
    //        verify(volleyRequestQueue).sendGetRequest(anyString(), successCaptor.capture(), any(Response.ErrorListener.class));
    //        String testApiResponse = KeywordGeneratorTestData.testApiJsonSyntaxCorrectResponse;
    //        successCaptor.getValue().onResponse(testApiResponse);
    //        doNothing().when(listener).onSyncSuccess(any(RegionTopSearchEntity.class));
    //
    //        verify(listener).onSyncSuccess(Matchers.isNotNull(RegionTopSearchEntity.class));
    //
    //    }
    //
    //    @Test
    //    public void testSyncSuccessWithCorrectApiResponse2(){
    //        doAnswer(new Answer<Void>() {
    //            @Override
    //            public Void answer(InvocationOnMock invocation) throws Throwable {
    //                Response.Listener listener = (Response.Listener)(invocation.getArguments()[1]);
    //                listener.onResponse(KeywordGeneratorTestData.testApiJsonSyntaxCorrectResponse);
    //                return null;
    //            }
    //        }).when(volleyRequestQueue).sendGetRequest(anyString(), any(Response.Listener.class), any(Response.ErrorListener.class));
    //        doNothing().when(listener).onSyncSuccess(any(RegionTopSearchEntity.class));
    //
    //        keywordGenerator = KeywordGenerator.getInstance(context);
    //        keywordGenerator.setVolleyRequestQueue(volleyRequestQueue);
    //        keywordGenerator.setListener(listener);
    //        keywordGenerator.sync();
    //
    //        verify(listener).onSyncSuccess(Matchers.isNotNull(RegionTopSearchEntity.class));
    //    }

}