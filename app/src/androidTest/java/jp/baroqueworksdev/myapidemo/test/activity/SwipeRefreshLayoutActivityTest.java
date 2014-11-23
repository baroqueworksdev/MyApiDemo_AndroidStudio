
package jp.baroqueworksdev.myapidemo.test.activity;

import jp.baroqueworksdev.myapidemo.R;
import jp.baroqueworksdev.myapidemo.activity.SwipeRefreshLayoutActivity;

import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import android.support.v4.widget.SwipeRefreshLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.KeyEvent;

public class SwipeRefreshLayoutActivityTest extends
        ActivityInstrumentationTestCase2<SwipeRefreshLayoutActivity> {

    Activity mActivity;

    public SwipeRefreshLayoutActivityTest() {
        super(SwipeRefreshLayoutActivity.class);
    }

    public SwipeRefreshLayoutActivityTest(Class<SwipeRefreshLayoutActivity> activityClass) {
        super(activityClass);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = getActivity();
    }

    @Override
    protected void tearDown() throws Exception {
        if (!mActivity.isFinishing()) {
            mActivity.finish();
            getInstrumentation().waitForIdleSync();
        }

        super.tearDown();
    }

    public void testBackKey() {
        sendKeys(KeyEvent.KEYCODE_BACK);
        getInstrumentation().waitForIdleSync();

        assertTrue(mActivity.isFinishing());
    }

    public void testHomeKey() {

        sendKeys(KeyEvent.KEYCODE_HOME);
        getInstrumentation().waitForIdleSync();

        assertFalse(mActivity.isFinishing());
    }

    public void testStartEndRepeat() {
        sendKeys(KeyEvent.KEYCODE_BACK);
        getInstrumentation().waitForIdleSync();

        assertTrue(mActivity.isFinishing());

        for (int i = 0; i < 50; i++) {
            mActivity = getActivity();
            getInstrumentation().waitForIdleSync();
            sendKeys(KeyEvent.KEYCODE_BACK);
            getInstrumentation().waitForIdleSync();

            assertTrue(mActivity.isFinishing());
        }
    }

    public void testRefresh() {
        SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout) mActivity
                .findViewById(R.id.swipe_refresh_widget);

        // init test
        assertFalse(refreshLayout.isRefreshing());

        // swipe down
        ActivityMonitor monitor = new ActivityMonitor(
                "jp.baroqueworksdev.myapidemo.activity.SwipeRefreshLayoutActivity", null, false);
        getInstrumentation().addMonitor(monitor);
        TouchUtils.dragQuarterScreenDown(this, mActivity);
        assertTrue(refreshLayout.isRefreshing());
        // wait 2sec
        getInstrumentation().waitForMonitorWithTimeout(monitor, 2000);

        // refreshed check
        assertFalse(refreshLayout.isRefreshing());
    }

    public void testClickAndDrag() {

    }

}
