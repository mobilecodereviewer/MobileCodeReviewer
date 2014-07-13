package pl.edu.agh.mobilecodereviewer.controllers;

import com.google.inject.Inject;

import pl.edu.agh.mobilecodereviewer.controllers.api.ReviewersTabController;
import pl.edu.agh.mobilecodereviewer.dao.api.ChangeInfoDAO;
import pl.edu.agh.mobilecodereviewer.view.api.ReviewersTabView;

public class ReviewersTabControllerImpl implements ReviewersTabController{

    @Inject
    private ChangeInfoDAO changeInfoDAO;

    public ReviewersTabControllerImpl(){}

    public ReviewersTabControllerImpl(ChangeInfoDAO changeInfoDAO) {
        this.changeInfoDAO = changeInfoDAO;
    }

    @Override
    public void updateReviewers(ReviewersTabView view, String changeId) {
        view.showReviewers(changeInfoDAO.getLabels(changeId));
    }

    public ChangeInfoDAO getChangeInfoDAO() {
        return changeInfoDAO;
    }

    public void setChangeInfoDAO(ChangeInfoDAO changeInfoDAO) {
        this.changeInfoDAO = changeInfoDAO;
    }
}
