package com.qwash.user.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.qwash.user.R;
import com.qwash.user.Sample;
import com.qwash.user.api.ApiUtils;
import com.qwash.user.api.client.order.OrderService;
import com.qwash.user.api.model.order.RatingWasher;
import com.qwash.user.model.PrepareOrder;
import com.qwash.user.model.WasherAccepted;
import com.qwash.user.service.PushNotification;
import com.qwash.user.ui.widget.RobotoBoldTextView;
import com.qwash.user.utils.Prefs;
import com.qwash.user.utils.ProgressDialogBuilder;
import com.qwash.user.utils.TextUtils;
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


/**
 * Created by binderbyte on 14/01/17.
 */

public class RatingActivity extends AppCompatActivity {

    @BindView(R.id.vehicle_image)
    ImageView vehicleImage;
    @BindView(R.id.vehicle_description)
    RobotoBoldTextView vehicleDescription;
    @BindView(R.id.image_washer)
    AvatarView imageWasher;
    @BindView(R.id.whaser_name)
    RobotoBoldTextView whaserName;
    @BindView(R.id.total_price)
    RobotoBoldTextView totalPrice;

    @BindView(R.id.view1)
    View view1;

    @BindView(R.id.rating_wash)
    RatingBar ratingWash;

    @BindView(R.id.comment)
    EditText comment;
    private String TAG = "RatingActivity";
    private WasherAccepted washerAccepted;
    private PrepareOrder prepareOrder;
    private ProgressDialogBuilder dialogProgress;

    @OnClick(R.id.btn_submit)
    void Send() {
        String rating = String.valueOf(ratingWash.getRating());
        if (rating.equalsIgnoreCase("0.0")) {
            Toast.makeText(this, "Please rate washer", Toast.LENGTH_SHORT).show();
            return;
        }
        SendRating();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        prepareOrder = (PrepareOrder) intent.getSerializableExtra(Sample.PREPARE_ORDER_OBJECT);
        washerAccepted = (WasherAccepted) intent.getSerializableExtra(Sample.WASHER_ACCEPTED_OBJECT);

        dialogProgress = new ProgressDialogBuilder(this);

        Glide
                .with(this)
                .load("")
                .centerCrop()
                .placeholder(prepareOrder.vId.equalsIgnoreCase("1") ? R.drawable.mobil : R.drawable.motor)
                .crossFade()
                .into(vehicleImage);
        vehicleDescription.setText(prepareOrder.vBrand + "\n" + prepareOrder.models + " " + prepareOrder.vTransmission + " " + prepareOrder.years);
        vehicleDescription.setTextColor(ContextCompat.getColor(this, R.color.white));
        whaserName.setText(washerAccepted.name);
        PicassoLoader imageLoader = new PicassoLoader();
        imageLoader.loadImage(imageWasher, Sample.BASE_URL_IMAGE + washerAccepted.photo, washerAccepted.name);
        totalPrice.setText(Utils.Rupiah(prepareOrder.estimated_price));

    }


    private void SendRating() {
        {
            dialogProgress.show("Rating ...", "Please wait...");
            Map<String, String> params = new HashMap<>();
            params.put(Sample.RATE, String.valueOf(ratingWash.getRating()));
            String s_c = comment.getText().toString().trim();
            if (!TextUtils.isNullOrEmpty(s_c)) {
                params.put(Sample.COMMENTS, s_c);
            }
            params.put(Sample.ORDERS_REF, prepareOrder.orders_ref);
            params.put(Sample.USER_ID, Prefs.getUserId(this));


            OrderService mService = ApiUtils.OrderService(this);
            mService.getRatingWasherLink(params).enqueue(new Callback<RatingWasher>() {
                @Override
                public void onResponse(Call<RatingWasher> call, Response<RatingWasher> response) {

                    dialogProgress.hide();
                    if (response.isSuccessful()) {
                        if (response.body().getStatus()) {
                            PushOpenFeedBack();

                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RatingActivity.this);
                            alertDialogBuilder.setMessage("Thanks for rating");
                            alertDialogBuilder.setPositiveButton("Ok",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            arg0.dismiss();
                                            finish();
                                        }
                                    });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();

                        }
                    } else {
                        int statusCode = response.code();
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String message = jsonObject.getString(Sample.MESSAGE);
                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        } catch (JSONException | IOException e) {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<RatingWasher> call, Throwable t) {
                    String message = t.getMessage();
                    dialogProgress.hide();
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private void PushOpenFeedBack() {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    JSONObject root = new JSONObject();
                    JSONArray jsonArray = new JSONArray();
                    jsonArray.put(washerAccepted.firebase_id);

                    JSONObject data = new JSONObject();
                    data.put(Sample.ACTION, Sample.ACTION_OPEN_FEED_ORDER);
                    data.put(Sample.MESSAGE, "open_feedback");

                    root.put(Sample.DATA, data);
                    root.put(Sample.REGISTRATION_IDS, jsonArray);

                    String result = PushNotification.postToFCM(root.toString());

                    return result;
                } catch (Exception ex) {
                    ex.printStackTrace();

                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {


                try {
                    JSONObject resultJson = new JSONObject(result);
                    int success, failure;
                    success = resultJson.getInt("success");
                    failure = resultJson.getInt("failure");
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }.execute();
    }


}
