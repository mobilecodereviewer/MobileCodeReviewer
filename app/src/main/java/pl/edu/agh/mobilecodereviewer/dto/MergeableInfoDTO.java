package pl.edu.agh.mobilecodereviewer.dto;

import com.google.gson.annotations.SerializedName;

public class MergeableInfoDTO {

    @SerializedName("submit_type")
    private String submitType;

    private boolean mergeable;

    public String getSubmitType() {
        return submitType;
    }

    public void setSubmitType(String submitType) {
        this.submitType = submitType;
    }

    public boolean isMergeable() {
        return mergeable;
    }

    public void setMergeable(boolean mergeable) {
        this.mergeable = mergeable;
    }
}
