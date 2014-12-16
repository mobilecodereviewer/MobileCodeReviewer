package pl.edu.agh.mobilecodereviewer.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import javax.inject.Inject;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.controllers.api.ChangeInfoTabController;
import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.model.MergeableInfo;
import pl.edu.agh.mobilecodereviewer.model.utilities.ChangeInfoHelper;
import pl.edu.agh.mobilecodereviewer.view.activities.resources.ExtraMessages;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.refresh.Refreshable;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.refresh.RefreshableTabBaseActivity;
import pl.edu.agh.mobilecodereviewer.view.api.ChangeInfoTabView;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;

public class ChangeInfoTab extends RefreshableTabBaseActivity implements ChangeInfoTabView, Refreshable {

    @Inject
    private ChangeInfoTabController controller;

    @InjectResource(R.string.pl_agh_edu_mobilecodereviewer_common_true)
    private String trueString;

    @InjectResource(R.string.pl_agh_edu_mobilecodereviewer_common_false)
    private String falseString;

    @InjectView(R.id.changeInfoTabChangeIdValue)
    private TextView changeIdView;

    @InjectView(R.id.changeInfoTabOwnerValue)
    private TextView changeOwnerView;

    @InjectView(R.id.changeInfoTabProjectValue)
    private TextView changeProjectView;

    @InjectView(R.id.changeInfoTabTopicValue)
    private TextView changeTopicView;

    @InjectView(R.id.changeInfoTabUploadedValue)
    private TextView changeUploadedView;

    @InjectView(R.id.changeInfoTabUpdatedValue)
    private TextView changeUpdatedView;

    @InjectView(R.id.changeInfoTabSubmitTypeValue)
    private TextView changeSubmitTypeView;

    @InjectView(R.id.changeInfoTabStatusValue)
    private TextView changeStatusView;

    @InjectView(R.id.changeInfoTabMergeableValue)
    private TextView changeMergeableView;

    @InjectView(R.id.changeInfoTabChangeNumber)
    private TextView changeNumberView;

    public ChangeInfoTab(){}

    public ChangeInfoTab(ChangeInfoTabController controller) { this.controller = controller; }

    /**
     * Invoked on start of the acivity, initialize view,
     * and show list of modified files
     *
     * @param savedInstanceState Last state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,controller);
        setContentView(R.layout.activity_change_info_tab);

        Intent intent = getIntent();

        registerController(controller);
        controller.initializeData(this,intent.getStringExtra(ExtraMessages.CHANGE_EXPLORER_SELECTED_CHANGE_ID) );
        refresh();
    }

    @Override
    public void showInfo(ChangeInfo changeInfo, MergeableInfo mergeableInfo, String topic) {
        changeNumberView.setText(changeInfo.getNumber().toString());
        changeIdView.setText(changeInfo.getChangeId());
        changeOwnerView.setText(changeInfo.getOwnerName());
        changeProjectView.setText(changeInfo.getProject());
        changeTopicView.setText(topic);
        changeUploadedView.setText(changeInfo.getCreated());
        changeUpdatedView.setText(ChangeInfoHelper.gerritDateToString(changeInfo.getUpdated()) );
        changeSubmitTypeView.setText(mergeableInfo.getSubmitType());
        changeStatusView.setText(changeInfo.getStatus().toString() );
        changeMergeableView.setText(mergeableInfo.isMergeable() ? trueString : falseString);
    }

}
