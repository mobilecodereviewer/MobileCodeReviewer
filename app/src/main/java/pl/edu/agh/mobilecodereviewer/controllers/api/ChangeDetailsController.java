package pl.edu.agh.mobilecodereviewer.controllers.api;

import java.util.Map;

import pl.edu.agh.mobilecodereviewer.view.api.ChangeDetailsView;

public interface ChangeDetailsController {

    void updateSetReviewPopup();

    void setReview(String message, Map<String, Integer> votes);

    void initializeData(ChangeDetailsView view,String changeId, String revisionId);
}
