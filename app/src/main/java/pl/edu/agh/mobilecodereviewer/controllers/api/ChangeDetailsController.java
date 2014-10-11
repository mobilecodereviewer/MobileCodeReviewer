package pl.edu.agh.mobilecodereviewer.controllers.api;

import java.util.Map;

import pl.edu.agh.mobilecodereviewer.view.api.ChangeDetailsView;

public interface ChangeDetailsController {

    void updateSetReviewPopup(ChangeDetailsView view, String changeId);

    void setReview(String changeId, String revisionId, String message, Map<String, Integer> votes);
}
