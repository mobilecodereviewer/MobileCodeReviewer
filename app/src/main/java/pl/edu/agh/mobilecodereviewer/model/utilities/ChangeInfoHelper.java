package pl.edu.agh.mobilecodereviewer.model.utilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;

public final class ChangeInfoHelper {

    public static enum ChildrenHeaders {
        SUBJECT, STATUS, OWNER, PROJECT, BRANCH, UPDATED, SIZE;
    }

    public static Map<String, Map<ChildrenHeaders, String>> getChildren(List<ChangeInfo> changeInfoList){
        Map<String, Map<ChildrenHeaders, String>> childsMap = new HashMap<String, Map<ChildrenHeaders, String>>();

        for(ChangeInfo changeInfo : changeInfoList){
            childsMap.put(changeInfo.getChangeId(), getChildContent(changeInfo));
        }

        return childsMap;
    }

    private static Map<ChildrenHeaders, String> getChildContent(ChangeInfo changeInfo){
        Map<ChildrenHeaders, String> childContent = new HashMap<ChildrenHeaders, String>();

        childContent.put(ChildrenHeaders.SUBJECT, changeInfo.getSubject());
        childContent.put(ChildrenHeaders.STATUS, changeInfo.getStatus().toString());
        childContent.put(ChildrenHeaders.OWNER, changeInfo.getOwnerName());
        childContent.put(ChildrenHeaders.PROJECT, changeInfo.getProject());
        childContent.put(ChildrenHeaders.BRANCH, changeInfo.getBranch());
        childContent.put(ChildrenHeaders.UPDATED, changeInfo.getUpdated());
        if (changeInfo.getSize() != null)
            childContent.put(ChildrenHeaders.SIZE, changeInfo.getSize().toString());

        return childContent;
    }
}
