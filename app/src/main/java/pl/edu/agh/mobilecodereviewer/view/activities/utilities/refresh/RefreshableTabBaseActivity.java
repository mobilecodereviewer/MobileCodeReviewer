package pl.edu.agh.mobilecodereviewer.view.activities.utilities.refresh;

import android.os.Bundle;

import pl.edu.agh.mobilecodereviewer.view.activities.base.BaseActivity;


public class RefreshableTabBaseActivity extends BaseActivity implements Refreshable{
    private RefreshUpdater refreshUpdater;
    private Refreshable controller;

    protected void onCreate(Bundle savedInstanceState,Refreshable controller) {
        super.onCreate(savedInstanceState);
        this.controller = controller;
        registerAsRefreshable();
    }

    private void registerAsRefreshable() {
        refreshUpdater = (RefreshUpdater) getParent();
        refreshUpdater.register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (controller != null)
            controller.refreshGui();
    }

    protected void registerController(Refreshable controller) {
        this.controller = controller;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        refreshUpdater.unregister(this);
    }

    public void refresh() {
        refreshData();
        refreshGui();
    }

    @Override
    public void refreshData() {
        controller.refreshData();
    }

    @Override
    public void refreshGui() {
        controller.refreshGui();
    }
}
