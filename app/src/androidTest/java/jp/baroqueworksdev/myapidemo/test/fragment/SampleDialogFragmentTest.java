
package jp.baroqueworksdev.myapidemo.test.fragment;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.DialogInterface;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentManager;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.view.ViewConfiguration;

import jp.baroqueworksdev.myapidemo.R;
import jp.baroqueworksdev.myapidemo.activity.SampleFragmentActivity;
import jp.baroqueworksdev.myapidemo.fragment.SampleDialogFragment;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static jp.baroqueworksdev.myapidemo.util.EspressoViewActionUtil.waitForDisplayId;


@RunWith(AndroidJUnit4.class)
public class SampleDialogFragmentTest extends
        ActivityInstrumentationTestCase2<SampleFragmentActivity> {

    private SampleFragmentActivity mActivity;

    private FragmentManager mFragmentManager;

    public SampleDialogFragmentTest() {
        super(SampleFragmentActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mActivity = getActivity();
        onView(isRoot()).perform(waitForDisplayId(R.id.container, 1000));
        mFragmentManager = mActivity.getSupportFragmentManager();
    }

    @After
    public void tearDown() throws Exception {
        if (!mActivity.isFinishing()) {
            mActivity.finish();
        }
        super.tearDown();

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
        onView(isRoot()).inRoot(isDialog()).perform(waitForDisplayId(android.R.id.button1, 1000));
    }

    /*
     * Create test
     */
    @Test
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
    @Test
    public void testClickPositive() {
        showDialog();
        // click BUTTON_POSITIVE
        onView(withText("positive")).inRoot(isDialog()).perform(click());

        SampleDialogFragment dialog = (SampleDialogFragment) mFragmentManager
                .findFragmentByTag("test");
        assertNull(dialog);
    }

    /*
     * Click negative button test
     */
    @Test
    public void testClickNegative() {
        showDialog();
        // click negative
        onView(withText("negative")).inRoot(isDialog()).perform(click());

        SampleDialogFragment dialog = (SampleDialogFragment) mFragmentManager
                .findFragmentByTag("test");
        assertNull(dialog);
    }

    /*
     * Click neutral button test
     */
    @Test
    public void testClickNeutral() {
        showDialog();
        // click negative
        onView(withText("neutral")).inRoot(isDialog()).perform(click());

        SampleDialogFragment dialog = (SampleDialogFragment) mFragmentManager
                .findFragmentByTag("test");
        assertNull(dialog);
    }

    /*
     * Click back key test
     */
    @Test
    public void testClickBackKey() {
        showDialog();

        pressBack();

        SampleDialogFragment dialog = (SampleDialogFragment) mFragmentManager
                .findFragmentByTag("test");
        assertNull(dialog);
    }

    /*
     * Click Home key test
     */
    @Test
    public void testClickHomeKey() {
        showDialog();

        // HOME key
        onView(isRoot()).inRoot(isDialog()).perform(pressKey(KeyEvent.KEYCODE_HOME));

        SampleDialogFragment dialog = (SampleDialogFragment) mFragmentManager
                .findFragmentByTag("test");
        assertNotNull(dialog);
        dialog.dismiss();
        getInstrumentation().waitForIdleSync();

        ViewConfiguration.getLongPressTimeout();

        // http://developer.android.com/intl/ja/reference/android/view/ViewConfiguration.html
    }

}
