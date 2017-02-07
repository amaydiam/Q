package com.qwash.user.api.client.history;

/**
 * Created by Amay on 12/29/2016.
 */

import com.qwash.user.api.model.history.HistoryDetailResponse;
import com.qwash.user.api.model.history.HistoryListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HistoryService {
    //  @GET("history")
    @GET("users/getHistoryOrders/{customer_id}/{page}/{limit}")
    Call<HistoryListResponse> getListHistory(@Path("customer_id") String washer_id, @Path("page") int page, @Path("limit") String limit);


    // @GET("history")
    @GET("/users/getDetailHistory/{customer_id}/{orders_ref}")
    Call<HistoryDetailResponse> getDetailHistory(@Query("customer_id") String customer_id, @Query("orders_ref") String orders_ref);
}