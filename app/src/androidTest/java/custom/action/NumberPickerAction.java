package custom.action;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.view.View;
import android.widget.DatePicker;
import android.widget.NumberPicker;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

/**
 * Created by tobeylin on 15/7/24.
 */
public class NumberPickerAction {

    private NumberPickerAction(){

    }

    public static ViewAction setValue(final int value) {
        ViewAction setValueAction = new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return Matchers.allOf(ViewMatchers.isAssignableFrom(NumberPicker.class), ViewMatchers.isDisplayed());
            }

            @Override
            public String getDescription() {
                return "set value";
            }

            @Override
            public void perform(UiController uiController, View view) {
                NumberPicker numberPicker = (NumberPicker) view;
                numberPicker.setValue(value);
            }
        };
        return setValueAction;
    }
}
