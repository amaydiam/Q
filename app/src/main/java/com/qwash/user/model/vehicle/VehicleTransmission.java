package com.qwash.user.model.vehicle;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

/**
 * Created by Amay on 1/12/2017.
 */
public class VehicleTransmission extends SugarRecord implements Parcelable {

    public static final Creator<VehicleTransmission> CREATOR = new Creator<VehicleTransmission>() {
        @Override
        public VehicleTransmission createFromParcel(Parcel in) {
            return new VehicleTransmission(in);
        }

        @Override
        public VehicleTransmission[] newArray(int size) {
            return new VehicleTransmission[size];
        }
    };
    private String vTransId;
    private String vModelIdFk;
    private String vTransmission;

    public VehicleTransmission() {

    }

    public VehicleTransmission(String vTransId, String vModelIdFk, String vTransmission) {
        this.vTransId = vTransId;
        this.vModelIdFk = vModelIdFk;
        this.vTransmission = vTransmission;
    }

    protected VehicleTransmission(Parcel in) {
        vTransId = in.readString();
        vModelIdFk = in.readString();
        vTransmission = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(vTransId);
        dest.writeString(vModelIdFk);
        dest.writeString(vTransmission);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}