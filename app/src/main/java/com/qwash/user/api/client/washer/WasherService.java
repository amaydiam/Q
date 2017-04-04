package com.qwash.user.api.client.washer;

/**
 * Created by Amay on 12/29/2016.
 */


import com.qwash.user.Sample;
import com.qwash.user.api.model.washer.ShowWasherOn;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface WasherService {
    
    @GET("async/getonline/{lat}/{long}")
    Call<ShowWasherOn> getshowWasherOnLink(@Header(Sample.AUTHORIZATION) String token, @Path("lat") String lat, @Path("long") String lo);
}