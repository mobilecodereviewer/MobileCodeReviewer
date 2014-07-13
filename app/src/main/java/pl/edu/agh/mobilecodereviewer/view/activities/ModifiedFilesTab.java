package pl.edu.agh.mobilecodereviewer.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.controllers.api.ModifiedFilesTabController;
import pl.edu.agh.mobilecodereviewer.model.FileInfo;
import pl.edu.agh.mobilecodereviewer.view.activities.resources.ExtraMessages;
import pl.edu.agh.mobilecodereviewer.view.api.ModifiedFilesTabView;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

/**
 * Activity show list of modified files and enables navigating through them
 * as well as displaying detailed view of selected file.
 *
 * @author AGH
 * @version 0.1
 * @since 0.1
 */
public class ModifiedFilesTab extends RoboActivity implements ModifiedFilesTabView {

    /**
     * Associated controller which make actions to activity events
     */
    @Inject
    private ModifiedFilesTabController controller;

    /**
     * List of modified files view
     */
    @InjectView(R.id.modifiedFilesTabListView)
    private ListView modifiedFilesTabListView;

    /**
     * No arg constructor,main for use by di and android framework
     */
    public ModifiedFilesTab() {
    }

    /**
     * Construct activity with given controller,for test only
     *
     * @param controller Controller to be used
     */
    public ModifiedFilesTab(ModifiedFilesTabController controller) {
        this.controller = controller;
    }

    /**
     * Invoked on start of the acivity, initialize view,
     * and show list of modified files
     *
     * @param savedInstanceState Last state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modified_files_tab);

        Intent intent = getIntent();

        controller.updateFiles(this, intent.getStringExtra(ExtraMessages.CHANGE_EXPLORER_SELECTED_CHANGE_ID));
    }

    /**
     * According to given list of modified files , displays it
     * on the list of modified files on frame
     *
     * @param filesList List of modified files to display
     */
    @Override
    public void showFiles(final List<FileInfo> filesList) {
        List<String> strChanges = new ArrayList<String>();
        for (FileInfo file : filesList) {
            strChanges.add(file.getFileName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                strChanges);

        modifiedFilesTabListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FileInfo selectedFile = filesList.get(i);
                showFileDetails(selectedFile.getChangeId(), selectedFile.getRevisionId(), selectedFile.getFileName());
            }
        });
        modifiedFilesTabListView.setAdapter(adapter);
    }

    /**
     * Show content of the file {@link pl.edu.agh.mobilecodereviewer.view.activities.SourceExplorer}
     */
    private void showFileDetails(String changeId, String revisionId, String fileName) {
        Intent intent = new Intent(getApplicationContext(), SourceExplorer.class);

        intent.putExtra(ExtraMessages.MODIFIED_FILES_SELECTED_FILE_CHANGE_ID, changeId);
        intent.putExtra(ExtraMessages.MODIFIED_FILES_SELECTED_FILE_REVISION_ID, revisionId);
        intent.putExtra(ExtraMessages.MODIFIED_FILES_SELECTED_FILE_FILE_NAME, fileName);

        startActivity(intent);
    }
}
