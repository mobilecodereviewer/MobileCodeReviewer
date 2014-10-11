package pl.edu.agh.mobilecodereviewer.controllers.api;

import pl.edu.agh.mobilecodereviewer.app.MobileCodeReviewerApplication;
import pl.edu.agh.mobilecodereviewer.dao.gerrit.tools.ConfigurationInfo;
import pl.edu.agh.mobilecodereviewer.view.api.ConfigurationView;

public interface ConfigurationController {

    public void authenticate(MobileCodeReviewerApplication mobileCodeReviewerApplication, ConfigurationView view, ConfigurationInfo configurationInfo, boolean saveConfiguration, boolean authenticateUser);

    public void updateSavedConfigurations(ConfigurationView view);

    void removeAuthenticationConfiguration(ConfigurationView view, ConfigurationInfo configurationInfo);

    void removeAuthenticationConfigurationByName(ConfigurationView view, String authenticationInfo);
}
