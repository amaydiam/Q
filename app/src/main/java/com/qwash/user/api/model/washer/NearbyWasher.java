package com.qwash.user.api.model.washer; /**
 * Created by Amay on 12/29/2016.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NearbyWasher {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("washers")
    @Expose
    private List<DataNearbyWasher> washers = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<DataNearbyWasher> getWashers() {
        return washers;
    }

    public void setWashers(List<DataNearbyWasher> washers) {
        this.washers = washers;
    }

}