package pl.edu.agh.mobilecodereviewer.utilities;

import com.google.inject.Inject;

import pl.edu.agh.mobilecodereviewer.dao.api.AccountDAO;
import pl.edu.agh.mobilecodereviewer.model.AccountInfo;

public class ConfigurationContainer {

    private ConfigurationInfo configurationInfo;

    private AccountInfo loggedUser;

    private static ConfigurationContainer ourInstance;

    public static ConfigurationContainer getInstance() {
        return ourInstance;
    }

    public ConfigurationContainer(ConfigurationInfo configurationInfo, AccountInfo accountInfo, boolean force) {
        if(ourInstance == null || force){
            this.configurationInfo = configurationInfo;
            loggedUser = accountInfo;
            ConfigurationContainer.ourInstance = this;
        }
    }

    public AccountInfo getLoggedUser(){
        return loggedUser;
    }

    public ConfigurationInfo getConfigurationInfo(){
        return configurationInfo;
    }
}
