package pl.edu.agh.mobilecodereviewer.dto;

import com.google.gson.annotations.SerializedName;

public class FileInfoDTO {

    private String status;

    @SerializedName("lines_inserted")
    private int linesInserted;

    public FileInfoDTO() {
    }

    public FileInfoDTO(String status, int linesInserted) {
        this.status = status;
        this.linesInserted = linesInserted;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getLinesInserted() {
        return linesInserted;
    }

    public void setLinesInserted(int linesInserted) {
        this.linesInserted = linesInserted;
    }
}
