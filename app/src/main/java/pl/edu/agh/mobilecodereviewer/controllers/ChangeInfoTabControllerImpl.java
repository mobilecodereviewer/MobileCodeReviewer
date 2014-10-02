package pl.edu.agh.mobilecodereviewer.controllers;

import com.google.inject.Inject;

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
public class ChangeInfoTabControllerImpl implements ChangeInfoTabController {

    /**
     * DAO Used to access information about change.
     */
    @Inject
    private ChangeInfoDAO changeInfoDAO;

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

    /**
     * Obtains information about change and informs view to show it.
     *
     * @param view     View in which information will be shown
     * @param changeId id of change for which information will be shown
     */
    @Override
    public void updateInfo(ChangeInfoTabView view, String changeId) {
        MergeableInfo mergeableInfo = changeInfoDAO.getMergeableInfo(changeId);
        String changeTopic = changeInfoDAO.getChangeTopic(changeId);
        ChangeInfo changeInfo = changeInfoDAO.getChangeInfoById(changeId);

        view.showInfo(changeInfo, mergeableInfo, changeTopic);
    }
}
