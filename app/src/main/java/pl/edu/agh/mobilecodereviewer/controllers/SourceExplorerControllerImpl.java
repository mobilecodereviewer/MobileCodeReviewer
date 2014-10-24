package pl.edu.agh.mobilecodereviewer.controllers;

import java.util.Date;

import javax.inject.Inject;

import pl.edu.agh.mobilecodereviewer.controllers.api.SourceExplorerController;
import pl.edu.agh.mobilecodereviewer.dao.api.SourceCodeDAO;
import pl.edu.agh.mobilecodereviewer.model.ChangeStatus;
import pl.edu.agh.mobilecodereviewer.model.Comment;
import pl.edu.agh.mobilecodereviewer.model.DiffLineType;
import pl.edu.agh.mobilecodereviewer.model.DiffedLine;
import pl.edu.agh.mobilecodereviewer.model.SourceCode;
import pl.edu.agh.mobilecodereviewer.model.SourceCodeDiff;
import pl.edu.agh.mobilecodereviewer.utilities.ConfigurationContainer;
import pl.edu.agh.mobilecodereviewer.view.activities.utilities.SourceCodeListAdapter;
import pl.edu.agh.mobilecodereviewer.view.api.SourceExplorerView;

/**
 * Implementation for controlling action after event in source explorer
 * activity took place
 *
 * @author AGH
 * @version 0.1
 * @since 0.2
 */
public class SourceExplorerControllerImpl implements SourceExplorerController{

    public static final String NO_PREVIOUS_CHANGE_FOUND = "No previous change found";
    public static final String NO_NEXT_CHANGE_FOUND = "No next change found";
    /**
     * Object gives information about source code
     */
    @Inject
    SourceCodeDAO sourceCodeDAO;

    public static final String INAPROPRIATE_LINE_FOR_COMMENT_INSERTION = "Comment cannot be inserted to skipped or removed line";

    boolean isDiffView = false;
    boolean isAddingCommentOptionsVisible = false;
    boolean showLineNumbers = false;
    int currentSelectedLine = -1;

    private SourceExplorerView view;
    private String change_id;
    private String revision_id;
    private String file_id;
    private ChangeStatus change_status;

    private SourceCode sourceCode;
    private SourceCodeDiff sourceCodeDiff;
    /**
     * Simple constructor. Used by DI framework.
     */
    public SourceExplorerControllerImpl() {
    }

    /**
     * Construct object with given DAO.
     *
     * @param sourceCodeDAO {@link pl.edu.agh.mobilecodereviewer.dao.api.SourceCodeDAO} object used by controller to obtain source code.
     */
    public SourceExplorerControllerImpl(SourceExplorerView view, String change_id, String revision_id, String file_id, SourceCodeDAO sourceCodeDAO) {
        this.view = view;
        this.change_id = change_id;
        this.revision_id = revision_id;
        this.file_id = file_id;
        this.sourceCodeDAO = sourceCodeDAO;
    }


    @Override
    public void initializeData(SourceExplorerView view, String change_id, String revision_id, String file_id, String changeStatus) {
        this.view = view;
        this.change_id = change_id;
        this.revision_id = revision_id;
        this.file_id = file_id;
        this.change_status = ChangeStatus.createStatusFromString( changeStatus);
    }

    @Override
    public void initializeView() {
        updateSourceCode();
    }

    @Override
    public void toggleDiffView() {
        view.clearSourceCode();
        isDiffView = !isDiffView;
        if (isDiffView) {
            updateSourceCodeDiff();
        } else {
            updateSourceCode();
        }
        isAddingCommentOptionsVisible = false;
    }

    private SourceCode getSourceCode() {
        if (sourceCode == null) { // Lazy initialization
            sourceCode = sourceCodeDAO.getSourceCode(change_id, revision_id, file_id);
        }
        return sourceCode;
    }

    private SourceCodeDiff getSourceCodeDiff() {
        if (sourceCodeDiff == null) { // Lazy initialization
            sourceCodeDiff =  sourceCodeDAO.getSourceCodeDiff(change_id, revision_id, file_id);
        }
        return sourceCodeDiff;
    }

    private void updateSourceCode() {
        SourceCode sourceCode = getSourceCode();

        view.clearSourceCode();
        view.showSourceCode(file_id,sourceCode);
        view.setInterfaceForCode();
    }


    private void updateSourceCodeDiff() {
        SourceCodeDiff sourceCodeDiff = getSourceCodeDiff();

        view.showSourceCodeDiff(sourceCodeDiff );
        view.setInterfaceForDiff();
    }

    @Override
    public void insertComment(String content, int lineNumber) {
        int linenum;
        if (isDiffView) {
            DiffedLine line = sourceCodeDiff.getLine(lineNumber);
            if (line.getLineType() == DiffLineType.SKIPPED || line.getLineType() == DiffLineType.REMOVED) {
                view.showMessage(INAPROPRIATE_LINE_FOR_COMMENT_INSERTION);
                return;
            }

            linenum = line.getNewLineNumber()+1;
        } else {
            linenum = lineNumber+1;
        }
        Comment comment = new Comment(linenum, file_id, content, ConfigurationContainer.getInstance().getLoggedUser().getName(), (new Date()).toString());

        sourceCodeDAO.putFileComment(change_id, revision_id, comment);
        sourceCode.getLine(linenum).getComments().add(comment);
        if (isDiffView)
            updateSourceCodeDiff();
        else
            updateSourceCode();
    }

    @Override
    public void setCurrentLinePosition(int currLine) {
        this.currentSelectedLine = currLine;
    }

    @Override
    public void navigateToNextChange() {
        int nextChangedLine = sourceCodeDiff.findNextChangedLine(currentSelectedLine+1);
        if (nextChangedLine > -1) {
            view.gotoLine(nextChangedLine);
            currentSelectedLine = nextChangedLine;
        } else view.showMessage(NO_NEXT_CHANGE_FOUND);
    }

    @Override
    public void navigateToPrevChange() {
        int prevChangedLine = sourceCodeDiff.findPrevChangedLine(currentSelectedLine-1);
        if (prevChangedLine > -1) {
            view.gotoLine(prevChangedLine);
            currentSelectedLine = prevChangedLine;
        } else view.showMessage(NO_PREVIOUS_CHANGE_FOUND);
    }

    @Override
    public void toogleVisibilityOfLineNumbers(SourceCodeListAdapter sourceCodeListAdapter) {
        showLineNumbers = !showLineNumbers;
        if (showLineNumbers) {
            sourceCodeListAdapter.showLineNumbers();
        } else {
            sourceCodeListAdapter.hideLineNumbers();
        }
    }

    @Override
    public boolean isAddingCommentAvalaible() {
        return ConfigurationContainer.getInstance().getConfigurationInfo().isAuthenticatedUser()
                && change_status != ChangeStatus.ABANDONED && change_status != ChangeStatus.MERGED;
    }
}
















