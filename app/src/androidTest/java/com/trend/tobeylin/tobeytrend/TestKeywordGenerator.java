package com.trend.tobeylin.tobeytrend;

import android.content.Context;
import android.test.InstrumentationTestCase;

import com.android.volley.Response;
import com.trend.tobeylin.tobeytrend.data.generator.KeywordGenerator;
import com.trend.tobeylin.tobeytrend.data.generator.KeywordGenerator.*;
import com.trend.tobeylin.tobeytrend.entity.RegionTopSearchEntity;
import com.trend.tobeylin.tobeytrend.test.data.KeywordGeneratorTestData;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;

import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

/**
 * Created by tobeylin on 15/7/6.
 */
@RunWith(MockitoJUnitRunner.class)
public class TestKeywordGenerator extends InstrumentationTestCase{

    private Context context;
    private VolleyRequestQueue volleyRequestQueue;
    private KeywordGenerator keywordGenerator;
    private KeywordGeneratorSyncListener listener;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        context = mock(Context.class);
        volleyRequestQueue = mock(VolleyRequestQueue.class);
        listener = mock(KeywordGeneratorSyncListener.class);
    }

    @Test
    public void testSyncSuccessWithCorrectSyntaxApiResponse1() {

        keywordGenerator = KeywordGenerator.getInstance(context);
        keywordGenerator.setVolleyRequestQueue(volleyRequestQueue);
        keywordGenerator.setListener(listener);
        keywordGenerator.sync();

        ArgumentCaptor<Response.Listener> successCaptor = ArgumentCaptor.forClass(Response.Listener.class);
        verify(volleyRequestQueue).sendGetRequest(anyString(), successCaptor.capture(), any(Response.ErrorListener.class));
        String testApiResponse = KeywordGeneratorTestData.testApiJsonSyntaxCorrectResponse;
        successCaptor.getValue().onResponse(testApiResponse);
        doNothing().when(listener).onSyncSuccess(any(RegionTopSearchEntity.class));

        verify(listener).onSyncSuccess(Matchers.isNotNull(RegionTopSearchEntity.class));

    }

    @Test
    public void testSyncSuccessWithCorrectApiResponse2(){
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Response.Listener listener = (Response.Listener)(invocation.getArguments()[1]);
                listener.onResponse(KeywordGeneratorTestData.testApiJsonSyntaxCorrectResponse);
                return null;
            }
        }).when(volleyRequestQueue).sendGetRequest(anyString(), any(Response.Listener.class), any(Response.ErrorListener.class));
        doNothing().when(listener).onSyncSuccess(any(RegionTopSearchEntity.class));

        keywordGenerator = KeywordGenerator.getInstance(context);
        keywordGenerator.setVolleyRequestQueue(volleyRequestQueue);
        keywordGenerator.setListener(listener);
        keywordGenerator.sync();

        verify(listener).onSyncSuccess(Matchers.isNotNull(RegionTopSearchEntity.class));
    }

}
