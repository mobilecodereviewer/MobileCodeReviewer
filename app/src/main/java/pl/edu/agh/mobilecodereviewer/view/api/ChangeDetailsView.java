package pl.edu.agh.mobilecodereviewer.view.api;

import java.util.List;
import java.util.Map;

import pl.edu.agh.mobilecodereviewer.model.Comment;
import pl.edu.agh.mobilecodereviewer.model.PermittedLabel;

public interface ChangeDetailsView {

    void showSetReviewPopup(List<PermittedLabel> permittedLabels, Map<String, List<Comment>> pendingComments);

    void setPutReviewVisibility(boolean putReviewVisibility);

    void setTitle(String titleText);

    void preparePendingFilesCommentsList(Map<String, List<Comment>> pendingComments);

    void onSubmitError(String message);

    void onSubmitSuccess();
}
