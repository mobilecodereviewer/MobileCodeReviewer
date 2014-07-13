package pl.edu.agh.mobilecodereviewer.view.activities.utilities;

import android.app.Activity;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.model.utilities.ChangeInfoHelper;
import pl.edu.agh.mobilecodereviewer.view.activities.ChangesExplorer;

public class ChangeExplorerViewExpandableListAdapter extends BaseExpandableListAdapter{

    private final Activity context;

    private final List<Pair<String, String>> groups;

    private final Map<String, List<Pair<String, String>>> children;

    public ChangeExplorerViewExpandableListAdapter(Activity context, List<ChangeInfo> changeInfos){
        this.context = context;
        groups = ChangeInfoHelper.getGroupsHeaders(changeInfos);
        children = ChangeInfoHelper.getChildren(changeInfos);
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

    /*

     if (convertView == null) {
        View convertView = View.inflate(getApplicationContext(), R.layout.group_layout, null);
        Button addButton = (Button)convertView.findViewById(R.id.addButton);

        addButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // your code to add to the child list
            }
        });
    }
    TextView textView = (TextView)convertView.findViewById(R.id.text1);
    textView.setText(getGroup(groupPosition).toString());
    return convertView;

     */

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        if(convertView == null){
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

        if(convertView == null){
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
