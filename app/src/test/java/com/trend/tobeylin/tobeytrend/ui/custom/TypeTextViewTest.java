package com.trend.tobeylin.tobeytrend.ui.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.trend.tobeylin.tobeytrend.BuildConfig;
import com.trend.tobeylin.tobeytrend.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;

import static org.assertj.android.api.Assertions.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class TypeTextViewTest {

    private TypeTextView typeTextView;
    private Context context;

    @Mock
    private TextView mockTextView;

    @Mock
    private CursorView mockCursorView;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        context = RuntimeEnvironment.application;
    }

    @TargetApi(16)
    @Test
    public void testConstructor1() throws Exception {
        typeTextView = new TypeTextView(context);

        assertEquals(TypeTextView.DEFAULT_TEXT_SIZE, typeTextView.getTextSize(), 0);
        assertEquals(TypeTextView.DEFAULT_TEXT_COLOR, typeTextView.getTextColor());
        assertEquals(TypeTextView.DEFAULT_CURSOR_COLOR, typeTextView.getCursorColor());
        assertEquals(TypeTextView.DEFAULT_TEXT_SHADOW_RADIUS, typeTextView.getTextShadowRadius());
        assertEquals(TypeTextView.DEFAULT_TEXT_SHADOW_COLOR, typeTextView.getTextShadowColor());
        assertEquals(TypeTextView.DEFAULT_TEXT_SHADOW_DX, typeTextView.getTextShadowDx());
        assertEquals(TypeTextView.DEFAULT_TEXT_SHADOW_DY, typeTextView.getTextShadowDy());
        TextView textView = (TextView) typeTextView.findViewById(R.id.typeTextView_textView);
        assertNotNull(textView);
        assertEquals(Typeface.BOLD, textView.getTypeface().getStyle());
        assertEquals(typeTextView.getTextSize(), textView.getTextSize(), 0);
        assertEquals(typeTextView.getTextColor(), textView.getCurrentTextColor());
        assertEquals(typeTextView.getTextShadowRadius(), textView.getShadowRadius(), 0);
        assertEquals(typeTextView.getTextShadowColor(), textView.getShadowColor());
        assertEquals(typeTextView.getTextShadowDx(), textView.getShadowDx(), 0);
        assertEquals(typeTextView.getTextShadowDy(), textView.getShadowDy(), 0);
        CursorView textCursorView = (CursorView) typeTextView.findViewById(R.id.typeTextView_textCursorView);
        assertNotNull(textCursorView);
        assertEquals(typeTextView.getCursorColor(), Shadows.shadowOf(textCursorView.getBackground()).getCreatedFromResId());
        assertThat(textCursorView).isGone();
    }

    @TargetApi(16)
    @Test
    public void testConstructor2() throws Exception {
        String testTextColor = "#FF0000";
        String testTextSize = "25sp";
        String testCursorColor = "#00FF00";
        String testShadowRadius = "1";
        String testShadowColor = "#0000FF";
        String testShadowDy = "2";
        String testShadowDx = "3";
        AttributeSet attrs = Robolectric.buildAttributeSet()
                .addAttribute(R.styleable.TypeTextView_textColor, testTextColor)
                .addAttribute(R.styleable.TypeTextView_textSize, testTextSize)
                .addAttribute(R.styleable.TypeTextView_cursorColor, testCursorColor)
                .addAttribute(R.styleable.TypeTextView_shadowRadius, testShadowRadius)
                .addAttribute(R.styleable.TypeTextView_shadowColor, testShadowColor)
                .addAttribute(R.styleable.TypeTextView_shadowDx, testShadowDx)
                .addAttribute(R.styleable.TypeTextView_shadowDy, testShadowDy)
                .build();

        typeTextView = new TypeTextView(context, attrs);

        int expectedTextColor = Color.parseColor(testTextColor);
        float expectedTextSize = 25;
        int expectedCursorColor = Color.parseColor(testCursorColor);
        int expectedShadowRadius = Integer.valueOf(testShadowRadius);
        int expectedShadowColor = Color.parseColor(testShadowColor);
        int expectedShadowDy = Integer.valueOf(testShadowDy);
        int expectedShadowDx = Integer.valueOf(testShadowDx);
        assertEquals(expectedTextSize, typeTextView.getTextSize(), 0);
        assertEquals(expectedTextColor, typeTextView.getTextColor());
        assertEquals(expectedCursorColor, typeTextView.getCursorColor());
        assertEquals(expectedShadowRadius, typeTextView.getTextShadowRadius());
        assertEquals(expectedShadowColor, typeTextView.getTextShadowColor());
        assertEquals(expectedShadowDx, typeTextView.getTextShadowDx());
        assertEquals(expectedShadowDy, typeTextView.getTextShadowDy());
        TextView textView = (TextView) typeTextView.findViewById(R.id.typeTextView_textView);
        assertNotNull(textView);
        assertEquals(Typeface.BOLD, textView.getTypeface().getStyle());
        assertEquals(typeTextView.getTextSize(), textView.getTextSize(), 0);
        assertEquals(typeTextView.getTextColor(), textView.getCurrentTextColor());
        assertEquals(typeTextView.getTextShadowRadius(), textView.getShadowRadius(), 0);
        assertEquals(typeTextView.getTextShadowColor(), textView.getShadowColor());
        assertEquals(typeTextView.getTextShadowDx(), textView.getShadowDx(), 0);
        assertEquals(typeTextView.getTextShadowDy(), textView.getShadowDy(), 0);
        CursorView textCursorView = (CursorView) typeTextView.findViewById(R.id.typeTextView_textCursorView);
        assertNotNull(textCursorView);
        assertEquals(typeTextView.getCursorColor(), Shadows.shadowOf(textCursorView.getBackground()).getCreatedFromResId());
        assertThat(textCursorView).isGone();
    }

    @Test
    public void testClearText() throws Exception {
        typeTextView = new TypeTextView(context);
        typeTextView.clearText();
        TextView textView = (TextView) typeTextView.findViewById(R.id.typeTextView_textView);
        assertThat(textView).hasText("");
    }

    @Test
    public void testGetCurrentText() throws Exception {
        typeTextView = new TypeTextView(context);
        TextView textView = (TextView) typeTextView.findViewById(R.id.typeTextView_textView);

        String actualCurrentText = typeTextView.getCurrentText();
        String expectedCurrentText = textView.getText().toString();
        assertEquals(expectedCurrentText, actualCurrentText);
    }

    @Test
    public void testShowTextCursor() throws Exception {
        typeTextView = spy(new TypeTextView(context));
        RelativeLayout.LayoutParams mockParams = mock(RelativeLayout.LayoutParams.class);
        doReturn(mockParams).when(typeTextView).getCursorLayoutParams();
        CursorView cursorView = (CursorView) typeTextView.findViewById(R.id.typeTextView_textCursorView);

        typeTextView.showTextCursor();

        assertThat(cursorView).isVisible();
        assertSame(mockParams, cursorView.getLayoutParams());
    }

    public void testStartTypeText() throws Exception {
        typeTextView = new TypeTextView(context, mockTextView, mockCursorView);

        String testText = "Hello";
        typeTextView.startTypeText(testText);

        assertEquals(testText, typeTextView.getFullText());
        verify(mockCursorView).resetCursor();

        //TODO: start typing text testing
    }

}