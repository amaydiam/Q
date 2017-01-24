package com.qwash.user.model;

import java.io.Serializable;

/**
 * Created by Amay on 1/16/2017.
 */
public class WasherAccepted implements Serializable {

    public String firebase_id, userId, email, name, phone, photo, rating ;

    public WasherAccepted(String firebase_id, String userId, String email, String name, String phone, String photo, String rating) {
        this.firebase_id = firebase_id;
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.photo = photo;
        this.rating = rating;
    }
}
