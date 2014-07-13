package pl.edu.agh.mobilecodereviewer.view.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.view.activities.resources.ExtraMessages;
import roboguice.activity.RoboTabActivity;
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
public class ChangeDetails extends RoboTabActivity {

    /**
     * Tag of modified files tab.
     */
    @InjectResource(R.string.pl_agh_edu_mobilecodereviewer_ChangeDetails_tabs_modifiedFilesTab_id)
    String modifiedFilesTabId;

    @InjectResource(R.drawable.change_details_modified_files_icon)
    Drawable modifiedFileTabIcon;

    @InjectResource(R.string.pl_agh_edu_mobilecodereviewer_ChangeDetails_tabs_changeInfoTab_id)
    String changeInfoTabId;

    @InjectResource(R.drawable.change_details_change_info_icon)
    Drawable changeInfoTabIcon;

    @InjectResource(R.string.pl_agh_edu_mobilecodereviewer_ChangeDetails_tabs_commitMessageTab_id)
    String commitMessageTabId;

    @InjectResource(R.drawable.change_details_commit_message_icon)
    Drawable commitMessageTabIcon;

    @InjectResource(R.string.pl_agh_edu_mobilecodereviewer_ChangeDetails_tabs_changeMessagesTab_id)
    String changeMessagesTabId;

    @InjectResource(R.drawable.change_details_change_messages_icon)
    Drawable changeMessagesTabIcon;

    @InjectResource(R.string.pl_agh_edu_mobilecodereviewer_ChangeDetails_tabs_reviewersTab_id)
    String reviewersTabId;

    @InjectResource(R.drawable.change_details_reviewers_icon)
    Drawable reviewersTabIcon;

    /**
     * Invoked on start of the acivity.
     * Prepares tabs to be shown, passes intents to each tab and sets currently
     * active tab.
     *
     * @param savedInstanceState Last state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        TabHost changeDetailsTabHost = getTabHost();
        changeDetailsTabHost.getTabWidget().setStripEnabled(false);
        addTabs(changeDetailsTabHost, intent.getStringExtra(ExtraMessages.CHANGE_EXPLORER_SELECTED_CHANGE_ID));
        changeDetailsTabHost.setCurrentTab(0);
    }

    /**
     * Adds required tabs for change details tabbed view container
     *
     * @param tabHost tabbed view container
     */
    private void addTabs(TabHost tabHost, String changeId) {

        tabHost.addTab(tabHost.newTabSpec(changeInfoTabId)
                .setIndicator("", changeInfoTabIcon)
                .setContent(new Intent(this, ChangeInfoTab.class)
                    .putExtra(ExtraMessages.CHANGE_EXPLORER_SELECTED_CHANGE_ID, changeId)));

        tabHost.addTab(tabHost.newTabSpec(commitMessageTabId)
                .setIndicator("", commitMessageTabIcon)
                .setContent(new Intent(this, CommitMessageTab.class)
                    .putExtra(ExtraMessages.CHANGE_EXPLORER_SELECTED_CHANGE_ID, changeId)));

        tabHost.addTab(tabHost.newTabSpec(reviewersTabId)
            .setIndicator("", reviewersTabIcon)
            .setContent(new Intent(this, ReviewersTab.class)
                .putExtra(ExtraMessages.CHANGE_EXPLORER_SELECTED_CHANGE_ID, changeId)));

        tabHost.addTab(tabHost.newTabSpec(modifiedFilesTabId)
                .setIndicator("", modifiedFileTabIcon)
                .setContent(new Intent(this, ModifiedFilesTab.class)
                        .putExtra(ExtraMessages.CHANGE_EXPLORER_SELECTED_CHANGE_ID, changeId)));

        tabHost.addTab(tabHost.newTabSpec(changeMessagesTabId)
            .setIndicator("", changeMessagesTabIcon)
            .setContent(new Intent(this, ChangeMessagesTab.class)
                .putExtra(ExtraMessages.CHANGE_EXPLORER_SELECTED_CHANGE_ID, changeId)));
    }

    /**
     * Preparing activity's options menu.
     *
     * @inheritDoc
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.change_details, menu);
        return true;
    }

    /**
     * Preparing activity's options menu onclick actions.
     *
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

}
