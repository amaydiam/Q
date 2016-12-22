package com.ad.sample.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ad.sample.R;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoIcons;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialCommunityIcons;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsIcons;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MenuHomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.img_menu_home)
    ImageView imgMenuHome;
    @BindView(R.id.img_menu_notification)
    ImageView imgMenuNotification;
    @BindView(R.id.img_menu_history)
    ImageView imgMenuHistory;
    @BindView(R.id.img_menu_help)
    ImageView imgMenuHelp;
    @BindView(R.id.img_menu_my_account)
    ImageView imgMenuMyAccount;

    @OnClick({R.id.menu_my_account,
            R.id.menu_help,
            R.id.menu_history,
            R.id.menu_notification,
            R.id.menu_home})
    void ClickMenu(View v) {
        mListener.OnSelectedMenu(v);
    }

    private String mParam1;
    private String mParam2;

    private OnSelectedMenuListener mListener;

    public MenuHomeFragment() {
        // Required empty public constructor
    }

    public static MenuHomeFragment newInstance(String param1, String param2) {
        MenuHomeFragment fragment = new MenuHomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_menu_home, container, false);
        ButterKnife.bind(this, view);

        imgMenuHome.setImageDrawable(new IconDrawable(getActivity(), SimpleLineIconsIcons.icon_home).colorRes(R.color.white).actionBarSize());
        imgMenuNotification.setImageDrawable(new IconDrawable(getActivity(), MaterialIcons.md_notifications_none).colorRes(R.color.white).actionBarSize());
        imgMenuHistory.setImageDrawable(new IconDrawable(getActivity(), MaterialCommunityIcons.mdi_history).colorRes(R.color.white).actionBarSize());
        imgMenuHelp.setImageDrawable(new IconDrawable(getActivity(), MaterialIcons.md_help_outline).colorRes(R.color.white).actionBarSize());
        imgMenuMyAccount.setImageDrawable(new IconDrawable(getActivity(), EntypoIcons.entypo_user).colorRes(R.color.white).actionBarSize());

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(View view) {
        if (mListener != null) {
            mListener.OnSelectedMenu(view);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSelectedMenuListener) {
            mListener = (OnSelectedMenuListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnSelectedMenuListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnSelectedMenuListener {
        // TODO: Update argument type and name
        void OnSelectedMenu(View view);
    }
}
