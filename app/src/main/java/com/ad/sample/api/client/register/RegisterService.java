package com.ad.sample.api.client.register;

/**
 * Created by Amay on 12/29/2016.
 */



import com.ad.sample.api.model.register.Register;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RegisterService {
    @FormUrlEncoded
    @POST("users/add")
    Call<Register> getRegisterLink(@FieldMap Map<String, String> params);
}