package pl.edu.agh.mobilecodereviewer.controllers.api;


import pl.edu.agh.mobilecodereviewer.view.api.ChangesExplorerView;

/**
 * ChangeExplorrer activity controller. Object which implements its
 * methods will be responsible for taking actions according to
 * activity events
 * @author AGH
 * @version 0.1
 * @since 0.1
 */
public interface ChangesExplorerController {

    /**
     * Request for updating change list
     * @param view View in which changes will be shown
     */
    void updateChanges(ChangesExplorerView view);
}
