package pl.edu.agh.mobilecodereviewer.controllers.api;

import pl.edu.agh.mobilecodereviewer.view.api.CommitMessageTabView;

/**
 * CommitMessageTab activity controller. Object which implements its
 * methods will be responsible for taking actions according to
 * activity events.
 *
 * @author AGH
 * @version 0.1
 * @since 0.3
 */
public interface CommitMessageTabController {

    /**
     * Request for updating commit message.
     *
     * @param view View in which commit message will be shown
     * @param changeId id of change for which commit message will be shown
     */
    void updateMessage(CommitMessageTabView view, String changeId);

}
