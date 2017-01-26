package com.qwash.user.ui.fragment;

import android.os.Bundle;
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
import com.qwash.user.adapter.BrandAdapter;
import com.qwash.user.model.vehicle.VehicleBrand;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DialogSelectBrandFragment extends DialogFragment implements BrandAdapter.OnBrandItemClickListener {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view_select_brand)
    RecyclerView recyclerViewSelectBrand;

    private Unbinder butterKnife;
    private List<VehicleBrand> dataVehicleBrand = new ArrayList<>();
    ;


    public DialogSelectBrandFragment() {

    }

    public static DialogSelectBrandFragment newInstance() {
        DialogSelectBrandFragment frag = new DialogSelectBrandFragment();
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

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {

        View view = inflater.inflate(
                R.layout.select_brand_user, container);
        butterKnife = ButterKnife.bind(this, view);
        toolbarTitle.setText(getResources().getString(R.string.select_brand));
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

        //Layout manager for Recycler view
        dataVehicleBrand = VehicleBrand.listAll(VehicleBrand.class);
        BrandAdapter adapter = new BrandAdapter(getActivity(), dataVehicleBrand);

        recyclerViewSelectBrand.setItemAnimator(new DefaultItemAnimator());
        recyclerViewSelectBrand.setHasFixedSize(true);
        recyclerViewSelectBrand.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewSelectBrand.setAdapter(adapter);
        adapter.setOnBrandItemClickListener(this);

        getDialog().getWindow().setSoftInputMode(
                LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        getDialog().getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    public void onRootClick(View v, int position) {

    }
}