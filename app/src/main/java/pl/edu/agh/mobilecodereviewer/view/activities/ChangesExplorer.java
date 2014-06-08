package pl.edu.agh.mobilecodereviewer.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.controllers.api.ChangesExplorerController;
import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
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
    @InjectView(R.id.changesExplorerListView)
    private ListView changesExplorerListView;

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
    public void showChanges(List<ChangeInfo> changes) {
        //TODO dorobienie customowego adaptera ktory umozliwi rozpoznanie id (ChangeInfo#id) kliknietego itema na liscie
        List<String> strChanges = new ArrayList<String>();
        for (ChangeInfo change : changes) {
            strChanges.add(change.getSubject());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                strChanges);

        //TODO modyfkiacja metody aby pobierala id kliknietego itema na liscie (ChangeInfo#id)
        changesExplorerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showChangeDetails();
            }
        });
        changesExplorerListView.setAdapter(adapter);
    }

    /**
     * Starts Change Details activity
     */
    private void showChangeDetails() {
        Intent intent = new Intent(getApplicationContext(), ChangeDetails.class);
        //TODO dodawania informacji o id wybranego ChangeDetails do Intent
        startActivity(intent);
    }
}


























