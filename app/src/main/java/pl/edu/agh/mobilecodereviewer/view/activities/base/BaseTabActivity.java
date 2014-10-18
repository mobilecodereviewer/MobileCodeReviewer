package pl.edu.agh.mobilecodereviewer.view.activities.base;

import android.os.Bundle;

import pl.edu.agh.mobilecodereviewer.exceptions.handlers.UncaughtExceptionHandler;
import roboguice.activity.RoboTabActivity;


public class BaseTabActivity extends RoboTabActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UncaughtExceptionHandler uncaughtExceptionHandler = new UncaughtExceptionHandler(this, Thread.getDefaultUncaughtExceptionHandler());
        Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);
    }
}
