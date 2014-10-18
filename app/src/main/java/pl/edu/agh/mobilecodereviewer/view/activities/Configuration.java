package pl.edu.agh.mobilecodereviewer.view.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.inject.Inject;

import java.io.File;
import java.util.List;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.app.MobileCodeReviewerApplication;
import pl.edu.agh.mobilecodereviewer.exceptions.handlers.UncaughtExceptionHandlerHelper;
import pl.edu.agh.mobilecodereviewer.controllers.api.ConfigurationController;
import pl.edu.agh.mobilecodereviewer.utilities.ConfigurationInfo;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.AboutDialogHelper;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.SavedConfigurationsCustomSpinner;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.SavedConfigurationsSpinnerAdapter;
import pl.edu.agh.mobilecodereviewer.view.api.ConfigurationView;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;

public class Configuration extends RoboActivity implements ConfigurationView {

    @Inject
    private ConfigurationController controller;

    @InjectView(R.id.savedConfigurationsSpinner)
    private SavedConfigurationsCustomSpinner savedConfigurationsSpinner;

    @InjectView(R.id.configName)
    private EditText configNameEdit;

    @InjectView(R.id.url)
    private EditText urlEdit;

    @InjectView(R.id.login)
    private EditText loginEdit;

    @InjectView(R.id.password)
    private EditText passwordEdit;

    @InjectView(R.id.saveConfiguration)
    private CheckBox saveConfigurationCheck;

    @InjectView(R.id.authenticateUser)
    private CheckBox authenticateUserCheck;

    @InjectView(R.id.applyButton)
    private Button applyButton;

    @InjectView(R.id.loginBox)
    private LinearLayout loginBox;

    @InjectView(R.id.showHidePasswordCheckbox)
    private CheckBox showHidePasswordCheckBox;

    @InjectView(R.id.crashReportLayout)
    private LinearLayout crashReportLayout;

    @InjectView(R.id.configurationLayout)
    private LinearLayout configurationLayout;

    @InjectView(R.id.sendCrashReportButton)
    private Button sendCrashReportButton;

    @InjectView(R.id.cancelCrashReportButton)
    private Button cancelCrashReportButton;

    @InjectResource(R.string.pl_agh_edu_mobilecodereviewer_Configuration_toast_giveName)
    private String giveNameToast;

    @InjectResource(R.string.pl_agh_edu_mobilecodereviewer_Configuration_toast_incorrectURL)
    private String incorrectUrlToast;

    @InjectResource(R.string.pl_agh_edu_mobilecodereviewer_Configuration_toast_incorrectLogin)
    private String incorrectLoginToast;

    @InjectResource(R.string.pl_agh_edu_mobilecodereviewer_Configuration_toast_overwrite)
    private String overwriteToast;

    @InjectResource(R.string.pl_agh_edu_mobilecodereviewer_Configuration_toast_wrongVersionNumber)
    private String gerritVersionToast;

    @InjectResource(R.string.pl_agh_edu_mobilecodereviewer_Configuration_toast_noInternetConnection)
    private String noInternetConnectionToast;

    @InjectResource(R.string.pl_agh_edu_mobilecodereviewer_Configuration_showPassword)
    private String showPasswordText;

    @InjectResource(R.string.pl_agh_edu_mobilecodereviewer_Configuration_hidePassword)
    private String hidePasswordText;

    SavedConfigurationsSpinnerAdapter spinnerAdapter;

