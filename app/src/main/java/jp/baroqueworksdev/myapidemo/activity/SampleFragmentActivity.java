
package jp.baroqueworksdev.myapidemo.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import jp.baroqueworksdev.myapidemo.R;

public class SampleFragmentActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_fragment);
    }
}
