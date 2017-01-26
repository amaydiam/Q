package com.qwash.user.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;

/**
 * Created by Amay on 1/12/2017.
 */
public class AddressUser extends SugarRecord implements Parcelable {

    public static final Creator<AddressUser> CREATOR = new Creator<AddressUser>() {
        @Override
        public AddressUser createFromParcel(Parcel in) {
            return new AddressUser(in);
        }

        @Override
        public AddressUser[] newArray(int size) {
            return new AddressUser[size];
        }
    };
    public String usersDetailsId;
    public String userIdFk;
    public String nameAddress;
    public String address;
    public String latlong;
    public String type;
    public String createAt;

    public AddressUser() {
    }

    public AddressUser(String usersDetailsId, String userIdFk, String nameAddress, String address, String latlong, String type, String createAt) {
        this.usersDetailsId = usersDetailsId;
        this.userIdFk = userIdFk;
        this.nameAddress = nameAddress;
        this.address = address;
        this.latlong = latlong;
        this.type = type;
        this.createAt = createAt;
    }

    protected AddressUser(Parcel in) {
        usersDetailsId = in.readString();
        userIdFk = in.readString();
        nameAddress = in.readString();
        address = in.readString();
        latlong = in.readString();
        type = in.readString();
        createAt = in.readString();
    }

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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(usersDetailsId);
        dest.writeString(userIdFk);
        dest.writeString(nameAddress);
        dest.writeString(address);
        dest.writeString(latlong);
        dest.writeString(type);
        dest.writeString(createAt);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
