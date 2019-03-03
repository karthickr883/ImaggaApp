package com.example.karthick.imaggaapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImgResponse {
    @SerializedName("result")
    @Expose
    private ResultRe result;
    @SerializedName("status")
    @Expose
    private StatusRe status;

    public ResultRe getResult() {
        return result;
    }

    public void setResultRe(ResultRe result) {
        this.result = result;
    }

    public StatusRe getStatusRe() {
        return status;
    }

    public void setStatusRe(StatusRe status) {
        this.status = status;
    }

}
