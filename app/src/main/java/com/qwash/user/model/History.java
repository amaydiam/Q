package com.qwash.user.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class History implements Parcelable {


    @SerializedName("orders_id")
    @Expose
    private String ordersId;
    @SerializedName("user_id_fk")
    @Expose
    private String userIdFk;
    @SerializedName("washer_id_fk")
    @Expose
    private String washerIdFk;
    @SerializedName("v_customers_id_fk")
    @Expose
    private String vCustomersIdFk;
    @SerializedName("create_at")
    @Expose
    private String createAt;
    @SerializedName("pick_date")
    @Expose
    private String pickDate;
    @SerializedName("pick_time")
    @Expose
    private String pickTime;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("name_address")
    @Expose
    private String nameAddress;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("perfumed")
    @Expose
    private String perfumed;
    @SerializedName("vacuum")
    @Expose
    private String vacuum;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("orders_ref")
    @Expose
    private String ordersRef;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("v_brand")
    @Expose
    private String vBrand;

    public History(String ordersId, String userIdFk, String washerIdFk, String vCustomersIdFk, String createAt, String pickDate, String pickTime, String lat, String lng, String nameAddress, String address, String price, String perfumed, String vacuum, String status, String description, String ordersRef, String name, String photo, String vBrand) {
        this.ordersId = ordersId;
        this.userIdFk = userIdFk;
        this.washerIdFk = washerIdFk;
        this.vCustomersIdFk = vCustomersIdFk;
        this.createAt = createAt;
        this.pickDate = pickDate;
        this.pickTime = pickTime;
        this.lat = lat;
        this.lng = lng;
        this.nameAddress = nameAddress;
        this.address = address;
        this.price = price;
        this.perfumed = perfumed;
        this.vacuum = vacuum;
        this.status = status;
        this.description = description;
        this.ordersRef = ordersRef;
        this.name = name;
        this.photo = photo;
        this.vBrand = vBrand;
    }

    protected History(Parcel in) {
        ordersId = in.readString();
        userIdFk = in.readString();
        washerIdFk = in.readString();
        vCustomersIdFk = in.readString();
        createAt = in.readString();
        pickDate = in.readString();
        pickTime = in.readString();
        lat = in.readString();
        lng = in.readString();
        nameAddress = in.readString();
        address = in.readString();
        price = in.readString();
        perfumed = in.readString();
        vacuum = in.readString();
        status = in.readString();
        description = in.readString();
        ordersRef = in.readString();
        name = in.readString();
        photo = in.readString();
        vBrand = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ordersId);
        dest.writeString(userIdFk);
        dest.writeString(washerIdFk);
        dest.writeString(vCustomersIdFk);
        dest.writeString(createAt);
        dest.writeString(pickDate);
        dest.writeString(pickTime);
        dest.writeString(lat);
        dest.writeString(lng);
        dest.writeString(nameAddress);
        dest.writeString(address);
        dest.writeString(price);
        dest.writeString(perfumed);
        dest.writeString(vacuum);
        dest.writeString(status);
        dest.writeString(description);
        dest.writeString(ordersRef);
        dest.writeString(name);
        dest.writeString(photo);
        dest.writeString(vBrand);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<History> CREATOR = new Creator<History>() {
        @Override
        public History createFromParcel(Parcel in) {
            return new History(in);
        }

        @Override
        public History[] newArray(int size) {
            return new History[size];
        }
    };

    public String getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(String ordersId) {
        this.ordersId = ordersId;
    }

    public String getUserIdFk() {
        return userIdFk;
    }

    public void setUserIdFk(String userIdFk) {
        this.userIdFk = userIdFk;
    }

    public String getWasherIdFk() {
        return washerIdFk;
    }

    public void setWasherIdFk(String washerIdFk) {
        this.washerIdFk = washerIdFk;
    }

    public String getVCustomersIdFk() {
        return vCustomersIdFk;
    }

    public void setVCustomersIdFk(String vCustomersIdFk) {
        this.vCustomersIdFk = vCustomersIdFk;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getPickDate() {
        return pickDate;
    }

    public void setPickDate(String pickDate) {
        this.pickDate = pickDate;
    }

    public String getPickTime() {
        return pickTime;
    }

    public void setPickTime(String pickTime) {
        this.pickTime = pickTime;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getNameAddress() {
        return nameAddress;
    }

    public void setNameAddress(String nameAddress) {
        this.nameAddress = nameAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPerfumed() {
        return perfumed;
    }

    public void setPerfumed(String perfumed) {
        this.perfumed = perfumed;
    }

    public String getVacuum() {
        return vacuum;
    }

    public void setVacuum(String vacuum) {
        this.vacuum = vacuum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrdersRef() {
        return ordersRef;
    }

    public void setOrdersRef(String ordersRef) {
        this.ordersRef = ordersRef;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getVBrand() {
        return vBrand;
    }

    public void setVBrand(String vBrand) {
        this.vBrand = vBrand;
    }

}