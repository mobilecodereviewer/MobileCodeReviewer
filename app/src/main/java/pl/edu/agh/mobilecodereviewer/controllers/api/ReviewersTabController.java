package pl.edu.agh.mobilecodereviewer.controllers.api;

import pl.edu.agh.mobilecodereviewer.view.api.ReviewersTabView;

/**
 * ReviewersTab activity controller. Object which implements its
 * methods will be responsible for taking actions according to
 * activity events.
 *
 * @author AGH
 * @version 0.1
 * @since 0.3
 */
public interface ReviewersTabController {

    /**
     * Request for updating reviewers list.
     *
     * @param view View in which reviewers list will be shown
     * @param changeId id of change for which reviewers list will be shown
     */
    void updateReviewers(ReviewersTabView view, String changeId);

}
