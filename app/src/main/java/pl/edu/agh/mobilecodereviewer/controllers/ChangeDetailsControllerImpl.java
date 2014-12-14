package pl.edu.agh.mobilecodereviewer.controllers;

import com.google.inject.Inject;

import java.util.List;
import java.util.Map;

import pl.edu.agh.mobilecodereviewer.controllers.api.ChangeDetailsController;
import pl.edu.agh.mobilecodereviewer.dao.api.ChangeInfoDAO;
import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.model.ChangeStatus;
import pl.edu.agh.mobilecodereviewer.model.Comment;
import pl.edu.agh.mobilecodereviewer.model.PermittedLabel;
import pl.edu.agh.mobilecodereviewer.utilities.ConfigurationContainer;
import pl.edu.agh.mobilecodereviewer.view.api.ChangeDetailsView;

public class ChangeDetailsControllerImpl implements ChangeDetailsController{

    @Inject
    private ChangeInfoDAO changeInfoDAO;

    private String changeId;

    private String revisionId;

    private ChangeDetailsView view;

    private ChangeInfo changeInfo;
    private List<PermittedLabel> permittedLabels;
    private String commitMessage;


    @Override
    public void initializeData(ChangeDetailsView view,String changeId, String revisionId) {
        this.view = view;
        this.changeId = changeId;
        this.revisionId = revisionId;

    }

    @Override
    public void refreshData() {
        updateData();
    }

    @Override
    public void deleteFileComment(String path, Comment comment) {
        view.preparePendingFilesCommentsList(changeInfoDAO.deleteFileComment(changeId, revisionId, path, comment));
    }

    @Override
    public void updateFileComment(String path, Comment comment, String content) {
        view.preparePendingFilesCommentsList(changeInfoDAO.updateFileComment(changeId, revisionId, path, comment, content));
    }

    @Override
    public void refreshGui() {
        updateGui();
    }


    private void updateGui() {
        view.setPutReviewVisibility(isPutReviewAvalaible());
        view.setTitle(getCommitMessageForChange(changeId));
    }

    @Override
    public void updateSetReviewPopup() {
        List<PermittedLabel> permittedLabels = changeInfoDAO.getPermittedLabels(changeId);
        Map<String, List<Comment>> pendingComments = changeInfoDAO.getPendingComments(changeId, revisionId);
        view.showSetReviewPopup(permittedLabels, pendingComments);
    }

    @Override
    public void setReview(String message, Map<String, Integer> votes) {
        changeInfoDAO.setReview(changeId,revisionId,message,votes);
    }

    public boolean isPutReviewAvalaible() {
        return ConfigurationContainer.getInstance().getConfigurationInfo().isAuthenticatedUser()
                && getChangeInfo().getStatus() != ChangeStatus.ABANDONED
                && getChangeInfo().getStatus() != ChangeStatus.MERGED;
    }

    private String cutCommitMessageToFirstLine(String commitMessageForChange) {
        int endOfFirstLine = commitMessageForChange.indexOf('\n');
        if (endOfFirstLine != -1)
            return commitMessageForChange.substring(0, endOfFirstLine);
        else
            return commitMessageForChange;
    }

    private void updateData() {
        changeInfo = changeInfoDAO.getChangeInfoById(changeId);
        permittedLabels =  changeInfoDAO.getPermittedLabels(changeId);
        commitMessage = cutCommitMessageToFirstLine(changeInfoDAO.getCommitMessageForChange(changeId));
    }

    private ChangeInfo getChangeInfo() {
        if (changeInfo == null) {
            changeInfo = changeInfoDAO.getChangeInfoById(changeId);
        }
        return changeInfo;
    }

    private String getCommitMessageForChange(String changeId) {
        if (commitMessage == null) {
            commitMessage = cutCommitMessageToFirstLine(changeInfoDAO.getCommitMessageForChange(changeId));
        }
        return commitMessage;
    }

}
