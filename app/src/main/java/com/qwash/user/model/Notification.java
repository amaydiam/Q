package com.qwash.user.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.Html;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.qwash.user.Sample;
import com.qwash.user.utils.TextUtils;

public class Notification implements Parcelable {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("priority")
    @Expose
    private Integer priority;
    @SerializedName("messages")
    @Expose
    private String messages;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    protected Notification(Parcel in) {
        title = in.readString();
        image = in.readString();
        messages = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(messages);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return Html.fromHtml(title).toString();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return Sample.BASE_URL_QWASH_IMAGE_NOTIFICATION +image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getMessages() {
        return Html.fromHtml(messages).toString();
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public String getCreatedAt() {
        return TextUtils.DefaultDateFormat(createdAt);
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }


}
