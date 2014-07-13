package pl.edu.agh.mobilecodereviewer.view.api;

import java.util.List;

import pl.edu.agh.mobilecodereviewer.model.LabelInfo;

public interface ReviewersTabView {

    void showReviewers(List<LabelInfo> labels);

}
