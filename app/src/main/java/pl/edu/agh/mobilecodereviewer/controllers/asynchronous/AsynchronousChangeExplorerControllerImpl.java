package pl.edu.agh.mobilecodereviewer.controllers.asynchronous;

import android.content.Context;
import android.os.AsyncTask;

import pl.edu.agh.mobilecodereviewer.controllers.api.ChangesExplorerController;
import pl.edu.agh.mobilecodereviewer.controllers.asynchronous.utils.ProgressAsyncTask;
import pl.edu.agh.mobilecodereviewer.model.ChangeStatus;
import pl.edu.agh.mobilecodereviewer.view.api.ChangesExplorerView;


public class AsynchronousChangeExplorerControllerImpl implements ChangesExplorerController{

    private final ChangesExplorerController controller;
    private final Context context;

    public AsynchronousChangeExplorerControllerImpl(Context context,ChangesExplorerController changesExplorerController) {
        this.controller = changesExplorerController;
        this.context = context;
    }


    @Override
    public void initializeData(final ChangesExplorerView changesExplorerView) {
        new ProgressAsyncTask(context,new Runnable() {
            @Override
            public void run() {
                controller.initializeData(changesExplorerView);
            }
        }).run();
    }

    @Override
    public void updateChanges() {
        new ProgressAsyncTask(context,new Runnable() {
            @Override
            public void run() {
                controller.updateChanges();
            }
        }).run();
    }

    @Override
    public void search(final String query) {
        new ProgressAsyncTask(context,new Runnable() {
            @Override
            public void run() {
                controller.search(query);
            }
        }).run();
    }

    @Override
    public void chooseStatus() {
        new ProgressAsyncTask(context,new Runnable() {
            @Override
            public void run() {
                controller.chooseStatus();
            }
        }).run();
    }

    @Override
    public void changeStatus(final ChangeStatus status) {
        new ProgressAsyncTask(context,new Runnable() {
            @Override
            public void run() {
                controller.changeStatus(status);
            }
        }).run();
    }

    @Override
    public void refreshChanges() {
        new ProgressAsyncTask(context,new Runnable() {
            @Override
            public void run() {
                controller.refreshChanges();
            }
        }).run();
    }
}
