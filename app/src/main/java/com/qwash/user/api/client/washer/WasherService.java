package com.qwash.user.api.client.washer;

/**
 * Created by Amay on 12/29/2016.
 */




import com.qwash.user.api.model.order.Order;
import com.qwash.user.api.model.washer.NearbyWasher;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WasherService {/*
   @GET("users/NearbyWasher/{LatLong}")
    Call<NearbyWasher> getNearbyWasherLink(@Path("LatLong") String LatLong);*/

    @GET("users/NearbyWasher")
    Call<NearbyWasher> getNearbyWasherLink();
}