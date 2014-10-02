package pl.edu.agh.mobilecodereviewer.controllers;

import com.google.inject.Inject;

import pl.edu.agh.mobilecodereviewer.controllers.api.ChangeDetailsController;
import pl.edu.agh.mobilecodereviewer.dao.api.ChangeInfoDAO;
import pl.edu.agh.mobilecodereviewer.view.api.ChangeDetailsView;

/**
 * Created by d00d171 on 2014-10-01.
 */
public class ChangeDetailsControllerImpl implements ChangeDetailsController {

    @Inject
    private ChangeInfoDAO changeInfoDAO;

    @Override
    public void updateSetReviewPopup(ChangeDetailsView view, String changeId) {
        view.showSetReviewPopup(changeInfoDAO.getLabels(changeId));
    }
}
