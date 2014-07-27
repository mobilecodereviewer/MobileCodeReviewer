package pl.edu.agh.mobilecodereviewer.app;

import pl.edu.agh.mobilecodereviewer.configuration.InjectionModule;
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

    /**
     * Initialize application and configure dependency injection
     * framework
     */
    @Override
    public void onCreate() {
        super.onCreate();
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

