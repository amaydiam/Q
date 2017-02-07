package com.qwash.user.model.vehicle;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

public class VehicleYear implements Parcelable {

    private String vYearsId;
    private String vTransIdFk;
    private String years;

    public VehicleYear() {

    }


    public VehicleYear(String vYearsId, String vTransIdFk, String years) {
        this.vYearsId = vYearsId;
        this.vTransIdFk = vTransIdFk;
        this.years = years;
    }

    public String getvYearsId() {
        return vYearsId;
    }

    public void setvYearsId(String vYearsId) {
        this.vYearsId = vYearsId;
    }

    public String getvTransIdFk() {
        return vTransIdFk;
    }

    public void setvTransIdFk(String vTransIdFk) {
        this.vTransIdFk = vTransIdFk;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    private VehicleYear(Parcel in) {
        vYearsId = in.readString();
        vTransIdFk = in.readString();
        years = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(vYearsId);
        dest.writeString(vTransIdFk);
        dest.writeString(years);
    }
    public static final Creator<VehicleYear> CREATOR = new Creator<VehicleYear>() {
        @Override
        public VehicleYear createFromParcel(Parcel in) {
            return new VehicleYear(in);
        }

        @Override
        public VehicleYear[] newArray(int size) {
            return new VehicleYear[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }
}