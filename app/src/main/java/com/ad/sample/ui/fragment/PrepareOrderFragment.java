package com.ad.sample.ui.fragment;

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

import com.ad.sample.R;
import com.ad.sample.Sample;
import com.ad.sample.model.PrepareOrder;
import com.ad.sample.model.Vehicle;
import com.ad.sample.ui.activity.SelectVehicleActivity;
import com.ad.sample.ui.activity.ServiceDetailUserActivity;
import com.ad.sample.ui.widget.RobotoBoldTextView;
import com.ad.sample.ui.widget.RobotoRegularButton;
import com.bumptech.glide.Glide;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.joanzapata.iconify.widget.IconTextView;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PrepareOrderFragment extends Fragment {


    @BindView(R.id.btn_cancel)
    RobotoRegularButton btnCancel;

    @BindView(R.id.indicator_1)
    IconTextView indicator1;
    @BindView(R.id.indicator_2)
    IconTextView indicator2;

    @BindView(R.id.btn_next)
    RobotoRegularButton btnNext;
    private PrepareOrder prepareOrder;

    @OnClick(R.id.btn_cancel)
    void ActionCancel() {
        mListener.onCancelOrder();
        Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_next)
    void ActionNext() {
        if (vehicle != null) {
            prepareOrder.vehicle_type = vehicle.vehicle_type;
            prepareOrder.vehicle_id = vehicle.vehicle_id;
            prepareOrder.vehicle_brand = vehicle.vehicle_brand;
            prepareOrder.vehicle_model = vehicle.vehicle_model;
            prepareOrder.vehicle_transmission = vehicle.vehicle_transmission;
            prepareOrder.vehicle_year = vehicle.vehicle_year;
            Intent intent = new Intent(getActivity(), ServiceDetailUserActivity.class);
            intent.putExtra(Sample.PREPARE_ORDER_OBJECT, prepareOrder);
            getActivity().startActivityForResult(intent, 1);
        } else {
            Toast.makeText(getActivity(), "Please add first your vehicle to will be wash", Toast.LENGTH_SHORT).show();
        }
    }


    @BindView(R.id.vehicle_image)
    ImageView vehicleImage;
    @BindView(R.id.vehicle_description)
    RobotoBoldTextView vehicleDescription;
    @BindView(R.id.btn_pick_vehicle)
    IconTextView btnPickVehicle;


    @OnClick(R.id.layout_select_vehicle)
    void SelectVehicle() {
        Intent intent = new Intent(getActivity(), SelectVehicleActivity.class);
        intent.putExtra(Sample.VEHICLE_OBJECT, vehicle);
        getActivity().startActivityForResult(intent, 1);
    }

    public Vehicle vehicle = null;

    // TODO: Rename and change types of parameters

    private OnOrderedListener mListener;

    public PrepareOrderFragment() {
        // Required empty public constructor
    }

    public Fragment newInstance(PrepareOrder prepareOrder) {
        PrepareOrderFragment fragment = new PrepareOrderFragment();
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
        View view = inflater.inflate(R.layout.fragment_prepare_order, container, false);
        ButterKnife.bind(this, view);

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

    public void setSelectedVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
        Glide
                .with(this)
                .load("")
                .centerCrop()
                .placeholder(vehicle.vehicle_type == 1 ? R.drawable.mobil : R.drawable.motor)
                .crossFade()
                .into(vehicleImage);

        vehicleDescription.setText(vehicle.vehicle_brand + "\n" + vehicle.vehicle_model + " " + vehicle.vehicle_transmission + " " + vehicle.vehicle_year);
    }


    public interface OnOrderedListener {
        void onCancelOrder();
    }

}
