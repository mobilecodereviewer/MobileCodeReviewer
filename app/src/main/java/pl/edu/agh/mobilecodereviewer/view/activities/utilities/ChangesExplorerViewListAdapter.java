package pl.edu.agh.mobilecodereviewer.view.activities.utilities;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.model.LabelInfo;


public class ChangesExplorerViewListAdapter extends ArrayAdapter<ChangeInfo> {

    private Activity context;

    private List<ChangeInfo> content;

    private List<String> visibleLabels;

    public ChangesExplorerViewListAdapter(Activity context, List<ChangeInfo> content, List<String> visibleLabels) {
        super(context, R.layout.layout_changes_explorer_item, content);
        this.context = context;
        this.content = content;
        this.visibleLabels = visibleLabels;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View itemView = inflater.inflate(R.layout.layout_changes_explorer_item, null, true);

        ChangeInfo currentChangeInfo = content.get(position);

        TextView subjectView = (TextView) itemView.findViewById(R.id.changesExplorerListItem);
        setSubject(subjectView, currentChangeInfo.getSubject());

        LinearLayout labelValuesLayout = (LinearLayout) itemView.findViewById(R.id.labelsLinearLayout);
        List<View> labelsViews = extractLabelsViews(currentChangeInfo.getLabels());
        for(View labelView : labelsViews){
            labelView.setFocusableInTouchMode(false);
            labelView.setFocusable(false);
            labelValuesLayout.addView(labelView);
        }

        return itemView;
    }

    private List<View> extractLabelsViews(List<LabelInfo> labelInfos){
        Map<String, View> labelViewMap = ChangesExplorerHelper.extractLabelsViewsMap(context, labelInfos);

        List<View> result = new LinkedList<View>();
        for(String labelAbbr : visibleLabels){
            if(labelViewMap.containsKey(labelAbbr)){
                result.add(labelViewMap.get(labelAbbr));
            } else {
                result.add(ChangesExplorerHelper.makeLabelTextView(context, "", R.color.grey, null));
            }
        }
        return result;
    }

    protected void setSubject(TextView view, String subject){
        view.setText(subject);
    }

}
