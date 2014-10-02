package pl.edu.agh.mobilecodereviewer.view.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;

import com.google.inject.Inject;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.controllers.api.SourceExplorerController;
import pl.edu.agh.mobilecodereviewer.model.SourceCode;
import pl.edu.agh.mobilecodereviewer.model.SourceCodeDiff;
import pl.edu.agh.mobilecodereviewer.view.activities.resources.ExtraMessages;
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

    @InjectView(R.id.optionsRelativeLayout)
    private RelativeLayout optionsRelativeLayout;

    @InjectView(R.id.commentOptionsTab)
    private TabHost commentOptionsTab;

    @InjectView(R.id.showHideCommentOptionsButton)
    private ImageButton showHideCommentOptionsButton;

    @InjectView(R.id.sendCommentButton)
    private ImageButton sendCommentButton;

    @InjectView(R.id.cancelCommentButton)
    private ImageButton cancelCommentButton;

    @InjectView(R.id.commentContentTextView)
    private EditText commentContentText;

    /**
     * Initialize view and request update of source code list
     * @param savedInstanceState Saved instance of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_source_explorer);

        initializeSourceProperties();
        controller.initializeData(this,change_id,revision_id,file_id);
        initializeView();
    }

    private void initializeView() {
        final SourceExplorerView sourceView = this;
        //sourceLinesListView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        sourceLinesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                controller.setCurrentLinePosition(i);
            }
        });
        sourceDiffToogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.toggleDiffView();
            }
        });
        sendCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.insertComment(commentContentText.getText().toString() );
            }
        });
        cancelCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.cancelComment();
            }
        });
        showHideCommentOptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.toggleCommentWriteMode();
            }
        });
        controller.initializeView();
    }

    @Override
    public void clearLines() {
        sourceLinesListView.setAdapter(null);
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
    }

    @Override
    public void showSourceCodeDiff(SourceCodeDiff sourceCodeDiff) {
        final SourceCodeDiffViewListAdapter sourceCodeDiffViewListAdapter =
                new SourceCodeDiffViewListAdapter(this,sourceCodeDiff);

        sourceLinesListView.setAdapter(sourceCodeDiffViewListAdapter);
    }

    @Override
    public void setInterfaceForCode() {
        sourceDiffToogleButton.setBackgroundResource(R.drawable.source_code_code_icon);
        commentOptionsTab.setVisibility(View.GONE);
        showHideCommentOptionsButton.setVisibility(View.VISIBLE);
        showHideCommentOptionsButton.setBackgroundResource(R.drawable.source_code_write_comment);
    }

    @Override
    public void setInterfaceForDiff() {
        sourceDiffToogleButton.setBackgroundResource( R.drawable.source_code_diff_icon );
        commentOptionsTab.setVisibility(View.GONE);
        showHideCommentOptionsButton.setVisibility(View.GONE);
    }

    @Override
    public void clearCommentContent() {
        commentContentText.setText("");
    }

    @Override
    public void showCommentOptions() {
        commentOptionsTab.setVisibility(View.VISIBLE);
        showHideCommentOptionsButton.setVisibility(View.VISIBLE);
        showHideCommentOptionsButton.setBackgroundResource(R.drawable.downicon);
    }

    @Override
    public void hideCommentOptions() {
        commentOptionsTab.setVisibility(View.GONE);
        showHideCommentOptionsButton.setVisibility(View.VISIBLE);
        showHideCommentOptionsButton.setBackgroundResource(R.drawable.source_code_write_comment);
    }
}

















