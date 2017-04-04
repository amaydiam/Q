package com.qwash.user.api.client.history;

/**
 * Created by Amay on 12/29/2016.
 */

import com.qwash.user.Sample;
import com.qwash.user.api.model.history.HistoryDetailResponse;
import com.qwash.user.api.model.history.HistoryListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HistoryService {

    @GET("orders/history/customers/{customer_id}/{page}/{limit}")
    Call<HistoryListResponse> getListHistory(@Header(Sample.AUTHORIZATION) String token, @Path("customer_id") String washer_id, @Path("page") int page, @Path("limit") String limit);


    // @GET("history")
    @GET("/users/getDetailHistory/{customer_id}/{washersId}")
    Call<HistoryDetailResponse> getDetailHistory(@Query("customer_id") String customer_id, @Query("washersId") String orders_ref);
}