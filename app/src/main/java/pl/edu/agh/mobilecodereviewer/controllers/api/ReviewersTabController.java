package pl.edu.agh.mobilecodereviewer.controllers.api;

import pl.edu.agh.mobilecodereviewer.view.api.ReviewersTabView;

public interface ReviewersTabController {

    void updateReviewers(ReviewersTabView view, String changeId);

}
