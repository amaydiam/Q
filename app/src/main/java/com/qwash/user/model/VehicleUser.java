package com.qwash.user.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

/**
 * Created by Amay on 1/12/2017.
 */
public class VehicleUser extends SugarRecord implements Parcelable {
    public static final Creator<VehicleUser> CREATOR = new Creator<VehicleUser>() {
        @Override
        public VehicleUser createFromParcel(Parcel in) {
            return new VehicleUser(in);
        }

        @Override
        public VehicleUser[] newArray(int size) {
            return new VehicleUser[size];
        }
    };
    private String vCustomersId;
    private String vName;
    private String vBrand;
    private String models;
    private String vTransmission;
    private String years;
    private String vId;
    private String vBrandId;
    private String vModelId;
    private String vTransId;
    private String vYearsId;

    public VehicleUser() {
    }

    public VehicleUser(String vCustomersId, String vName, String vBrand, String models, String vTransmission, String years, String vId, String vBrandId, String vModelId, String vTransId, String vYearsId) {
        this.vCustomersId = vCustomersId;
        this.vName = vName;
        this.vBrand = vBrand;
        this.models = models;
        this.vTransmission = vTransmission;
        this.years = years;
        this.vId = vId;
        this.vBrandId = vBrandId;
        this.vModelId = vModelId;
        this.vTransId = vTransId;
        this.vYearsId = vYearsId;
    }

    protected VehicleUser(Parcel in) {
        vCustomersId = in.readString();
        vName = in.readString();
        vBrand = in.readString();
        models = in.readString();
        vTransmission = in.readString();
        years = in.readString();
        vId = in.readString();
        vBrandId = in.readString();
        vModelId = in.readString();
        vTransId = in.readString();
        vYearsId = in.readString();
    }

    public String getvCustomersId() {
        return vCustomersId;
    }

    public void setvCustomersId(String vCustomersId) {
        this.vCustomersId = vCustomersId;
    }

    public String getvName() {
        return vName;
    }

    public void setvName(String vName) {
        this.vName = vName;
    }

    public String getvBrand() {
        return vBrand;
    }

    public void setvBrand(String vBrand) {
        this.vBrand = vBrand;
    }

    public String getModels() {
        return models;
    }

    public void setModels(String models) {
        this.models = models;
    }

    public String getvTransmission() {
        return vTransmission;
    }

    public void setvTransmission(String vTransmission) {
        this.vTransmission = vTransmission;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public String getvId() {
        return vId;
    }

    public void setvId(String vId) {
        this.vId = vId;
    }

    public String getvBrandId() {
        return vBrandId;
    }

    public void setvBrandId(String vBrandId) {
        this.vBrandId = vBrandId;
    }

    public String getvModelId() {
        return vModelId;
    }

    public void setvModelId(String vModelId) {
        this.vModelId = vModelId;
    }

    public String getvTransId() {
        return vTransId;
    }

    public void setvTransId(String vTransId) {
        this.vTransId = vTransId;
    }

    public String getvYearsId() {
        return vYearsId;
    }

    public void setvYearsId(String vYearsId) {
        this.vYearsId = vYearsId;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(vCustomersId);
        dest.writeString(vName);
        dest.writeString(vBrand);
        dest.writeString(models);
        dest.writeString(vTransmission);
        dest.writeString(years);
        dest.writeString(vId);
        dest.writeString(vBrandId);
        dest.writeString(vModelId);
        dest.writeString(vTransId);
        dest.writeString(vYearsId);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}