package pl.edu.agh.mobilecodereviewer.view.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import java.util.Collections;
import java.util.List;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.controllers.api.ReviewersTabController;
import pl.edu.agh.mobilecodereviewer.model.LabelInfo;
import pl.edu.agh.mobilecodereviewer.view.activities.resources.ExtraMessages;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.ReviewersViewListAdapter;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.refresh.RefreshableTabBaseActivity;
import pl.edu.agh.mobilecodereviewer.view.api.ReviewersTabView;
import roboguice.inject.InjectView;

public class ReviewersTab extends RefreshableTabBaseActivity implements ReviewersTabView{

    @Inject
    private ReviewersTabController controller;

    @InjectView(R.id.reviewersTabReviewersList)
    private ListView reviewersListView;

    @InjectView(R.id.reviewersTabLabelSpinner)
    private Spinner reviewersTabLabelSpinner;

    public ReviewersTab() {
    }

    public ReviewersTab(ReviewersTabController controller) {
        this.controller = controller;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,controller);
        setContentView(R.layout.activity_reviewers_tab);

        Intent intent = getIntent();

        registerController(controller);
        controller.initializeData(this,intent.getStringExtra(ExtraMessages.CHANGE_EXPLORER_SELECTED_CHANGE_ID) );
        refresh();
    }


    @Override
    public void showReviewers(final List<LabelInfo> labels) {
        List<String> labelNames;

        if (labels != null) {
            labelNames = Lists.transform(labels,
                    new Function<LabelInfo, String>() {
                        @Override
                        public String apply(LabelInfo from) {
                            return from.getName();
                        }
                    }
            );
        } else
            labelNames = Collections.EMPTY_LIST;

        if ( labels != null && labels.size() > 0)
            reviewersListView.setAdapter( new ReviewersViewListAdapter(this, labels.get(0).getAll()));

        ArrayAdapter<String> labelSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, labelNames);
        reviewersTabLabelSpinner.setAdapter(labelSpinnerAdapter);
        labelSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reviewersTabLabelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedLabelName = labels.get(position).getName();

                if(getLabelInfo(selectedLabelName, labels) != null && view != null){
                   reviewersListView.setAdapter(new ReviewersViewListAdapter((Activity) view.getContext(), getLabelInfo(selectedLabelName, labels).getAll()));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private LabelInfo getLabelInfo(String name, List<LabelInfo> labels){
        for(LabelInfo labelInfo : labels){

            if(name.equals(labelInfo.getName())){
                return labelInfo;
            }
        }

        return null;
    }
}
