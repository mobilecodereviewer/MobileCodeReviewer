package pl.edu.agh.mobilecodereviewer.dto;

import com.google.gson.annotations.SerializedName;

public class AccountInfoDTO {

    @SerializedName("_account_id")
    private String accountId;

    private String name;

    private String email;

    private String username;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
