package jp.baroqueworksdev.myapidemo.test.fragment;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.FragmentActivity;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import jp.baroqueworksdev.myapidemo.R;
import jp.baroqueworksdev.myapidemo.activity.SampleFragmentActivity;
import jp.baroqueworksdev.myapidemo.data.SampleData;
import jp.baroqueworksdev.myapidemo.fragment.SampleViewPagerFragment;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;

@RunWith(AndroidJUnit4.class)
public class SampleViewPagerFragmentTest extends ActivityInstrumentationTestCase2<SampleFragmentActivity> {

    public SampleViewPagerFragmentTest() {
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
    public void testPager() {
        ArrayList<SampleData> list = new ArrayList<SampleData>();
        SampleData data0 = new SampleData();
        data0.setName("SampleData 00");
        list.add(data0);

        SampleData data1 = new SampleData();
        data0.setName("SampleData 01");
        list.add(data1);

        SampleViewPagerFragment fragment = SampleViewPagerFragment.newInstance(list, 0);

        assertNotNull(fragment);

        FragmentActivity activity = getActivity();
        assertNotNull(activity);
        activity.getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment, null)
                .commit();
        {
            onView(ViewMatchers.withId(R.id.viewpager)).perform(swipeLeft());
            onView(ViewMatchers.withId(R.id.viewpager)).perform(swipeLeft());
            onView(ViewMatchers.withId(R.id.viewpager)).perform(swipeRight());
        }
        assertNotNull(fragment);
    }
}