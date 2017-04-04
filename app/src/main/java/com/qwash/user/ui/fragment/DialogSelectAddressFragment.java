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
import com.qwash.user.Sample;
import com.qwash.user.adapter.AddressUserAdapter;
import com.qwash.user.model.AddressUser;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DialogSelectAddressFragment extends DialogFragment implements AddressUserAdapter.OnAddressUserItemClickListener {

    @BindView(R.id.title_toolbar)
    TextView titleToolbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view_select_brand)
    RecyclerView recyclerView;

    private Unbinder butterKnife;
    private AddressUserAdapter adapter;
    private int type_address;

    public DialogSelectAddressFragment() {

    }

    public static DialogSelectAddressFragment newInstance(int type_address) {
        DialogSelectAddressFragment frag = new DialogSelectAddressFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Sample.ADDRESS_TYPE, type_address);
        frag.setArguments(bundle);
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
        type_address = getArguments().getInt(Sample.ADDRESS_TYPE);

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


        titleToolbar.setText(getResources().getString(type_address == Sample.CODE_ADRESS_HOME ? R.string.select_address_home : R.string.select_address_work));
        //Layout manager for Recycler view
        List<AddressUser> dataAddressUser = AddressUser.find(AddressUser.class, "type = ?", String.valueOf(type_address));
        adapter = new AddressUserAdapter(getActivity(), dataAddressUser);
        recyclerView.setAdapter(adapter);
        adapter.setOnAddressUserItemClickListener(this);


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

    public interface DialogSelectAddressUserListener {
        void onFinishDialogSelectAddressUserDialog(AddressUser addressUser);

    }


    @Override
    public void onRootClick(View v, int position) {
        DialogSelectAddressUserListener listener = (DialogSelectAddressUserListener) getActivity();
        AddressUser addressUserBrand = adapter.data.get(position);
        listener.onFinishDialogSelectAddressUserDialog(addressUserBrand);
        dismiss();
    }
}