package pl.edu.agh.mobilecodereviewer.view.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.controllers.api.ChangeDetailsController;
import pl.edu.agh.mobilecodereviewer.controllers.api.SourceExplorerController;
import pl.edu.agh.mobilecodereviewer.model.AccountInfo;
import pl.edu.agh.mobilecodereviewer.model.Comment;
import pl.edu.agh.mobilecodereviewer.model.PermittedLabel;
import pl.edu.agh.mobilecodereviewer.utilities.*;
import pl.edu.agh.mobilecodereviewer.view.activities.resources.ExtraMessages;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.AddReviewFilesExpandableListAdapter;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.AddReviewVotesListAdapter;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.refresh.RefreshManagerTabBaseActivity;
import pl.edu.agh.mobilecodereviewer.view.api.ChangeDetailsView;
import roboguice.inject.InjectResource;

/**
 * Activity is a parent activity for displaying detailed information about change.
 * Contains container for change details tabbed view.
 * It enables navigating between tabs associated with specific set of details.
 *
 * @author AGH
 * @version 0.1
 * @since 0.1
 */
public class ChangeDetails extends RefreshManagerTabBaseActivity implements ChangeDetailsView {

    @Inject
    private ChangeDetailsController controller;

    @Inject
    private SourceExplorerController sourceExplorerController;

    /**
     * Tag of modified files tab.
     */
    @InjectResource(R.string.pl_agh_edu_mobilecodereviewer_ChangeDetails_tabs_modifiedFilesTab_id)
    String modifiedFilesTabId;

    @InjectResource(R.drawable.change_details_modified_files_icon)
    Drawable modifiedFileTabIcon;

    @InjectResource(R.string.pl_agh_edu_mobilecodereviewer_ChangeDetails_tabs_changeInfoTab_id)
    String changeInfoTabId;

    @InjectResource(R.drawable.common_info_icon)
    Drawable changeInfoTabIcon;

    @InjectResource(R.string.pl_agh_edu_mobilecodereviewer_ChangeDetails_tabs_commitMessageTab_id)
    String commitMessageTabId;

    @InjectResource(R.drawable.change_details_commit_message_icon)
    Drawable commitMessageTabIcon;

    @InjectResource(R.string.pl_agh_edu_mobilecodereviewer_ChangeDetails_tabs_changeMessagesTab_id)
    String changeMessagesTabId;

    @InjectResource(R.drawable.common_label_icon)
    Drawable changeMessagesTabIcon;

    @InjectResource(R.string.pl_agh_edu_mobilecodereviewer_ChangeDetails_tabs_reviewersTab_id)
    String reviewersTabId;

    @InjectResource(R.drawable.change_details_reviewers_icon)
    Drawable reviewersTabIcon;

    private String currentChangeId;

    private String currentRevisionId;

    private boolean putReviewVisibility;

    private MenuItem putReviewMenuItem;

    private View addReviewView;

    /**
     * Invoked on start of the acivity.
     * Prepares tabs to be shown, passes intents to each tab and sets currently
     * active tab.
     *
     * @param savedInstanceState Last state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,controller);

        Intent intent = getIntent();

        TabHost changeDetailsTabHost = getTabHost();
        changeDetailsTabHost.getTabWidget().setStripEnabled(false);
        currentChangeId = intent.getStringExtra(ExtraMessages.CHANGE_EXPLORER_SELECTED_CHANGE_ID);
        currentRevisionId = intent.getStringExtra(ExtraMessages.CHANGE_EXPLORER_SELECTED_CHANGES_REVISION_ID);
        addTabs(changeDetailsTabHost);
        changeDetailsTabHost.setCurrentTab(0);

        registerController(controller);
        controller.initializeData(this, currentChangeId, currentRevisionId);
        refreshGui();
    }

    /**
     * Adds required tabs for change details tabbed view container
     *
     * @param tabHost tabbed view container
     */
    private void addTabs(TabHost tabHost) {

        tabHost.addTab(tabHost.newTabSpec(changeInfoTabId)
                .setIndicator("", changeInfoTabIcon)
                .setContent(new Intent(this, ChangeInfoTab.class)
                        .putExtra(ExtraMessages.CHANGE_EXPLORER_SELECTED_CHANGE_ID, currentChangeId)));

        tabHost.addTab(tabHost.newTabSpec(commitMessageTabId)
                .setIndicator("", commitMessageTabIcon)
                .setContent(new Intent(this, CommitMessageTab.class)
                    .putExtra(ExtraMessages.CHANGE_EXPLORER_SELECTED_CHANGE_ID, currentChangeId)));

        tabHost.addTab(tabHost.newTabSpec(reviewersTabId)
            .setIndicator("", reviewersTabIcon)
            .setContent(new Intent(this, ReviewersTab.class)
                .putExtra(ExtraMessages.CHANGE_EXPLORER_SELECTED_CHANGE_ID, currentChangeId)));

        tabHost.addTab(tabHost.newTabSpec(modifiedFilesTabId)
                .setIndicator("", modifiedFileTabIcon)
                .setContent(new Intent(this, ModifiedFilesTab.class)
                        .putExtra(ExtraMessages.CHANGE_EXPLORER_SELECTED_CHANGE_ID, currentChangeId)));

        tabHost.addTab(tabHost.newTabSpec(changeMessagesTabId)
            .setIndicator("", changeMessagesTabIcon)
            .setContent(new Intent(this, ChangeMessagesTab.class)
                .putExtra(ExtraMessages.CHANGE_EXPLORER_SELECTED_CHANGE_ID, currentChangeId)));
    }

