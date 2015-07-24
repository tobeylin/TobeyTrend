package custom.matcher;

import android.content.res.Resources;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by tobeylin on 15/7/21.
 */
public class RecyclerViewMatcher {

    private RecyclerViewMatcher() {

    }

    public static Matcher<View> withRowCount(final int expectedRowCount) {
        return withRowCount(Matchers.equalTo(expectedRowCount));
    }

    public static Matcher<View> withRowCount(final Matcher<Integer> rowCountMatcher) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                if (!(item instanceof RecyclerView)) {
                    return false;
                }
                RecyclerView recyclerView = (RecyclerView) item;
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (!(layoutManager instanceof GridLayoutManager)) {
                    return false;
                }
                GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                int columnCount = gridLayoutManager.getSpanCount();
                int itemCount = recyclerView.getChildCount();
                int rowCount = itemCount / columnCount;
                return rowCountMatcher.matches(rowCount);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("has row count: ");
                rowCountMatcher.describeTo(description);
            }
        };
    }

    public static Matcher<View> withColumnCount(final int expectedColumnCount) {
        return withColumnCount(Matchers.equalTo(expectedColumnCount));
    }

    public static Matcher<View> withColumnCount(final Matcher<Integer> columnCountMatcher) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                if (!(item instanceof RecyclerView)) {
                    return false;
                }
                RecyclerView recyclerView = (RecyclerView) item;
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (!(layoutManager instanceof GridLayoutManager)) {
                    return false;
                }
                GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                int columnCount = gridLayoutManager.getSpanCount();
                return columnCountMatcher.matches(columnCount);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("has column count: ");
                columnCountMatcher.describeTo(description);
            }
        };
    }

    public static Matcher<View> atPosition(final int recyclerViewId, final int position) {
        return atPositionOnView(recyclerViewId, position, -1);
    }

    public static Matcher<View> atPositionOnView(final int recyclerViewId, final int position, final int targetViewId) {

        return new TypeSafeMatcher<View>() {
            Resources resources = null;
            View childView;

            public void describeTo(Description description) {
                String idDescription = Integer.toString(recyclerViewId);
                if (this.resources != null) {
                    try {
                        idDescription = this.resources.getResourceName(recyclerViewId);
                    } catch (Resources.NotFoundException var4) {
                        idDescription = String.format("%s (resource name not found)", new Object[]{Integer.valueOf(recyclerViewId)});
                    }
                }

                description.appendText("with id: " + idDescription);
            }

            public boolean matchesSafely(View view) {

                this.resources = view.getResources();

                if (childView == null) {
                    RecyclerView recyclerView = (RecyclerView) view.getRootView().findViewById(recyclerViewId);
                    if (recyclerView != null && recyclerView.getId() == recyclerViewId) {
                        childView = recyclerView.getChildAt(position);
                    } else {
                        return false;
                    }
                }

                if (targetViewId == -1) {
                    return view == childView;
                } else {
                    View targetView = childView.findViewById(targetViewId);
                    return view == targetView;
                }

            }
        };
    }

}
