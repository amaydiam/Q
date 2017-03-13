package com.qwash.user.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.MaterialCommunityModule;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.joanzapata.iconify.widget.IconTextView;
import com.kennyc.bottomsheet.BottomSheet;
import com.kennyc.bottomsheet.BottomSheetListener;
import com.qwash.user.R;
import com.qwash.user.Sample;
import com.qwash.user.api.ApiUtils;
import com.qwash.user.api.client.order.OrderService;
import com.qwash.user.api.model.order.CancelOrder;
import com.qwash.user.model.PrepareOrder;
import com.qwash.user.model.WasherAccepted;
import com.qwash.user.service.PushNotification;
import com.qwash.user.ui.widget.RobotoBoldTextView;
import com.qwash.user.ui.widget.RobotoRegularButton;
import com.qwash.user.utils.ProgressDialogBuilder;
import com.qwash.user.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import agency.tango.android.avatarview.loader.PicassoLoader;
import agency.tango.android.avatarview.views.AvatarView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WasherOrderFragment extends Fragment implements BottomSheetListener {

    @BindView(R.id.layout_btn_washer_order)
    public
    LinearLayout layoutBtnWasherOrder;
    @BindView(R.id.image_washer)
    AvatarView imageWasher;
    @BindView(R.id.whaser_name)
    RobotoBoldTextView whaserName;
    @BindView(R.id.rating_whaser)
    IconTextView ratingWhaser;
    @BindView(R.id.kiri)
    LinearLayout kiri;
    @BindView(R.id.total_price)
    RobotoBoldTextView estimatedPrice;
    @BindView(R.id.content)
    LinearLayout content;
    @BindView(R.id.kanan)
    RelativeLayout kanan;
    @BindView(R.id.btn_contact)
    RobotoRegularButton btnContact;
    @BindView(R.id.btn_cancel)
    RobotoRegularButton btnCancel;
    private PicassoLoader imageLoader;
    private WasherAccepted washerAccepted;
    private PrepareOrder prepareOrder;
    private ProgressDialogBuilder dialogProgress;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnWasherOrderListener mListener;


    @SuppressLint("UseSparseArrays")

    public WasherOrderFragment() {
        // Required empty public constructor
    }

    @OnClick({R.id.btn_contact, R.id.btn_cancel})
    void Action(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_contact:
                new BottomSheet.Builder(getActivity())
                        .setSheet(R.menu.contact_wahser)
                        .grid()
                        .setTitle("Options")
                        .setListener(this)
                        .show();
                break;
            case R.id.btn_cancel:

                CancelOrder();

                break;
        }


    }

    public Fragment newInstance(PrepareOrder prepareOrder, WasherAccepted washerAccepted) {
        WasherOrderFragment fragment = new WasherOrderFragment();
        Bundle args = new Bundle();
        args.putSerializable(Sample.PREPARE_ORDER_OBJECT, prepareOrder);
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
        dialogProgress = new ProgressDialogBuilder(getActivity());
        if (getArguments() != null) {
            prepareOrder = (PrepareOrder) getArguments().getSerializable(Sample.PREPARE_ORDER_OBJECT);
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
        imageLoader.loadImage(imageWasher, Sample.BASE_URL_IMAGE + washerAccepted.photo, washerAccepted.name);
        ratingWhaser.setText("{entypo-star-outlined} " + washerAccepted.rating);
        estimatedPrice.setText(Utils.Rupiah(prepareOrder.estimated_price));

        return view;
    }


    // ======== AcceptOrder Order
    private void CancelOrder() {
        {
            dialogProgress.show("Cancel Order Wash ...", "Please wait...");
            Map<String, String> params = new HashMap<>();
            params.put(Sample.ORDERS_REF, prepareOrder.orders_ref);

            OrderService mService = ApiUtils.OrderService(getActivity());
            mService.getCancelOrderLink(params).enqueue(new Callback<CancelOrder>() {
                @Override
                public void onResponse(Call<CancelOrder> call, Response<CancelOrder> response) {

                    dialogProgress.hide();
                    if (response.isSuccessful()) {
                        if (response.body().getStatus()) {
                            mListener.onCancelOrder();
                            Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
                            PushCancelOrder();
                        }
                    } else {
                        int statusCode = response.code();
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String message = jsonObject.getString(Sample.MESSAGE);
                            // TODO
                            // kalo udah diterima orang finish();
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        } catch (JSONException | IOException e) {
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<CancelOrder> call, Throwable t) {
                    String message = t.getMessage();
                    dialogProgress.hide();
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void PushCancelOrder() {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    JSONObject root = new JSONObject();
                    JSONArray jsonArray = new JSONArray();
                    jsonArray.put(washerAccepted.firebase_id);

                    JSONObject data = new JSONObject();
                    data.put(Sample.ACTION, Sample.ACTION_CANCEL_ORDER);
                    data.put(Sample.MESSAGE, "cancel_order");

                    root.put(Sample.DATA, data);
                    root.put(Sample.REGISTRATION_IDS, jsonArray);
                    String result = PushNotification.postToFCM(root.toString());

                    Log.v("result",result);

                    return result;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Log.v("Error",ex.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
            }
        }.execute();
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

    @Override
    public void onSheetShown(@NonNull BottomSheet bottomSheet) {

    }

    @Override
    public void onSheetItemSelected(@NonNull BottomSheet bottomSheet, MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.sms_washer:
                Intent sms = new Intent(Intent.ACTION_SENDTO);
                sms.setData(Uri.parse("smsto:" + Uri.encode(prepareOrder.phone)));
                startActivity(sms);
                break;
            case R.id.call_washer:
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts(
                        "tel", prepareOrder.phone, null));
                startActivity(phoneIntent);
                break;
        }
    }

    @Override
    public void onSheetDismissed(@NonNull BottomSheet bottomSheet, @DismissEvent int i) {

    }

    public interface OnWasherOrderListener {
        // TODO: Update argument vId and name
        void onCancelOrder();
    }


}
