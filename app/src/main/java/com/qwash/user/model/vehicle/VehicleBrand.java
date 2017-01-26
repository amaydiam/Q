package com.qwash.user.model.vehicle;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

/**
 * Created by Amay on 1/12/2017.
 */
public class VehicleBrand extends SugarRecord implements Parcelable {

    public static final Creator<VehicleBrand> CREATOR = new Creator<VehicleBrand>() {
        @Override
        public VehicleBrand createFromParcel(Parcel in) {
            return new VehicleBrand(in);
        }

        @Override
        public VehicleBrand[] newArray(int size) {
            return new VehicleBrand[size];
        }
    };
    public String vBrandId;
    public String vIdFk;
    public String vBrand;

    public VehicleBrand() {
    }

    public VehicleBrand(String vBrandId, String vIdFk, String vBrand) {
        this.vBrandId = vBrandId;
        this.vIdFk = vIdFk;
        this.vBrand = vBrand;
    }

    protected VehicleBrand(Parcel in) {
        vBrandId = in.readString();
        vIdFk = in.readString();
        vBrand = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(vBrandId);
        dest.writeString(vIdFk);
        dest.writeString(vBrand);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}