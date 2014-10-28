package pl.edu.agh.mobilecodereviewer.controllers.api;

import pl.edu.agh.mobilecodereviewer.view.activities.ChangeMessagesTab;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.refresh.Refreshable;
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
public interface ChangeMessagesTabController extends Refreshable{
    void initializeData(ChangeMessagesTabView view, String changeId);
}
