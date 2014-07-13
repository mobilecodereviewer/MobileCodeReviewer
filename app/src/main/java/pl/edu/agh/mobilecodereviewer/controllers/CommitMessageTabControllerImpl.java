package pl.edu.agh.mobilecodereviewer.controllers;

import com.google.inject.Inject;

import pl.edu.agh.mobilecodereviewer.controllers.api.CommitMessageTabController;
import pl.edu.agh.mobilecodereviewer.dao.api.ChangeInfoDAO;
import pl.edu.agh.mobilecodereviewer.view.api.CommitMessageTabView;

public class CommitMessageTabControllerImpl implements CommitMessageTabController{

    @Inject
    private ChangeInfoDAO changeInfoDAO;

    public CommitMessageTabControllerImpl() {}

    public CommitMessageTabControllerImpl(ChangeInfoDAO changeInfoDAO){
        this.changeInfoDAO = changeInfoDAO;
    }

    @Override
    public void updateMessage(CommitMessageTabView view, String changeId) {
        view.showMessage(changeInfoDAO.getCommitMessageForChange(changeId));
    }

    public ChangeInfoDAO getChangeInfoDAO() {
        return changeInfoDAO;
    }

    public void setChangeInfoDAO(ChangeInfoDAO changeInfoDAO) {
        this.changeInfoDAO = changeInfoDAO;
    }
}
