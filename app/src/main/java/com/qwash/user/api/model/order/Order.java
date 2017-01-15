package com.qwash.user.api.model.order; /**
 * Created by Amay on 12/29/2016.
 */


import com.qwash.user.api.model.login.AddressLogin;
import com.qwash.user.api.model.login.DataLogin;
import com.qwash.user.api.model.login.VehicleLogin;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Order {

    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("failure")
    @Expose
    private Integer failure;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getFailure() {
        return failure;
    }

    public void setFailure(Integer failure) {
        this.failure = failure;
    }

}