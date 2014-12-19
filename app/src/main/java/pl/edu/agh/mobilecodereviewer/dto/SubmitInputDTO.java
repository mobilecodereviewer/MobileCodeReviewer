package pl.edu.agh.mobilecodereviewer.dto;

import com.google.gson.annotations.SerializedName;

public class SubmitInputDTO {

    @SerializedName("wait_for_merge")
    private boolean waitForMerge;

    public SubmitInputDTO(boolean waitForMerge) {
        this.waitForMerge = waitForMerge;
    }

    public boolean isWaitForMerge() {
        return waitForMerge;
    }

    public void setWaitForMerge(boolean waitForMerge) {
        this.waitForMerge = waitForMerge;
    }
}