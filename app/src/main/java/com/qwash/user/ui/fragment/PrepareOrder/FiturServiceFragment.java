package com.qwash.user.ui.fragment.PrepareOrder;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FiturServiceFragment extends Fragment {



    @BindView(R.id.fitur_service_motor)
    MaterialRadioGroup fiturServiceMotor;
    @BindView(R.id.fitur_service_car)
    MaterialRadioGroup fiturServiceCar;
    @BindView(R.id.rd_perfumed)
    MaterialRadioButton rdPerfumed;
    @BindView(R.id.rd_interior_vaccum)
    MaterialRadioButton rdInteriorVaccum;
    @BindView(R.id.rd_waterless)
    MaterialRadioButton rdWaterless;
    @BindView(R.id.layout_additional_car)
    LinearLayout layoutAdditionalCar;
    @BindView(R.id.rg_perfumed)
    MaterialRadioGroup rgPerfumed;
    @BindView(R.id.rg_interior_vaccum)
    MaterialRadioGroup rgInteriorVaccum;
    @BindView(R.id.rg_waterless)
    MaterialRadioGroup rgWaterless;
    @BindView(R.id.tap_perfumed)
    View tapPerfumed;
    @BindView(R.id.tap_interior_vaccum)
    View tapInteriorVaccum;
    @BindView(R.id.tap_waterless)
    View tapWaterless;

    private int selectedType;
    private int selectedChildType;

    public FiturServiceFragment() {
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
        View view = inflater.inflate(R.layout.fragment_prepare_order_fitur_service, container, false);
        ButterKnife.bind(this, view);

        tapPerfumed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rdPerfumed.isChecked()) {
                    rgPerfumed.clearCheck();
                }
                else {
                    rgPerfumed.check(R.id.rd_perfumed);
                }

                EventBus.getDefault().post(new FiturService(rdPerfumed.isChecked(),rdInteriorVaccum.isChecked(),rdWaterless.isChecked()));
            }

        });

        tapInteriorVaccum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rdInteriorVaccum.isChecked()) {
                    rgInteriorVaccum.clearCheck();
                }
                else {
                    rgInteriorVaccum.check(R.id.rd_interior_vaccum);
                }
                EventBus.getDefault().post(new FiturService(rdPerfumed.isChecked(),rdInteriorVaccum.isChecked(),rdWaterless.isChecked()));
            }
        });

        tapWaterless.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rdWaterless.isChecked()) {
                    rgWaterless.clearCheck();
                }
                else {
                    rgPerfumed.check(R.id.rd_perfumed);
                    rgInteriorVaccum.check(R.id.rd_interior_vaccum);
                    rgWaterless.check(R.id.rd_waterless);
                }
                EventBus.getDefault().post(new FiturService(rdPerfumed.isChecked(),rdInteriorVaccum.isChecked(),rdWaterless.isChecked()));
            }
        });

        selectedType = Sample.VEHICLE_CAR;
        checkSelected();

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

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe
    public void getTypeVehicle(TypeVehicle typeVehicle) {
        selectedType = typeVehicle.getTypeVehicle();
        selectedChildType = typeVehicle.getChildTypeVehicle();
        checkSelected();
    }

    private void checkSelected() {
        if (selectedType == Sample.VEHICLE_CAR) {
            fiturServiceCar.setVisibility(View.VISIBLE);
            layoutAdditionalCar.setVisibility(View.VISIBLE);
            fiturServiceMotor.setVisibility(View.GONE);
        } else {
            fiturServiceCar.setVisibility(View.GONE);
            layoutAdditionalCar.setVisibility(View.GONE);
            fiturServiceMotor.setVisibility(View.VISIBLE);
        }
    }


}
