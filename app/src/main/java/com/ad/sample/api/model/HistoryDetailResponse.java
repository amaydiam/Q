package com.ad.sample.api.model;

/**
 * Created by Amay on 12/29/2016.
 */


import com.ad.sample.model.History;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryDetailResponse {

    @SerializedName("isSuccess")
    @Expose
    private String isSuccess;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("history")
    @Expose
    private History history = null;

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


    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }


}
