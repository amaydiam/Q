package com.qwash.user.model.vehicle;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

/**
 * Created by Amay on 1/12/2017.
 */
public class VehicleModel extends SugarRecord implements Parcelable {

    public static final Creator<VehicleModel> CREATOR = new Creator<VehicleModel>() {
        @Override
        public VehicleModel createFromParcel(Parcel in) {
            return new VehicleModel(in);
        }

        @Override
        public VehicleModel[] newArray(int size) {
            return new VehicleModel[size];
        }
    };
    private String vModelId;
    private String vBrandIdFk;
    private String models;

    public VehicleModel() {

    }

    public VehicleModel(String vModelId, String vBrandIdFk, String models) {
        this.vModelId = vModelId;
        this.vBrandIdFk = vBrandIdFk;
        this.models = models;
    }

    protected VehicleModel(Parcel in) {
        vModelId = in.readString();
        vBrandIdFk = in.readString();
        models = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(vModelId);
        dest.writeString(vBrandIdFk);
        dest.writeString(models);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}