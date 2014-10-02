package pl.edu.agh.mobilecodereviewer.controllers;

import javax.inject.Inject;

import pl.edu.agh.mobilecodereviewer.controllers.api.SourceExplorerController;
import pl.edu.agh.mobilecodereviewer.dao.api.SourceCodeDAO;
import pl.edu.agh.mobilecodereviewer.model.Comment;
import pl.edu.agh.mobilecodereviewer.model.SourceCode;
import pl.edu.agh.mobilecodereviewer.model.SourceCodeDiff;
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

    /**
     * Object gives information about source code
     */
    @Inject
    SourceCodeDAO sourceCodeDAO;

    boolean isDiffView = false;
    boolean isAddingCommentOptionsVisible = false;
    int currentSelectedLine = -1;

    private SourceExplorerView view;
    private String change_id;
    private String revision_id;
    private String file_id;

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
        view.clearLines();
        isDiffView = !isDiffView;
        if (isDiffView) {
            updateSourceCodeDiff();
        } else {
            updateSourceCode();
        }
        isAddingCommentOptionsVisible = false;
    }

    /**
     * Obtains source code with comments and line numbers and informs view to show it.
     *
     * @param view View in which source code will be shown
     * @param change_id id of change containing revision with file
     * @param revision_id id of revision containing file
     * @param file_id id of file for which source code will be shown
     */
    @Override
    public void updateSourceCode() {
        SourceCode sourceCode = sourceCodeDAO.getSourceCode(change_id,revision_id,file_id);

        view.showSourceCode(sourceCode);
        view.setInterfaceForCode();
    }

    @Override
    public void updateSourceCodeDiff() {
        SourceCodeDiff sourceCodeDiff = sourceCodeDAO.getSourceCodeDiff(change_id, revision_id, file_id);

        view.showSourceCodeDiff(sourceCodeDiff );
        view.setInterfaceForDiff();
    }

    @Override
    public void insertComment(String content) {
        Comment comment = new Comment(currentSelectedLine, file_id, content);

        //sourceCodeDAO.putFileComment(change_id, revision_id, comment);
        //TODO zahardcodowana wartosc, z normalnym revision_id byl bug trzeba to zmienic , ale na ten moment nie ma czasu....
        sourceCodeDAO.putFileComment(change_id, "1", comment);
        updateSourceCode();
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
        currentSelectedLine = currLine;
    }

}
















