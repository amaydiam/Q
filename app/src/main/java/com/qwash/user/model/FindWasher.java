package com.qwash.user.model; /**
 * Created by Amay on 12/29/2016.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FindWasher {

    public String userId;
    public String username;
    public String email;
    public String name;
    public String phone;
    public String city;
    public String authLevel;
    public String firebaseId;
    public boolean deacline;

    FindWasher(){

    }

    public FindWasher(String userId, String username, String email, String name, String phone, String city, String authLevel, String firebaseId, boolean deacline) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.city = city;
        this.authLevel = authLevel;
        this.firebaseId = firebaseId;
        this.deacline = deacline;
    }

}