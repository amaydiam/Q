package com.qwash.user.model;

import java.io.Serializable;

/**
 * Created by Amay on 1/16/2017.
 */
public class WasherAccepted implements Serializable {

    public String userId,
            name,
            email,
            phone,
            firebase_id,
            rating,
            datetime,
            estimated_price;

    public WasherAccepted(String firebase_id, String userId, String email, String name, String phone, String rating, String datetime, String estimated_price) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.firebase_id = firebase_id;
        this.rating = rating;
        this.datetime = datetime;
        this.estimated_price = estimated_price;
    }

}
