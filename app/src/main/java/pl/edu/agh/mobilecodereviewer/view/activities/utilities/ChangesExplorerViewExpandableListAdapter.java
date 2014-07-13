package pl.edu.agh.mobilecodereviewer.view.activities.utilities;

import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.dao.gerrit.tools.Pair;
import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.model.utilities.ChangeInfoHelper;
import pl.edu.agh.mobilecodereviewer.view.activities.ChangesExplorer;

public class ChangesExplorerViewExpandableListAdapter extends BaseExpandableListAdapter {

    private  Map<ChangeInfoHelper.ChildrenHeaders, String> childrenHeadersMap;

    private final Activity context;

    private final List<Pair<String, String>> groups;

    private final Map<String, List<Pair<String, String>>> children;

    public ChangesExplorerViewExpandableListAdapter(Activity context, List<ChangeInfo> changeInfos) {
        this.context = context;
        prepareChildrenHeadersMap();
        groups = ChangeInfoHelper.getGroupsHeaders(changeInfos);
        children = ChangeInfoHelper.getChildren(changeInfos, childrenHeadersMap);
    }

    private void prepareChildrenHeadersMap(){
        Resources resources = context.getResources();
        this.childrenHeadersMap = new HashMap<ChangeInfoHelper.ChildrenHeaders, String>();
        this.childrenHeadersMap.put(ChangeInfoHelper.ChildrenHeaders.SUBJECT, resources.getString(R.string.pl_agh_edu_mobilecodereviewer_ChangesExplorer_changeInfo_subject));
        this.childrenHeadersMap.put(ChangeInfoHelper.ChildrenHeaders.STATUS, resources.getString(R.string.pl_agh_edu_mobilecodereviewer_ChangesExplorer_changeInfo_status));
        this.childrenHeadersMap.put(ChangeInfoHelper.ChildrenHeaders.OWNER, resources.getString(R.string.pl_agh_edu_mobilecodereviewer_ChangesExplorer_changeInfo_owner));
        this.childrenHeadersMap.put(ChangeInfoHelper.ChildrenHeaders.PROJECT, resources.getString(R.string.pl_agh_edu_mobilecodereviewer_ChangesExplorer_changeInfo_project));
        this.childrenHeadersMap.put(ChangeInfoHelper.ChildrenHeaders.BRANCH, resources.getString(R.string.pl_agh_edu_mobilecodereviewer_ChangesExplorer_changeInfo_branch));
        this.childrenHeadersMap.put(ChangeInfoHelper.ChildrenHeaders.UPDATED, resources.getString(R.string.pl_agh_edu_mobilecodereviewer_ChangesExplorer_changeInfo_updated));
        this.childrenHeadersMap.put(ChangeInfoHelper.ChildrenHeaders.SIZE, resources.getString(R.string.pl_agh_edu_mobilecodereviewer_ChangesExplorer_changeInfo_size));
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return children.get(groups.get(groupPosition).second).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return children.get(groups.get(groupPosition).second).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_changes_explorer_group, null);

            Button goButton = (Button) convertView.findViewById(R.id.changesExplorerGroupGoButton);
            goButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ChangesExplorer) context).showChangeDetails(groups.get(groupPosition).second);
                }
            });
        }

        String header = groups.get(groupPosition).first;
        TextView headerView = (TextView) convertView.findViewById(R.id.changesExplorerGroupLabel);
        headerView.setText(header);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_changes_explorer_child, null);
        }

        String label = children.get(groups.get(groupPosition).second).get(childPosition).first;
        String value = children.get(groups.get(groupPosition).second).get(childPosition).second;

        TextView childLabel = (TextView) convertView.findViewById(R.id.changesExplorerChildLabel);
        TextView childValue = (TextView) convertView.findViewById(R.id.changesExplorerChildValue);

        childLabel.setText(label);
        childValue.setText(value);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
