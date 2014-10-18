package pl.edu.agh.mobilecodereviewer.view.api;

import java.util.List;

import pl.edu.agh.mobilecodereviewer.utilities.ConfigurationInfo;

public interface ConfigurationView {

    void addSavedConfiguration(ConfigurationInfo configurationInfo);

    void removeSavedConfiguration(ConfigurationInfo configurationInfo);

    void showSavedConfigurations(List<ConfigurationInfo> configurationInfos);

    void showIncorrectUrlInformation();

    void showIncorrectLoginInformation();

    void showIncorrectServerVersionInformation(String currentVersion);

    void showNoInternetConnectionInformation();

    void onAuthenticationSuccess();

    void showUnsentCrashReportInformation();
}
