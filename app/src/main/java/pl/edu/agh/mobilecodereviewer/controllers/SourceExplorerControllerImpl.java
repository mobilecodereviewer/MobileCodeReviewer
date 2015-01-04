package pl.edu.agh.mobilecodereviewer.controllers;

import com.google.common.io.Files;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import pl.edu.agh.mobilecodereviewer.controllers.api.SourceExplorerController;
import pl.edu.agh.mobilecodereviewer.dao.api.ChangeInfoDAO;
import pl.edu.agh.mobilecodereviewer.dao.api.SourceCodeDAO;
import pl.edu.agh.mobilecodereviewer.model.ChangeStatus;
import pl.edu.agh.mobilecodereviewer.model.Comment;
import pl.edu.agh.mobilecodereviewer.model.DiffLineType;
import pl.edu.agh.mobilecodereviewer.model.DiffedLine;
import pl.edu.agh.mobilecodereviewer.model.FileInfo;
import pl.edu.agh.mobilecodereviewer.model.SourceCode;
import pl.edu.agh.mobilecodereviewer.model.SourceCodeDiff;
import pl.edu.agh.mobilecodereviewer.model.utilities.SourceCodeHelper;
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
@Singleton
public class SourceExplorerControllerImpl implements SourceExplorerController{

    private static final String NO_PREVIOUS_CHANGE_FOUND = "No previous change found";
    private static final String NO_NEXT_CHANGE_FOUND = "No next change found";
    private static final String NO_COMMENTS_IN_LINE = "No comments in selected line";
    private static final String INAPROPRIATE_LINE_FOR_COMMENT_INSERTION = "Comment cannot be inserted to skipped or removed line";
    private static final String INAPPROPRIATE_LINE_FOR_GETTING_LIST_OF_COMMENT = "Comments are not displayed from deleted or skipped lines";

    /**
     * Object gives information about source code
     */
    @Inject
    SourceCodeDAO sourceCodeDAO;

    @Inject
    ChangeInfoDAO changeInfoDAO;

    boolean isDiffView = true;
    boolean showLineNumbers = false;
    int currentSelectedLine = -1;

    private SourceExplorerView view;
    private String change_id;
    private String revision_id;
    private String file_id;
    private ChangeStatus change_status;

    private Map<String, SourceCode> sourceCodeMap = new HashMap<>();
    private Map<String, SourceCodeDiff> sourceCodeDiffMap = new HashMap<>();

    private List<FileInfo> fileInfos;
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

    /**
     * Invoked by ModifiedFilesTab before navigating to source explorer.
     * @param fileInfos
     * @param status
     */
    @Override
    public void preInitialize(List<FileInfo> fileInfos, ChangeStatus status) {
        this.fileInfos = fileInfos;
        this.change_status = status;
    }

    @Override
    public void initializeData(SourceExplorerView view, int fileIndex) {
        this.view = view;
        FileInfo info = fileInfos.get(fileIndex);
        this.change_id = info.getChangeId();
        this.revision_id = info.getRevisionId();
        this.file_id = info.getFileName();
    }

    @Override
    public void initializeView() {
        String fileNameWithExtension = Files.getNameWithoutExtension(file_id) ;
        if ( Files.getFileExtension(file_id) != null && !Files.getFileExtension(file_id).equals(""))
            fileNameWithExtension += "." + Files.getFileExtension(file_id);
        view.setTitle(fileNameWithExtension);
        updateAppropriateSourceCodeMode();
    }

    @Override
    public void toggleDiffView() {
        showLineNumbers = false;
        currentSelectedLine = -1;
        view.clearSourceCode();
        isDiffView = !isDiffView;
        updateAppropriateSourceCodeMode();
    }

    private void updateAppropriateSourceCodeMode() {
        if (isDiffView) {
            updateSourceCodeDiff();
        } else {
            updateSourceCode();
        }
    }

    private SourceCode getSourceCode() {
        String uid = getUID(revision_id, change_id, file_id);

        if(!sourceCodeMap.containsKey(uid)) {
            Map<String, List<Comment>> pendingComments = changeInfoDAO.getPendingComments(change_id, revision_id);
            List<Comment> pendingCommentsForFile = pendingComments != null ? pendingComments.get(file_id) : null;

            sourceCodeMap.put(uid, sourceCodeDAO.getSourceCode(change_id, revision_id, file_id, pendingCommentsForFile));
        }

        return sourceCodeMap.get(uid);
    }

