package com.qwash.user.api.client.washer;

/**
 * Created by Amay on 12/29/2016.
 */


import com.qwash.user.Sample;
import com.qwash.user.api.model.history.HistoryDetailResponse;
import com.qwash.user.api.model.washer.ShowWasherOn;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WasherService {
    
    @GET("async/getonline/{lat}/{long}")
    Call<ShowWasherOn> getshowWasherOnLink(@Header(Sample.AUTHORIZATION) String token, @Path("lat") String lat, @Path("long") String lo);
}