package com.qwash.user.api.model.vehicle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Amay on 1/12/2017.
 */
public class DataVehicleModelList {


    @SerializedName("v_model_id")
    @Expose
    private String vModelId;
    @SerializedName("v_brand_id_fk")
    @Expose
    private String vBrandIdFk;
    @SerializedName("models")
    @Expose
    private String models;

    public String getVModelId() {
        return vModelId;
    }

    public void setVModelId(String vModelId) {
        this.vModelId = vModelId;
    }

    public String getVBrandIdFk() {
        return vBrandIdFk;
    }

    public void setVBrandIdFk(String vBrandIdFk) {
        this.vBrandIdFk = vBrandIdFk;
    }

    public String getModels() {
        return models;
    }

    public void setModels(String models) {
        this.models = models;
    }

}