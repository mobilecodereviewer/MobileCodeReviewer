package pl.edu.agh.mobilecodereviewer.view.api;

import java.util.List;
import java.util.Map;

import pl.edu.agh.mobilecodereviewer.model.ApprovalInfo;
import pl.edu.agh.mobilecodereviewer.model.LabelInfo;
import pl.edu.agh.mobilecodereviewer.model.PermittedLabel;

public interface ChangeDetailsView {

    void showSetReviewPopup(List<PermittedLabel> permittedLabels);

}
