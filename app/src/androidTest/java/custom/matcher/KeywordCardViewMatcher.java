package custom.matcher;

import android.view.View;
import android.view.ViewGroup;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by tobeylin on 15/7/27.
 */
public class KeywordCardViewMatcher {

    public static Matcher<View> withVisibleKeywordText(final View keywordCardView){
        return new TypeSafeMatcher<View>(){
            @Override
            public void describeTo(Description description) {
                description.appendText("with withVisibleKeywordText");
            }

            @Override
            protected boolean matchesSafely(View item) {
                View view0 = keywordCardView.findViewWithTag("keywordTypeTextView_0");
                View view1 = keywordCardView.findViewWithTag("keywordTypeTextView_1");
                View targetView = (((ViewGroup)view0.getParent()).getVisibility() == View.VISIBLE)? view0: view1;
                return item == targetView;
            }
        };
    }


}
