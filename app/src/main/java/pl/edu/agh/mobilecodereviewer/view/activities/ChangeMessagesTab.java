package pl.edu.agh.mobilecodereviewer.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.google.inject.Inject;

import java.util.Collections;
import java.util.List;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.controllers.api.ChangeMessagesTabController;
import pl.edu.agh.mobilecodereviewer.model.ChangeMessageInfo;
import pl.edu.agh.mobilecodereviewer.view.activities.resources.ExtraMessages;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.ChangeMessagesViewListAdapter;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.refresh.Refreshable;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.refresh.RefreshableTabBaseActivity;
import pl.edu.agh.mobilecodereviewer.view.api.ChangeMessagesTabView;
import roboguice.inject.InjectView;

public class ChangeMessagesTab extends RefreshableTabBaseActivity implements ChangeMessagesTabView, Refreshable {

    @Inject
    private ChangeMessagesTabController controller;

    @InjectView(R.id.changeMessagesTabListView)
    private ListView changeMessagesListView;

    public ChangeMessagesTab(){}

    public ChangeMessagesTab(ChangeMessagesTabController controller) { this.controller = controller; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,controller);
        setContentView(R.layout.activity_change_messages_tab);

        Intent intent = getIntent();

        registerController(controller);
        controller.initializeData(this, intent.getStringExtra(ExtraMessages.CHANGE_EXPLORER_SELECTED_CHANGE_ID));
        refresh();
    }

    @Override
    public void showMessages(List<ChangeMessageInfo> messages) {
        ChangeMessagesViewListAdapter changeMessagesViewListAdapter;
        if ( messages != null )
            changeMessagesViewListAdapter = new ChangeMessagesViewListAdapter(this, messages);
        else
            changeMessagesViewListAdapter = new ChangeMessagesViewListAdapter(this, Collections.EMPTY_LIST);
        changeMessagesListView.setAdapter(changeMessagesViewListAdapter);
    }

}
