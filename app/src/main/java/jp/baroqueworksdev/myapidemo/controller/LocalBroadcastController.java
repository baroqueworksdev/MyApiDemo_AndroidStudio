package jp.baroqueworksdev.myapidemo.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

public class LocalBroadcastController {

    private LocalBroadcastManager mLocalBroadcastManager;

    private IntentFilter mIntentFilter;

    private OnLocalBroadcastController mOnLocalBroadcastController;

    public interface OnLocalBroadcastController {

        public void onReceive(Context context, Intent intent);
    }

    /**
     * Constructor
     *
     * @param context  Context
     * @param filter   Intent Filter which need to receive an action
     * @param listener onReceive
     */
    public LocalBroadcastController(Context context, IntentFilter filter,
            OnLocalBroadcastController listener) {
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(context);
        mIntentFilter = filter;
        mOnLocalBroadcastController = listener;
    }

    /**
     * Register Receiver
     */
    public void registerReceiver() {
        mLocalBroadcastManager.registerReceiver(mReceiver, mIntentFilter);
    }

    /**
     * Unregister Receiver
     */
    public void unregisterReceiver() {
        mLocalBroadcastManager.unregisterReceiver(mReceiver);
    }


    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mOnLocalBroadcastController.onReceive(context, intent);

        }
    };

}
