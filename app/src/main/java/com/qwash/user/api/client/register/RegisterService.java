package com.qwash.user.api.client.register;

/**
 * Created by Amay on 12/29/2016.
 */


import com.qwash.user.Sample;
import com.qwash.user.api.model.register.Register;
import com.qwash.user.api.model.register.SendSms;
import com.qwash.user.api.model.washer.ShowWasherOn;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RegisterService {

    @FormUrlEncoded
    @POST("auth/registration/customers")
    Call<Register> getRegisterLink(@FieldMap Map<String, String> params);

}