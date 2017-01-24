package com.qwash.user.api.client.order;

/**
 * Created by Amay on 12/29/2016.
 */


import com.qwash.user.api.model.order.CancelOrder;
import com.qwash.user.api.model.order.RatingWasher;
import com.qwash.user.api.model.order.RequestNewOrder;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface OrderService {
    @FormUrlEncoded
    @POST("findmatch/startOrder")
    Call<RequestNewOrder> getRequestStartOrderLink(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("transact/ratings")
    Call<RatingWasher> getRatingWasherLink(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("findmatch/cancelOrder")
    Call<CancelOrder> getCancelOrderLink(@FieldMap Map<String, String> params);
}