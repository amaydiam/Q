package com.qwash.user.api.client.addressfromgoogleapi;

/**
 * Created by Amay on 12/29/2016.
 */

import com.qwash.user.api.model.AddressFromMapsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AddressMapsFromGoogleApi {
    @GET("geocode/json?sensor=true")
    Call<AddressFromMapsResponse> getAddress(@Query("latlng") String tags);
}