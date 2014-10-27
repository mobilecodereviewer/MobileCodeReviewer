package pl.edu.agh.mobilecodereviewer.controllers;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import pl.edu.agh.mobilecodereviewer.app.MobileCodeReviewerApplication;
import pl.edu.agh.mobilecodereviewer.exceptions.handlers.UncaughtExceptionHandlerHelper;
import pl.edu.agh.mobilecodereviewer.utilities.PreferencesAccessor;
import pl.edu.agh.mobilecodereviewer.controllers.api.ConfigurationController;
import pl.edu.agh.mobilecodereviewer.utilities.ConfigurationInfo;
import pl.edu.agh.mobilecodereviewer.dao.gerrit.utilities.AsynchronousRestApi;
import pl.edu.agh.mobilecodereviewer.dao.gerrit.utilities.RestApi;
import pl.edu.agh.mobilecodereviewer.view.api.ConfigurationView;

public class ConfigurationControllerImpl implements ConfigurationController {

    public static enum ConfigurationError {INCORRECT_URL, INCORRECT_LOGIN_INFORMATION, GERRIT_VERSION_TO_LOW};

    private String retrievedVersion;

    @Override
    public void checkCrashReports(ConfigurationView view, boolean forceDontLoadLastSavedConfiguration) {
        if(UncaughtExceptionHandlerHelper.checkIfPendingReportsExist()) {
            view.showUnsentCrashReportInformation();
        }else {
            updateSavedConfigurationsOrUseLastSavedIfExists(view, forceDontLoadLastSavedConfiguration);
        }
    }



    @Override
    public void authenticate(MobileCodeReviewerApplication mobileCodeReviewerApplication, ConfigurationView view, ConfigurationInfo configurationInfo, boolean saveConfiguration, boolean authenticateUser) {

        if(!mobileCodeReviewerApplication.isNetworkAvailable()){
            view.showNoInternetConnectionInformation();
            return;
        }

        ConfigurationError errors = checkIfConfigurationCorrect(configurationInfo, authenticateUser);

        if(errors == null) {
            mobileCodeReviewerApplication.initialize(configurationInfo);

            if (saveConfiguration) {
                saveNewConfiguration(configurationInfo);
                view.addSavedConfiguration(configurationInfo);
            }
        } else if(authenticateUser && errors.equals(ConfigurationError.INCORRECT_LOGIN_INFORMATION)){
            view.showIncorrectLoginInformation();
            return;
        } else if(errors.equals(ConfigurationError.INCORRECT_URL)){
            view.showIncorrectUrlInformation();
            return;
        } else if(errors.equals(ConfigurationError.GERRIT_VERSION_TO_LOW)) {
            view.showIncorrectServerVersionInformation(retrievedVersion);
            return;
        }

        PreferencesAccessor.saveLastUsedConfiguration(configurationInfo);
        view.onAuthenticationSuccess();
    }


    private ConfigurationError checkIfConfigurationCorrect(ConfigurationInfo configurationInfo, boolean checkAuthentication){

        RestApi restApi = new AsynchronousRestApi(new RestApi(configurationInfo));

        retrievedVersion = restApi.getVersion();
        if(retrievedVersion == null) {
            return ConfigurationError.INCORRECT_URL;
        } else {
            String[] versionNumbers = retrievedVersion.split("\\.");
            String[] minimalVersionNumbers = MobileCodeReviewerApplication.MINIMAL_GERRIT_VERSION.split("\\.");

            if( Integer.parseInt(versionNumbers[0]) < Integer.parseInt(minimalVersionNumbers[0])
                    || ( Integer.parseInt(versionNumbers[0]) == Integer.parseInt(minimalVersionNumbers[0])
                        && Integer.parseInt(versionNumbers[1]) < Integer.parseInt(minimalVersionNumbers[1] )
                    )
               ){
                return ConfigurationError.GERRIT_VERSION_TO_LOW;
            }

        }

        if(checkAuthentication) {
            if(restApi.getAccountInfo() == null) {
                return ConfigurationError.INCORRECT_LOGIN_INFORMATION;
            }
        }

        return null;
    }

    @Override
    public void updateSavedConfigurationsOrUseLastSavedIfExists(ConfigurationView view, boolean forceDontLoadLastSaved) {
        ConfigurationInfo configurationInfo = PreferencesAccessor.getLastUsedConfiguration();
        if(configurationInfo != null && !forceDontLoadLastSaved){
            view.authenticateUsingSavedConfiguration(configurationInfo);
        } else {
            List<ConfigurationInfo> configurationInfoList = PreferencesAccessor.getConfigurations();
            view.showSavedConfigurations(configurationInfoList != null ? configurationInfoList : new LinkedList<ConfigurationInfo>());
        }
    }

    @Override
    public void removeAuthenticationConfiguration(ConfigurationView view, ConfigurationInfo configurationInfo) {
        List<ConfigurationInfo> configurationInfoList = PreferencesAccessor.getConfigurations();
        Iterator<ConfigurationInfo> iterator = configurationInfoList.iterator();
        while(iterator.hasNext()){
            ConfigurationInfo currentConfigurationInfo = iterator.next();
            if(currentConfigurationInfo.equals(configurationInfo)){
                iterator.remove();
                PreferencesAccessor.saveConfigurations(configurationInfoList);
                view.removeSavedConfiguration(configurationInfo);
                return;
            }
        }
    }

    @Override
    public void removeAuthenticationConfigurationByName(ConfigurationView view, String name) {
        List<ConfigurationInfo> configurationInfoList = PreferencesAccessor.getConfigurations();
        Iterator<ConfigurationInfo> iterator = configurationInfoList.iterator();
        while(iterator.hasNext()){
            ConfigurationInfo currentConfigurationInfo = iterator.next();
            if(name.equals(currentConfigurationInfo.getName())){
                iterator.remove();
                PreferencesAccessor.saveConfigurations(configurationInfoList);
                view.removeSavedConfiguration(currentConfigurationInfo);
                return;
            }
        }
    }

    private void saveNewConfiguration(ConfigurationInfo configurationInfo) {
        List<ConfigurationInfo> configurationInfoList = PreferencesAccessor.getConfigurations();
        if(configurationInfoList == null){
            configurationInfoList = new LinkedList<ConfigurationInfo>();
        }
        configurationInfoList.add(configurationInfo);

        PreferencesAccessor.saveConfigurations(configurationInfoList);
    }


}
