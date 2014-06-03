package pl.edu.agh.mobilecodereviewer.activities;

import android.app.Activity;
import android.os.Bundle;

import dagger.ObjectGraph;
import pl.edu.agh.mobilecodereviewer.controllers.MainApplicationController;
import pl.edu.agh.mobilecodereviewer.modules.ControllersModule;

public class BaseActivity extends Activity {

    @Override
    protected void onResume(){
        super.onResume();
        setAsActive();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ObjectGraph daggerGraph = ObjectGraph.create(new ControllersModule());
        daggerGraph.validate();
        daggerGraph.inject(getAppContext());
    }

    protected void setAsActive(){
       getAppContext().setCurrentActivity(this);
    }

    protected MainApplicationController getAppContext(){
        return (MainApplicationController) getApplicationContext();
    }


}
