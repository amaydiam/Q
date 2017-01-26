package com.qwash.user.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialCommunityIcons;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.qwash.user.R;
import com.qwash.user.Sample;
import com.qwash.user.api.ApiUtils;
import com.qwash.user.api.client.vehicle.VehicleService;
import com.qwash.user.api.model.vehicle.DataVehicleBrandList;
import com.qwash.user.api.model.vehicle.DataVehicleModelList;
import com.qwash.user.api.model.vehicle.DataVehicleTransmissionList;
import com.qwash.user.api.model.vehicle.DataVehicleYearList;
import com.qwash.user.api.model.vehicle.VehicleBrandFromService;
import com.qwash.user.api.model.vehicle.VehicleModelFromService;
import com.qwash.user.api.model.vehicle.VehicleTransmissionFromService;
import com.qwash.user.api.model.vehicle.VehicleYearFromService;
import com.qwash.user.model.vehicle.VehicleBrand;
import com.qwash.user.model.vehicle.VehicleModel;
import com.qwash.user.model.vehicle.VehicleTransmission;
import com.qwash.user.model.vehicle.VehicleYear;
import com.qwash.user.ui.fragment.DialogSelectBrandFragment;
import com.qwash.user.ui.widget.RobotoRegularTextView;
import com.qwash.user.utils.ProgressDialogBuilder;

