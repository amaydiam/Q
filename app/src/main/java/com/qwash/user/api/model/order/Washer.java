package com.qwash.user.api.model.order; /**
 * Created by Amay on 12/29/2016.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Washer {

    @SerializedName("customersId")
    @Expose
    private String userId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("firebaseId")
    @Expose
    private String firebaseId;
    @SerializedName("saldo")
    @Expose
    private Integer saldo;
    @SerializedName("profilePhoto")
    @Expose
    private Object profilePhoto;
    @SerializedName("geometryLat")
    @Expose
    private Double geometryLat;
    @SerializedName("geometryLong")
    @Expose
    private Double geometryLong;
    @SerializedName("online")
    @Expose
    private Integer online;
    @SerializedName("distance")
    @Expose
    private Double distance;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }

    public Integer getSaldo() {
        return saldo;
    }

    public void setSaldo(Integer saldo) {
        this.saldo = saldo;
    }

    public Object getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(Object profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public Double getGeometryLat() {
        return geometryLat;
    }

    public void setGeometryLat(Double geometryLat) {
        this.geometryLat = geometryLat;
    }

    public Double getGeometryLong() {
        return geometryLong;
    }

    public void setGeometryLong(Double geometryLong) {
        this.geometryLong = geometryLong;
    }

    public Integer getOnline() {
        return online;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

}