package com.qwash.user.api.client.order;

/**
 * Created by Amay on 12/29/2016.
 */


import com.qwash.user.Sample;
import com.qwash.user.api.model.order.CancelOrder;
import com.qwash.user.api.model.order.RatingWasher;
import com.qwash.user.api.model.order.RequestNewOrder;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface OrderService {

    @FormUrlEncoded
    @POST("orders/ratings")
    Call<RatingWasher> getRatingWasherLink(@Header(Sample.AUTHORIZATION) String token, @FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("orders/cancelcustomers")
    Call<CancelOrder> getCancelOrderLink(@Header(Sample.AUTHORIZATION) String token, @FieldMap Map<String, String> params);

}