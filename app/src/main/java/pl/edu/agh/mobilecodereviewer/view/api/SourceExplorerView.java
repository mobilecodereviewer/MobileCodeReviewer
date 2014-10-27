package pl.edu.agh.mobilecodereviewer.view.api;

import pl.edu.agh.mobilecodereviewer.model.Line;
import pl.edu.agh.mobilecodereviewer.model.SourceCode;
import pl.edu.agh.mobilecodereviewer.model.SourceCodeDiff;

/**
 * Represents the view which will show source code with comments
 *
 * @author AGH
 * @version 0.1
 * @since 0.1
 */
public interface SourceExplorerView {

    void showCommentListDialog(Line line);

    void clearSourceCode();

    /**
     * Update source code in the view from a given source code ({@link pl.edu.agh.mobilecodereviewer.model.SourceCode} )
     * @param sourceCode {@link pl.edu.agh.mobilecodereviewer.model.SourceCode}
     */
    void showSourceCode(SourceCode sourceCode);

    void showSourceCodeDiff(SourceCodeDiff sourceCodeDiff);

    void setInterfaceForCode();

    void hideNavigationButtons();

    void setInterfaceForDiff();

    void showNavigationButtons();

    void showMessage(String message);

    void gotoLine(int line);

    void setTitle(String fileName);
}
