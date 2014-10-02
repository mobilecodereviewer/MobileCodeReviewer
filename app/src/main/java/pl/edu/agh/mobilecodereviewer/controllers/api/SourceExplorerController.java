package pl.edu.agh.mobilecodereviewer.controllers.api;

import pl.edu.agh.mobilecodereviewer.model.Comment;
import pl.edu.agh.mobilecodereviewer.view.activities.SourceExplorer;
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

    void initializeView();

    void toggleDiffView();

    /**
     * Method updates model and instruct view what to do , when
     * update source code is requested
     */
    void updateSourceCode();

    void updateSourceCodeDiff();

    void initializeData(SourceExplorerView view, String change_id, String revision_id, String file_id);

    void insertComment(String content);

    void cancelComment();

    void toggleCommentWriteMode();

    void setCurrentLinePosition(int currLine);
}
