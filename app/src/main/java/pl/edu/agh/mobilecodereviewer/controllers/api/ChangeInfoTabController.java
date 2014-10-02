package pl.edu.agh.mobilecodereviewer.controllers.api;

import pl.edu.agh.mobilecodereviewer.view.api.ChangeInfoTabView;

/**
 * ChangeInfoTab activity controller. Object which implements its
 * methods will be responsible for taking actions according to
 * activity events.
 *
 * @author AGH
 * @version 0.1
 * @since 0.3
 */
public interface ChangeInfoTabController {

    /**
     * Request for updating information about change.
     *
     * @param view View in which information will be shown
     * @param changeId id of change for which information will be shown
     */
    void updateInfo(ChangeInfoTabView view, String changeId);
}
