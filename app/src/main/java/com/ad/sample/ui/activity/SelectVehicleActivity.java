package com.ad.sample.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ad.sample.R;
import com.ad.sample.ui.widget.RobotoRegularButton;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by aan on 12/21/16.
 */

public class SelectVehicleActivity extends AppCompatActivity {

    @BindView(R.id.img_mobil)
    ImageView imgMobil;
    @BindView(R.id.merk_mobil)
    TextView merkMobil;
    @BindView(R.id.model_mobil)
    TextView modelMobil;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_vehicle_user);
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
        toolbarTitle.setText(getResources().getString(R.string.select_vehicle));
    }

    @OnClick(R.id.btn_add_vehicle)
    public void onClickAddVehicle() {
        //new activity
        Intent intent = new Intent(SelectVehicleActivity.this, AddVehicleActivity.class);
        intent.putExtra("select", "isi");
        intent.putExtra("imagevehicle", "isi");
        intent.putExtra("merkmobil", "isi");
        intent.putExtra("modelmobil", "isi");
        startActivity(intent);

    }
}
