package jp.baroqueworksdev.myapidemo.controller;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.test.InstrumentationTestCase;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class LocalBroadcastControllerTest extends InstrumentationTestCase {
    private class TestBroadcastListener implements LocalBroadcastController.OnLocalBroadcastController {

        private final CountDownLatch countDownLatch;
        private boolean isResponse;
        private Intent receivedIntent;

        public TestBroadcastListener() {
            countDownLatch = new CountDownLatch(1);

        }

        @Override
        public void onReceive(Context context, Intent intent) {
            isResponse = true;
            receivedIntent = intent;
            countDownLatch.countDown();
        }
    }

    public void testReceiver() throws InterruptedException {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("test_action");

        TestBroadcastListener listener = new TestBroadcastListener();

        LocalBroadcastController localBroadcastController = new LocalBroadcastController(
                getInstrumentation().getTargetContext(), intentFilter, listener);

        {
            localBroadcastController.registerReceiver();

            // send Broadcast Intent
            Intent intent = new Intent("test_action");
            LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getInstrumentation().getTargetContext());
            manager.sendBroadcast(intent);

            //wait for receiving
            listener.countDownLatch.await(5, TimeUnit.SECONDS);
            // test
            assertTrue(listener.isResponse);
            assertNotNull(listener.receivedIntent);
            assertEquals("test_action", listener.receivedIntent.getAction());

            localBroadcastController.unregisterReceiver();
        }
    }

}