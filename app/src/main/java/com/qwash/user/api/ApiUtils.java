package com.qwash.user.api;

import android.content.Context;

import com.qwash.user.api.client.auth.LoginService;
import com.qwash.user.api.client.history.HistoryService;
import com.qwash.user.api.client.RetrofitClient;
import com.qwash.user.api.client.addressfromgoogleapi.AddressMapsFromGoogleApi;
import com.qwash.user.api.client.order.OrderService;
import com.qwash.user.api.client.register.RegisterService;
import com.qwash.user.api.client.washer.WasherService;

/**
 * Created by Amay on 12/29/2016.
 */

public class ApiUtils {

    public static final String BASE_URL_MAP = "http://maps.googleapis.com/maps/api/";
    public static final String BASE_URL_MY_JON = "https://api.myjson.com/bins/";
    public static final String BASE_URL_QWASH = "http://apis.aanaliudin.com/index.php/api/";

    public static AddressMapsFromGoogleApi getAddressMapsFromGoogleApi(Context context) {
        return RetrofitClient.getClient(context, BASE_URL_MAP).create(AddressMapsFromGoogleApi.class);
    }

    public static HistoryService getHistory(Context context) {
        return RetrofitClient.getClient(context, BASE_URL_MY_JON).create(HistoryService.class);
    }

    public static LoginService LoginService(Context context) {
        return RetrofitClient.getClient(context, BASE_URL_QWASH).create(LoginService.class);

    }

    public static RegisterService RegisterService(Context context) {
        return RetrofitClient.getClient(context, BASE_URL_QWASH).create(RegisterService.class);

    }

    public static OrderService OrderService(Context context) {
        return RetrofitClient.getClient(context, BASE_URL_QWASH).create(OrderService.class);

    }


    public static WasherService WasherService(Context context) {
        return RetrofitClient.getClient(context, BASE_URL_QWASH).create(WasherService.class);

    }
}
