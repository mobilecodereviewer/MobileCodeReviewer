package pl.edu.agh.mobilecodereviewer.controllers;

import com.google.inject.Inject;

import java.util.List;

import pl.edu.agh.mobilecodereviewer.controllers.api.ChangeMessagesTabController;
import pl.edu.agh.mobilecodereviewer.dao.api.ChangeInfoDAO;
import pl.edu.agh.mobilecodereviewer.model.ChangeMessageInfo;
import pl.edu.agh.mobilecodereviewer.view.api.ChangeMessagesTabView;

public class ChangeMessagesTabControllerImpl implements ChangeMessagesTabController {

    @Inject
    private ChangeInfoDAO changeInfoDAO;

    public ChangeMessagesTabControllerImpl() {}

    public ChangeMessagesTabControllerImpl(ChangeInfoDAO changeInfoDAO){
        this.changeInfoDAO = changeInfoDAO;
    }

    @Override
    public void updateMessages(ChangeMessagesTabView view, String changeId) {
        List<ChangeMessageInfo> changeMessageInfos = changeInfoDAO.getChangeMessages(changeId);
        view.showMessages(changeMessageInfos);
    }

    public ChangeInfoDAO getChangeInfoDAO() {
        return changeInfoDAO;
    }

    public void setChangeInfoDAO(ChangeInfoDAO changeInfoDAO) {
        this.changeInfoDAO = changeInfoDAO;
    }
}
