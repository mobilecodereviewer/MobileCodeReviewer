package pl.edu.agh.mobilecodereviewer.controllers.api;

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
public interface ModifiedFilesTabController {

    /**
     * Request for updating list of modified files.
     *
     * @param view View in which modified files will be shown
     * @param changeId id of change for which modified files will be shown
     */
    void updateFiles(ModifiedFilesTabView view, String changeId);
}
