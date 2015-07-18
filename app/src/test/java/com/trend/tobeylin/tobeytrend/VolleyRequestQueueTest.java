package com.trend.tobeylin.tobeytrend;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by tobeylin on 15/7/18.
 */
public class VolleyRequestQueueTest {

    private VolleyRequestQueue volleyRequestQueue;

    @Mock
    private RequestQueue mockRequestQueue;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        volleyRequestQueue = new VolleyRequestQueue(mockRequestQueue);
    }

    @Test
    public void testSendRequest() throws Exception {
        Request mockRequest = mock(Request.class);
        volleyRequestQueue.sendRequest(mockRequest);
        verify(mockRequestQueue).add(eq(mockRequest));
    }
}