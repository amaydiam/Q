package com.qwash.user.api.model.vehicle; /**
 * Created by Amay on 12/29/2016.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegisterVehicle {


    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Boolean data;
    @SerializedName("vehicles")
    @Expose
    private List<DataVehicle> vehicles = null;

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

    public Boolean getData() {
        return data;
    }

    public void setData(Boolean data) {
        this.data = data;
    }

    public List<DataVehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<DataVehicle> vehicles) {
        this.vehicles = vehicles;
    }

}