package com.qwash.user.api.client.notification;

/**
 * Created by Amay on 12/29/2016.
 */


import com.qwash.user.api.model.notification.NotificationDetailResponse;
import com.qwash.user.api.model.notification.NotificationListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NotificationService {
    //  @GET("history")
    @GET("notifications/getNotifications/{page}/{limit}")
    Call<NotificationListResponse> getListNotification( @Path("page") int page, @Path("limit") String limit);


    // @GET("history")
    @GET("1bom2v")
    Call<NotificationDetailResponse> getDetailNotification(@Query("id") String id);
}