package pl.edu.agh.mobilecodereviewer.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.inject.Inject;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.controllers.api.CommitMessageTabController;
import pl.edu.agh.mobilecodereviewer.view.activities.resources.ExtraMessages;
import pl.edu.agh.mobilecodereviewer.view.api.CommitMessageTabView;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

public class CommitMessageTab extends RoboActivity implements CommitMessageTabView{

    @Inject
    private CommitMessageTabController controller;

    @InjectView(R.id.commitMessageTabValue)
    private TextView commitMessageValueView;

    public CommitMessageTab(){}

    public CommitMessageTab(CommitMessageTabController controller) { this.controller = controller; }

    /**
     * Invoked on start of the acivity, initialize view,
     * and show list of modified files
     *
     * @param savedInstanceState Last state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commit_message_tab);

        Intent intent = getIntent();

        controller.updateMessage(this, intent.getStringExtra(ExtraMessages.CHANGE_EXPLORER_SELECTED_CHANGE_ID));
    }

    @Override
    public void showMessage(String message) {
        commitMessageValueView.setText(message);
    }
}
