
package jp.baroqueworksdev.myapidemo.test.activity;

import jp.baroqueworksdev.myapidemo.activity.SampleFragmentActivity;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;

public class SampleFragmentActivityTest extends
        ActivityInstrumentationTestCase2<SampleFragmentActivity> {

    public SampleFragmentActivityTest() {
        super(SampleFragmentActivity.class);
    }

    public SampleFragmentActivityTest(Class<SampleFragmentActivity> activityClass) {
        super(activityClass);
    }

    public void testBackKey() {
        Activity activity = getActivity();

        sendKeys(KeyEvent.KEYCODE_BACK);
        getInstrumentation().waitForIdleSync();

        assertTrue(activity.isFinishing());
    }

    public void testHomeKey() {
        Activity activity = getActivity();

        sendKeys(KeyEvent.KEYCODE_HOME);
        getInstrumentation().waitForIdleSync();

        assertFalse(activity.isFinishing());

        activity.finish();
        getInstrumentation().waitForIdleSync();

    }

}
