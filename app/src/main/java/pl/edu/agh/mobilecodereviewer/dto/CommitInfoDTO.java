package pl.edu.agh.mobilecodereviewer.dto;


import com.google.gson.annotations.SerializedName;

public class CommitInfoDTO {

    @SerializedName("commit")
    private String commitId;

    private String message;

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
