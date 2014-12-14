package pl.edu.agh.mobilecodereviewer.controllers.api;

import java.util.List;

import pl.edu.agh.mobilecodereviewer.model.ChangeStatus;
import pl.edu.agh.mobilecodereviewer.model.Comment;
import pl.edu.agh.mobilecodereviewer.model.FileInfo;
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

    void preInitialize(List<FileInfo> filesList, ChangeStatus status);

    void initializeData(SourceExplorerView view, int fileIndex);

    void insertComment(String content, int lineNumber);

    void setCurrentLinePosition(int currLine);

    void navigateToNextChange();

    void navigateToPrevChange();

    void toogleVisibilityOfLineNumbers(SourceCodeListAdapter sourceCodeListAdapter);

    boolean isAddingCommentAvalaible();

    void setVisibilityOnSourceCodeNavigation();

    void showComments(int position);

    void deleteFileComment(Comment comment);

    void updateFileComment(Comment comment, String content);

    boolean fileExists(int index);

}
