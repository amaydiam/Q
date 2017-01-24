package com.qwash.user.api.model.order; /**
 * Created by Amay on 12/29/2016.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Washer {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("user_id_fk")
    @Expose
    private String userIdFk;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("long")
    @Expose
    private String _long;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("firebase_id")
    @Expose
    private String firebaseId;

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

    public String getLong() {
        return _long;
    }

    public void setLong(String _long) {
        this._long = _long;
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

}