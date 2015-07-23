package custom.matcher;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.core.deps.guava.base.Preconditions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.view.View;

import com.trend.tobeylin.tobeytrend.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.AllOf;

import static android.support.test.espresso.core.deps.guava.base.Preconditions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

/**
 * Created by tobeylin on 15/7/23.
 */
public class NumberPickerViewMatcher {

    public static final String TAG = NumberPickerViewMatcher.class.getSimpleName();

    public static Matcher<View> withValue(int numberPickerId, String expectedValue) {
        checkNotNull(numberPickerId);
        checkNotNull(expectedValue);
        return withValue(numberPickerId, withText(expectedValue));
    }

    public static Matcher<View> withValue(final int numberPickerId, final Matcher<View> valueMatcher) {

        checkNotNull(numberPickerId);
        checkNotNull(valueMatcher);
        Matcher<View> matcher = new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                boolean result = AllOf.allOf(valueMatcher, withParent(withId(numberPickerId))).matches(item);
                return result;
            }

            @Override
            public void describeTo(Description description) {
               description.appendText("================== " + TAG + " ==================");
            }
        };
        return matcher;
    }

}
