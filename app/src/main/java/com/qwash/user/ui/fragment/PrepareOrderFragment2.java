package com.qwash.user.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.joanzapata.iconify.widget.IconTextView;
import com.qwash.user.R;
import com.qwash.user.Sample;
import com.qwash.user.model.PrepareOrder;
import com.qwash.user.model.VehicleUser;
import com.qwash.user.ui.activity.SelectVehicleActivity;
import com.qwash.user.ui.activity.ServiceDetailUserActivity;
import com.qwash.user.ui.widget.RobotoBoldTextView;
import com.qwash.user.ui.widget.RobotoRegularButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PrepareOrderFragment2 extends Fragment {


    public VehicleUser vehicle = null;
    @BindView(R.id.btn_cancel)
    RobotoRegularButton btnCancel;
    @BindView(R.id.indicator_1)
    IconTextView indicator1;
    @BindView(R.id.indicator_2)
    IconTextView indicator2;
    @BindView(R.id.btn_next)
    RobotoRegularButton btnNext;
    @BindView(R.id.vehicle_image)
    ImageView vehicleImage;
    @BindView(R.id.vehicle_description)
    RobotoBoldTextView vehicleDescription;
    @BindView(R.id.btn_pick_vehicle)
    IconTextView btnPickVehicle;
    @BindView(R.id.layout_selected_vehicle)
    View layoutSelectedVehicle;
    @BindView(R.id.layout_no_one_vehicle)
    View layoutNoOneVehicle;
    private PrepareOrder prepareOrder;
    private OnOrderedListener mListener;

    public PrepareOrderFragment2() {
        // Required empty public constructor
    }

    @OnClick(R.id.btn_cancel)
    void ActionCancel() {
        mListener.onCancelOrder();
        Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
    }

    // TODO: Rename and change types of parameters

    @OnClick(R.id.btn_next)
    void ActionNext() {
        if (vehicle != null) {
            prepareOrder.vId = vehicle.getvId();
            prepareOrder.vCustomersId = vehicle.getvCustomersId();
            prepareOrder.vName = vehicle.getvName();
            prepareOrder.vBrand = vehicle.getvBrand();
            prepareOrder.models = vehicle.getModels();
            prepareOrder.vTransmission = vehicle.getvTransmission();
            prepareOrder.years = vehicle.getYears();
            prepareOrder.vBrandId = vehicle.getvBrandId();
            prepareOrder.vModelId = vehicle.getvModelId();
            prepareOrder.vTransId = vehicle.getvTransId();
            prepareOrder.vYearsId = vehicle.getvYearsId();
            Intent intent = new Intent(getActivity(), ServiceDetailUserActivity.class);
            intent.putExtra(Sample.PREPARE_ORDER_OBJECT, prepareOrder);
            getActivity().startActivityForResult(intent, 1);
        } else {
            Toast.makeText(getActivity(), "Please add first your vehicle to will be wash", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.layout_select_vehicle)
    void SelectVehicle() {
        Intent intent = new Intent(getActivity(), SelectVehicleActivity.class);
        intent.putExtra(Sample.VEHICLE_OBJECT, vehicle);
        getActivity().startActivityForResult(intent, 1);
    }

    public Fragment newInstance(PrepareOrder prepareOrder) {
        PrepareOrderFragment2 fragment = new PrepareOrderFragment2();
        Bundle args = new Bundle();
        args.putSerializable(Sample.PREPARE_ORDER_OBJECT, prepareOrder);
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
            prepareOrder = (PrepareOrder) getArguments().getSerializable(Sample.PREPARE_ORDER_OBJECT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_prepare_order_2, container, false);
        ButterKnife.bind(this, view);

        if (VehicleUser.findAll(VehicleUser.class).hasNext()) {
            List<VehicleUser> vehicleUsers = VehicleUser.find(VehicleUser.class, null, null, null, null, "1");
            vehicle = vehicleUsers.get(0);
            Glide
                    .with(this)
                    .load("")
                    .centerCrop()
                    .placeholder(vehicle.getvId().equalsIgnoreCase("1") ? R.drawable.mobil : R.drawable.motor)
                    .crossFade()
                    .into(vehicleImage);
            vehicleDescription.setText(vehicle.getvBrand() + "\n" + vehicle.getModels() + " " + vehicle.getvTransmission() + " " + vehicle.getYears());
            layoutSelectedVehicle.setVisibility(View.VISIBLE);
            layoutNoOneVehicle.setVisibility(View.GONE);
        } else {
            layoutSelectedVehicle.setVisibility(View.INVISIBLE);
            layoutNoOneVehicle.setVisibility(View.GONE);
        }

        btnCancel.setText(getActivity().getResources().getString(R.string.button_cancel));
        indicator1.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue_2196F3));
        indicator2.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue_BBDEFB));

        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onCancelOrder();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnOrderedListener) {
            mListener = (OnOrderedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnOrderedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setSelectedVehicle(VehicleUser vehicle) {
        this.vehicle = vehicle;
        Glide
                .with(this)
                .load("")
                .centerCrop()
                .placeholder(vehicle.getvId().equalsIgnoreCase("1") ? R.drawable.mobil : R.drawable.motor)
                .crossFade()
                .into(vehicleImage);

        vehicleDescription.setText(vehicle.getvBrand() + "\n" + vehicle.getModels() + " " + vehicle.getvTransmission() + " " + vehicle.getYears());
    }


    public interface OnOrderedListener {
        void onCancelOrder();
    }

}