    private List<ConfigurationInfo> savedConfigurationInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_configuration);

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyConfiguration(view);
            }
        });

        authenticateUserCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAuthenticationVisibility();
            }
        });

        showHidePasswordCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean showPassword) {
                if (showPassword) {
                    showPasswordText();
                } else {
                    hidePasswordText();
                }
            }
        });

        setAuthenticationVisibility();
        controller.checkCrashReports(this);
    }

    private void hidePasswordText() {
        showHidePasswordCheckBox.setText(showPasswordText);
        passwordEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    private void showPasswordText() {
        showHidePasswordCheckBox.setText(hidePasswordText);
        passwordEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.configuration, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_about) {
            AboutDialogHelper.showDialog(this);
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void addSavedConfiguration(ConfigurationInfo configurationInfo){
        savedConfigurationInfos.add(configurationInfo);
        spinnerAdapter.notifyDataSetChanged();
        setSpinnerVisibility();
    }

    @Override
    public void removeSavedConfiguration(ConfigurationInfo configurationInfo){
        savedConfigurationInfos.remove(configurationInfo);
        spinnerAdapter.notifyDataSetChanged();
        setSpinnerVisibility();
    }

    @Override
    public void showSavedConfigurations(List<ConfigurationInfo> savedConfigurationInfos) {

        crashReportLayout.setVisibility(View.GONE);
        configurationLayout.setVisibility(View.VISIBLE);

        this.savedConfigurationInfos = savedConfigurationInfos;

        spinnerAdapter = new SavedConfigurationsSpinnerAdapter(this, savedConfigurationInfos, controller);
        setSpinnerVisibility();

        savedConfigurationsSpinner.setAdapter(spinnerAdapter);
        savedConfigurationsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ConfigurationInfo selectedItem = (ConfigurationInfo) adapterView.getSelectedItem();

                loginEdit.setText(selectedItem.getLogin());
                configNameEdit.setText(selectedItem.getName());
                urlEdit.setText(selectedItem.getUrl());
                passwordEdit.setText(selectedItem.getPassword());
                authenticateUserCheck.setChecked(selectedItem.isAuthenticatedUser());
                setAuthenticationVisibility();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void showIncorrectUrlInformation() {
        showToast(incorrectUrlToast);
    }

    @Override
    public void showIncorrectLoginInformation() {
       showToast(incorrectLoginToast);
    }

    @Override
    public void showIncorrectServerVersionInformation(String currentVersion) {
        showToast(gerritVersionToast + " " + currentVersion);
    }

    @Override
    public void showNoInternetConnectionInformation() {
        showToast(noInternetConnectionToast);
    }

    @Override
    public void onAuthenticationSuccess() {
        Intent intent = new Intent(getApplicationContext(), ChangesExplorer.class);
        startActivity(intent);
    }

    @Override
    public void showUnsentCrashReportInformation() {

        crashReportLayout.setVisibility(View.VISIBLE);
        configurationLayout.setVisibility(View.GONE);

        UncaughtExceptionHandlerHelper.prepareFileToSend();

        sendCrashReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent(Intent.ACTION_SEND);

                sendIntent.setType("plain/text");
                sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{UncaughtExceptionHandlerHelper.CRASH_REPORT_EMAIL_ADDRESS});
                sendIntent.putExtra(Intent.EXTRA_TEXT, UncaughtExceptionHandlerHelper.CRASH_REPORT_EMAIL_BODY);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, UncaughtExceptionHandlerHelper.CRASH_REPORT_EMAIL_SUBJECT);
                sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(UncaughtExceptionHandlerHelper.getCrashLogFilePath())));

                sendIntent.setType("message/rfc822");
                startActivity(Intent.createChooser(sendIntent, UncaughtExceptionHandlerHelper.SEND_EMAIL_DIALOG_TITLE));

                controller.updateSavedConfigurations(Configuration.this);
            }
        });

        cancelCrashReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.updateSavedConfigurations(Configuration.this);
            }
        });

        removeCrashReportTmpFile();
    }

    private void removeCrashReportTmpFile(){
        File toDelete = new File(UncaughtExceptionHandlerHelper.getCrashLogTmpFilePath());
        toDelete.delete();
    }

    private void setAuthenticationVisibility(){
        if(!authenticateUserCheck.isChecked()){
            loginBox.setVisibility(View.GONE);
        } else {
            loginBox.setVisibility(View.VISIBLE);
        }
    }

    private void setSpinnerVisibility(){
        if(spinnerAdapter.getCount() == 0){
            savedConfigurationsSpinner.setVisibility(View.GONE);
        } else {
            savedConfigurationsSpinner.setVisibility(View.VISIBLE);
        }
    }

    private void showToast(String message){
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
    }

    public void applyConfiguration(View v){
        String name = configNameEdit.getText().toString();
        String url = urlEdit.getText().toString();
        String login = loginEdit.getText().toString();
        String password = passwordEdit.getText().toString();
        boolean saveConfiguration = saveConfigurationCheck.isChecked();
        boolean authenticateUser = authenticateUserCheck.isChecked();

        ConfigurationInfo configurationInfo = new ConfigurationInfo(name, url, authenticateUser ? login : null, authenticateUser ? password : null, authenticateUser);

        if(name == null || name.isEmpty()){
            showToast(giveNameToast);
        }else if(saveConfiguration && configWithGivenNameExists(name)){
            checkIfOverwriteConfiguration(configurationInfo, authenticateUser);
        } else{
            controller.authenticate((MobileCodeReviewerApplication) getApplication(), this, configurationInfo, saveConfiguration, authenticateUser);
        }
    }

    private void checkIfOverwriteConfiguration(final ConfigurationInfo configurationInfo, final boolean authenticateUser){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        overwriteConfiguration(configurationInfo, authenticateUser);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.cancel();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(overwriteToast)
                .setPositiveButton(R.string.pl_agh_edu_common_ok, dialogClickListener)
                .setNegativeButton(R.string.pl_agh_edu_common_cancel, dialogClickListener)
                .show();
    }

    private void overwriteConfiguration(ConfigurationInfo configurationInfo, boolean authenticateUser){
        controller.removeAuthenticationConfigurationByName(this, configurationInfo.getName());
        controller.authenticate((MobileCodeReviewerApplication) getApplication(), this, configurationInfo, true, authenticateUser);
    }

    private boolean configWithGivenNameExists(String name){
        for(ConfigurationInfo configurationInfo : savedConfigurationInfos){
            if(name.equals(configurationInfo.getName())){
                return true;
            }
        }

        return false;
    }

}
