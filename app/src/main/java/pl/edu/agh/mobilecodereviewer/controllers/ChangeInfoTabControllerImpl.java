package pl.edu.agh.mobilecodereviewer.controllers;

import com.google.inject.Inject;

import pl.edu.agh.mobilecodereviewer.controllers.api.ChangeInfoTabController;
import pl.edu.agh.mobilecodereviewer.dao.api.ChangeInfoDAO;
import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.model.MergeableInfo;
import pl.edu.agh.mobilecodereviewer.view.api.ChangeInfoTabView;

/**
 * Created by d00d171 on 2014-07-09.
 */
public class ChangeInfoTabControllerImpl implements ChangeInfoTabController {

    @Inject
    private ChangeInfoDAO changeInfoDAO;

    public ChangeInfoTabControllerImpl() {}

    public ChangeInfoTabControllerImpl(ChangeInfoDAO changeInfoDAO){
        this.changeInfoDAO = changeInfoDAO;
    }

    @Override
    public void updateInfo(ChangeInfoTabView view, String changeId) {
        MergeableInfo mergeableInfo = changeInfoDAO.getMergeableInfo(changeId);
        String changeTopic = changeInfoDAO.getChangeTopic(changeId);
        ChangeInfo changeInfo = changeInfoDAO.getChangeInfoById(changeId);

        view.showInfo(changeInfo, mergeableInfo, changeTopic);
    }

    public ChangeInfoDAO getChangeInfoDAO() {
        return changeInfoDAO;
    }

    public void setChangeInfoDAO(ChangeInfoDAO changeInfoDAO) {
        this.changeInfoDAO = changeInfoDAO;
    }
}
