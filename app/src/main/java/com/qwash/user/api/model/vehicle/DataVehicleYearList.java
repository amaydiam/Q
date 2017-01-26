package com.qwash.user.api.model.vehicle;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Amay on 1/12/2017.
 */
public class DataVehicleYearList {

    @SerializedName("v_years_id")
    @Expose
    private String vYearsId;
    @SerializedName("v_trans_id_fk")
    @Expose
    private String vTransIdFk;
    @SerializedName("years")
    @Expose
    private String years;

    public String getVYearsId() {
        return vYearsId;
    }

    public void setVYearsId(String vYearsId) {
        this.vYearsId = vYearsId;
    }

    public String getVTransIdFk() {
        return vTransIdFk;
    }

    public void setVTransIdFk(String vTransIdFk) {
        this.vTransIdFk = vTransIdFk;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

}