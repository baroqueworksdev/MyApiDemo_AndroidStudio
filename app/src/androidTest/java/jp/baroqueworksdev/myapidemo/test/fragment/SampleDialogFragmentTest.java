
package jp.baroqueworksdev.myapidemo.test.fragment;

import jp.baroqueworksdev.myapidemo.activity.SampleFragmentActivity;
import jp.baroqueworksdev.myapidemo.fragment.SampleDialogFragment;

import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.view.ViewConfiguration;

public class SampleDialogFragmentTest extends
        ActivityInstrumentationTestCase2<SampleFragmentActivity> {

    private SampleFragmentActivity mActivity;

    private FragmentManager mFragmentManager;

    public SampleDialogFragmentTest(Class<SampleFragmentActivity> activityClass) {
        super(activityClass);
    }

    public SampleDialogFragmentTest() {
        super(SampleFragmentActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = getActivity();

        getInstrumentation().waitForIdleSync();
        mFragmentManager = mActivity.getSupportFragmentManager();
    }

    private void showDialog() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SampleDialogFragment dialog = SampleDialogFragment.newInstance("positive",
                        "negative",
                        "neutral");
                dialog.show(mFragmentManager, "test");
            }
        });
        getInstrumentation().waitForIdleSync();
    }

    /*
     * Create test
     */
    public void testCreateDialog() {
        showDialog();
        SampleDialogFragment dialog = (SampleDialogFragment) mFragmentManager
                .findFragmentByTag("test");

        assertNotNull(dialog);

        dialog.dismiss();
        getInstrumentation().waitForIdleSync();
    }

    /*
     * Click positive button test
     */
    public void testClickPositive() {
        showDialog();
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SampleDialogFragment dialog = (SampleDialogFragment) mFragmentManager
                        .findFragmentByTag("test");
                dialog.onClick(dialog.getDialog(), DialogInterface.BUTTON_POSITIVE);
            }
        });
        getInstrumentation().waitForIdleSync();

        SampleDialogFragment dialog = (SampleDialogFragment) mFragmentManager
                .findFragmentByTag("test");
        assertNull(dialog);
    }

    /*
     * Click negative button test
     */
    public void testClickNegative() {
        showDialog();
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SampleDialogFragment dialog = (SampleDialogFragment) mFragmentManager
                        .findFragmentByTag("test");
                dialog.onClick(dialog.getDialog(), DialogInterface.BUTTON_NEGATIVE);
            }
        });
        getInstrumentation().waitForIdleSync();

        SampleDialogFragment dialog = (SampleDialogFragment) mFragmentManager
                .findFragmentByTag("test");
        assertNull(dialog);
    }

    /*
     * Click neutral button test
     */
    public void testClickNeutral() {
        showDialog();
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                SampleDialogFragment dialog = (SampleDialogFragment) mFragmentManager
                        .findFragmentByTag("test");
                dialog.onClick(dialog.getDialog(), DialogInterface.BUTTON_NEUTRAL);
            }
        });
        getInstrumentation().waitForIdleSync();

        SampleDialogFragment dialog = (SampleDialogFragment) mFragmentManager
                .findFragmentByTag("test");
        assertNull(dialog);
    }

    /*
     * Click back key test
     */
    public void testClickBackKey() {
        showDialog();

        getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);

        SampleDialogFragment dialog = (SampleDialogFragment) mFragmentManager
                .findFragmentByTag("test");
        assertNull(dialog);
    }

    /*
     * Click Home key test
     */
    public void testClickHomeKey() {
        showDialog();

        getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_HOME);

        SampleDialogFragment dialog = (SampleDialogFragment) mFragmentManager
                .findFragmentByTag("test");
        assertNotNull(dialog);
        dialog.dismiss();
        getInstrumentation().waitForIdleSync();

        ViewConfiguration.getLongPressTimeout();

        // http://developer.android.com/intl/ja/reference/android/view/ViewConfiguration.html
    }

}
