package com.example.karthick.imaggaapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultRe {
    @SerializedName("upload_id")
    @Expose
    private String uploadId;

    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }
}
