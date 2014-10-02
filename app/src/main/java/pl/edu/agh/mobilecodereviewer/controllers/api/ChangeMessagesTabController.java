package pl.edu.agh.mobilecodereviewer.controllers.api;

import pl.edu.agh.mobilecodereviewer.view.api.ChangeMessagesTabView;

/**
 * ChangeMessagesTab activity controller. Object which implements its
 * methods will be responsible for taking actions according to
 * activity events.
 *
 * @author AGH
 * @version 0.1
 * @since 0.3
 */
public interface ChangeMessagesTabController {

    /**
     * Request for updating associated with changes.
     *
     * @param view View in which messages will be shown
     * @param changeId id of change for which messages will be shown
     */
    void updateMessages(ChangeMessagesTabView view, String changeId);

}
