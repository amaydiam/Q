package com.qwash.user.api;

import android.content.Context;

import com.qwash.user.Sample;
import com.qwash.user.api.client.RetrofitClient;
import com.qwash.user.api.client.account.AccountService;
import com.qwash.user.api.client.addressfromgoogleapi.AddressMapsFromGoogleApi;
import com.qwash.user.api.client.auth.LoginService;
import com.qwash.user.api.client.forgotpassword.ForgotPasswordService;
import com.qwash.user.api.client.history.HistoryService;
import com.qwash.user.api.client.notification.NotificationService;
import com.qwash.user.api.client.order.OrderService;
import com.qwash.user.api.client.register.RegisterService;
import com.qwash.user.api.client.sms.VerificationCodeService;
import com.qwash.user.api.client.washer.WasherService;

/**
 * Created by Amay on 12/29/2016.
 */

public class ApiUtils {


    public static AddressMapsFromGoogleApi getAddressMapsFromGoogleApi(Context context) {
        return RetrofitClient.getClient(context, Sample.BASE_URL_MAP).create(AddressMapsFromGoogleApi.class);
    }

    public static HistoryService getHistory(Context context) {
        return RetrofitClient.getClient(context, Sample.BASE_URL_QWASH_API).create(HistoryService.class);
    }

    public static NotificationService getNotification(Context context) {
        return RetrofitClient.getClient(context, Sample.BASE_URL_QWASH_API).create(NotificationService.class);
    }

    public static LoginService LoginService(Context context) {
        return RetrofitClient.getClient(context, Sample.BASE_URL_QWASH_PUBLIC).create(LoginService.class);

    }

    public static RegisterService RegisterService(Context context) {
        return RetrofitClient.getClient(context, Sample.BASE_URL_QWASH_PUBLIC).create(RegisterService.class);

    }

    public static OrderService OrderService(Context context) {
        return RetrofitClient.getClient(context, Sample.BASE_URL_QWASH_API).create(OrderService.class);

    }

    public static WasherService WasherService(Context context) {
        return RetrofitClient.getClient(context, Sample.BASE_URL_QWASH_API).create(WasherService.class);

    }

    public static String getImageAddressMapsFromGoogleApi(String LatLong) {
        return Sample.BASE_URL_MAP+"staticmap?center="+LatLong+"&zoom=8&scale=false&size=100x100&maptype=roadmap&format=png&visual_refresh=true&markers=size:mid%7Ccolor:0xff0000%7Clabel:%7C"+LatLong;
    }


    public static VerificationCodeService VerificationCodeService(Context context) {
        return RetrofitClient.getClient(context, Sample.BASE_URL_QWASH_API).create(VerificationCodeService.class);
    }

    public static AccountService AccountService(Context context) {
        return RetrofitClient.getClient(context, Sample.BASE_URL_QWASH_API).create(AccountService.class);

    }

    public static ForgotPasswordService ForgotPasswordService(Context context) {
        return RetrofitClient.getClient(context, Sample.BASE_URL_QWASH_API).create(ForgotPasswordService.class);

    }
}
