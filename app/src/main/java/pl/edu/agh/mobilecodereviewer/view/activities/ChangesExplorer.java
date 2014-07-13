package pl.edu.agh.mobilecodereviewer.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.google.inject.Inject;

import java.util.List;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.controllers.api.ChangesExplorerController;
import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.view.activities.resources.ExtraMessages;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.ChangesExplorerViewExpandableListAdapter;
import pl.edu.agh.mobilecodereviewer.view.api.ChangesExplorerView;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

/**
 * Activity shows list of Changes and enables navigating through them
 * as well as displaying details about selected change.
 *
 * @author AGH
 * @version 0.1
 * @since 0.1
 */
public class ChangesExplorer extends RoboActivity implements ChangesExplorerView {

    /**
     * Associated controller which make actions to activity events
     */
    @Inject
    private ChangesExplorerController controller;

    /**
     * List of changes view
     */
    @InjectView(R.id.changesExplorerExpandableListView)
    private ExpandableListView changesExplorerExpandableListView;

    /**
     * No arg constructor,main for use by di and android framework
     */
    public ChangesExplorer() {
    }

    /**
     * Construct activity with given controller,for test only
     *
     * @param controller Controller to be used
     */
    public ChangesExplorer(ChangesExplorerController controller) {
        this.controller = controller;
    }

    /**
     * Invoked on start of the acivity, initialize view,
     * and show list of changes
     *
     * @param savedInstanceState Last state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changes_explorer);

        controller.updateChanges(this);
    }

    /**
     * Preparing activity's options menu.
     * @inheritDoc
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.changes_explorer, menu);
        return true;
    }

    /**
     * Preparing activity's options menu onclick actions.
     * @inheritDoc
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * According to given list of changes , displays it
     * on the list of changes on frame.
     * Besides attaches listener to click events on list.
     *
     * @param changes List of changes to display
     */
    @Override
    public void showChanges(final List<ChangeInfo> changes) {
        ChangesExplorerViewExpandableListAdapter expandableListAdapter = new ChangesExplorerViewExpandableListAdapter(this, changes);
        changesExplorerExpandableListView.setAdapter(expandableListAdapter);
    }

    /**
     * Starts Change Details activity
     */
    public void showChangeDetails(String changeId) {
        Intent intent = new Intent(getApplicationContext(), ChangeDetails.class);
        intent.putExtra(ExtraMessages.CHANGE_EXPLORER_SELECTED_CHANGE_ID, changeId);
        startActivity(intent);
    }
}


























