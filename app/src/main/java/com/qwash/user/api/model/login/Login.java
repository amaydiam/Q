package com.qwash.user.api.model.login; /**
 * Created by Amay on 12/29/2016.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.qwash.user.api.model.vehicle.DataVehicle;

import java.util.List;

public class Login {


    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("data")
    @Expose
    private DataLogin data;
    @SerializedName("address")
    @Expose
    private List<AddressLogin> address = null;
    @SerializedName("vehicles")
    @Expose
    private List<DataVehicle> vehicles = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public DataLogin getDataLogin() {
        return data;
    }

    public void setDataLogin(DataLogin data) {
        this.data = data;
    }

    public List<AddressLogin> getAddressLogin() {
        return address;
    }

    public void setAddressLogin(List<AddressLogin> address) {
        this.address = address;
    }

    public List<DataVehicle> getVehicle() {
        return vehicles;
    }

    public void setVehicle(List<DataVehicle> vehicles) {
        this.vehicles = vehicles;
    }

}

