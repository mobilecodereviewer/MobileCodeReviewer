package pl.edu.agh.mobilecodereviewer.controllers.api;

import pl.edu.agh.mobilecodereviewer.model.Comment;
import pl.edu.agh.mobilecodereviewer.view.activities.SourceExplorer;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.SourceCodeListAdapter;
import pl.edu.agh.mobilecodereviewer.view.api.SourceExplorerView;

/**
 * SourceExplorer activity controller. Object which implements its
 * methods will be responsible for taking actions according to
 * activity events.
 *
 * @author AGH
 * @version 0.1
 * @since 0.1
 */
public interface SourceExplorerController {

    void initializeView();

    void toggleDiffView();

    void initializeData(SourceExplorerView view, String change_id, String revision_id, String file_id, String changeStatus);

    void insertComment(String content, int lineNumber);

    void setCurrentLinePosition(int currLine);

    void navigateToNextChange();

    void navigateToPrevChange();

    void toogleVisibilityOfLineNumbers(SourceCodeListAdapter sourceCodeListAdapter);

    boolean isAddingCommentAvalaible();

    void setVisibilityOnSourceCodeNavigation();
}
