
package jp.baroqueworksdev.myapidemo.activity;

import jp.baroqueworksdev.myapidemo.fragment.SampleDialogFragment;
import jp.baroqueworksdev.myapidemo.fragment.SampleDialogFragment.onClickedLisnter;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class SampleDialogFragmentActivity extends FragmentActivity implements onClickedLisnter {

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        FragmentManager fragmentManager = getSupportFragmentManager();
        SampleDialogFragment dialog = SampleDialogFragment.newInstance("positive",
                "negative",
                "neutral");
        dialog.show(fragmentManager, "test");
    }

    @Override
    public void onClickedPositiveButton() {
    }

    @Override
    public void onClickedNegativeButton() {
    }

    @Override
    public void onClickedNeurtralButton() {
    }
}