import java.util.List;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddVehicleActivity extends AppCompatActivity {

    private static final int TAG_V_BRAND = 1;
    private static final int TAG_V_MODEL = 2;
    private static final int TAG_V_TRANSMISSION = 3;
    private static final int TAG_V_YEAR = 4;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindDimen(R.dimen.activity_vertical_margin)
    int activity_vertical_margin;

    @BindView(R.id.vehicle_mobil)
    CardView vehicleMobil;
    @BindView(R.id.vehicle_motor)
    CardView vehicleMotor;
    @BindView(R.id.select_vehicle)
    LinearLayout selectVehicle;
    @BindView(R.id.indicator_select_brand)
    LinearLayout indicatorSelectBrand;
    @BindView(R.id.indicator_select_model)
    LinearLayout indicatorSelectModel;
    @BindView(R.id.indicator_select_transmission)
    LinearLayout indicatorSelectTransmission;
    @BindView(R.id.indicator_select_year)
    LinearLayout indicatorSelectYear;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.text_brand)
    RobotoRegularTextView textBrand;
    @BindView(R.id.text_model)
    RobotoRegularTextView textModel;
    @BindView(R.id.text_transmission)
    RobotoRegularTextView textTransmission;
    @BindView(R.id.text_year)
    RobotoRegularTextView textYear;
    @BindView(R.id.klik_submit)
    Button klikSubmit;
    @BindView(R.id.img_car)
    ImageView imgCar;
    @BindView(R.id.tx_car)
    RobotoRegularTextView txCar;
    @BindView(R.id.img_motorcycle)
    ImageView imgMotorcycle;
    @BindView(R.id.tx_motorcycle)
    RobotoRegularTextView txMotorcycle;
    @BindView(R.id.select_brand)
    RelativeLayout selectBrand;
    @BindView(R.id.select_model)
    RelativeLayout selectModel;
    @BindView(R.id.select_transmission)
    RelativeLayout selectTransmission;
    @BindView(R.id.select_year)
    RelativeLayout selectYear;
    @BindView(R.id.img_brand)
    ImageView imgBrand;
    @BindView(R.id.img_model)
    ImageView imgModel;
    @BindView(R.id.img_transmission)
    ImageView imgTransmission;
    @BindView(R.id.img_year)
    ImageView imgYear;
    private int selected_vehicle;
    private int selected_brand_vehicle;
    private int selected_model_vehicle;
    private int selected_transmission_vehicle;
    private int selected_year_vehicle;
    private ProgressDialogBuilder dialogProgress;

    @OnClick(R.id.vehicle_mobil)
    void VehicleMobil() {
        SelectVehicle(Sample.VEHICLE_CAR);
    }

    @OnClick(R.id.vehicle_motor)
    void VehicleCar() {
        SelectVehicle(Sample.VEHICLE_MOTORCYCLE);
    }

    @OnClick(R.id.select_brand)
    void select_brand() {
        FragmentManager fm = getSupportFragmentManager();
        DialogSelectBrandFragment dialogSelectBrandFragment = DialogSelectBrandFragment.newInstance();
        dialogSelectBrandFragment.show(fm, "Select Brand");
    }

    @OnClick(R.id.select_model)
    void select_model() {
        startActivity(new Intent(this, SelectModelActivity.class));
        //Toast.makeText(this, "Select Model", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.select_transmission)
    void select_transmission() {
        startActivity(new Intent(this, SelectTransmissionActivity.class));
        //Toast.makeText(this, "Select Transmission", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.select_year)
    void select_year() {
        startActivity(new Intent(this, SelectYearActivity.class));
        //Toast.makeText(this, "Select Year", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.klik_submit)
    public void klikSubmit() {

        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Iconify
                .with(new FontAwesomeModule())
                .with(new EntypoModule())
                .with(new MaterialModule())
                .with(new MaterialCommunityModule())
                .with(new SimpleLineIconsModule());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);
        ButterKnife.bind(this);
        dialogProgress = new ProgressDialogBuilder(this);
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
        toolbarTitle.setText(getResources().getString(R.string.title_activity_add_vehicle_type));

        imgBrand.setImageDrawable(new IconDrawable(this, MaterialCommunityIcons.mdi_speedometer).colorRes(R.color.blue_2196F3).actionBarSize());
        imgModel.setImageDrawable(new IconDrawable(this, MaterialCommunityIcons.mdi_steering).colorRes(R.color.blue_2196F3).actionBarSize());
        imgYear.setImageDrawable(new IconDrawable(this, MaterialCommunityIcons.mdi_calendar).colorRes(R.color.blue_2196F3).actionBarSize());

        Bitmap transmission_blue = BitmapFactory.decodeResource(getResources(), R.drawable.transmission_blue);
        imgTransmission.setImageBitmap(Bitmap.createScaledBitmap(transmission_blue, convertToPx(IconDrawable.ANDROID_ACTIONBAR_ICON_SIZE_DP), convertToPx(IconDrawable.ANDROID_ACTIONBAR_ICON_SIZE_DP), false));

        SelectVehicle(Sample.VEHICLE_CAR);
        setWidthSelectView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setWidthSelectView() {
        vehicleMotor.measure(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int half_width = vehicleMotor.getMeasuredWidth() / 2;

        ViewGroup.LayoutParams paramsindicatorSelectBrand = indicatorSelectBrand.getLayoutParams();
        paramsindicatorSelectBrand.width = half_width + activity_vertical_margin;
        indicatorSelectBrand.setLayoutParams(paramsindicatorSelectBrand);

        ViewGroup.LayoutParams paramsindicatorSelectModel = indicatorSelectModel.getLayoutParams();
        paramsindicatorSelectModel.width = half_width + activity_vertical_margin;
        indicatorSelectModel.setLayoutParams(paramsindicatorSelectModel);

        ViewGroup.LayoutParams paramsindicatorSelectTransmission = indicatorSelectTransmission.getLayoutParams();
        paramsindicatorSelectTransmission.width = half_width + activity_vertical_margin;
        indicatorSelectTransmission.setLayoutParams(paramsindicatorSelectTransmission);

        ViewGroup.LayoutParams paramsindicatorSelectYear = indicatorSelectYear.getLayoutParams();
        paramsindicatorSelectYear.width = half_width + activity_vertical_margin;
        indicatorSelectYear.setLayoutParams(paramsindicatorSelectYear);
    }


    public int convertToPx(int dp) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (dp * scale + 0.5f);
    }


    private void SelectVehicle(int i) {
        if (i == Sample.VEHICLE_CAR) {
            vehicleMobil.setCardBackgroundColor(ContextCompat.getColor(this, R.color.blue_2196F3));
            imgCar.setImageResource(R.drawable.car_white);
            txCar.setTextColor(ContextCompat.getColor(this, R.color.white));

            vehicleMotor.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white));
            imgMotorcycle.setImageResource(R.drawable.motor_blue);
            txMotorcycle.setTextColor(ContextCompat.getColor(this, R.color.black_424242));

        } else if (i == Sample.VEHICLE_MOTORCYCLE) {
            vehicleMobil.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white));
            imgCar.setImageResource(R.drawable.car_blue);
            txCar.setTextColor(ContextCompat.getColor(this, R.color.black_424242));

            vehicleMotor.setCardBackgroundColor(ContextCompat.getColor(this, R.color.blue_2196F3));
            imgMotorcycle.setImageResource(R.drawable.motor_white);
            txMotorcycle.setTextColor(ContextCompat.getColor(this, R.color.white));

        }

        selected_vehicle = i;
        getVehicle(TAG_V_BRAND);
    }


    private void getVehicle(final int TAG) {
        onRetrofitStart(TAG);
        VehicleService mService = ApiUtils.VehicleService(this);
        // get Brand
        if (TAG == TAG_V_BRAND)
            mService.getBrandVehicleLink(selected_vehicle).enqueue(new Callback<VehicleBrandFromService>() {
                @Override
                public void onResponse(Call<VehicleBrandFromService> call, Response<VehicleBrandFromService> response) {
                    if (response.isSuccessful()) {
                        onRetrofitSuccessVehicleBrandResponse(response);
                    } else {
                        int statusCode = response.code();
                        onRetrofitErrorVehicleBrandResponse(statusCode);
                    }
                    onRetrofitEnd();
                }

                @Override
                public void onFailure(Call<VehicleBrandFromService> call, Throwable t) {
                    String message = t.getMessage();
                    onRetrofitErrorVehicleBrandResponse(0);
                    onRetrofitEnd();
                }
            });
// get model
        if (TAG == TAG_V_MODEL)
            mService.getModelVehicleLink(selected_brand_vehicle).enqueue(new Callback<VehicleModelFromService>() {
                @Override
                public void onResponse(Call<VehicleModelFromService> call, Response<VehicleModelFromService> response) {
                    if (response.isSuccessful()) {
                        onRetrofitSuccessVehicleModelResponse(response);
                    } else {
                        int statusCode = response.code();
                        onRetrofitErrorVehicleModelResponse(statusCode);
                    }
                    onRetrofitEnd();
                }

                @Override
                public void onFailure(Call<VehicleModelFromService> call, Throwable t) {
                    String message = t.getMessage();
                    onRetrofitErrorVehicleModelResponse(0);
                    onRetrofitEnd();
                }
            });
// get tranmission
        if (TAG == TAG_V_TRANSMISSION)
            mService.getTransmissionVehicleLink(selected_model_vehicle).enqueue(new Callback<VehicleTransmissionFromService>() {
                @Override
                public void onResponse(Call<VehicleTransmissionFromService> call, Response<VehicleTransmissionFromService> response) {
                    if (response.isSuccessful()) {
                        onRetrofitSuccessVehicleTransmissionResponse(response);
                    } else {
                        int statusCode = response.code();
                        onRetrofitErrorVehicleTransmissionResponse(statusCode);
                    }
                    onRetrofitEnd();
                }

                @Override
                public void onFailure(Call<VehicleTransmissionFromService> call, Throwable t) {
                    String message = t.getMessage();
                    onRetrofitErrorVehicleTransmissionResponse(0);
                    onRetrofitEnd();
                }
            });
// year
        if (TAG == TAG_V_YEAR)
            mService.getYearVehicleLink(selected_year_vehicle).enqueue(new Callback<VehicleYearFromService>() {
                @Override
                public void onResponse(Call<VehicleYearFromService> call, Response<VehicleYearFromService> response) {
                    if (response.isSuccessful()) {
                        onRetrofitSuccessVehicleYearResponse(response);
                    } else {
                        int statusCode = response.code();
                        onRetrofitErrorVehicleYearResponse(statusCode);
                    }
                    onRetrofitEnd();
                }

                @Override
                public void onFailure(Call<VehicleYearFromService> call, Throwable t) {
                    String message = t.getMessage();
                    onRetrofitErrorVehicleYearResponse(0);
                    onRetrofitEnd();
                }
            });


    }


    public void onRetrofitStart(int TAG) {
        String msg = "";
        if (TAG == TAG_V_BRAND)
            msg = "Get Brands ...";
        if (TAG == TAG_V_MODEL)
            msg = "Get Models ...";
        if (TAG == TAG_V_TRANSMISSION)
            msg = "Get Transmissions ...";
        if (TAG == TAG_V_YEAR)
            msg = "Get Years ...";

        dialogProgress.show(msg, "Please wait...");
    }

    public void onRetrofitEnd() {
        dialogProgress.hide();
    }

    public void onRetrofitSuccessVehicleBrandResponse(Response<VehicleBrandFromService> response) {
        List<DataVehicleBrandList> vehicleVehicleBrandList = response.body().getData();
        if (vehicleVehicleBrandList.size() > 0 && (VehicleBrand.findAll(VehicleBrand.class).hasNext())) {
            VehicleBrand.deleteAll(VehicleBrand.class);
        }
        for (int i = 0; i < vehicleVehicleBrandList.size(); i++) {
            String vBrandId = vehicleVehicleBrandList.get(i).getVBrandId();
            String vIdFk = vehicleVehicleBrandList.get(i).getVIdFk();
            String vBrand = vehicleVehicleBrandList.get(i).getVBrand();
            VehicleBrand vehicleBrand = new VehicleBrand(vBrandId, vIdFk, vBrand);
            vehicleBrand.save();
        }

    }

    public void onRetrofitSuccessVehicleModelResponse(Response<VehicleModelFromService> response) {
        List<DataVehicleModelList> vehicleVehicleModelList = response.body().getData();
        if (vehicleVehicleModelList.size() > 0 && (VehicleModel.findAll(VehicleModel.class).hasNext())) {
            VehicleModel.deleteAll(VehicleModel.class);
        }
        for (int i = 0; i < vehicleVehicleModelList.size(); i++) {
            String vModelId = vehicleVehicleModelList.get(i).getVModelId();
            String vBrandIdFk = vehicleVehicleModelList.get(i).getVModelId();
            String models = vehicleVehicleModelList.get(i).getVModelId();

            VehicleModel vehicleModel = new VehicleModel(vModelId, vBrandIdFk, models);
            vehicleModel.save();
        }

    }

    public void onRetrofitSuccessVehicleTransmissionResponse(Response<VehicleTransmissionFromService> response) {
        List<DataVehicleTransmissionList> vehicleVehicleTransmissionList = response.body().getData();
        if (vehicleVehicleTransmissionList.size() > 0 && (VehicleTransmission.findAll(VehicleTransmission.class).hasNext())) {
            VehicleTransmission.deleteAll(VehicleTransmission.class);
        }
        for (int i = 0; i < vehicleVehicleTransmissionList.size(); i++) {
            String vTransId = vehicleVehicleTransmissionList.get(i).getVTransId();
            String vModelIdFk = vehicleVehicleTransmissionList.get(i).getVModelIdFk();
            String vTransmission = vehicleVehicleTransmissionList.get(i).getVTransmission();
            VehicleTransmission vehicleTransmission = new VehicleTransmission(vTransId, vModelIdFk, vTransmission);
            vehicleTransmission.save();
        }

    }

    public void onRetrofitSuccessVehicleYearResponse(Response<VehicleYearFromService> response) {
        List<DataVehicleYearList> vehicleVehicleYearList = response.body().getData();
        if (vehicleVehicleYearList.size() > 0 && (VehicleYear.findAll(VehicleYear.class).hasNext())) {
            VehicleYear.deleteAll(VehicleYear.class);
        }
        for (int i = 0; i < vehicleVehicleYearList.size(); i++) {
            String vYearsId = vehicleVehicleYearList.get(i).getVYearsId();
            String vTransIdFk = vehicleVehicleYearList.get(i).getVTransIdFk();
            String years = vehicleVehicleYearList.get(i).getYears();

            VehicleYear vehicleYear = new VehicleYear(vYearsId, vTransIdFk, years);
            vehicleYear.save();
        }

    }

    void onRetrofitErrorVehicleBrandResponse(int statusCode) {

    }

    void onRetrofitErrorVehicleModelResponse(int statusCode) {

    }

    void onRetrofitErrorVehicleTransmissionResponse(int statusCode) {

    }

    void onRetrofitErrorVehicleYearResponse(int statusCode) {

    }

}
