package jp.baroqueworksdev.myapidemo.fragment;

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

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class SampleDetailFragmentTest extends ActivityInstrumentationTestCase2<SampleFragmentActivity> {

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
    public void construct() {
        SampleData data0 = new SampleData();
        data0.setName("SampleData 00");

        SampleDetailFragment fragment = SampleDetailFragment.newInstance(data0);

        FragmentActivity activity = getActivity();
        activity.getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment, null)
                .commit();

    }

}
