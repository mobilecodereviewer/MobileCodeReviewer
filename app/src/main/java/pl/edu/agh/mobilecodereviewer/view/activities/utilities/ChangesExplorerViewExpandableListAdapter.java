package pl.edu.agh.mobilecodereviewer.view.activities.utilities;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.dao.gerrit.tools.Pair;
import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.model.utilities.ChangeInfoHelper;
import pl.edu.agh.mobilecodereviewer.view.activities.ChangesExplorer;

public class ChangesExplorerViewExpandableListAdapter extends BaseExpandableListAdapter{

    private final Activity context;

    private final List<Pair<String, String>> groups;

    private final Map<String, Map<ChangeInfoHelper.ChildrenHeaders, String>> children;

    public ChangesExplorerViewExpandableListAdapter(Activity context, List<ChangeInfo> changeInfos){
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
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return children.get(groups.get(groupPosition).first);
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

        if(convertView == null){
            convertView = inflater.inflate(R.layout.layout_changes_explorer_group, null);

            Button goButton = (Button) convertView.findViewById(R.id.changesExplorerGroupGoButton);
            goButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ChangesExplorer) context).showChangeDetails(groups.get(groupPosition).first);
                }
            });
        }

        String changeSubject = groups.get(groupPosition).second;
        TextView changeSubjectView = (TextView) convertView.findViewById(R.id.changesExplorerGroupLabel);
        changeSubjectView.setText(changeSubject);

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

        Map<ChangeInfoHelper.ChildrenHeaders, String> childValues = children.get(groups.get(groupPosition).first);
        subject.setText(childValues.get(ChangeInfoHelper.ChildrenHeaders.SUBJECT));
        status.setText(childValues.get(ChangeInfoHelper.ChildrenHeaders.STATUS));
        owner.setText(childValues.get(ChangeInfoHelper.ChildrenHeaders.OWNER));
        project.setText(childValues.get(ChangeInfoHelper.ChildrenHeaders.PROJECT));
        branch.setText(childValues.get(ChangeInfoHelper.ChildrenHeaders.BRANCH));
        updated.setText(childValues.get(ChangeInfoHelper.ChildrenHeaders.UPDATED));
        size.setText(childValues.get(ChangeInfoHelper.ChildrenHeaders.SIZE));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
