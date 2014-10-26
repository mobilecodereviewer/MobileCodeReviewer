package pl.edu.agh.mobilecodereviewer.controllers;

import com.google.inject.Inject;

import java.util.List;
import java.util.Map;

import pl.edu.agh.mobilecodereviewer.controllers.api.ChangeDetailsController;
import pl.edu.agh.mobilecodereviewer.dao.api.ChangeInfoDAO;
import pl.edu.agh.mobilecodereviewer.model.PermittedLabel;
import pl.edu.agh.mobilecodereviewer.view.api.ChangeDetailsView;

public class ChangeDetailsControllerImpl implements ChangeDetailsController {

    @Inject
    private ChangeInfoDAO changeInfoDAO;

    @Override
    public void updateSetReviewPopup(ChangeDetailsView view, String changeId) {
        List<PermittedLabel> permittedLabels = changeInfoDAO.getPermittedLabels(changeId);
        view.showSetReviewPopup(permittedLabels);
    }

    @Override
    public void setReview(String changeId, String revisionId, String message, Map<String, Integer> votes) {
        changeInfoDAO.setReview(changeId,revisionId,message,votes);
    }
}
