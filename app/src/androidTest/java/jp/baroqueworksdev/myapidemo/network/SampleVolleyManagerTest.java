package jp.baroqueworksdev.myapidemo.network;


import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;

import android.graphics.Bitmap;
import android.test.InstrumentationTestCase;
import android.widget.ImageView;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import jp.baroqueworksdev.myapidemo.R;

public class SampleVolleyManagerTest extends InstrumentationTestCase {

    private class TestResponseListener implements SampleVolleyManager.ResponseListener {

        public CountDownLatch countDownLatch;

        public boolean isResponse;

        public Object data;

        public VolleyError errorCode;

        public TestResponseListener() {
            countDownLatch = new CountDownLatch(1);
        }

        @Override
        public void onResponse(Object response) {
            isResponse = true;
            data = response;
            countDownLatch.countDown();
        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {
            errorCode = volleyError;
            countDownLatch.countDown();

        }
    }

    public void setUp() throws Exception {
        super.setUp();

    }

    public void tearDown() throws Exception {

    }

    public void testGetInstance() throws Exception {
        SampleVolleyManager instance = SampleVolleyManager.getInstance(
                getInstrumentation().getTargetContext());
        assertNotNull(instance);
    }

    public void testGetForStringRequestForErrorCase() throws Exception {
        SampleVolleyManager instance = SampleVolleyManager.getInstance(
                getInstrumentation().getTargetContext());
        String url = "http://xxxx.yyyy";
        TestResponseListener listener = new TestResponseListener();
        instance.get(url, listener);

        listener.countDownLatch.await(5, TimeUnit.SECONDS);
        {
            assertFalse(listener.isResponse);
            assertNull(listener.data);
            assertNotNull(listener.errorCode);

        }
    }

    public void testGetForStringRequest() throws Exception {
        SampleVolleyManager instance = SampleVolleyManager.getInstance(
                getInstrumentation().getTargetContext());
        String url = "http://feeds.feedburner.com/blogspot/hsDu";
        TestResponseListener listener = new TestResponseListener();
        instance.get(url, listener);

        listener.countDownLatch.await(5, TimeUnit.SECONDS);
        {
            assertTrue(listener.isResponse);
            assertNotNull(listener.data);

        }

    }

    public void testGetForStringRequestMultiRequest() throws Exception {
        SampleVolleyManager instance = SampleVolleyManager.getInstance(
                getInstrumentation().getTargetContext());
        String url = "http://feeds.feedburner.com/blogspot/hsDu";

        //request 1
        TestResponseListener listener = new TestResponseListener();

        //request 2
        TestResponseListener listener2 = new TestResponseListener();

        instance.get(url, listener);
        instance.get(url, listener2);

        listener.countDownLatch.await(5, TimeUnit.SECONDS);
        listener2.countDownLatch.await(5, TimeUnit.SECONDS);
        {
            //request 1
            assertTrue(listener.isResponse);
            assertNotNull(listener.data);

            //request 2
            assertTrue(listener2.isResponse);
            assertNotNull(listener2.data);
        }

    }

    public void testGetImageLoader() throws Exception {
        SampleVolleyManager instance = SampleVolleyManager.getInstance(
                getInstrumentation().getTargetContext());
        ImageLoader imageLoader = instance.getImageLoader();
        {
            assertNotNull(imageLoader);
        }

    }

    public void testGetImage() throws Exception {
        final TestResponseListener listener = new TestResponseListener();
        //request 1

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                final String url = "http://developer.android.com/images/training/system-ui.png";
                SampleVolleyManager instance = SampleVolleyManager.getInstance(
                        getInstrumentation().getTargetContext());

                ImageView view = new ImageView(getInstrumentation().getTargetContext());
                ImageListener imageListener = instance
                        .getImageListener(listener, view, R.drawable.ic_launcher,
                                R.drawable.ic_drawer);
                instance.get(url, imageListener);
            }
        });
        listener.countDownLatch.await(5, TimeUnit.SECONDS);

        {

            assertTrue(listener.isResponse);
            assertNotNull(listener.data);
        }

    }

    public void testGetImageResizeOfWidth() throws Exception {
        final TestResponseListener listener = new TestResponseListener();
        //request 1

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                final String url = "http://developer.android.com/images/training/system-ui.png";
                SampleVolleyManager instance = SampleVolleyManager.getInstance(
                        getInstrumentation().getTargetContext());

                ImageView view = new ImageView(getInstrumentation().getTargetContext());
                ImageListener imageListener = instance
                        .getImageListener(listener, view, R.drawable.ic_launcher,
                                R.drawable.ic_drawer);
                instance.get(url, imageListener, 100, 5000);
            }
        });
        listener.countDownLatch.await(5, TimeUnit.SECONDS);

        {
            assertTrue(listener.isResponse);
            assertNotNull(listener.data);
            Bitmap bitmap = (Bitmap) listener.data;
            assertEquals(100, bitmap.getWidth());
        }

    }

    public void testGetImageResizeOfHeight() throws Exception {
        final TestResponseListener listener = new TestResponseListener();
        //request 1

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                final String url = "http://developer.android.com/images/training/system-ui.png";
                SampleVolleyManager instance = SampleVolleyManager.getInstance(
                        getInstrumentation().getTargetContext());

                ImageView view = new ImageView(getInstrumentation().getTargetContext());
                ImageListener imageListener = instance
                        .getImageListener(listener, view, R.drawable.ic_launcher,
                                R.drawable.ic_drawer);
                instance.get(url, imageListener, 5000, 100);
            }
        });
        listener.countDownLatch.await(5, TimeUnit.SECONDS);

        {
            assertTrue(listener.isResponse);
            assertNotNull(listener.data);
            Bitmap bitmap = (Bitmap) listener.data;
            assertEquals(100, bitmap.getHeight());
        }

    }

}