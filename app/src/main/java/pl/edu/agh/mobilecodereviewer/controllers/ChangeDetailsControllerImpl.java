package pl.edu.agh.mobilecodereviewer.controllers;

import com.google.inject.Inject;

import java.util.List;
import java.util.Map;

import pl.edu.agh.mobilecodereviewer.controllers.api.ChangeDetailsController;
import pl.edu.agh.mobilecodereviewer.dao.api.ChangeInfoDAO;
import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.model.ChangeStatus;
import pl.edu.agh.mobilecodereviewer.model.PermittedLabel;
import pl.edu.agh.mobilecodereviewer.utilities.ConfigurationContainer;
import pl.edu.agh.mobilecodereviewer.view.api.ChangeDetailsView;

public class ChangeDetailsControllerImpl implements ChangeDetailsController {

    @Inject
    private ChangeInfoDAO changeInfoDAO;

    private String changeId;

    private String revisionId;

    private ChangeDetailsView view;

    private ChangeInfo changeInfo;


    @Override
    public void initializeData(ChangeDetailsView view,String changeId, String revisionId) {
        this.view = view;
        this.changeId = changeId;
        this.revisionId = revisionId;

        view.setPutReviewVisibility(isPutReviewAvalaible());
        view.setTitle(getCommitMessageForChange(changeId));
    }

    private String getCommitMessageForChange(String changeId) {
        return cutCommitMessageToFirstLine(changeInfoDAO.getCommitMessageForChange(changeId));
    }

    private String cutCommitMessageToFirstLine(String commitMessageForChange) {
        int endOfFirstLine = commitMessageForChange.indexOf('\n');
        if (endOfFirstLine != -1)
            return commitMessageForChange.substring(0, endOfFirstLine);
        else
            return commitMessageForChange;
    }

    @Override
    public void updateSetReviewPopup() {
        List<PermittedLabel> permittedLabels = changeInfoDAO.getPermittedLabels(changeId);
        view.showSetReviewPopup(permittedLabels);
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

    private ChangeInfo getChangeInfo() {
        if (changeInfo == null) {
            changeInfo = changeInfoDAO.getChangeInfoById(changeId);
        }
        return changeInfo;
    }
}
