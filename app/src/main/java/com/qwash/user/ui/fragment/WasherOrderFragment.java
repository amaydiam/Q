package com.qwash.user.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.qwash.user.R;
import com.qwash.user.Sample;
import com.qwash.user.model.WasherAccepted;
import com.qwash.user.ui.widget.RobotoBoldTextView;
import com.qwash.user.ui.widget.RobotoRegularButton;
import com.qwash.user.utils.Utils;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.joanzapata.iconify.widget.IconTextView;

import agency.tango.android.avatarview.loader.PicassoLoader;
import agency.tango.android.avatarview.views.AvatarView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WasherOrderFragment extends Fragment {

    @BindView(R.id.image_washer)
    AvatarView imageWasher;
    @BindView(R.id.whaser_name)
    RobotoBoldTextView whaserName;
    @BindView(R.id.rating_whaser)
    IconTextView ratingWhaser;
    @BindView(R.id.kiri)
    LinearLayout kiri;
    @BindView(R.id.book_date)
    IconTextView bookDate;
    @BindView(R.id.book_time)
    IconTextView bookTime;
    @BindView(R.id.bill_total)
    RobotoBoldTextView estimatedPrice;
    @BindView(R.id.content)
    LinearLayout content;
    @BindView(R.id.kanan)
    RelativeLayout kanan;
    @BindView(R.id.btn_contact)
    RobotoRegularButton btnContact;
    @BindView(R.id.btn_cancel)
    RobotoRegularButton btnCancel;
    @BindView(R.id.layout_btn_washer_order)
    LinearLayout layoutBtnWasherOrder;
    private PicassoLoader imageLoader;
    private WasherAccepted washerAccepted;


    @OnClick(R.id.btn_cancel)
    void ActionCancel() {
        mListener.onCancelOrder();
        Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_contact)
    void ActionContact() {

    }

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnWasherOrderListener mListener;

    @SuppressLint("UseSparseArrays")

    public WasherOrderFragment() {
        // Required empty public constructor
    }


    public Fragment newInstance(WasherAccepted washerAccepted) {
        WasherOrderFragment fragment = new WasherOrderFragment();
        Bundle args = new Bundle();
        args.putSerializable(Sample.WASHER_ACCEPTED_OBJECT, washerAccepted);
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
            washerAccepted = (WasherAccepted) getArguments().getSerializable(Sample.WASHER_ACCEPTED_OBJECT);
        }
        imageLoader = new PicassoLoader();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmentca
        View view = inflater.inflate(R.layout.fragment_washer_order, container, false);
        ButterKnife.bind(this, view);
        whaserName.setText(washerAccepted.name);
        imageLoader.loadImage(imageWasher, "URL", washerAccepted.name);
        ratingWhaser.setText("{entypo-star-outlined} "+washerAccepted.rating);
        bookDate.setText("{fa-calendar} "+washerAccepted.datetime);
        bookTime.setText("{entypo-clock} "+washerAccepted.datetime);
        estimatedPrice.setText(Utils.Rupiah(washerAccepted.estimated_price));

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnWasherOrderListener) {
            mListener = (OnWasherOrderListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnWasherOrderListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnWasherOrderListener {
        // TODO: Update argument vId and name
        void onCancelOrder();
    }


}
