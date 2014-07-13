package pl.edu.agh.mobilecodereviewer.model.utilities;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.edu.agh.mobilecodereviewer.dao.gerrit.tools.Pair;
import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;

public final class ChangeInfoHelper {

    public static enum ChildrenHeaders {
        SUBJECT, STATUS, OWNER, PROJECT, BRANCH, UPDATED, SIZE;
    }

    public static List<Pair<String, String>> getGroupsHeaders(List<ChangeInfo> changeInfoList) {
        return Lists.transform(changeInfoList,
                new Function<ChangeInfo, Pair<String, String>>() {
                    @Override
                    public Pair<String, String> apply(ChangeInfo from) {
                        return getGroupHeader(from);
                    }
                }
        );
    }

    public static Map<String, List<Pair<String, String>>> getChildren(List<ChangeInfo> changeInfoList, Map<ChildrenHeaders, String> childrenHeadersMap){
        Map<String, List<Pair<String, String>>> childsMap = new HashMap<String, List<Pair<String, String>>>();

        for(ChangeInfo changeInfo : changeInfoList){
            childsMap.put(changeInfo.getChangeId(), getChildContent(changeInfo, childrenHeadersMap));
        }

        return childsMap;
    }

    private static Pair<String, String> getGroupHeader(ChangeInfo changeInfo){
        return new Pair(changeInfo.getSubject(), changeInfo.getChangeId());
    }

    private static List<Pair<String, String>> getChildContent(ChangeInfo changeInfo, Map<ChildrenHeaders, String> childrenHeadersMap){
        List<Pair<String, String>> childContent = new ArrayList<Pair<String, String>>();

        childContent.add(new Pair(childrenHeadersMap.get(ChildrenHeaders.SUBJECT), changeInfo.getSubject()));
        childContent.add(new Pair(childrenHeadersMap.get(ChildrenHeaders.STATUS), changeInfo.getStatus()));
        childContent.add(new Pair(childrenHeadersMap.get(ChildrenHeaders.OWNER), changeInfo.getOwnerName()));
        childContent.add(new Pair(childrenHeadersMap.get(ChildrenHeaders.PROJECT), changeInfo.getProject()));
        childContent.add(new Pair(childrenHeadersMap.get(ChildrenHeaders.BRANCH), changeInfo.getBranch()));
        childContent.add(new Pair(childrenHeadersMap.get(ChildrenHeaders.UPDATED), changeInfo.getUpdated()));
        childContent.add(new Pair(childrenHeadersMap.get(ChildrenHeaders.SIZE), changeInfo.getSize().toString()));

        return childContent;
    }
}
