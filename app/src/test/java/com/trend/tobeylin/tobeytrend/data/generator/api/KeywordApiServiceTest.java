package com.trend.tobeylin.tobeytrend.data.generator.api;

import com.android.volley.Request;
import com.android.volley.Response;
import com.google.gson.JsonParseException;
import com.trend.tobeylin.tobeytrend.VolleyRequestQueue;
import com.trend.tobeylin.tobeytrend.entity.RegionTopSearchEntity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.trend.tobeylin.tobeytrend.data.generator.api.KeywordApiService.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by tobeylin on 15/7/18.
 */
public class KeywordApiServiceTest {

    private KeywordApiService keywordApiService;

    @Mock
    private VolleyRequestQueue mockVolleyRequestQueue;

    @Mock
    private ApiCallback mockApiCallback;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testStart_Success() throws Exception {
        keywordApiService = spy(new KeywordApiService(mockVolleyRequestQueue));
        Request mockRequest = mock(Request.class);
        doReturn(mockRequest).when(keywordApiService).getRequest(any(Response.Listener.class), any(Response.ErrorListener.class));
        doReturn(mock(RegionTopSearchEntity.class)).when(keywordApiService).parse(anyString());

        keywordApiService.start(mockApiCallback);

        ArgumentCaptor<Response.Listener> successListenerCaptor = ArgumentCaptor.forClass(Response.Listener.class);
        verify(keywordApiService).getRequest(successListenerCaptor.capture(), any(Response.ErrorListener.class));
        verify(mockVolleyRequestQueue).sendRequest(same(mockRequest));
        assertNotNull("SuccessListener should not be null.", successListenerCaptor.getValue());
        successListenerCaptor.getValue().onResponse("");
        assertNotNull(keywordApiService.getTopSearchEntity());
        verify(mockApiCallback).onSuccess(any(RegionTopSearchEntity.class));
    }

    @Test
    public void testStart_NetworkFail() throws Exception {
        keywordApiService = spy(new KeywordApiService(mockVolleyRequestQueue));
        Request mockRequest = mock(Request.class);
        doReturn(mockRequest).when(keywordApiService).getRequest(any(Response.Listener.class), any(Response.ErrorListener.class));

        keywordApiService.start(mockApiCallback);

        ArgumentCaptor<Response.ErrorListener> errorListenerCaptor = ArgumentCaptor.forClass(Response.ErrorListener.class);
        verify(keywordApiService).getRequest(any(Response.Listener.class), errorListenerCaptor.capture());
        verify(mockVolleyRequestQueue).sendRequest(same(mockRequest));
        assertNotNull("ErrorListener should not be null.", errorListenerCaptor.getValue());
        errorListenerCaptor.getValue().onErrorResponse(null);
        verify(mockApiCallback).onFail();
    }

    @Test
    public void testStart_JsonParseFail() throws Exception {
        keywordApiService = spy(new KeywordApiService(mockVolleyRequestQueue));
        Request mockRequest = mock(Request.class);
        doReturn(mockRequest).when(keywordApiService).getRequest(any(Response.Listener.class), any(Response.ErrorListener.class));
        doThrow(JsonParseException.class).when(keywordApiService).parse(anyString());

        keywordApiService.start(mockApiCallback);

        ArgumentCaptor<Response.Listener> successListenerCaptor = ArgumentCaptor.forClass(Response.Listener.class);
        verify(keywordApiService).getRequest(successListenerCaptor.capture(), any(Response.ErrorListener.class));
        verify(mockVolleyRequestQueue).sendRequest(same(mockRequest));
        assertNotNull("SuccessListener should not be null.", successListenerCaptor.getValue());
        successListenerCaptor.getValue().onResponse("");
        verify(mockApiCallback).onFail();
    }

}