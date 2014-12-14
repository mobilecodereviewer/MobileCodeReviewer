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
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.inject.Inject;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.controllers.api.ChangesExplorerController;
import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.model.ChangeStatus;
import pl.edu.agh.mobilecodereviewer.view.activities.base.BaseActivity;
import pl.edu.agh.mobilecodereviewer.view.activities.resources.ExtraMessages;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.AboutDialogHelper;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.ChangesExplorerHelper;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.ChangesExplorerSearchViewExpandableListAdapter;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.ChangesExplorerSearchViewListAdapter;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.ChangesExplorerViewExpandableListAdapter;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.ChangesExplorerViewListAdapter;
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

    @InjectView(R.id.changesExplorerListView)
    private ListView changesExplorerListView;

    @InjectView(R.id.headerLayout)
    private LinearLayout headerLayout;

    private Menu menu;

    private List<String> visibileLabels = new LinkedList<String>();

    private boolean simpleListView = true;

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

        prepareSimpleList();
        controller.initializeData(this);
        controller.updateChanges();
    }

    @Override
    public void onBackPressed() {
        finish();
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
        } else if (id == R.id.refreshChanges){
            controller.refreshChanges();
        } else if (id == R.id.discardSearch) {
            hideSearchPanel();
            controller.updateChanges();
        } else if (id == R.id.selectStatus) {
            controller.chooseStatus();
        } else if (id == R.id.changeConfiguration){
            Intent intent = new Intent(getApplicationContext(), Configuration.class);
            intent.putExtra(ExtraMessages.CONFIGURATION_DONT_LOAD_LAST_SAVED, "Y");
            startActivity(intent);
        } else if (id == R.id.switchView) {
            simpleListView = !simpleListView;
            showList();
        }

        return super.onOptionsItemSelected(item);

    }

    private void prepareSimpleList(){
        changesExplorerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ChangeInfo item = (ChangeInfo) adapterView.getItemAtPosition(i);
                showChangeDetails(item.getChangeId(), item.getCurrentRevision());
            }
        });
    }

    private void showList(){
        if(findViewById(R.id.noChangesLayout).getVisibility() == View.GONE) {
            if (!simpleListView) {
                headerLayout.setVisibility(View.GONE);
                changesExplorerExpandableListView.setVisibility(View.VISIBLE);
                changesExplorerListView.setVisibility(View.GONE);
            } else {
                headerLayout.setVisibility(View.VISIBLE);
                changesExplorerExpandableListView.setVisibility(View.GONE);
                changesExplorerListView.setVisibility(View.VISIBLE);
            }
        }
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
        showList();
        updateVisibleLabels(changes);
        changesExplorerExpandableListView.setAdapter(new ChangesExplorerViewExpandableListAdapter(this, changes, visibileLabels));
        changesExplorerListView.setAdapter(new ChangesExplorerViewListAdapter(this, changes, visibileLabels));
    }

    @Override
    public void showFoundChanges(String query, List<ChangeInfo> searchedInfos) {
        updateVisibleLabels(searchedInfos);
        changesExplorerExpandableListView.setAdapter(new ChangesExplorerSearchViewExpandableListAdapter(this, searchedInfos, query, visibileLabels) );
        changesExplorerListView.setAdapter(new ChangesExplorerSearchViewListAdapter(this, searchedInfos, query, visibileLabels));
    }

    @Override
    public void clearChangesList() {
        List<ChangeInfo> changes = Collections.emptyList();
        updateVisibleLabels(changes);
        changesExplorerExpandableListView.setAdapter(new ChangesExplorerViewExpandableListAdapter(this, changes, visibileLabels));
        changesExplorerListView.setAdapter(new ChangesExplorerViewListAdapter(this, changes, visibileLabels));
    }

    private void updateVisibleLabels(List<ChangeInfo> changeInfos){
        visibileLabels.clear();
        visibileLabels = ChangesExplorerHelper.getLabelsAbbreviationsList(changeInfos);

        LinearLayout labelsAbbreviations = (LinearLayout) headerLayout.findViewById(R.id.labelsAbbreviations);
        labelsAbbreviations.removeAllViews();

        for(String labelAbbr : visibileLabels){
            labelsAbbreviations.addView(ChangesExplorerHelper.makeLabelTextView(this, labelAbbr, null, null));
        }
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
        findViewById(R.id.changesExplorerListView).setVisibility(View.GONE);
        findViewById(R.id.changesExplorerExpandableListView).setVisibility(View.GONE);
        findViewById(R.id.noChangesLayout).setVisibility(View.VISIBLE);
    }

}


























