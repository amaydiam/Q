package com.qwash.user.ui.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.qwash.user.R;
import com.qwash.user.Sample;
import com.qwash.user.adapter.VehicleVariansAdapter;
import com.qwash.user.model.vehicle.VehicleBrand;
import com.qwash.user.model.vehicle.VehicleModel;
import com.qwash.user.model.vehicle.VehicleTransmission;
import com.qwash.user.model.vehicle.VehicleYear;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DialogSelectVehicleVariansFragment extends DialogFragment implements VehicleVariansAdapter.OnVehicleVariansItemClickListener {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view_select_brand)
    RecyclerView recyclerView;

    int TAG;

    private Unbinder butterKnife;
    private VehicleVariansAdapter adapterBrands;
    private VehicleVariansAdapter adapterModels;
    private VehicleVariansAdapter adapterTransmissions;
    private VehicleVariansAdapter adapterYears;

    public DialogSelectVehicleVariansFragment() {

    }

    public static DialogSelectVehicleVariansFragment newInstance(List<?> varians, int TAG) {
        DialogSelectVehicleVariansFragment frag = new DialogSelectVehicleVariansFragment();
        Bundle args = new Bundle();
        args.putInt(Sample.TAG, TAG);
        args.putParcelableArrayList(Sample.LIST_VARIANS_OBJECT, (ArrayList<? extends Parcelable>) varians);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        butterKnife.unbind();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getArguments().getInt(Sample.TAG);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {

        View view = inflater.inflate(
                R.layout.dialog_select, container);
        butterKnife = ButterKnife.bind(this, view);
        toolbar.setNavigationIcon(
                new IconDrawable(getActivity(), MaterialIcons.md_close)
                        .colorRes(R.color.white)
                        .actionBarSize());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        if (TAG == Sample.TAG_V_BRAND) {
            toolbarTitle.setText(getResources().getString(R.string.select_brand));
            //Layout manager for Recycler view

            List<VehicleBrand> dataVehicleBrand = getArguments().getParcelableArrayList(Sample.LIST_VARIANS_OBJECT);
            adapterBrands = new VehicleVariansAdapter(getActivity(), TAG, dataVehicleBrand);
            recyclerView.setAdapter(adapterBrands);
            adapterBrands.setOnVehicleVariansItemClickListener(this);
        } else if (TAG == Sample.TAG_V_MODEL) {
            toolbarTitle.setText(getResources().getString(R.string.select_model));
            //Layout manager for Recycler view
            List<VehicleModel> dataVehicleModel = getArguments().getParcelableArrayList(Sample.LIST_VARIANS_OBJECT);
            adapterModels = new VehicleVariansAdapter(getActivity(), TAG, dataVehicleModel);
            recyclerView.setAdapter(adapterModels);
            adapterModels.setOnVehicleVariansItemClickListener(this);
        } else if (TAG == Sample.TAG_V_TRANSMISSION) {
            toolbarTitle.setText(getResources().getString(R.string.select_transmission));
            //Layout manager for Recycler view
            List<VehicleTransmission> dataVehicleTransmission =  getArguments().getParcelableArrayList(Sample.LIST_VARIANS_OBJECT);
            adapterTransmissions = new VehicleVariansAdapter(getActivity(), TAG, dataVehicleTransmission);
            recyclerView.setAdapter(adapterTransmissions);
            adapterTransmissions.setOnVehicleVariansItemClickListener(this);
        } else if (TAG == Sample.TAG_V_YEAR) {
            toolbarTitle.setText(getResources().getString(R.string.select_year));
            //Layout manager for Recycler view
            List<VehicleYear> dataVehicleYear = getArguments().getParcelableArrayList(Sample.LIST_VARIANS_OBJECT);
            adapterYears = new VehicleVariansAdapter(getActivity(), TAG, dataVehicleYear);
            recyclerView.setAdapter(adapterYears);
            adapterYears.setOnVehicleVariansItemClickListener(this);
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getDialog().getWindow().setSoftInputMode(
                LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        getDialog().getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    public interface DialogSelectVehicleVariansListener {
        void onFinishDialogSelectVehicleVarianBrandsDialog(int TAG, VehicleBrand vehicleBrand);

        void onFinishDialogSelectVehicleVarianModelsDialog(int tag, VehicleModel vehicleModel);

        void onFinishDialogSelectVehicleVarianTransmissionsDialog(int tag, VehicleTransmission vehicleTransmission);

        void onFinishDialogSelectVehicleVarianYearsDialog(int tag, VehicleYear vehicleYear);
    }


    @Override
    public void onRootClick(View v, int position) {
        DialogSelectVehicleVariansListener listener = (DialogSelectVehicleVariansListener) getActivity();
        if (TAG == Sample.TAG_V_BRAND) {
            VehicleBrand vehicleBrand = (VehicleBrand) adapterBrands.data.get(position);
            listener.onFinishDialogSelectVehicleVarianBrandsDialog(TAG, vehicleBrand);
        } else if (TAG == Sample.TAG_V_MODEL) {
            VehicleModel vehicleModel = (VehicleModel) adapterModels.data.get(position);
            listener.onFinishDialogSelectVehicleVarianModelsDialog(TAG, vehicleModel);
        } else if (TAG == Sample.TAG_V_TRANSMISSION) {
            VehicleTransmission vehicleTransmission = (VehicleTransmission) adapterTransmissions.data.get(position);
            listener.onFinishDialogSelectVehicleVarianTransmissionsDialog(TAG, vehicleTransmission);
        } else if (TAG == Sample.TAG_V_YEAR) {
            VehicleYear vehicleYear = (VehicleYear) adapterYears.data.get(position);
            listener.onFinishDialogSelectVehicleVarianYearsDialog(TAG, vehicleYear);
        }
        dismiss();
    }
}