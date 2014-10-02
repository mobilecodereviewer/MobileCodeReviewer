package pl.edu.agh.mobilecodereviewer.view.api;

import java.util.List;

import pl.edu.agh.mobilecodereviewer.model.ApprovalInfo;
import pl.edu.agh.mobilecodereviewer.model.LabelInfo;

/**
 * Created by d00d171 on 2014-10-01.
 */
public interface ChangeDetailsView {

    void showSetReviewPopup(List<LabelInfo> approvalInfoList);

}
