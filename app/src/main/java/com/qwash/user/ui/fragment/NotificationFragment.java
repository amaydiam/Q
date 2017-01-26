package com.qwash.user.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.qwash.user.adapter.NotificationAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by binderbyte on 13/01/17.
 */

public class NotificationFragment extends Fragment {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view_notification)
    RecyclerView recyclerViewNotification;

    Context context;

    public NotificationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_notification_fragment, container, false);
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
                getActivity().finish();
            }
        });
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
        toolbarTitle.setText(getResources().getString(R.string.notification));

        NotificationAdapter adapter = new NotificationAdapter(getFragmentManager(), getActivity());
        recyclerViewNotification.setAdapter(adapter);
        recyclerViewNotification.setHasFixedSize(true);

        //Layout manager for Recycler view
        recyclerViewNotification.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

}
