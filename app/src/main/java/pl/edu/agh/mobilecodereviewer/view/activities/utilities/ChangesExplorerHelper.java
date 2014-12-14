package pl.edu.agh.mobilecodereviewer.view.activities.utilities;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.model.LabelInfo;

public class ChangesExplorerHelper {

    public static TextView makeLabelTextView(Context context, String text, Integer backgroundResourceId, Integer textColor){
        TextView textView = new TextView(context);
        textView.setText(text);
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(45, ViewGroup.LayoutParams.MATCH_PARENT );
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        textView.setPadding(5, 0, 5, 0);

        if(backgroundResourceId != null) {
            textView.setBackgroundResource(backgroundResourceId);
        }
        if(textColor != null){
            textView.setTextColor(textColor);
        }

        return textView;
    }

    public static List<String> getLabelsAbbreviationsList(List<ChangeInfo> changeInfoList){
        List<String> visibleLabels = new LinkedList<String>();
        for(ChangeInfo changeInfo : changeInfoList){
            for(LabelInfo labelInfo : changeInfo.getLabels()){
                if(!visibleLabels.contains(labelInfo.getAbbreviation())){
                    visibleLabels.add(labelInfo.getAbbreviation());
                }
            }
        }
        return visibleLabels;
    }

    public static Map<String, View> extractLabelsViewsMap(Context context, List<LabelInfo> labelInfos){
        Map<String, View> labelViewMap = new HashMap<>();

        for(LabelInfo labelInfo : labelInfos){
            String labelAbbr = labelInfo.getAbbreviation();
            Integer labelValueInt = labelInfo.getMinApprovalValueSet();
            String labelValueString = labelValueInt != null ? labelValueInt.toString() : "";

            if(labelInfo.isHasMinLabelValueApproval()){
                labelViewMap.put(labelAbbr, ChangesExplorerHelper.makeLabelTextView(context, labelValueString, R.color.red, null));
            } else if(labelInfo.isHasMaxLabelValueApproval()){
                labelViewMap.put(labelAbbr,ChangesExplorerHelper.makeLabelTextView(context, labelValueString, R.color.green, null));
            } else if(labelValueInt != null && labelValueInt > 0){
                Integer textColor = labelValueInt > 0 ? R.color.dark_green : R.color.dark_red;
                labelViewMap.put(labelAbbr,ChangesExplorerHelper.makeLabelTextView(context, labelValueString, null, context.getResources().getColor(textColor)));
            } else {
                labelViewMap.put(labelAbbr,ChangesExplorerHelper.makeLabelTextView(context, "", null, null));
            }
        }

        return labelViewMap;
    }
}
