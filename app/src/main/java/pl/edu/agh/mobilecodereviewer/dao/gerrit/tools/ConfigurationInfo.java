package pl.edu.agh.mobilecodereviewer.dao.gerrit.tools;

import java.io.Serializable;

public class ConfigurationInfo implements Serializable{

    private String name;

    private String url;

    private String login;

    private String password;

    private boolean authenticatedUser;

    public ConfigurationInfo(){}

    public ConfigurationInfo(String name, String url, String login, String password, boolean authenticatedUser){
        this.name = name;
        this.login = login;
        this.url = url;
        this.password = password;
        this.authenticatedUser = authenticatedUser;
    }

    public String getName(){
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAuthenticatedUser() {
        return authenticatedUser;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConfigurationInfo that = (ConfigurationInfo) o;

        if (authenticatedUser != that.authenticatedUser) return false;
        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null)
            return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (authenticatedUser ? 1 : 0);
        return result;
    }
}
