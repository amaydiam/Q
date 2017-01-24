package com.qwash.user.api.model.order; /**
 * Created by Amay on 12/29/2016.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RequestNewOrder {


    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("orders")
    @Expose
    private String orders;
    @SerializedName("washers")
    @Expose
    private List<Washer> washers = null;

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

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public List<Washer> getWashers() {
        return washers;
    }

    public void setWashers(List<Washer> washers) {
        this.washers = washers;
    }

}
