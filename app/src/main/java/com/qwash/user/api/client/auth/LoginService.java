package com.qwash.user.api.client.auth;

/**
 * Created by Amay on 12/29/2016.
 */


import com.qwash.user.api.model.login.Login;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {


    @FormUrlEncoded
    @POST("auth/login/customers")
    Call<Login> getLoginLink(@FieldMap Map<String, String> params);


    @FormUrlEncoded
    @POST("login/social")
    Call<Login> getLoginSosialLink(@FieldMap Map<String, String> params);
}
