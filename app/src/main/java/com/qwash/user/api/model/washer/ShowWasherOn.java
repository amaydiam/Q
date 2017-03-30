package com.qwash.user.api.model.washer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.qwash.user.api.model.order.Washer;

import java.util.List;

/**
 * Created by Amay on 1/23/2017.
 */

public class ShowWasherOn {
    @SerializedName("washers")
    @Expose
    private List<Washer> washers = null;

    public List<Washer> getWashers() {
        return washers;
    }

    public void setWashers(List<Washer> washers) {
        this.washers = washers;
    }

}