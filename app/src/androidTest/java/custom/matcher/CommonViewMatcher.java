package custom.matcher;

import android.support.test.espresso.core.deps.guava.base.Preconditions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.util.List;

/**
 * Created by tobeylin on 15/7/24.
 */
public class CommonViewMatcher {

    private CommonViewMatcher(){

    }

    public static Matcher<View> withText(final List<String> candidateTextList) {
        Preconditions.checkNotNull(candidateTextList);
        return new BoundedMatcher(TextView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("with text in candidateTextList");
            }

            @Override
            protected boolean matchesSafely(Object o) {
                if(!(o instanceof TextView)){
                    return false;
                }

                TextView textView = (TextView) o;
                String actualText = textView.getText().toString();
                for(String candidate: candidateTextList) {
                    if(actualText.contains(candidate)){
                        return true;
                    }
                }
                return false;
            }
        };
    }

}
