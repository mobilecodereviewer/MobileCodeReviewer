package pl.edu.agh.mobilecodereviewer.controllers.api;

import pl.edu.agh.mobilecodereviewer.view.activities.ChangeInfoTab;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.refresh.Refreshable;
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
public interface ChangeInfoTabController extends Refreshable{
    void initializeData(ChangeInfoTabView changeInfoTab, String change_id);
}
