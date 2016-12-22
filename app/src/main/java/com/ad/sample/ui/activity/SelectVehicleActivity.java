package com.ad.sample.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ad.sample.R;
import com.ad.sample.ui.fragment.SelectVehicleFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by aan on 12/21/16.
 */

public class SelectVehicleActivity extends AppCompatActivity {

    @BindView(R.id.select_vehicle_mobil)
    RadioButton selectVehicleMobil;
    @BindView(R.id.img_mobil)
    ImageView imgMobil;
    @BindView(R.id.merk_mobil)
    TextView merkMobil;
    @BindView(R.id.model_mobil)
    TextView modelMobil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_vehicle_user);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_add_vehicle)
    public void onClickAddVehicle() {
        //new activity
        Intent intent = new Intent(SelectVehicleActivity.this, SelectVehicleFragment.class);
        intent.putExtra("select", "isi");
        intent.putExtra("imagevehicle", "isi");
        intent.putExtra("merkmobil", "isi");
        intent.putExtra("modelmobil", "isi");
        startActivity(intent);

    }
}
