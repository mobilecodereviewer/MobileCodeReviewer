package pl.edu.agh.mobilecodereviewer.view.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.inject.Inject;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.controllers.api.SourceExplorerController;
import pl.edu.agh.mobilecodereviewer.model.SourceCode;
import pl.edu.agh.mobilecodereviewer.model.SourceCodeDiff;
import pl.edu.agh.mobilecodereviewer.utilities.ConfigurationContainer;
import pl.edu.agh.mobilecodereviewer.view.activities.base.BaseActivity;
import pl.edu.agh.mobilecodereviewer.view.activities.resources.ExtraMessages;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.AboutDialogHelper;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.SourceCodeDiffViewListAdapter;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.SourceCodeViewListAdapter;
import pl.edu.agh.mobilecodereviewer.view.api.SourceExplorerView;
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
public class SourceExplorer extends BaseActivity implements SourceExplorerView{
    private String change_id;
    private String revision_id;
    private String file_id;

    private Menu menu;
    private MenuItem prevChangeNavigation;
    private MenuItem nextChangeNavigation;

    private boolean isDiffView = false;

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

        sourceLinesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                controller.setCurrentLinePosition(i);
            }
        });

        if(ConfigurationContainer.getInstance().getConfigurationInfo().isAuthenticatedUser()) {
            sourceLinesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {

                    if(isDiffView) {
                        LayoutInflater li = LayoutInflater.from(SourceExplorer.this);
                        View commentLineView = li.inflate(R.layout.layout_comment_line, null);

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SourceExplorer.this);
                        alertDialogBuilder.setView(commentLineView);

                        final EditText userInput = (EditText) commentLineView.findViewById(R.id.commentContentTextView);
                        final TextView selectedLineView = (TextView) commentLineView.findViewById(R.id.selectedCodeLine);

                        selectedLineView.setText((String) adapterView.getItemAtPosition(i));

                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        controller.insertComment(userInput.getText().toString(), i);
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        dialog.cancel();
                                        break;
                                }
                            }
                        };

                        alertDialogBuilder
                                .setCancelable(false)
                                .setPositiveButton(R.string.pl_agh_edu_common_ok, dialogClickListener)
                                .setNegativeButton(R.string.pl_agh_edu_common_cancel, dialogClickListener);

                        AlertDialog alertDialog = alertDialogBuilder.create();

                        alertDialog.show();
                    }

                    return true;
                }
            });
        }

        controller.initializeView();
    }

    @Override
    public void clearSourceCode() {
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
        this.menu = menu;
        getMenuInflater().inflate(R.menu.source_explorer, menu);
        this.prevChangeNavigation = menu.findItem(R.id.gotoPrevChange);
        this.nextChangeNavigation = menu.findItem(R.id.gotoNextChange);
        hideNavigationButtons();
        return true;
    }

    /**
     * Preparing activity's options menu onclick actions.
     * @inheritDoc
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.sourceDiffToggleButton) {
            isDiffView = !isDiffView;
            controller.toggleDiffView();
        }else if(id == R.id.action_about) {
            AboutDialogHelper.showDialog(this);
        }else if( id == R.id.gotoNextChange) {
            controller.navigateToNextChange();
        }else if (id == R.id.gotoPrevChange) {
            controller.navigateToPrevChange();
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
        if(menu != null) {
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.source_code_diff_icon));
        }
        hideNavigationButtons();
    }

    private void hideNavigationButtons() {
        if (prevChangeNavigation == null || nextChangeNavigation == null)
            return;
        prevChangeNavigation.setVisible(false);
        nextChangeNavigation.setVisible(false);
        prevChangeNavigation.setEnabled(false);
        nextChangeNavigation.setEnabled(false);
    }

    @Override
    public void setInterfaceForDiff() {
        if(menu != null)
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.source_code_code_icon));
        showNavigationButtons();
    }

    private void showNavigationButtons() {
        prevChangeNavigation.setVisible(true);
        nextChangeNavigation.setVisible(true);
        prevChangeNavigation.setEnabled(true);
        nextChangeNavigation.setEnabled(true);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void gotoLine(int line) {
        sourceLinesListView.smoothScrollToPosition(line);
        sourceLinesListView.setSelection(line);
        sourceLinesListView.setSelected(true);
    }
}

















