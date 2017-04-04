package com.qwash.user.ui.fragment.PrepareOrder;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.merhold.extensiblepageindicator.ExtensiblePageIndicator;
import com.qwash.user.R;
import com.qwash.user.Sample;
import com.qwash.user.adapter.PrepareOrderFragmentAdapter;
import com.qwash.user.model.PrepareOrder;
import com.qwash.user.ui.widget.RobotoBoldTextView;
import com.qwash.user.ui.widget.RobotoRegularButton;
import com.qwash.user.ui.widget.WrapContentViewPager;
import com.qwash.user.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PrepareOrderFragment extends Fragment {


    @BindView(R.id.viewPager)
    WrapContentViewPager viewPager;
    @BindView(R.id.total_harga)
    RobotoBoldTextView totalHarga;
    @BindView(R.id.btn_next)
    RobotoRegularButton btnNext;
    @BindView(R.id.indicator)
    ExtensiblePageIndicator indicator;

    private PrepareOrder prepareOrder;
    private OnOrderedListener mListener;
    private int selectedTYpe;
    private int selectedChildType;


    public PrepareOrderFragment() {
        // Required empty public constructor
    }

    /*@OnClick(R.id.btn_cancel)
    void ActionCancel() {
        mListener.onCancelOrder();
        Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
    }*/


    private int estimated_price = 0;

    private boolean isPerfurmed = false;
    private boolean isInteriorVaccum = false;
    private boolean isWaterless = false;

    private int perfumed_price = 2000;
    private int interior_vaccum_price = 2000;
    private int waterless_price = 7000;
    private int price_CAR = 25000;
    private int price_MOTOR = 10000;


    @OnClick(R.id.btn_next)
    void ActionNext() {
        if (viewPager.getCurrentItem() == 0)
            viewPager.setCurrentItem(1);
        else {

            prepareOrder.vehicles_type = selectedTYpe;
            prepareOrder.vehicles = selectedChildType;

            if (selectedTYpe == Sample.VEHICLE_CAR) {
                prepareOrder.price = price_CAR;
                prepareOrder.perfum_price = isPerfurmed ? perfumed_price : 0;
                prepareOrder.perfum_status = isPerfurmed ? 1 : 0;
                prepareOrder.interior_vaccum_price = isInteriorVaccum ? interior_vaccum_price : 0;
                prepareOrder.interior_vaccum_status = isInteriorVaccum ? 1 : 0;
                prepareOrder.waterless_price = isWaterless ? waterless_price : 0;
                prepareOrder.waterless_status = isWaterless ? 1 : 0;
                prepareOrder.estimated_price = estimated_price;

            } else {
                prepareOrder.price = price_MOTOR;
                prepareOrder.perfum_price = 0;
                prepareOrder.perfum_status = 0;
                prepareOrder.interior_vaccum_price = 0;
                prepareOrder.interior_vaccum_status = 0;
                prepareOrder.waterless_price = 0;
                prepareOrder.waterless_status = 0;
                prepareOrder.estimated_price = price_MOTOR;
            }

            EventBus.getDefault().post(new NewOrder(prepareOrder));

        }

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
        View view = inflater.inflate(R.layout.fragment_prepare_order_2, container, false);
        ButterKnife.bind(this, view);

        FragmentManager manager = getActivity().getSupportFragmentManager();
        PrepareOrderFragmentAdapter adapter = new PrepareOrderFragmentAdapter(manager);
        viewPager.setAdapter(adapter);
        indicator.initViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0)
                    btnNext.setText(getResources().getString(R.string.btn_next));
                else
                    btnNext.setText(getResources().getString(R.string.btn_order_washer));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setHarga();

        return view;
    }

    /*vehicles
    "Car Small, Medium, Large"
    "Motor Small, Medium, Large"*/

    private void setHarga() {
        if (selectedTYpe == Sample.VEHICLE_CAR) {
            estimated_price = price_CAR + (isPerfurmed ? perfumed_price : 0) + (isInteriorVaccum ? interior_vaccum_price : 0) + (isWaterless ? waterless_price : 0);
        } else {
            estimated_price = price_MOTOR;
        }
        totalHarga.setText(Utils.Rupiah(estimated_price));
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

    public interface OnOrderedListener {
        void onCancelOrder();
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
        selectedTYpe = typeVehicle.getTypeVehicle();
        selectedChildType = typeVehicle.getChildTypeVehicle();
        setHarga();
    }

    @Subscribe
    public void getFiturService(FiturService fiturService) {
        isPerfurmed = fiturService.isPerfurmed();
        isInteriorVaccum = fiturService.isInteriorVaccum();
        isWaterless = fiturService.isWaterless();
        setHarga();
    }

}
