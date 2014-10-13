package pl.edu.agh.mobilecodereviewer.controllers.api;

import pl.edu.agh.mobilecodereviewer.view.activities.ChangesExplorer;
import pl.edu.agh.mobilecodereviewer.view.api.ChangesExplorerView;

/**
 * ChangeExplorer activity controller. Object which implements its
 * methods will be responsible for taking actions according to
 * activity events.
 *
 * @author AGH
 * @version 0.1
 * @since 0.1
 */
public interface ChangesExplorerController {
    void initializeData(ChangesExplorerView changesExplorerView);

    void updateChanges();

    void search(String query);
}
