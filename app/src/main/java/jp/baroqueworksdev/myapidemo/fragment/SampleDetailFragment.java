
package jp.baroqueworksdev.myapidemo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import jp.baroqueworksdev.myapidemo.R;
import jp.baroqueworksdev.myapidemo.data.SampleData;

public class SampleDetailFragment extends Fragment {

    private SampleData mSampleData;

    public static SampleDetailFragment newInstance(SampleData data) {
        SampleDetailFragment fragment = new SampleDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", data);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        SampleData data = bundle.getParcelable("data");
        mSampleData = data;

        View view = inflater.inflate(R.layout.sample_detail, null);
        TextView text = (TextView) view.findViewById(R.id.name);
        text.setText(mSampleData.getName());
        return view;
    }

}
