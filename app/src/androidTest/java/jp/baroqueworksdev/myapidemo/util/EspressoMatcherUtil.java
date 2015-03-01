package jp.baroqueworksdev.myapidemo.util;

import android.content.res.Resources;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class EspressoMatcherUtil {

    /**
     * Returns a matcher that matches a descendant of {@link TextView} that has the text color
     * associated with the given resource id.
     *
     * @param resourceId color res id
     */
    public static Matcher<View> withTextColor(final int resourceId) {

        return new BoundedMatcher<View, TextView>(TextView.class) {
            private int expectedColor = -1;

            private String resourceName;


            @Override
            protected boolean matchesSafely(TextView textView) {
                if (expectedColor == -1) {
                    try {
                        expectedColor = textView.getResources().getColor(resourceId);
                        resourceName = textView.getResources().getResourceEntryName(resourceId);
                    } catch (Resources.NotFoundException ignored) {
                        // view could be from a context unaware of the resource id.
                    }
                }

                if (expectedColor != -1) {
                    return (expectedColor == textView.getCurrentTextColor());
                } else {
                    return false;
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with color from resource id: ");
                description.appendValue(resourceId);
                if (null != resourceName) {
                    description.appendText("[");
                    description.appendText(resourceName);
                    description.appendText("]");
                }
                if (-1 != expectedColor) {
                    description.appendText(" value: ");
                    description.appendText(String.valueOf(expectedColor));
                }
            }
        };
    }

    /**
     * /**
     * Returns a matcher that matches a descendant of {@link TextView} that has the hint text color
     * associated with the given resource id.
     *
     * @param resourceId color res id
     */
    public static Matcher<View> withTextHintColor(final int resourceId) {

        return new BoundedMatcher<View, TextView>(TextView.class) {
            private int expectedColor = -1;

            private String resourceName;


            @Override
            protected boolean matchesSafely(TextView textView) {
                if (expectedColor == -1) {
                    try {
                        expectedColor = textView.getResources().getColor(resourceId);
                        resourceName = textView.getResources().getResourceEntryName(resourceId);
                    } catch (Resources.NotFoundException ignored) {
                        // view could be from a context unaware of the resource id.
                    }
                }
                if (expectedColor != -1) {
                    return (expectedColor == textView.getCurrentHintTextColor());
                } else {
                    return false;
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with hint color from resource id: ");
                description.appendValue(resourceId);
                if (null != resourceName) {
                    description.appendText("[");
                    description.appendText(resourceName);
                    description.appendText("]");
                }
                if (-1 != expectedColor) {
                    description.appendText(" value: ");
                    description.appendText(String.valueOf(expectedColor));
                }
            }
        };
    }
}
