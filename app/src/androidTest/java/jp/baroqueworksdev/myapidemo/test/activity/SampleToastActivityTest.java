package jp.baroqueworksdev.myapidemo.test.activity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import jp.baroqueworksdev.myapidemo.R;
import jp.baroqueworksdev.myapidemo.activity.SampleToastActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static jp.baroqueworksdev.myapidemo.util.EspressoRootMatcherUtil.isToast;
import static jp.baroqueworksdev.myapidemo.util.EspressoViewActionUtil.waitForDisplayId;
import static jp.baroqueworksdev.myapidemo.util.EspressoViewActionUtil.waitForDisplayText;

@RunWith(AndroidJUnit4.class)
public class SampleToastActivityTest extends
        ActivityInstrumentationTestCase2<SampleToastActivity> {

    public SampleToastActivityTest() {
        super(SampleToastActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testShowToast() {
        Activity activity = getActivity();
        // wait for display
        onView(isRoot()).perform(waitForDisplayId(R.id.button, 1000));
        // click button
        onView(withId(R.id.button)).perform(click());
        // wait for toast
        onView(isRoot()).inRoot(isToast())
                .perform(waitForDisplayText(activity.getString(R.string.show_toast), 1000));

        activity.finish();
    }

}