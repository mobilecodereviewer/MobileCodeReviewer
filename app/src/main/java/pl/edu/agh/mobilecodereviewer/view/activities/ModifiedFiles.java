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
import pl.edu.agh.mobilecodereviewer.controllers.api.ModifiedFilesController;
import pl.edu.agh.mobilecodereviewer.model.FileInfo;
import pl.edu.agh.mobilecodereviewer.view.api.ModifiedFilesView;
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
public class ModifiedFiles extends RoboActivity implements ModifiedFilesView {

    /**
     * Associated controller which make actions to activity events
     */
    @Inject
    private ModifiedFilesController controller;

    /**
     * List of modified files view
     */
    @InjectView(R.id.modifiedFilesListView)
    private ListView modifiedFilesListView;

    /**
     * No arg constructor,main for use by di and android framework
     */
    public ModifiedFiles() {
    }

    /**
     * Construct activity with given controller,for test only
     *
     * @param controller Controller to be used
     */
    public ModifiedFiles(ModifiedFilesController controller) {
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
        setContentView(R.layout.activity_modified_files);

        //TODO pobrac id z intenta przekazanego przez changedetails i przekazac do kontrolera
        controller.updateFiles(this);
    }

    /**
     * According to given list of modified files , displays it
     * on the list of modified files on frame
     *
     * @param filesList List of modified files to display
     */
    @Override
    public void showFiles(List<FileInfo> filesList) {
        List<String> strChanges = new ArrayList<String>();
        for (FileInfo file : filesList) {
            strChanges.add(file.getFileName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                strChanges);

        modifiedFilesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showFileDetails();
            }
        });
        modifiedFilesListView.setAdapter(adapter);
    }

    /**
     * Show content of the file {@link pl.edu.agh.mobilecodereviewer.view.activities.SourceExplorer}
     */
    private void showFileDetails() {
        Intent intent = new Intent(getApplicationContext(), SourceExplorer.class);
        startActivity(intent);
    }
}
