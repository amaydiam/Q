package com.qwash.user.api.model.vehicle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Amay on 1/12/2017.
 */
public class DataVehicleTransmissionList {

    @SerializedName("v_trans_id")
    @Expose
    private String vTransId;
    @SerializedName("v_model_id_fk")
    @Expose
    private String vModelIdFk;
    @SerializedName("v_transmission")
    @Expose
    private String vTransmission;

    public String getVTransId() {
        return vTransId;
    }

    public void setVTransId(String vTransId) {
        this.vTransId = vTransId;
    }

    public String getVModelIdFk() {
        return vModelIdFk;
    }

    public void setVModelIdFk(String vModelIdFk) {
        this.vModelIdFk = vModelIdFk;
    }

    public String getVTransmission() {
        return vTransmission;
    }

    public void setVTransmission(String vTransmission) {
        this.vTransmission = vTransmission;
    }

}