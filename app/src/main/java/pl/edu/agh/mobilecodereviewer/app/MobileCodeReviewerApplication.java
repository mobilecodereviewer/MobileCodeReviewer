package pl.edu.agh.mobilecodereviewer.app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.inject.Inject;

import pl.edu.agh.mobilecodereviewer.model.AccountInfo;
import pl.edu.agh.mobilecodereviewer.utilities.ConfigurationContainer;
import pl.edu.agh.mobilecodereviewer.utilities.PreferencesAccessor;
import pl.edu.agh.mobilecodereviewer.configuration.InjectionModule;
import pl.edu.agh.mobilecodereviewer.dao.api.AccountDAO;
import pl.edu.agh.mobilecodereviewer.dao.api.ChangeInfoDAO;
import pl.edu.agh.mobilecodereviewer.dao.api.SourceCodeDAO;
import pl.edu.agh.mobilecodereviewer.utilities.ConfigurationInfo;
import pl.edu.agh.mobilecodereviewer.dao.gerrit.utilities.AsynchronousRestApi;
import pl.edu.agh.mobilecodereviewer.dao.gerrit.utilities.RestApi;
import roboguice.RoboGuice;

/**
 * MobileCodeReviewerApllication class is the main point for the whole application.
 * Its main purpose is to initialize some application configuration and supply
 * service method for all activities
 *
 * @author AGH
 * @version 0.1
 * @since 0.1
 */
public class MobileCodeReviewerApplication extends android.app.Application {

    public static final String MINIMAL_GERRIT_VERSION = "2.8";

    @Inject
    private ChangeInfoDAO changeInfoDAO;

    @Inject
    private SourceCodeDAO sourceCodeDAO;

    @Inject
    private AccountDAO accountDAO;

    private RestApi restApi;

    public void initialize(ConfigurationInfo configurationInfo) {
        this.restApi = new AsynchronousRestApi( new RestApi(configurationInfo) );

        changeInfoDAO.initialize(restApi);
        sourceCodeDAO.initialize(restApi);
        accountDAO.initialize(restApi);

        AccountInfo accountInfo = null;
        if(configurationInfo.isAuthenticatedUser()){
            accountInfo = accountDAO.getAccountInfo();
        }

        new ConfigurationContainer(configurationInfo, accountInfo, true);
    }

    public boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * Initialize application and configure dependency injection
     * framework
     */
    @Override
    public void onCreate() {
        super.onCreate();
        PreferencesAccessor.initialize(getApplicationContext());
        configureInjections();
    }

    /**
     * Configure dependency injection framework
     */
    private void configureInjections() {
        RoboGuice.setBaseApplicationInjector(this, RoboGuice.DEFAULT_STAGE,
                RoboGuice.newDefaultRoboModule(this), new InjectionModule());
    }
}

