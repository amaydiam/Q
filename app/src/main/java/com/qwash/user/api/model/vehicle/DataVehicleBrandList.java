package com.qwash.user.api.model.vehicle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Amay on 1/12/2017.
 */
public class DataVehicleBrandList {

    @SerializedName("v_brand_id")
    @Expose
    private String vBrandId;
    @SerializedName("v_id_fk")
    @Expose
    private String vIdFk;
    @SerializedName("v_brand")
    @Expose
    private String vBrand;

    public String getVBrandId() {
        return vBrandId;
    }

    public void setVBrandId(String vBrandId) {
        this.vBrandId = vBrandId;
    }

    public String getVIdFk() {
        return vIdFk;
    }

    public void setVIdFk(String vIdFk) {
        this.vIdFk = vIdFk;
    }

    public String getVBrand() {
        return vBrand;
    }

    public void setVBrand(String vBrand) {
        this.vBrand = vBrand;
    }

}