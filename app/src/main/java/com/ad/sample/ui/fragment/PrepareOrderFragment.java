package com.ad.sample.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.ad.sample.R;
import com.ad.sample.ui.widget.CustomViewPager;
import com.ad.sample.ui.widget.RobotoRegularButton;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PrepareOrderFragment extends Fragment implements SelectVehicleFragment.OnVehicleSelectedListener, SelectServiceFragment.OnServiceSelectedListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.pager)
    CustomViewPager pager;

    @BindView(R.id.btn_cancel)
    RobotoRegularButton btnCancel;

    @BindView(R.id.indicator_1)
    IconTextView indicator1;
    @BindView(R.id.indicator_2)
    IconTextView indicator2;
    @BindView(R.id.indicator_3)
    IconTextView indicator3;

    @BindView(R.id.btn_next)
    RobotoRegularButton btnNext;

    @OnClick(R.id.btn_cancel)
    void ActionCancel() {
        int position = pager.getCurrentItem();
        if (position == 0) {
            mListener.onCancelOrder();
            Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
        } else if (position == 1) {
            pager.setCurrentItem(0);
        }
    }

    @OnClick(R.id.btn_next)
    void ActionNext() {
        int position = pager.getCurrentItem();
        if (position == 0) {
            pager.setCurrentItem(1);
        } else if (position == 1) {
            mListener.onOrdered();
        }

    }

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnOrderedListener mListener;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    @SuppressLint("UseSparseArrays")
    private Map<Integer, Fragment> mPageReferenceMap = new HashMap<>();

    public PrepareOrderFragment() {
        // Required empty public constructor
    }


    public static PrepareOrderFragment newInstance(String param1, String param2) {
        PrepareOrderFragment fragment = new PrepareOrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Iconify
                .with(new FontAwesomeModule())
                .with(new EntypoModule())
                .with(new MaterialModule())
                .with(new MaterialCommunityModule())
                .with(new SimpleLineIconsModule());
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_prepare_order, container, false);
        ButterKnife.bind(this, view);
        pager.setPagingEnabled(false);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        pager.setAdapter(mSectionsPagerAdapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                ChangeIndicator(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        ChangeIndicator(0);
        return view;
    }

    private void ChangeIndicator(int position) {
        if (position == 0) {
            btnCancel.setText(getActivity().getResources().getString(R.string.button_cancel));
            indicator1.setTextColor(ContextCompat.getColor(getActivity(), R.color.biru));
            indicator2.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue_ccccff));
            indicator3.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue_ccccff));
        } else if (position == 1) {
            btnCancel.setText(getActivity().getResources().getString(R.string.button_back));
            indicator1.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue_ccccff));
            indicator2.setTextColor(ContextCompat.getColor(getActivity(), R.color.biru));
            indicator3.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue_ccccff));
        } else if (position == 1) {
            indicator1.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue_ccccff));
            indicator2.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue_ccccff));
            indicator3.setTextColor(ContextCompat.getColor(getActivity(), R.color.biru));
        }
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    SelectVehicleFragment Step1 = new SelectVehicleFragment();
                    mPageReferenceMap.put(position, Step1);
                    return Step1;
                case 1:
                    SelectServiceFragment Step2 = new SelectServiceFragment();
                    mPageReferenceMap.put(position, Step2);
                    return Step2;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Select Vehicle";
                case 1:
                    return "select Service";
            }
            return null;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            super.destroyItem(container, position, object);
            mPageReferenceMap.remove(position);
        }

        Fragment getFragment(int key) {
            return mPageReferenceMap.get(key);
        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onOrdered();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnOrderedListener) {
            mListener = (OnOrderedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnOrderedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnOrderedListener {
        // TODO: Update argument type and name
        void onOrdered();

        void onCancelOrder();
    }


    @Override
    public void onServiceSelected(Uri uri) {

    }

    @Override
    public void onVehicleSelected(Uri uri) {

    }
}
