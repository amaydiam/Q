package com.qwash.user.api.model.history;

/**
 * Created by Amay on 12/29/2016.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.qwash.user.model.History;

import java.util.List;

public class HistoryListResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("history")
    @Expose
    private List<History> history = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<History> getHistory() {
        return history;
    }

    public void setHistory(List<History> history) {
        this.history = history;
    }

}

