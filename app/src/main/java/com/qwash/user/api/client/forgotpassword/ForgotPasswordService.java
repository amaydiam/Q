package com.qwash.user.api.client.forgotpassword;

/**
 * Created by Amay on 12/29/2016.
 */


import com.qwash.user.api.model.forgotpassword.ForgotPassword;
import com.qwash.user.api.model.forgotpassword.RequestForgotPassword;
import com.qwash.user.api.model.forgotpassword.VerificationCodeForgotPassword;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ForgotPasswordService {
    @GET("users/request/forgotpass/{email}")
    Call<RequestForgotPassword> getRequestForgotPasswordLink(@Path("email") String email);

    @FormUrlEncoded
    @POST("users/checkcode/forgotpass")
    Call<VerificationCodeForgotPassword> getSmsVerificationLink(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("users/forgot/password")
    Call<ForgotPassword> getForgotPasswordLink(@FieldMap Map<String, String> params);

}