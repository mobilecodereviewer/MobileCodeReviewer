package pl.edu.agh.mobilecodereviewer.controllers.api;

import pl.edu.agh.mobilecodereviewer.view.api.SourceExplorerView;

/**
 * SourceExplorer activity controller. Object which implements its
 * methods will be responsible for taking actions according to
 * activity events
 *
 * @author AGH
 * @version 0.1
 * @since 0.1
 */
public interface SourceExplorerController {

    /**
     * Method updates model and instruct view what to do , when
     * update source code is requested
     * @param view
     */
    void updateSourceCode(SourceExplorerView view,String change_id,String revision_id,String file_id);
}