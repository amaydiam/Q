package com.qwash.user.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notification implements Parcelable {


    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("create_at")
    @Expose
    private String createAt;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("cover")
    @Expose
    private String cover;
    @SerializedName("description")
    @Expose
    private String description;

    public Notification(String id, String createAt, String type, String title, String cover, String description) {
        this.id = id;
        this.createAt = createAt;
        this.type = type;
        this.title = title;
        this.cover = cover;
        this.description = description;
    }

    protected Notification(Parcel in) {
        id = in.readString();
        createAt = in.readString();
        type = in.readString();
        title = in.readString();
        cover = in.readString();
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(createAt);
        dest.writeString(type);
        dest.writeString(title);
        dest.writeString(cover);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Notification> CREATOR = new Creator<Notification>() {
        @Override
        public Notification createFromParcel(Parcel in) {
            return new Notification(in);
        }

        @Override
        public Notification[] newArray(int size) {
            return new Notification[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
