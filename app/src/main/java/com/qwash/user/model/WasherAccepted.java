package com.qwash.user.model;

import java.io.Serializable;

/**
 * Created by Amay on 1/16/2017.
 */
public class WasherAccepted implements Serializable {

    private String ordersId, firebase_id, userId, email, name, username, photo, rating;

    public WasherAccepted(String ordersId,String firebase_id, String userId, String email, String name, String username, String photo, String rating) {
        this.ordersId = ordersId;
        this.firebase_id = firebase_id;
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.username = username;
        this.photo = photo;
        this.rating = rating;
    }

    public String getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(String ordersId) {
        this.ordersId = ordersId;
    }

    public String getFirebase_id() {
        return firebase_id;
    }

    public void setFirebase_id(String firebase_id) {
        this.firebase_id = firebase_id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
