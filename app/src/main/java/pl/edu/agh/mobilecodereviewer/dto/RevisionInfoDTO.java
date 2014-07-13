package pl.edu.agh.mobilecodereviewer.dto;


import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class RevisionInfoDTO {

    @SerializedName("_number")
    private String number;

    private Map<String, FileInfoDTO> files;

    private CommitInfoDTO commit;

    public RevisionInfoDTO() {
    }

    public RevisionInfoDTO(String number, Map<String, FileInfoDTO> files) {
        this.number = number;
        this.files = files;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Map<String, FileInfoDTO> getFiles() {
        return files;
    }

    public void setFiles(Map<String, FileInfoDTO> files) {
        this.files = files;
    }

    public CommitInfoDTO getCommit() {
        return commit;
    }

    public void setCommit(CommitInfoDTO commit) {
        this.commit = commit;
    }
}
