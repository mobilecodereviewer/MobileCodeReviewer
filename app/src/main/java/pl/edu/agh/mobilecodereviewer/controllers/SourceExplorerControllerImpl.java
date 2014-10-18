package pl.edu.agh.mobilecodereviewer.controllers;

import javax.inject.Inject;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.controllers.api.SourceExplorerController;
import pl.edu.agh.mobilecodereviewer.dao.api.SourceCodeDAO;
import pl.edu.agh.mobilecodereviewer.model.Comment;
import pl.edu.agh.mobilecodereviewer.model.DiffLineType;
import pl.edu.agh.mobilecodereviewer.model.DiffedLine;
import pl.edu.agh.mobilecodereviewer.model.SourceCode;
import pl.edu.agh.mobilecodereviewer.model.SourceCodeDiff;
import pl.edu.agh.mobilecodereviewer.view.api.SourceExplorerView;
import roboguice.inject.InjectResource;

/**
 * Implementation for controlling action after event in source explorer
 * activity took place
 *
 * @author AGH
 * @version 0.1
 * @since 0.2
 */
public class SourceExplorerControllerImpl implements SourceExplorerController{

    /**
     * Object gives information about source code
     */
    @Inject
    SourceCodeDAO sourceCodeDAO;

    public static final String INAPROPRIATE_LINE_FOR_COMMENT_INSERTION = "Comment cannot be inserted to skipped or removed line";

    boolean isDiffView = false;
    boolean isAddingCommentOptionsVisible = false;
    int currentSelectedLine = -1;

    private SourceExplorerView view;
    private String change_id;
    private String revision_id;
    private String file_id;

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
    public void initializeData(SourceExplorerView view, String change_id, String revision_id, String file_id) {
        this.view = view;
        this.change_id = change_id;
        this.revision_id = revision_id;
        this.file_id = file_id;
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


    @Override
    public void updateSourceCode() {
        SourceCode sourceCode = getSourceCode();

        view.clearSourceCode();
        view.showSourceCode(sourceCode);
        view.setInterfaceForCode();
    }



    @Override
    public void updateSourceCodeDiff() {
        SourceCodeDiff sourceCodeDiff = getSourceCodeDiff();

        view.showSourceCodeDiff(sourceCodeDiff );
        view.setInterfaceForDiff();
    }

    @Override
    public void insertComment(String content) {
        if (currentSelectedLine != -1) {
            DiffedLine line = sourceCodeDiff.getLine(currentSelectedLine);
            if (line.getLineType() == DiffLineType.SKIPPED || line.getLineType() == DiffLineType.REMOVED) {
                view.showMessage(INAPROPRIATE_LINE_FOR_COMMENT_INSERTION);
            }

            int linenum = line.getNewLineNumber();
            Comment comment = new Comment(linenum, file_id, content, null, null);

            sourceCodeDAO.putFileComment(change_id, revision_id, comment);
            sourceCode.getLine(linenum).getComments().add(comment);
            updateSourceCodeDiff();
        }
    }

    @Override
    public void cancelComment() {
        view.clearCommentContent();
    }

    @Override
    public void toggleCommentWriteMode() {
        isAddingCommentOptionsVisible = !isAddingCommentOptionsVisible;
        if ( isAddingCommentOptionsVisible ) {
            view.showCommentOptions();
        } else {
            view.clearCommentContent();
            view.hideCommentOptions();
        }
    }

    @Override
    public void setCurrentLinePosition(int currLine) {
        currentSelectedLine = currLine+1;
    }

    @Override
    public void navigateToNextChange() {
        int nextChangedLine = sourceCodeDiff.findNextChangedLine(currentSelectedLine+1);
        if (nextChangedLine > -1) {
            view.gotoLine(nextChangedLine);
            currentSelectedLine = nextChangedLine;
        }
    }

    @Override
    public void navigateToPrevChange() {
        int prevChangedLine = sourceCodeDiff.findPrevChangedLine(currentSelectedLine-1);
        if (prevChangedLine > -1) {
            view.gotoLine(prevChangedLine);
            currentSelectedLine = prevChangedLine;
        }
    }

}
















