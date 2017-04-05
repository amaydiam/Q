package com.qwash.user.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.qwash.user.ui.activity.BaseActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.qwash.user.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by binderbyte on 27/12/16.
 */

public class HelpActivity extends BaseActivity {

    @BindView(R.id.title_toolbar)
    TextView titleToolbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_user);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(
                new IconDrawable(this, MaterialIcons.md_arrow_back)
                        .colorRes(R.color.black_424242)
                        .actionBarSize());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setTitle("");
        titleToolbar.setText(getResources().getString(R.string.title_help));

    }
}
