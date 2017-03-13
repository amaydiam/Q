package com.qwash.user.ui.fragment.PrepareOrder;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.qwash.user.R;
import com.qwash.user.Sample;

import net.soulwolf.widget.materialradio.MaterialRadioButton;
import net.soulwolf.widget.materialradio.MaterialRadioGroup;
import net.soulwolf.widget.materialradio.listener.OnCheckedChangeListener;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectVehicleFragment extends Fragment {


    @BindView(R.id.rd_car_city_car)
    MaterialRadioButton rdCarCityCar;
    @BindView(R.id.rd_car_minivan)
    MaterialRadioButton rdCarMinivan;
    @BindView(R.id.rd_car_suv)
    MaterialRadioButton rdCarSuv;
    @BindView(R.id.rg_car)
    MaterialRadioGroup rgCar;
    @BindView(R.id.rd_motocycle_under_150)
    MaterialRadioButton rdMotocycleUnder150;
    @BindView(R.id.rd_motocycle_150)
    MaterialRadioButton rdMotocycle150;
    @BindView(R.id.rd_motocycle_above_150)
    MaterialRadioButton rdMotocycleAbove150;
    @BindView(R.id.rg_motocycle)
    MaterialRadioGroup rgMotocycle;


    public SelectVehicleFragment() {
        // Required empty public constructor
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prepare_order_select_vehicle, container, false);
        ButterKnife.bind(this, view);

        EventBus.getDefault().post(new TypeVehicle(Sample.VEHICLE_CAR,Sample.VEHICLE_CAR_CITY_CAR));

        rgCar.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialRadioGroup group, int checkedId) {
                if (rdCarCityCar.isChecked() || rdCarMinivan.isChecked() || rdCarSuv.isChecked()) {
                    int child= Sample.VEHICLE_CAR_CITY_CAR;
                    if (rdCarCityCar.isChecked()) {
                        child = Sample.VEHICLE_CAR_CITY_CAR;
                    }
                    else if (rdCarMinivan.isChecked()) {
                        child = Sample.VEHICLE_CAR_MINIVAN;
                    }
                    else if (rdCarSuv.isChecked()) {
                        child = Sample.VEHICLE_CAR_SUV;
                    }
                    rgMotocycle.clearCheck();
                    EventBus.getDefault().post(new TypeVehicle(Sample.VEHICLE_CAR,child));
                }
            }
        });

        rgMotocycle.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MaterialRadioGroup group, int checkedId) {
                if (rdMotocycleUnder150.isChecked() || rdMotocycle150.isChecked() || rdMotocycleAbove150.isChecked()) {
                    int child= Sample.VEHICLE_MOTORCYCLE_UNDER_150;
                    if (rdMotocycleUnder150.isChecked()) {
                        child = Sample.VEHICLE_MOTORCYCLE_UNDER_150;
                    }
                    else if (rdMotocycle150.isChecked()) {
                        child = Sample.VEHICLE_MOTORCYCLE_150;
                    }
                    else if (rdMotocycleAbove150.isChecked()) {
                        child = Sample.VEHICLE_MOTORCYCLE_ABOVE_150;
                    }
                    rgCar.clearCheck();
                    EventBus.getDefault().post(new TypeVehicle(Sample.VEHICLE_MOTORCYCLE,child));
                }
            }
        });

        rdCarCityCar.setChecked(true);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
