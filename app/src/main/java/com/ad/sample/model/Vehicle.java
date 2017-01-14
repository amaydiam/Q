package com.ad.sample.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Vehicle implements Parcelable {

    // Attributes
    public String vehicle_id;
    public int vehicle_type;
    public String vehicle_brand;
    public String vehicle_model;
    public String vehicle_transmission;
    public String vehicle_year;

    // Constructor
    public Vehicle(String id_vehicle, int type, String brand, String model, String transmission, String year) {
        this.vehicle_id = id_vehicle;
        this.vehicle_type = type;
        this.vehicle_brand = brand;
        this.vehicle_model = model;
        this.vehicle_transmission = transmission;
        this.vehicle_year = year;
    }

    public Vehicle(Parcel in) {
        this.vehicle_id = in.readString();
        this.vehicle_type = in.readInt();
        this.vehicle_brand = in.readString();
        this.vehicle_model = in.readString();
        this.vehicle_transmission = in.readString();
        this.vehicle_year = in.readString();
    }

    // Parcelable Creator
    public static final Creator CREATOR = new Creator() {
        public Vehicle createFromParcel(Parcel in) {
            return new Vehicle(in);
        }

        public Vehicle[] newArray(int size) {
            return new Vehicle[size];
        }
    };

    // Parcelling methods
    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(vehicle_id);
        out.writeInt(vehicle_type);
        out.writeString(vehicle_brand);
        out.writeString(vehicle_model);
        out.writeString(vehicle_transmission);
        out.writeString(vehicle_year);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
