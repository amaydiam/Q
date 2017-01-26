package com.qwash.user.api.model.login;

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
    @SerializedName("v_transmission")
    @Expose
    private String vTransmission;
    @SerializedName("years")
    @Expose
    private String years;
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
    @SerializedName("v_years_id")
    @Expose
    private String vYearsId;

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

    public String getVTransmission() {
        return vTransmission;
    }

    public void setVTransmission(String vTransmission) {
        this.vTransmission = vTransmission;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
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

    public String getVYearsId() {
        return vYearsId;
    }

    public void setVYearsId(String vYearsId) {
        this.vYearsId = vYearsId;
    }

}