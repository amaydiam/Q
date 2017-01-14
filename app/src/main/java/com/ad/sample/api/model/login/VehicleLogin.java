package com.ad.sample.api.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Amay on 1/12/2017.
 */
public class VehicleLogin {

    @SerializedName("v_customers_id")
    @Expose
    private String vCustomersId;
    @SerializedName("v_name")
    @Expose
    private String vName;
    @SerializedName("v_brand")
    @Expose
    private String vBrand;
    @SerializedName("models")
    @Expose
    private String models;
    @SerializedName("v_transmision")
    @Expose
    private String vTransmision;
    @SerializedName("v_id")
    @Expose
    private String vId;
    @SerializedName("v_brand_id")
    @Expose
    private String vBrandId;
    @SerializedName("v_model_id")
    @Expose
    private String vModelId;
    @SerializedName("v_trans_id")
    @Expose
    private String vTransId;

    public String getVCustomersId() {
        return vCustomersId;
    }

    public void setVCustomersId(String vCustomersId) {
        this.vCustomersId = vCustomersId;
    }

    public String getVName() {
        return vName;
    }

    public void setVName(String vName) {
        this.vName = vName;
    }

    public String getVBrand() {
        return vBrand;
    }

    public void setVBrand(String vBrand) {
        this.vBrand = vBrand;
    }

    public String getModels() {
        return models;
    }

    public void setModels(String models) {
        this.models = models;
    }

    public String getVTransmision() {
        return vTransmision;
    }

    public void setVTransmision(String vTransmision) {
        this.vTransmision = vTransmision;
    }

    public String getVId() {
        return vId;
    }

    public void setVId(String vId) {
        this.vId = vId;
    }

    public String getVBrandId() {
        return vBrandId;
    }

    public void setVBrandId(String vBrandId) {
        this.vBrandId = vBrandId;
    }

    public String getVModelId() {
        return vModelId;
    }

    public void setVModelId(String vModelId) {
        this.vModelId = vModelId;
    }

    public String getVTransId() {
        return vTransId;
    }

    public void setVTransId(String vTransId) {
        this.vTransId = vTransId;
    }

}