    private SourceCodeDiff getSourceCodeDiff() {
        String uid = getUID(revision_id, change_id, file_id);

        if(!sourceCodeDiffMap.containsKey(uid)) {
            sourceCodeDiffMap.put(uid, sourceCodeDAO.getSourceCodeDiff(change_id, revision_id, file_id));
        }

        return sourceCodeDiffMap.get(uid);
    }

    private void updateSourceCode() {
        SourceCode sourceCode = getSourceCode();

        view.clearSourceCode();
        view.showSourceCode(file_id,sourceCode);
        view.setInterfaceForCode();
    }


    private void updateSourceCodeDiff() {
        SourceCodeDiff sourceCodeDiff = getSourceCodeDiff();
        SourceCode sourceCode = getSourceCode();

        view.showSourceCodeDiff(file_id,sourceCodeDiff, SourceCodeHelper.getHasLineComments(sourceCode), SourceCodeHelper.getHasLinePendingComments(sourceCode) );
        view.setInterfaceForDiff();
    }

    private int calculateLineNumberFromListPosition(int lineNumber) {
        if (isDiffView) {
            DiffedLine line = sourceCodeDiffMap.get(getUID(revision_id, change_id, file_id)).getLine(lineNumber);
            if (line.getLineType() == DiffLineType.SKIPPED || line.getLineType() == DiffLineType.REMOVED) {
                return -1;
            }

            return line.getNewLineNumber()+1;
        } else {
            return lineNumber+1;
        }
    }

    @Override
    public void insertComment(String content, int lineNumber) {
        int linenum = calculateLineNumberFromListPosition(lineNumber);
        if (linenum == -1) {
            view.showMessage(INAPROPRIATE_LINE_FOR_COMMENT_INSERTION);
            return;
        }

        Comment comment = new Comment(linenum, file_id, content, ConfigurationContainer.getInstance().getLoggedUser().getName(), (new Date()).toString());
        changeInfoDAO.putDraftComment(change_id, revision_id, comment);
        comment.setDraft(true);
        comment.setDraftId("current");

        getSourceCode().getLine(linenum).getComments().add(0, comment);
    }



    @Override
    public void setCurrentLinePosition(int currLine) {
        this.currentSelectedLine = currLine;
    }

    @Override
    public void navigateToNextChange() {
        int nextChangedLine = sourceCodeDiffMap.get(getUID(revision_id, change_id, file_id)).findNextChangedLine(currentSelectedLine + 1);
        if (nextChangedLine > -1) {
            view.gotoLine(nextChangedLine);
            currentSelectedLine = nextChangedLine;
        } else view.showMessage(NO_NEXT_CHANGE_FOUND);
    }

    @Override
    public void navigateToPrevChange() {
        int prevChangedLine = sourceCodeDiffMap.get(getUID(revision_id, change_id, file_id)).findPrevChangedLine(currentSelectedLine - 1);
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

    @Override
    public void setVisibilityOnSourceCodeNavigation() {
        if (isDiffView) {
            view.showNavigationButtons();
        } else
            view.hideNavigationButtons();
    }

    @Override
    public void showComments(int position) {
        int linenum = calculateLineNumberFromListPosition(position);
        if (linenum == -1) {
            view.showMessage(INAPPROPRIATE_LINE_FOR_GETTING_LIST_OF_COMMENT);
            return;
        }

        if (getSourceCode().getLine(linenum).hasComments()) {
            view.showCommentListDialog(getSourceCode().getLine(linenum));
        } else {
            view.showMessage(NO_COMMENTS_IN_LINE);
        }
    }

    @Override
    public void deleteFileComment(Comment comment) {
        changeInfoDAO.deleteFileComment(change_id, revision_id, file_id, comment);
        getSourceCode().getLine(comment.getLine()).getComments().remove(comment);

        view.dismissCommentListDialog();

        if(getSourceCode().getLine(comment.getLine()).getComments().size() != 0) {
            view.showCommentListDialog(getSourceCode().getLine(comment.getLine()));
        }
    }

    @Override
    public void updateFileComment(Comment comment, String content) {
        changeInfoDAO.updateFileComment(change_id, revision_id, file_id, comment, content);
        int updatedIndex = getSourceCode().getLine(comment.getLine()).getComments().indexOf(comment);
        getSourceCode().getLine(comment.getLine()).getComments().get(updatedIndex).setContent(content);
    }

    @Override
    public boolean fileExists(int index) {
        return fileInfos != null && fileInfos.size() - 1  >= index && index >= 0;
    }

    @Override
    public void clearCache() {
        sourceCodeMap.clear();
        sourceCodeDiffMap.clear();
    }

    private String getUID(String revision_id, String change_id, String file_id){
        return revision_id + "/" + change_id + "/" + file_id;
    }
}
















