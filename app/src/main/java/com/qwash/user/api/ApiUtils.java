package com.qwash.user.api;

import android.app.Activity;
import android.content.Context;

import com.qwash.user.Sample;
import com.qwash.user.api.client.RetrofitClient;
import com.qwash.user.api.client.addressfromgoogleapi.AddressMapsFromGoogleApi;
import com.qwash.user.api.client.auth.LoginService;
import com.qwash.user.api.client.history.HistoryService;
import com.qwash.user.api.client.notification.NotificationService;
import com.qwash.user.api.client.order.OrderService;
import com.qwash.user.api.client.register.RegisterService;
import com.qwash.user.api.client.vehicle.VehicleService;
import com.qwash.user.api.client.washer.WasherService;

import agency.tango.android.avatarview.AvatarPlaceholder;

/**
 * Created by Amay on 12/29/2016.
 */

public class ApiUtils {


    public static AddressMapsFromGoogleApi getAddressMapsFromGoogleApi(Context context) {
        return RetrofitClient.getClient(context, Sample.BASE_URL_MAP).create(AddressMapsFromGoogleApi.class);
    }

    public static HistoryService getHistory(Context context) {
        return RetrofitClient.getClient(context, Sample.BASE_URL_QWASH).create(HistoryService.class);
    }

    public static NotificationService getNotification(Context context) {
        return RetrofitClient.getClient(context, Sample.BASE_URL_QWASH).create(NotificationService.class);
    }

    public static LoginService LoginService(Context context) {
        return RetrofitClient.getClient(context, Sample.BASE_URL_QWASH).create(LoginService.class);

    }

    public static RegisterService RegisterService(Context context) {
        return RetrofitClient.getClient(context, Sample.BASE_URL_QWASH).create(RegisterService.class);

    }

    public static OrderService OrderService(Context context) {
        return RetrofitClient.getClient(context, Sample.BASE_URL_QWASH).create(OrderService.class);

    }

    public static WasherService WasherService(Context context) {
        return RetrofitClient.getClient(context, Sample.BASE_URL_QWASH).create(WasherService.class);

    }

    public static VehicleService VehicleService(Context context) {
        return RetrofitClient.getClient(context, Sample.BASE_URL_QWASH).create(VehicleService.class);

    }

    public static String getImageAddressMapsFromGoogleApi(String LatLong) {
        return Sample.BASE_URL_MAP+"staticmap?center="+LatLong+"&zoom=8&scale=false&size=100x100&maptype=roadmap&format=png&visual_refresh=true&markers=size:mid%7Ccolor:0xff0000%7Clabel:%7C"+LatLong;
    }
}