    /**
     * Preparing activity's options menu.
     *
     * @inheritDoc
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.change_details, menu);
        putReviewMenuItem = menu.findItem(R.id.action_addReview);

        menu.setGroupVisible(R.id.changeDetailsPrivilegedGroup, putReviewVisibility);
        return true;
    }

    @Override
    public void setPutReviewVisibility(boolean putReviewVisibility) {
        this.putReviewVisibility = putReviewVisibility;
        if (putReviewMenuItem != null)
            putReviewMenuItem.setVisible( this.putReviewVisibility);
    }

    @Override
    public void setTitle(String titleText) {
        super.setTitle(titleText);
    }

    /**
     * Preparing activity's options menu onclick actions.
     *
     * @inheritDoc
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_addReview) {
            controller.updateSetReviewPopup();
        } else if (id == R.id.refreshChangeDetails){
            refresh();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showSetReviewPopup(final List<PermittedLabel> permittedLabels, Map<String, List<Comment>> pendingComments) {
        LayoutInflater li = LayoutInflater.from(ChangeDetails.this);
        View addReviewView = li.inflate(R.layout.layout_add_review, null);

        this.addReviewView = addReviewView;

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ChangeDetails.this);
        alertDialogBuilder.setView(addReviewView);

        final EditText userInput = (EditText) addReviewView.findViewById(R.id.editTextDialogUserInput);

        AccountInfo loggedUser = ConfigurationContainer.getInstance().getLoggedUser();
        final ListView votesList = (ListView) addReviewView.findViewById(R.id.votesList);
        votesList.setAdapter(new AddReviewVotesListAdapter(this, permittedLabels, loggedUser));

        preparePendingFilesCommentsList(pendingComments);

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:

                        String message = userInput.getText().toString();

                        Map<String, Integer> votes = new HashMap<String, Integer>();
                        for(int i = 0; i<permittedLabels.size(); i++){
                            View voteItem = votesList.getChildAt(i);
                            Spinner voteSpinner = (Spinner) voteItem.findViewById(R.id.singleVoteSpinner);
                            if(voteSpinner.getSelectedItem() != null && voteSpinner.getAdapter().getCount() != 0) {
                                votes.put(permittedLabels.get(i).getName(), (Integer) voteSpinner.getSelectedItem());
                            }
                        }

                        controller.setReview(message, votes);
                        sourceExplorerController.clearCache();

                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.cancel();
                        break;
                }
            }
        };

        alertDialogBuilder
                .setCancelable(true)
                .setPositiveButton(R.string.pl_agh_edu_common_ok, dialogClickListener)
                .setNegativeButton(R.string.pl_agh_edu_common_cancel, dialogClickListener);

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }

    @Override
    public void preparePendingFilesCommentsList(Map<String, List<Comment>> pendingComments){
        if(addReviewView != null && pendingComments != null) {
            final ExpandableListView filesList = (ExpandableListView) addReviewView.findViewById(R.id.addReviewFilesList);
            filesList.setAdapter(new AddReviewFilesExpandableListAdapter(this, controller, pendingComments));
        }
    }

    @Override
    public void onBackPressed(){
        sourceExplorerController.clearCache();
    }

}
