package com.qwash.user.api.model.washer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Amay on 1/23/2017.
 */

public class DataShowWasherOn {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id_fk")
    @Expose
    private String userIdFk;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("firebase_id")
    @Expose
    private String firebaseId;
    @SerializedName("distance")
    @Expose
    private String distance;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserIdFk() {
        return userIdFk;
    }

    public void setUserIdFk(String userIdFk) {
        this.userIdFk = userIdFk;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

}
