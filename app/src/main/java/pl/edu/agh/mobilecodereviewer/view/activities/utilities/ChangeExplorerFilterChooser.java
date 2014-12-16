package pl.edu.agh.mobilecodereviewer.view.activities.utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.controllers.api.ChangesExplorerController;
import pl.edu.agh.mobilecodereviewer.controllers.utilities.ChangesFilter;
import pl.edu.agh.mobilecodereviewer.utilities.PreferencesAccessor;


public class ChangeExplorerFilterChooser {
    public static final String TITLE = "Choose filter:";
    private final List<ChangesFilter> changeFilters;
    private final Context context;
    private final ChangesExplorerController controller;
    private ChangesFilter currentFilter;

    private Map<ChangesFilter, RadioButton> filterButtons;

    public ChangeExplorerFilterChooser(Context context, ChangesExplorerController controller,
                                       ChangesFilter currentFilter, List<ChangesFilter> changeFilters) {
        this.context = context;
        this.controller = controller;
        this.currentFilter = currentFilter;
        this.changeFilters = changeFilters;

        this.filterButtons = new LinkedHashMap<>();
    }

    public void showFilterChooser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(TITLE);

        LayoutInflater inflater = LayoutInflater.from(context);
        View alertView = inflater.inflate(R.layout.layout_changes_explorer_filter, null);

        final RadioGroup filterGroup = (RadioGroup) alertView.findViewById(R.id.changesExplorerFilterRadioGroup);

        final LinearLayout addFilterLayout = (LinearLayout) alertView.findViewById(R.id.changesExplorerFilterAddLayout);
        hideLayout(addFilterLayout);
        initializeAddNewFilterButton(alertView, addFilterLayout);
        initializeRemoveFilterButton(alertView, filterGroup);

        final TextView nameTextView = (TextView) alertView.findViewById(R.id.changesExplorerFilterNameText);
        final TextView queryTextView = (TextView) alertView.findViewById(R.id.changesExplorerFilterQueryText);
        initializeSaveFilterButton(alertView, filterGroup, addFilterLayout, nameTextView, queryTextView);
        initializeCancelAddingFilterButton(alertView, addFilterLayout, nameTextView, queryTextView);

        addFiltersToRadioGroup(filterGroup);

        builder.setView(alertView);
        AlertDialog statusDialog = builder.create();
        statusDialog.show();
    }

    public void initializeCancelAddingFilterButton(View alertView, final LinearLayout addFilterLayout, final TextView nameTextView, final TextView queryTextView) {
        final Button cancelButton = (Button) alertView.findViewById(R.id.changesExplorerFilterCancelQueryButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearTextViews(nameTextView, queryTextView);
                hideLayout(addFilterLayout);
            }
        });
    }

    public void initializeSaveFilterButton(View alertView, final RadioGroup filterGroup, final LinearLayout addFilterLayout, final TextView nameTextView, final TextView queryTextView) {
        final Button saveButton = (Button) alertView.findViewById(R.id.changesExplorerFilterSaveQueryButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangesFilter changesFilter = new ChangesFilter(nameTextView.getText().toString(),
                        queryTextView.getText().toString());

                changeFilters.add(changesFilter);
                putFilterInRadioGroup(filterGroup, changesFilter);
                controller.addChangeFilter(changesFilter);
                clearTextViews(nameTextView, queryTextView);
                hideLayout(addFilterLayout);
            }
        });
    }

    public void initializeRemoveFilterButton(View alertView, final RadioGroup filterGroup) {
        final ImageButton removeFilterButton = (ImageButton) alertView.findViewById(R.id.changesExplorerFilterRemoveButton);
        removeFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int indexOfFilterToRemove = getSelectedIndex(filterGroup);
                ChangesFilter filterToRemove = changeFilters.get(indexOfFilterToRemove);

                if (controller.removeChangeFilter(filterToRemove)) {
                    int indexOfNewCurrentFilter;
                    if (indexOfFilterToRemove == 0) {
                        indexOfNewCurrentFilter = 0;
                    } else indexOfNewCurrentFilter = indexOfFilterToRemove - 1;
                    changeFilters.remove(filterToRemove);
                    currentFilter = changeFilters.get(indexOfNewCurrentFilter);
                    filterGroup.removeViewAt(indexOfFilterToRemove);
                    setCheckedIndex(indexOfNewCurrentFilter);
                    controller.setChangeFilter(currentFilter);
                }
            }
        });
    }

    public void initializeAddNewFilterButton(View alertView, final LinearLayout addFilterLayout) {
        final ImageButton addFilterButton = (ImageButton) alertView.findViewById(R.id.changesExplorerFilterAddButton);
        addFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLayout(addFilterLayout);
            }
        });
    }

    private void setCheckedIndex(int index) {
        LinkedList<RadioButton> changesFilters = Lists.newLinkedList(filterButtons.values());
        changesFilters.get(index).setChecked(true);
    }

    private int getSelectedIndex(RadioGroup group) {
        int radioButtonID = group.getCheckedRadioButtonId();
        View radioButton = group.findViewById(radioButtonID);
        return group.indexOfChild(radioButton);
    }

    private void clearTextViews(TextView... textViews) {
        for (TextView textView : textViews)
            textView.setText("");
    }

    private void showLayout(LinearLayout layout) {
        layout.setVisibility(View.VISIBLE);
    }

    private void hideLayout(LinearLayout layout) {
        layout.setVisibility(View.GONE);
    }

    public void addFiltersToRadioGroup(RadioGroup filterGroup) {
        for (ChangesFilter changeFilter : changeFilters) {
            RadioButton radioButton = putFilterInRadioGroup(filterGroup, changeFilter);
            if (changeFilter.equals(currentFilter))
                radioButton.setChecked(true);
        }
    }

    public RadioButton putFilterInRadioGroup(RadioGroup filterGroup, final ChangesFilter radioButtonChangeFilter) {
        RadioButton radioButton = new RadioButton(context);
        radioButton.setText(radioButtonChangeFilter.getName());

        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            private final ChangesFilter changeFilter = radioButtonChangeFilter;

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    controller.setChangeFilter(changeFilter);
                }
            }
        });
        filterGroup.addView(radioButton);
        filterButtons.put(radioButtonChangeFilter, radioButton);
        return radioButton;
    }
}
