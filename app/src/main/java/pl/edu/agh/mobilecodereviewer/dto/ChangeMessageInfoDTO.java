package pl.edu.agh.mobilecodereviewer.dto;

import com.google.gson.annotations.SerializedName;

public class ChangeMessageInfoDTO {

    private String id;

    private AccountInfoDTO author;

    private String date;

    private String message;

    @SerializedName("_revision_number")
    private String revisionNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AccountInfoDTO getAuthor() {
        return author;
    }

    public void setAuthor(AccountInfoDTO author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRevisionNumber() {
        return revisionNumber;
    }

    public void setRevisionNumber(String revisionNumber) {
        this.revisionNumber = revisionNumber;
    }
}
