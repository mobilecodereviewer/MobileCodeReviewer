package pl.edu.agh.mobilecodereviewer.view.activities.utilities.refresh;

import android.app.Activity;
import android.app.TabActivity;
import android.os.Bundle;

import java.util.LinkedList;
import java.util.List;

import pl.edu.agh.mobilecodereviewer.view.activities.base.BaseTabActivity;


public class RefreshManagerTabBaseActivity extends BaseTabActivity implements RefreshUpdater{
    private Refreshable mainRefreshable;
    private List<Refreshable> refreshables;

    public RefreshManagerTabBaseActivity() {
        super();
        refreshables = new LinkedList<Refreshable>();
    }

    protected void onCreate(Bundle savedInstanceState,Refreshable refreshable) {
        super.onCreate(savedInstanceState);
        mainRefreshable = refreshable;
    }

    @Override
    public void register(Refreshable refreshable) {
        refreshables.add(refreshable);
    }

    @Override
    public void unregister(Refreshable refreshable) {
        refreshables.remove(refreshable);
    }

    protected void registerController(Refreshable refreshable) {
        this.mainRefreshable = refreshable;
    }

    protected void refresh() {
        mainRefreshable.refreshData();
        for (Refreshable refreshable : refreshables) {
            refreshable.refreshData();
        }
        Refreshable currentRefreshable = (Refreshable) getCurrentTabActivity();
        currentRefreshable.refreshGui();
        mainRefreshable.refreshGui();
    }

    protected void refreshGui() {
        mainRefreshable.refreshGui();
    }

    private Activity getCurrentTabActivity() {
        String tabTag = getTabHost().getCurrentTabTag();
        return getLocalActivityManager().getActivity(tabTag);
    }
}
