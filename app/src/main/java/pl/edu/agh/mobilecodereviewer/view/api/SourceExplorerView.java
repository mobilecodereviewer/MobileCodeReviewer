package pl.edu.agh.mobilecodereviewer.view.api;

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

    void clearSourceCode();

    /**
     * Update source code in the view from a given source code ({@link pl.edu.agh.mobilecodereviewer.model.SourceCode} )
     * @param file_path
     * @param sourceCode {@link pl.edu.agh.mobilecodereviewer.model.SourceCode}
     */
    void showSourceCode(String file_path, SourceCode sourceCode);

    void showSourceCodeDiff(String file_path,SourceCodeDiff sourceCodeDiff);

    void setInterfaceForCode();

    void setInterfaceForDiff();

    void showMessage(String message);

    void gotoLine(int line);
}
