package jp.baroqueworksdev.myapidemo.activity;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import jp.baroqueworksdev.myapidemo.R;
import jp.baroqueworksdev.myapidemo.network.SampleVolleyManager;

public class SampleVolleyActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_volley);

        final String url = "http://developer.android.com/images/training/system-ui.png";

        ImageView view = (ImageView) findViewById(R.id.imageView);
        SampleVolleyManager.getInstance(this)
                .get(url, new IamgeLoadingListener(), view, R.drawable.ic_launcher,
                        R.drawable.ic_drawer);
    }

    private class IamgeLoadingListener implements SampleVolleyManager.ResponseListener {

        @Override
        public void onResponse(Object response) {

        }

        @Override
        public void onErrorResponse(VolleyError volleyError) {

        }
    }


}
