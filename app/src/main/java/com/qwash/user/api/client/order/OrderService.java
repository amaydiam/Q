package com.qwash.user.api.client.order;

/**
 * Created by Amay on 12/29/2016.
 */




import com.qwash.user.api.model.order.Order;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface OrderService {
    @FormUrlEncoded
    @POST("firebase/sendOrder")
    Call<Order> getOrderLink(@FieldMap Map<String, String> params);
}