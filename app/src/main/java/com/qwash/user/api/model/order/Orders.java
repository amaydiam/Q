package com.qwash.user.api.model.order; /**
 * Created by Amay on 12/29/2016.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Orders {

    @SerializedName("user_id_fk")
    @Expose
    private String userIdFk;
    @SerializedName("washer_id_fk")
    @Expose
    private Object washerIdFk;
    @SerializedName("v_customers_id_fk")
    @Expose
    private String vCustomersIdFk;
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
    @SerializedName("orders_ref")
    @Expose
    private String ordersRef;

    public String getUserIdFk() {
        return userIdFk;
    }

    public void setUserIdFk(String userIdFk) {
        this.userIdFk = userIdFk;
    }

    public Object getWasherIdFk() {
        return washerIdFk;
    }

    public void setWasherIdFk(Object washerIdFk) {
        this.washerIdFk = washerIdFk;
    }

    public String getVCustomersIdFk() {
        return vCustomersIdFk;
    }

    public void setVCustomersIdFk(String vCustomersIdFk) {
        this.vCustomersIdFk = vCustomersIdFk;
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

    public String getOrdersRef() {
        return ordersRef;
    }

    public void setOrdersRef(String ordersRef) {
        this.ordersRef = ordersRef;
    }

}