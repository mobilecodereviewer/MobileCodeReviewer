package pl.edu.agh.mobilecodereviewer.view.api;

import java.util.List;

import pl.edu.agh.mobilecodereviewer.model.ApprovalInfo;
import pl.edu.agh.mobilecodereviewer.model.LabelInfo;

public interface ChangeDetailsView {

    void showSetReviewPopup(List<LabelInfo> labelInfos);

}
