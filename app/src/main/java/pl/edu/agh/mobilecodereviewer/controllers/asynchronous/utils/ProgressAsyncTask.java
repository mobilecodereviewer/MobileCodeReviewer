package pl.edu.agh.mobilecodereviewer.controllers.asynchronous.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class ProgressAsyncTask implements Runnable {
    private Context context;
    private Runnable task;

    public ProgressAsyncTask(Context activity,Runnable task) {
        this.context = activity;
        this.task = task;
    }


    @Override
    public void run() {
        final ProgressDialog dialog = ProgressDialog.show(context, "", "Please wait..");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                task.run();
                dialog.dismiss();
            }
        }).start();
    }
}
