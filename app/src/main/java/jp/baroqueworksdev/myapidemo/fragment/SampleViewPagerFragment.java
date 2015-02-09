
package jp.baroqueworksdev.myapidemo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.baroqueworksdev.myapidemo.R;
import jp.baroqueworksdev.myapidemo.data.SampleData;

import java.util.ArrayList;

public class SampleViewPagerFragment extends Fragment {

    private static String LIST_DATA = "listdata";

    private static String POSITION = "position";

    private ViewPager mViewPager;

    private int mDefaultPosition;

    ArrayList<SampleData> mSamplelist;

    public static SampleViewPagerFragment newInstance(ArrayList<SampleData> list, int position) {
        SampleViewPagerFragment fragment = new SampleViewPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(LIST_DATA, list);
        bundle.putInt(POSITION, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mSamplelist = bundle.getParcelableArrayList(LIST_DATA);
        mDefaultPosition = bundle.getInt(POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sample_viewpager, null);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);

        FragmentManager fm = getChildFragmentManager();
        mViewPager.setAdapter(new SampleDetailPagerAdapter(fm));
        mViewPager.setOnPageChangeListener(mOnPageChangeListener);
        mViewPager.setCurrentItem(mDefaultPosition);
        getActivity().setTitle(mSamplelist.get(mDefaultPosition).getName());
        return view;
    }

    @Override
    public void onDestroyView() {
        mViewPager.setAdapter(null);
        mViewPager.setOnPageChangeListener(null);
        mOnPageChangeListener = null;
        mViewPager = null;
        mSamplelist = null;
        super.onDestroyView();
    }

    private OnPageChangeListener mOnPageChangeListener = new OnPageChangeListener() {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int postion) {
        }

    };

    public class SampleDetailPagerAdapter extends FragmentPagerAdapter {

        public SampleDetailPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            SampleDetailFragment fragment = SampleDetailFragment.newInstance(mSamplelist
                    .get(position));
            return fragment;
        }

        @Override
        public int getCount() {
            return mSamplelist.size();
        }

    }

}
