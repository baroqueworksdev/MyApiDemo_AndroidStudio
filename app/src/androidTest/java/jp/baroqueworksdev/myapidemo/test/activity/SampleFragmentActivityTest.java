
package jp.baroqueworksdev.myapidemo.test.activity;

import jp.baroqueworksdev.myapidemo.R;
import jp.baroqueworksdev.myapidemo.activity.SampleFragmentActivity;
import jp.baroqueworksdev.myapidemo.activity.SwipeRefreshLayoutActivity;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.NoActivityResumedException;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static jp.baroqueworksdev.myapidemo.util.EspressoViewActionUtil.waitForDisplayId;

@RunWith(AndroidJUnit4.class)
public class SampleFragmentActivityTest extends
        ActivityInstrumentationTestCase2<SampleFragmentActivity> {

    public SampleFragmentActivityTest() {
        super(SampleFragmentActivity.class);
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
    public void testBackKey() {
        Activity activity = getActivity();
        try {
            onView(isRoot()).perform(waitForDisplayId(R.id.container, 1000));
            Espresso.pressBack();
        } catch (NoActivityResumedException exception) {
            // // test success
        }

        assertTrue(activity.isFinishing());
    }

    @Test
    public void testHomeKey() {
        Activity activity = getActivity();
        onView(isRoot()).perform(waitForDisplayId(R.id.container, 1000));

        sendKeys(KeyEvent.KEYCODE_HOME);
        getInstrumentation().waitForIdleSync();

        assertFalse(activity.isFinishing());

        activity.finish();
        getInstrumentation().waitForIdleSync();

    }

}
