
package jp.baroqueworksdev.myapidemo.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import jp.baroqueworksdev.myapidemo.R;
import jp.baroqueworksdev.myapidemo.data.SampleData;

import java.util.ArrayList;

public class SampleListFragment extends ListFragment implements OnItemClickListener {

    private static String LIST_DATA = "listdata";

    private onSampleListItemClickListener mOnSampleListItemClickListener;

    public static SampleListFragment newInstance(ArrayList<SampleData> list) {
        SampleListFragment fragment = new SampleListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(LIST_DATA, list);
        fragment.setArguments(bundle);
        return fragment;
    }

    public interface onSampleListItemClickListener {

        public void OnItemClicked(int position);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof onSampleListItemClickListener) {
            mOnSampleListItemClickListener = (onSampleListItemClickListener) activity;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        ArrayList<SampleData> list = bundle.getParcelableArrayList(LIST_DATA);
        SampleAdapter adapter = new SampleAdapter(getActivity(),
                R.layout.sample_list_row, list);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);

    }

    @Override
    public void onDestroy() {
        mOnSampleListItemClickListener = null;
        super.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
        if (mOnSampleListItemClickListener != null) {
            mOnSampleListItemClickListener.OnItemClicked(position);
        }
    }

    private class SampleAdapter extends ArrayAdapter<SampleData> {

        private ArrayList<SampleData> mList;

        private LayoutInflater mInflater;

        private int mLayoutId;

        private class ViewHolder {

            TextView mName;
        }

        public SampleAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
            mInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mList = new ArrayList<SampleData>();
            mLayoutId = textViewResourceId;
        }

        public SampleAdapter(Context context, int textViewResourceId,
                ArrayList<SampleData> list) {
            super(context, textViewResourceId);
            mInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mList = list;
            mLayoutId = textViewResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            ViewHolder holder = null;

            if (view == null) {
                view = mInflater.inflate(mLayoutId, null);
                holder = new ViewHolder();
                holder.mName = (TextView) view.findViewById(R.id.name);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.mName.setText(mList.get(position).getName());

            return view;
        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }

}
