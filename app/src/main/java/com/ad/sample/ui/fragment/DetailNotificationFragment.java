package com.ad.sample.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ad.sample.R;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by binderbyte on 13/01/17.
 */

public class DetailNotificationFragment extends Fragment {

    @BindView(R.id.img_detail_notification)
    ImageView imgDetailNotification;
    @BindView(R.id.deskripsi_detail_notification)
    TextView deskripsiDetailNotification;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Context context;

    public DetailNotificationFragment() {
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

        ((AppCompatActivity) getContext()).setSupportActionBar(toolbar);
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
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
        toolbarTitle.setText(getResources().getString(R.string.detail_notification));

        showData();

        return view;
    }

    private void showData() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String name = bundle.getString("name");
            String deskripsi = bundle.getString("deskripsi");
            int img = bundle.getInt("image", R.mipmap.ic_launcher);
            Log.d("TAG", name + deskripsi);
            imgDetailNotification.setImageResource(img);
            deskripsiDetailNotification.setText(deskripsi);
        }
    }


}
