package pl.edu.agh.mobilecodereviewer.view.api;

import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.model.MergeableInfo;

public interface ChangeInfoTabView {

    void showInfo(ChangeInfo changeInfo, MergeableInfo mergeableInfo, String topic);

}
