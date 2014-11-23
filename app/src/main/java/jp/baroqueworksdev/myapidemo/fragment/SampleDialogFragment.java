
package jp.baroqueworksdev.myapidemo.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class SampleDialogFragment extends DialogFragment implements OnClickListener {

    private static final String POSITIVE_STRING = "positive";

    private static final String NEGATIVE_STRING = "negative";

    private static final String NEUTRAL_STRING = "neutral";

    private onClickedLisnter mOnClickedLisnter;

    public static SampleDialogFragment newInstance(String positive, String negative,
            String neutral) {
        SampleDialogFragment fragment = new SampleDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(POSITIVE_STRING, positive);
        bundle.putString(NEGATIVE_STRING, negative);
        bundle.putString(NEUTRAL_STRING, neutral);
        fragment.setArguments(bundle);
        return fragment;
    }

    public interface onClickedLisnter {

        public void onClickedPositiveButton();

        public void onClickedNegativeButton();

        public void onClickedNeurtralButton();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof onClickedLisnter) {
            mOnClickedLisnter = (onClickedLisnter) activity;
        }
    }

    @Override
    public void onDestroy() {
        mOnClickedLisnter = null;
        super.onDestroy();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        String positive = bundle.getString(POSITIVE_STRING);
        String negative = bundle.getString(NEGATIVE_STRING);
        String neutral = bundle.getString(NEUTRAL_STRING);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("SampleDialogFragment");

        if (positive != null) {
            builder.setPositiveButton(positive, this);
        }
        if (negative != null) {
            builder.setNegativeButton(negative, this);
        }
        if (neutral != null) {
            builder.setNeutralButton(neutral, this);
        }
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                if (mOnClickedLisnter != null) {
                    mOnClickedLisnter.onClickedPositiveButton();
                }
                dialog.dismiss();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                if (mOnClickedLisnter != null) {
                    mOnClickedLisnter.onClickedNegativeButton();
                }
                dialog.dismiss();
                break;
            case DialogInterface.BUTTON_NEUTRAL:
                if (mOnClickedLisnter != null) {
                    mOnClickedLisnter.onClickedNeurtralButton();
                }
                dialog.dismiss();
                break;
        }

    }
}
