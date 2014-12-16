package pl.edu.agh.mobilecodereviewer.view.activities.utilities;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.model.utilities.ChangeInfoHelper;
import pl.edu.agh.mobilecodereviewer.view.activities.ChangesExplorer;

public class ChangesExplorerViewExpandableListAdapter extends BaseExpandableListAdapter{

    private final Activity context;

    private final List<ChangeInfo> groups;

    private List<String> visibleLabels;

    public ChangesExplorerViewExpandableListAdapter(Activity context, List<ChangeInfo> changeInfos, List<String> visibleLabels){
        this.context = context;
        groups = changeInfos;
        this.visibleLabels = visibleLabels;
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).getChangeId();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.layout_changes_explorer_group, null);
        }

        Button goButton = (Button) convertView.findViewById(R.id.changesExplorerGroupGoButton);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ChangesExplorer) context).showChangeDetails(groups.get(groupPosition).getChangeId(), groups.get(groupPosition).getCurrentRevision());
            }
        });

        String changeSubject = groups.get(groupPosition).getSubject();
        TextView changeSubjectView = (TextView) convertView.findViewById(R.id.changesExplorerGroupLabel);
        setTextViewContent(changeSubjectView,changeSubject);

        return convertView;
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        if(convertView == null){
            convertView = inflater.inflate(R.layout.layout_changes_explorer_child, null);
        }

        TextView subject = (TextView) convertView.findViewById(R.id.changeExplorerChildSubjectValue);
        TextView status = (TextView) convertView.findViewById(R.id.changeExplorerChildStatusValue);
        TextView owner = (TextView) convertView.findViewById(R.id.changeExplorerChildOwnerValue);
        TextView project = (TextView) convertView.findViewById(R.id.changeExplorerChildProjectValue);
        TextView branch = (TextView) convertView.findViewById(R.id.changeExplorerChildBranchValue);
        TextView updated = (TextView) convertView.findViewById(R.id.changeExplorerChildUpdatedValue);
        TextView size = (TextView) convertView.findViewById(R.id.changeExplorerChildSizeValue);

        ChangeInfo currentChangeInfo = groups.get(groupPosition);

        String changeSubject = currentChangeInfo.getSubject();
        String changeStatus = currentChangeInfo.getStatus().toString();
        String changeOwner = currentChangeInfo.getOwnerName();
        String changeProject = currentChangeInfo.getProject();
        String changeBranch = currentChangeInfo.getBranch();
        String changeUpdated = ChangeInfoHelper.gerritDateToString(currentChangeInfo.getUpdated() );
        String changeSize = currentChangeInfo.getSize().toString();

        setTextViewContent(subject, changeSubject);
        setTextViewContent(status, changeStatus);
        setTextViewContent(owner, changeOwner);
        setTextViewContent(project, changeProject);
        setTextViewContent(branch, changeBranch);
        setTextViewContent(updated, changeUpdated);
        setTextViewContent(size, changeSize);

        LinearLayout labelsAbbrsView = (LinearLayout) convertView.findViewById(R.id.changeExplorerChildLabelsValue);
        labelsAbbrsView.removeAllViews();
        Map<String, View> labelsViews = ChangesExplorerHelper.extractLabelsViewsMap(context, currentChangeInfo.getLabels());
        for(String labelAbbr : visibleLabels){
            labelsAbbrsView.addView(ChangesExplorerHelper.makeLabelTextView(context, labelAbbr, null, null));
            if(labelsViews.containsKey(labelAbbr)){
                labelsAbbrsView.addView(labelsViews.get(labelAbbr));
            } else {
                labelsAbbrsView.addView(ChangesExplorerHelper.makeLabelTextView(context, "", R.color.grey, null));
            }
        }

        return convertView;
    }

    protected void setTextViewContent(TextView textView, String content) {
        textView.setText(content);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
