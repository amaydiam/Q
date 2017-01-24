package com.qwash.user.api.model.washer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Amay on 1/23/2017.
 */

public class ShowWasherOn {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<DataShowWasherOn> data = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataShowWasherOn> getData() {
        return data;
    }

    public void setData(List<DataShowWasherOn> data) {
        this.data = data;
    }

}