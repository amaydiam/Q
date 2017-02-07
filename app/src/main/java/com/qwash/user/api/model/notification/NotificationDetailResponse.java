package com.qwash.user.api.model.notification;

/**
 * Created by Amay on 12/29/2016.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.qwash.user.model.Notification;

public class NotificationDetailResponse {

    @SerializedName("isSuccess")
    @Expose
    private String isSuccess;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("notification")
    @Expose
    private Notification notification = null;

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }


}

