package pl.edu.agh.mobilecodereviewer.controllers;

import android.app.Activity;
import android.app.Application;

import java.util.List;

import javax.inject.Inject;

import pl.edu.agh.mobilecodereviewer.controllers.changes.explorer.ChangesExplorerController;
import pl.edu.agh.mobilecodereviewer.dao.ChangeInfo;

/**
 * This is main controller class of application.
 */
public class MainApplicationController extends Application{

    @Inject
    ChangesExplorerController changesExplorerController;

    /*
     *  Field used to indicate current visible (resumed) activity.
     *  Set during activity's onResume() method.
     */
    private Activity currentActivity;

    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }

    public List<ChangeInfo> getChanges(){
        return changesExplorerController.getChangesList();
    }
}

