package pl.edu.agh.mobilecodereviewer.controllers.api;

import pl.edu.agh.mobilecodereviewer.view.api.ModifiedFilesView;

/**
 * ModifiedFiles activity controller. Object which implements its
 * methods will be responsible for taking actions according to
 * activity events
 *
 * @author AGH
 * @version 0.1
 * @since 0.1
 */
public interface ModifiedFilesController {

    /**
     * Request for updating list of files.
     *
     * @param view View in which files will be shown
     */
    void updateFiles(ModifiedFilesView view);
}
