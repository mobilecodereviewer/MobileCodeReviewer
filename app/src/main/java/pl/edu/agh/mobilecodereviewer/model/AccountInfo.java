package pl.edu.agh.mobilecodereviewer.model;

public class AccountInfo {

    private String accountId;

    private String name;

    private String email;

    private String username;

    public AccountInfo(){}

    public AccountInfo(String accountId, String name, String email, String username) {
        this.accountId = accountId;
        this.name = name;
        this.email = email;
        this.username = username;
    }

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

    @Override
    public String toString() {
        return "AccountInfo{" +
                "accountId='" + accountId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
