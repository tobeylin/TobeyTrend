package com.trend.tobeylin.tobeytrend.data.generator.api;

import android.test.AndroidTestCase;

import com.android.volley.Response;

import com.trend.tobeylin.tobeytrend.VolleyRequestQueue;
import com.trend.tobeylin.tobeytrend.entity.RegionTopSearchEntity;

import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;

/**
 * Created by tobeylin on 15/7/9.
 */
@RunWith(MockitoJUnitRunner.class)
public class KeywordApiServiceTest extends AndroidTestCase {

    private KeywordApiService keywordApiService;
    private KeywordApiService.ApiCallback callback;
    private VolleyRequestQueue volleyRequestQueue;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        volleyRequestQueue = VolleyRequestQueue.getInstance(getContext());
        callback = mock(KeywordApiService.ApiCallback.class);
    }

    public void testStart_ByCatchResponseEntity() throws Exception {

        keywordApiService = new KeywordApiService(volleyRequestQueue);
        keywordApiService.start(callback);

        ArgumentCaptor<RegionTopSearchEntity> entityArgumentCaptor = ArgumentCaptor.forClass(RegionTopSearchEntity.class);
        verify(callback).onSuccess(entityArgumentCaptor.capture());
        assertFalse("".equals(entityArgumentCaptor.getValue().US[0]));

    }


    public void testStart_ByCatchResponseString() throws Exception {

        keywordApiService = new KeywordApiService(getContext());
        keywordApiService.setVolleyRequestQueue(volleyRequestQueue);
        Response.Listener successListener = mock(Response.Listener.class);
        keywordApiService.start(callback);

        ArgumentCaptor<String> responseCaptor = ArgumentCaptor.forClass(String.class);
        verify(successListener).onResponse(responseCaptor.capture());

    }

    public void testStart_withDoAnswer(){

        keywordApiService = new KeywordApiService(getContext());
        keywordApiService.setVolleyRequestQueue(volleyRequestQueue);
        Response.Listener listener = mock(Response.Listener.class);


        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String response = (String)(invocation.getArguments()[0]);
                Response.Listener listener = (Response.Listener)invocation.getMock();
                listener.onResponse(response);
                return null;
            }
        }).when(listener).onResponse(anyString());
        keywordApiService.start(callback);
        verify(callback).onSuccess(Matchers.isNotNull(RegionTopSearchEntity.class));

    }

}