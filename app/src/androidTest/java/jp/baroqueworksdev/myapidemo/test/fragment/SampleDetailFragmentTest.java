package jp.baroqueworksdev.myapidemo.test.fragment;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentActivity;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import jp.baroqueworksdev.myapidemo.R;
import jp.baroqueworksdev.myapidemo.activity.SampleFragmentActivity;
import jp.baroqueworksdev.myapidemo.data.SampleData;
import jp.baroqueworksdev.myapidemo.fragment.SampleDetailFragment;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static jp.baroqueworksdev.myapidemo.util.EspressoMatcherUtil.withTextColor;
import static jp.baroqueworksdev.myapidemo.util.EspressoViewActionUtil.waitForDisplayId;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class SampleDetailFragmentTest
        extends ActivityInstrumentationTestCase2<SampleFragmentActivity> {

    public SampleDetailFragmentTest() {
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
    public void testConstruct() {
        SampleData data0 = new SampleData();
        data0.setName("SampleData 00");

        SampleDetailFragment fragment = SampleDetailFragment.newInstance(data0);
        assertNotNull(fragment);

        FragmentActivity activity = getActivity();
        assertNotNull(activity);
        activity.getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment, null)
                .commit();

        // wait for display and check text color
        onView(isRoot()).perform(waitForDisplayId(R.id.name, 1000));

        onView(withId(R.id.name)).check(matches(withTextColor(android.R.color.black)));


    }

}
