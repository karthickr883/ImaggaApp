package com.example.karthick.imaggaapi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tag {

    @SerializedName("confidence")
    @Expose
    private Double confidence;
    @SerializedName("tag")
    @Expose
    private Tag_ tag;

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    public Tag_ getTag() {
        return tag;
    }

    public void setTag(Tag_ tag) {
        this.tag = tag;
    }

}