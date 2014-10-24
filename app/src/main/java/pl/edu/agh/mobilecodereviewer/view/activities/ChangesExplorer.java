package pl.edu.agh.mobilecodereviewer.view.activities;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.inject.Inject;

import java.util.Collections;
import java.util.List;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.controllers.api.ChangesExplorerController;
import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.model.ChangeStatus;
import pl.edu.agh.mobilecodereviewer.view.activities.base.BaseActivity;
import pl.edu.agh.mobilecodereviewer.view.activities.resources.ExtraMessages;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.AboutDialogHelper;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.ChangesExplorerSearchViewExpandableListAdapter;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.ChangesExplorerViewExpandableListAdapter;
import pl.edu.agh.mobilecodereviewer.view.api.ChangesExplorerView;
import roboguice.inject.InjectView;

/**
 * Activity shows list of Changes and enables navigating through them
 * as well as displaying details about selected change.
 *
 * @author AGH
 * @version 0.1
 * @since 0.1
 */
public class ChangesExplorer extends BaseActivity implements ChangesExplorerView {

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

    private Menu menu;

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

        controller.initializeData(this);
        controller.updateChanges();
    }

    /**
     * Preparing activity's options menu.
     * @inheritDoc
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.changes_explorer, menu);
        setSearchableConfiguration(menu);
        return true;
    }

    private void setSearchableConfiguration(Menu menu) {
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
    }

    /**
     * Preparing activity's options menu onclick actions.
     * @inheritDoc
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_about) {
            AboutDialogHelper.showDialog(this);
        } else if (id == R.id.discardSearch) {
            hideSearchPanel();
            controller.updateChanges();
        } else if (id == R.id.selectStatus) {
            controller.chooseStatus();
        } else if (id == R.id.changeConfiguration){
            Intent intent = new Intent(getApplicationContext(), Configuration.class);
            intent.putExtra(ExtraMessages.CONFIGURATION_DONT_LOAD_LAST_SAVED, "Y");
            startActivity(intent);
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
        findViewById(R.id.noChangesLayout).setVisibility(View.GONE);
        findViewById(R.id.changesExplorerExpandableListView).setVisibility(View.VISIBLE);
        ChangesExplorerViewExpandableListAdapter expandableListAdapter = new ChangesExplorerViewExpandableListAdapter(this, changes);
        changesExplorerExpandableListView.setAdapter(expandableListAdapter);
    }

    @Override
    public void showFoundChanges(String query, List<ChangeInfo> searchedInfos) {
        ChangesExplorerSearchViewExpandableListAdapter expandableSearchListAdapter = new ChangesExplorerSearchViewExpandableListAdapter(this, searchedInfos, query);
        changesExplorerExpandableListView.setAdapter( expandableSearchListAdapter );
    }

    @Override
    public void clearChangesList() {
        List<ChangeInfo> changes = Collections.emptyList();
        ChangesExplorerViewExpandableListAdapter expandableListAdapter = new ChangesExplorerViewExpandableListAdapter(this, changes);
        changesExplorerExpandableListView.setAdapter(expandableListAdapter);
    }

    /**
     * Starts Change Details activity
     */
    public void showChangeDetails(String changeId, String revisionId) {
        Intent intent = new Intent(getApplicationContext(), ChangeDetails.class);
        intent.putExtra(ExtraMessages.CHANGE_EXPLORER_SELECTED_CHANGE_ID, changeId);
        intent.putExtra(ExtraMessages.CHANGE_EXPLORER_SELECTED_CHANGES_REVISION_ID, revisionId);
        startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            controller.search(query);
            hideSearchPanel();
        }
    }

    @Override
    public void hideSearchPanel() {
        menu.findItem(R.id.search).collapseActionView();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void showListOfAvalaibleStatus(ChangeStatus currentStatus, final ChangeStatus[] changeStatuses) {
        final String[] statuses = new String[ changeStatuses.length ];
        int currStatus = -1;
        for (int i=0;i< changeStatuses.length;i++) {
            statuses[i] = changeStatuses[i].toString();
            if (currentStatus == changeStatuses[i])
                currStatus = i;
        }
        AlertDialog statusDialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose status:");
        builder.setSingleChoiceItems(statuses, currStatus, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int selectedStatus) {
                controller.changeStatus( changeStatuses[selectedStatus] );
                dialogInterface.dismiss();
            }
        });
        statusDialog = builder.create();
        statusDialog.show();
    }

    @Override
    public void showNoChangesToDisplay() {
        findViewById(R.id.changesExplorerExpandableListView).setVisibility(View.GONE);
        findViewById(R.id.noChangesLayout).setVisibility(View.VISIBLE);
    }

}


























