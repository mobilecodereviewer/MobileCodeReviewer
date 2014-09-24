package pl.edu.agh.mobilecodereviewer.view.activities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.inject.Inject;

import java.util.Arrays;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.controllers.api.SourceExplorerController;
import pl.edu.agh.mobilecodereviewer.model.Comment;
import pl.edu.agh.mobilecodereviewer.model.Line;
import pl.edu.agh.mobilecodereviewer.model.SourceCode;
import pl.edu.agh.mobilecodereviewer.model.SourceCodeDiff;
import pl.edu.agh.mobilecodereviewer.view.activities.resources.ExtraMessages;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.SingleLineCommentViewListAdapter;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.SourceCodeDiffViewListAdapter;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.SourceCodeViewListAdapter;
import pl.edu.agh.mobilecodereviewer.view.api.SourceExplorerView;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

/**
 * Activity is primary point for exploring changes of the file for a given change.
 * Source Explorer show changes of the file,comments of the lines, give
 * tools to add comment etc
 *
 * @author AGH
 * @version 0.1
 * @since 0.1
 */
public class SourceExplorer extends RoboActivity implements SourceExplorerView{
    private String change_id;
    private String revision_id;
    private String file_id;

    private boolean isDiffView;

    private final Context context = this;
    /**
     *  Associated controller which make actions to activity events
     */
    @Inject
    private SourceExplorerController controller;

    /**
     * List View which shows source code and comments
     */
    @InjectView(R.id.sourceLinesListView)
    private ListView sourceLinesListView;

    @InjectView(R.id.sourceDiffToggleButton)
    private ImageButton sourceDiffToogleButton;

    
    @InjectView(R.id.sourceWriteCommentButton)
    private ImageButton sourceWriteCommentButton;

    /**
     * Initialize view and request update of source code list
     * @param savedInstanceState Saved instance of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source_explorer);

        initializeSourceProperties();
        isDiffView = false;
        final SourceExplorerView sourceView = this;
        sourceDiffToogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SourceExplorer.this.clearSourceLines();
                if ( !SourceExplorer.this.isDiffView() ) {
                    controller.updateSourceCode(sourceView,change_id,revision_id,file_id);
                } else {
                    controller.updateSourceCodeDiff(sourceView,change_id,revision_id,file_id);
                }
                SourceExplorer.this.toogleDiffView();
            }
        });
        controller.updateSourceCode(this,change_id,revision_id,file_id);
    }

    /**
     * Initialize properties of the source code to show, got from the
     * previous activity
     */
    protected void initializeSourceProperties() {
        change_id = getIntent().getStringExtra(ExtraMessages.MODIFIED_FILES_SELECTED_FILE_CHANGE_ID);
        revision_id = getIntent().getStringExtra(ExtraMessages.MODIFIED_FILES_SELECTED_FILE_REVISION_ID);
        file_id = getIntent().getStringExtra(ExtraMessages.MODIFIED_FILES_SELECTED_FILE_FILE_NAME);
    }


    /**
     * Preparing activity's options menu.
     * @inheritDoc
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.source_explorer, menu);
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
     * Show content from given source code
     * @param sourceCode {@link pl.edu.agh.mobilecodereviewer.model.SourceCode}
     */
    @Override
    public void showSourceCode(final SourceCode sourceCode) {
        final SourceCodeViewListAdapter sourceCodeViewListAdapter =
                new SourceCodeViewListAdapter(this,sourceCode);

        sourceLinesListView.setAdapter(sourceCodeViewListAdapter);
        sourceLinesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                if ( sourceCode.getLine(position+1).hasComments() ) {
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.layout_line_comments);
                    dialog.setTitle("Comments");

                    ListView listView = (ListView) dialog.findViewById(R.id.lineCommentsList);
                    listView.setAdapter(new SingleLineCommentViewListAdapter(context, sourceCode.getLine(position+1) ) );
                    dialog.show();
                }

            }
        });
    }

    @Override
    public void clearSourceLines() {
        sourceLinesListView.setAdapter(null);
    }

    @Override
    public void showSourceCodeDiff(SourceCodeDiff sourceCodeDiff) {
        final SourceCodeDiffViewListAdapter sourceCodeDiffViewListAdapter =
                new SourceCodeDiffViewListAdapter(this,sourceCodeDiff);

        sourceLinesListView.setAdapter(sourceCodeDiffViewListAdapter);
    }

    public boolean isDiffView() {
        return isDiffView;
    }

    public void toogleDiffView() {
        isDiffView = !isDiffView;
        if (isDiffView) {
            sourceDiffToogleButton.setBackgroundResource( R.drawable.source_code_diff_icon );
        } else {
            sourceDiffToogleButton.setBackgroundResource( R.drawable.source_code_code_icon );
        }
    }
}

















