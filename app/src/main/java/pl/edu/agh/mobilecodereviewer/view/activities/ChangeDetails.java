package pl.edu.agh.mobilecodereviewer.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

import pl.edu.agh.mobilecodereviewer.R;
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
    @InjectResource(R.string.pl_agh_edu_mobilecodereviewer_ChangeDetails_tabs_modifiedFiles_id)
    String modifiedFilesTabId;

    /**
     * Visible label of modified files tab.
     */
    @InjectResource(R.string.pl_agh_edu_mobilecodereviewer_ChangeDetails_tabs_modifiedFiles_label)
    String modifiedFilesTabLabel;


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
        //TODO pobieranie id zmiany z dodatkowego parametru intent i przekazywanie go do metody
        //TODO zostanie on przekazany do addTabs aby a nastepnie w intencie do kazdej zakladki

        TabHost changeDetailsTabHost = getTabHost();
        addTabs(changeDetailsTabHost);
        changeDetailsTabHost.setCurrentTab(0);
    }

    /**
     * Adds required tabs for change details tabbed view container
     * @param tabHost tabbed view container
     */
    //TODO zmodyfikowac zeby przekazywany byl tez intent ktory ma zostac ustawiony
    private void addTabs(TabHost tabHost) {
        tabHost.addTab(tabHost.newTabSpec(modifiedFilesTabId).setIndicator(modifiedFilesTabLabel).setContent(new Intent(this, ModifiedFiles.class)));
    }

    /**
     * Preparing activity's options menu.
     * @inheritDoc
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.change_details, menu);
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

}
