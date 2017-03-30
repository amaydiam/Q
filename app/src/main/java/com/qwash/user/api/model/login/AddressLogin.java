package com.qwash.user.api.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Amay on 1/12/2017.
 */
public class AddressLogin {

    @SerializedName("users_details_id")
    @Expose
    private String usersDetailsId;
    @SerializedName("user_id_fk")
    @Expose
    private String userIdFk;
    @SerializedName("name_address")
    @Expose
    private String nameAddress;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("lat")
    @Expose
    private String latlong;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("create_at")
    @Expose
    private String createAt;

    public String getUsersDetailsId() {
        return usersDetailsId;
    }

    public void setUsersDetailsId(String usersDetailsId) {
        this.usersDetailsId = usersDetailsId;
    }

    public String getUserIdFk() {
        return userIdFk;
    }

    public void setUserIdFk(String userIdFk) {
        this.userIdFk = userIdFk;
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

    public String getLatlong() {
        return latlong;
    }

    public void setLatlong(String latlong) {
        this.latlong = latlong;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

}
