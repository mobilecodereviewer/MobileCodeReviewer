package pl.edu.agh.mobilecodereviewer.dto;


import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class RevisionInfoDTO {

    @SerializedName("_number")
    private String number;

    private Map<String, FileInfoDTO> files;

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
}
