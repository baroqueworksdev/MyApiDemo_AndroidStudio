
package jp.baroqueworksdev.myapidemo.activity;

import jp.baroqueworksdev.myapidemo.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;

public class SwipeRefreshLayoutActivity extends Activity implements
        OnRefreshListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_swipe_refresh_widget);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void onDestroy() {
        mHander.removeMessages(0);
        mSwipeRefreshLayout.setOnRefreshListener(null);
        mSwipeRefreshLayout = null;
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    private void refresh() {
        Message msg = mHander.obtainMessage(0, this);
        mHander.sendMessageDelayed(msg, 1000);
    }

    private static Handler mHander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            SwipeRefreshLayoutActivity activity = (SwipeRefreshLayoutActivity) msg.obj;
            activity.mSwipeRefreshLayout.setRefreshing(false);
        }
    };

}
