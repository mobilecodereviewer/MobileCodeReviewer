package pl.edu.agh.mobilecodereviewer.controllers.api;

import pl.edu.agh.mobilecodereviewer.view.activities.ModifiedFilesTab;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.refresh.Refreshable;
import pl.edu.agh.mobilecodereviewer.view.api.ModifiedFilesTabView;

/**
 * ModifiedFilesTab activity controller. Object which implements its
 * methods will be responsible for taking actions according to
 * activity events.
 *
 * @author AGH
 * @version 0.1
 * @since 0.1
 */
public interface ModifiedFilesTabController extends Refreshable {

    void initializeData(ModifiedFilesTabView view, String changeId);

    void checkIfFilesPendingCommentsChanged();

}
