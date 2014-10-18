package pl.edu.agh.mobilecodereviewer.view.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.inject.Inject;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.app.MobileCodeReviewerApplication;
import pl.edu.agh.mobilecodereviewer.controllers.api.SourceExplorerController;
import pl.edu.agh.mobilecodereviewer.model.SourceCode;
import pl.edu.agh.mobilecodereviewer.model.SourceCodeDiff;
import pl.edu.agh.mobilecodereviewer.view.activities.resources.ExtraMessages;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.AboutDialogHelper;
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

    private Menu menu;
    private MenuItem prevChangeNavigation;
    private MenuItem nextChangeNavigation;

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

    @InjectView(R.id.addCommentOptions)
    private LinearLayout addCommentOptions;

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
        sourceLinesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                controller.setCurrentLinePosition(i);
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

        setCommentOptionsVisibility(null);

        controller.initializeView();
    }

    private void setCommentOptionsVisibility(Boolean visibility){
        if(!((MobileCodeReviewerApplication) getApplication()).isAuthenticated()){
            showHideCommentOptionsButton.setVisibility(View.GONE);
        } else if(visibility != null){
            showHideCommentOptionsButton.setVisibility(visibility ? View.VISIBLE : View.GONE);
        }
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
        addCommentOptions.setVisibility(View.GONE);
        setCommentOptionsVisibility(false);
        showHideCommentOptionsButton.setBackgroundResource(R.drawable.common_expand_icon);
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
        addCommentOptions.setVisibility(View.GONE);
        setCommentOptionsVisibility(true);
        showNavigationButtons();
    }

    private void showNavigationButtons() {
        prevChangeNavigation.setVisible(true);
        nextChangeNavigation.setVisible(true);
        prevChangeNavigation.setEnabled(true);
        nextChangeNavigation.setEnabled(true);
    }

    @Override
    public void clearCommentContent() {
        commentContentText.setText("");
    }

    @Override
    public void showCommentOptions() {
        Animation bottomUp = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.bottom_up);

        addCommentOptions.startAnimation(bottomUp);
        addCommentOptions.setVisibility(View.VISIBLE);

        showHideCommentOptionsButton.startAnimation(bottomUp);
        showHideCommentOptionsButton.setBackgroundResource(R.drawable.common_collapse_icon);
    }

    @Override
    public void hideCommentOptions() {

        Animation topDown = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.top_down);
        topDown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                addCommentOptions.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        addCommentOptions.startAnimation(topDown);

        showHideCommentOptionsButton.setBackgroundResource(R.drawable.common_expand_icon);
        showHideCommentOptionsButton.startAnimation(topDown);
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

















