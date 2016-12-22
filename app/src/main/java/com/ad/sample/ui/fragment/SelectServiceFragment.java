package com.ad.sample.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ad.sample.R;
import com.ad.sample.ui.activity.ServiceDetailUserActivity;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.joanzapata.iconify.widget.IconTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectServiceFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.btn_wash_and_go)
    LinearLayout btnWashAndGo;
    @BindView(R.id.btn_luxury_wash)
    LinearLayout btnLuxuryWash;
    @BindView(R.id.indicator_wash_and_go)
    IconTextView indicatorWashAndGo;
    @BindView(R.id.indicator_luxury_wash)
    IconTextView indicatorLuxuryWash;

    @OnClick({R.id.btn_wash_and_go, R.id.btn_luxury_wash})
    void Select(View v) {
        int id = v.getId();
        if (id == R.id.btn_wash_and_go) {
            ServiceSelected(0);
        } else if (id == R.id.btn_luxury_wash) {
            ServiceSelected(1);
        }
    }

    private String mParam1;
    private String mParam2;

    private OnServiceSelectedListener mListener;

    public SelectServiceFragment() {
        // Required empty public constructor
    }

    public static SelectServiceFragment newInstance(String param1, String param2) {
        SelectServiceFragment fragment = new SelectServiceFragment();
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
        View view = inflater.inflate(R.layout.fragment_select_service, container, false);
        ButterKnife.bind(this, view);
        ServiceSelected(0);
        return view;
    }

    private void ServiceSelected(int poistion) {
        if (poistion == 0) {
            indicatorWashAndGo.setVisibility(View.VISIBLE);
            indicatorLuxuryWash.setVisibility(View.GONE);

            //new activity
            Intent intent = new Intent(getActivity(), ServiceDetailUserActivity.class);
            intent.putExtra("tes", "isi");
            startActivity(intent);
        } else if (poistion == 1) {
            indicatorWashAndGo.setVisibility(View.GONE);
            indicatorLuxuryWash.setVisibility(View.VISIBLE);

            //new activity
            Intent intent = new Intent(getActivity(), ServiceDetailUserActivity.class);
            intent.putExtra("tes", "isi");
            startActivity(intent);
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onServiceSelected(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnServiceSelectedListener) {
            mListener = (OnServiceSelectedListener) context;
        } else {/*
            throw new RuntimeException(context.toString()
                    + " must implement OnServiceSelectedListener");*/
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnServiceSelectedListener {
        void onServiceSelected(Uri uri);
    }
}
