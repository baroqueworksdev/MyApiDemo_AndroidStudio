
package jp.baroqueworksdev.myapidemo.test.activity;

import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoActivityResumedException;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.widget.SwipeRefreshLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import jp.baroqueworksdev.myapidemo.R;
import jp.baroqueworksdev.myapidemo.activity.SwipeRefreshLayoutActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.pressBack;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static jp.baroqueworksdev.myapidemo.util.EspressoViewActionUtil.waitForDisplayId;

@RunWith(AndroidJUnit4.class)
public class SwipeRefreshLayoutActivityTest extends
        ActivityInstrumentationTestCase2<SwipeRefreshLayoutActivity> {

    Activity mActivity;

    public SwipeRefreshLayoutActivityTest() {
        super(SwipeRefreshLayoutActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mActivity = getActivity();
        onView(isRoot()).perform(waitForDisplayId(R.id.swipe_refresh_widget, 1000));
    }

    @After
    public void tearDown() throws Exception {
        if (!mActivity.isFinishing()) {
            mActivity.finish();
            getInstrumentation().waitForIdleSync();
        }

        super.tearDown();
    }

    @Test
    public void testBackKey() {
        try {
            onView(isRoot()).perform(pressBack());
        } catch (NoActivityResumedException exception) {
            // // test success
        }

        assertTrue(mActivity.isFinishing());
    }

    @Test
    public void testHomeKey() {

        // onView(ViewMatchers.isRoot()).perform(pressKey(KeyEvent.KEYCODE_HOME));
        sendKeys(KeyEvent.KEYCODE_HOME);
        getInstrumentation().waitForIdleSync();

        assertFalse(mActivity.isFinishing());
    }

    @Test
    public void testRefresh() {
        SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) mActivity
                .findViewById(R.id.swipe_refresh_widget);

        // init test
        assertFalse(refreshLayout.isRefreshing());

        // swipe down
        ActivityMonitor monitor = new ActivityMonitor(
                "jp.baroqueworksdev.myapidemo.activity.SwipeRefreshLayoutActivity", null, false);
        getInstrumentation().addMonitor(monitor);

        onView(ViewMatchers.withId(R.id.swipe_refresh_widget)).perform(swipeDown());
        assertTrue(refreshLayout.isRefreshing());
        // wait 2sec
        getInstrumentation().waitForMonitorWithTimeout(monitor, 2000);

        // refreshed check
        assertFalse(refreshLayout.isRefreshing());
    }

    @Test
    public void testClickAndDrag() {

    }

}
