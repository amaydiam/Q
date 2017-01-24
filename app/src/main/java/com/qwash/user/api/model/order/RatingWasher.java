package com.qwash.user.api.model.order; /**
 * Created by Amay on 12/29/2016.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.qwash.user.api.model.login.AddressLogin;
import com.qwash.user.api.model.login.DataLogin;
import com.qwash.user.api.model.login.VehicleLogin;
import com.qwash.user.api.model.register.DataRegister;

import java.util.List;

public class RatingWasher {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}

