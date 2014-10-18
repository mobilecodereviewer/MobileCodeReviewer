package pl.edu.agh.mobilecodereviewer.view.activities.base;


import android.os.Bundle;

import pl.edu.agh.mobilecodereviewer.exceptions.handlers.UncaughtExceptionHandler;
import roboguice.activity.RoboActivity;

public class BaseActivity extends RoboActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UncaughtExceptionHandler uncaughtExceptionHandler = new UncaughtExceptionHandler(this, Thread.getDefaultUncaughtExceptionHandler());
        Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);
    }

}
