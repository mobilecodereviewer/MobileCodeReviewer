package pl.edu.agh.mobilecodereviewer.controllers;

import com.google.inject.Inject;

import javax.inject.Singleton;

import pl.edu.agh.mobilecodereviewer.controllers.api.ChangeInfoTabController;
import pl.edu.agh.mobilecodereviewer.dao.api.ChangeInfoDAO;
import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.model.MergeableInfo;
import pl.edu.agh.mobilecodereviewer.view.api.ChangeInfoTabView;

/**
 * Implementation of the ChangeInfoTabController interface.
 * <p/>
 * Used for controlling actions after event in {@link pl.edu.agh.mobilecodereviewer.view.activities.ChangeInfoTab} activity took place.
 *
 * @author AGH
 * @version 0.1
 * @since 0.3
 */
@Singleton
public class ChangeInfoTabControllerImpl implements ChangeInfoTabController {

    private String changeId;
    private ChangeInfoTabView view;

    /**
     * DAO Used to access information about change.
     */
    @Inject
    private ChangeInfoDAO changeInfoDAO;


    private ChangeInfo changeInfo;
    private String changeTopic;
    private MergeableInfo mergeableInfo;


    /**
     * Simple constructor. Used by DI framework.
     */
    public ChangeInfoTabControllerImpl() {
    }

    /**
     * Construct object with given DAO.
     *
     * @param changeInfoDAO {@link pl.edu.agh.mobilecodereviewer.dao.api.ChangeInfoDAO} object used by controller to obtain information about change
     */
    public ChangeInfoTabControllerImpl(ChangeInfoDAO changeInfoDAO) {
        this.changeInfoDAO = changeInfoDAO;
    }

    @Override
    public void initializeData(ChangeInfoTabView changeInfoTab, String change_id) {
        this.changeId = change_id;
        this.view = changeInfoTab;
    }

    /**
     * Obtains information about change and informs view to show it.
     *
     */
    public void updateInfo() {
        MergeableInfo mergeableInfo = getMergeableInfo(changeId);
        String changeTopic = getChangeTopic(changeId);
        ChangeInfo changeInfo = getChangeInfoById(changeId);

        view.showInfo(changeInfo, mergeableInfo, changeTopic);
    }

    @Override
    public void refreshData() {
        if(changeId != null)
            updateData();
    }

    @Override
    public void refreshGui() {
        if(view != null)
            updateInfo();
    }

    private void updateData() {
        changeInfo =  changeInfoDAO.getChangeInfoById(changeId);
        changeTopic =  changeInfoDAO.getChangeTopic(changeId);
        mergeableInfo =  changeInfoDAO.getMergeableInfo(changeId);
    }


    private ChangeInfo getChangeInfoById(String changeId) {
        if ( changeInfo == null ) {
            new UnsupportedOperationException("You should have invoked refreshData first!!");
        }
        return changeInfo;
    }

    private String getChangeTopic(String changeId) {
        if ( changeTopic == null) {
            new UnsupportedOperationException("You should have invoked refreshData first!!");
        }
        return changeTopic;
    }

    private MergeableInfo getMergeableInfo(String changeId) {
        if ( mergeableInfo == null ) {
            new UnsupportedOperationException("You should have invoked refreshData first!!");
        }
        return mergeableInfo;
    }

}
