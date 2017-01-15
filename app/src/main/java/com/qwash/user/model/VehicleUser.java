package com.qwash.user.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

/**
 * Created by Amay on 1/12/2017.
 */
public class VehicleUser extends SugarRecord implements Parcelable {
    public String vCustomersId;
    public String vName;
    public String vBrand;
    public String models;
    public String vTransmision;
    public String years;
    public String vId;
    public String vBrandId;
    public String vModelId;
    public String vTransId;
    public String vYearsId;

    public VehicleUser(){}

    public VehicleUser(String vCustomersId, String vName, String vBrand, String models, String vTransmision, String years, String vId, String vBrandId, String vModelId, String vTransId, String vYearsId) {
        this.vCustomersId = vCustomersId;
        this.vName = vName;
        this.vBrand = vBrand;
        this.models = models;
        this.vTransmision = vTransmision;
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
        vTransmision = in.readString();
        years = in.readString();
        vId = in.readString();
        vBrandId = in.readString();
        vModelId = in.readString();
        vTransId = in.readString();
        vYearsId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(vCustomersId);
        dest.writeString(vName);
        dest.writeString(vBrand);
        dest.writeString(models);
        dest.writeString(vTransmision);
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
}