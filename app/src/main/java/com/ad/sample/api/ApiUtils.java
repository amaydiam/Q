package com.ad.sample.api;

import android.content.Context;

import com.ad.sample.api.client.auth.LoginService;
import com.ad.sample.api.client.history.HistoryService;
import com.ad.sample.api.client.RetrofitClient;
import com.ad.sample.api.client.addressfromgoogleapi.AddressMapsFromGoogleApi;
import com.ad.sample.api.client.register.RegisterService;

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
}
