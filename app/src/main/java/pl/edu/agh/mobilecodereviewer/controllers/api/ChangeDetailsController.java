package pl.edu.agh.mobilecodereviewer.controllers.api;

import java.util.Map;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.refresh.Refreshable;
import pl.edu.agh.mobilecodereviewer.model.Comment;

import pl.edu.agh.mobilecodereviewer.view.api.ChangeDetailsView;

public interface ChangeDetailsController extends Refreshable{

    void updateSetReviewPopup();

    void setReview(String message, Map<String, Integer> votes);

    void initializeData(ChangeDetailsView view,String changeId, String revisionId);

    void deleteFileComment(String path, Comment comment);

    void updateFileComment(String path, Comment comment, String content);
}
