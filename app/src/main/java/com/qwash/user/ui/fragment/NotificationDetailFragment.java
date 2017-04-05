package com.qwash.user.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.qwash.user.ui.activity.BaseActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.qwash.user.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by binderbyte on 13/01/17.
 */

public class NotificationDetailFragment extends Fragment {

    @BindView(R.id.img_detail_notification)
    ImageView imgDetailNotification;
    @BindView(R.id.deskripsi_detail_notification)
    TextView deskripsiDetailNotification;
    @BindView(R.id.title_toolbar)
    TextView titleToolbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Context context;

    public NotificationDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_detail_notification, container, false);
        ButterKnife.bind(this, view);
        Iconify
                .with(new FontAwesomeModule())
                .with(new EntypoModule())
                .with(new MaterialModule())
                .with(new MaterialCommunityModule())
                .with(new SimpleLineIconsModule());

        ((BaseActivity) getContext()).setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(
                new IconDrawable(getActivity(), MaterialIcons.md_arrow_back)
                        .colorRes(R.color.black_424242)
                        .actionBarSize());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });
        ((BaseActivity) getActivity()).getSupportActionBar().setTitle("");
        titleToolbar.setText(getResources().getString(R.string.detail_notification));

        showData();

        return view;
    }

    private void showData() {

    }


}
