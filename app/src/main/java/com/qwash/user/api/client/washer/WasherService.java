package com.qwash.user.api.client.washer;

/**
 * Created by Amay on 12/29/2016.
 */


import com.qwash.user.api.model.washer.ShowWasherOn;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface WasherService {
    @FormUrlEncoded
    @POST("findmatch/showWasherOn")
    Call<ShowWasherOn> getshowWasherOnLink(@FieldMap Map<String, String> params);

}