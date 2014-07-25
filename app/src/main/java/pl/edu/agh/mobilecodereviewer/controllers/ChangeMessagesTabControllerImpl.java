package pl.edu.agh.mobilecodereviewer.controllers;

import com.google.inject.Inject;

import java.util.List;

import pl.edu.agh.mobilecodereviewer.controllers.api.ChangeMessagesTabController;
import pl.edu.agh.mobilecodereviewer.dao.api.ChangeInfoDAO;
import pl.edu.agh.mobilecodereviewer.model.ChangeMessageInfo;
import pl.edu.agh.mobilecodereviewer.view.api.ChangeMessagesTabView;


/**
 * Implementation of the ChangeMessagesTabController interface.
 * <p/>
 *  Used for controlling actions after event in {@link pl.edu.agh.mobilecodereviewer.view.activities.ChangeMessagesTab} activity took place.
 *
 * @author AGH
 * @version 0.1
 * @since 0.3
 */
public class ChangeMessagesTabControllerImpl implements ChangeMessagesTabController {

    /**
     * DAO Used to access messages associated with change.
     */
    @Inject
    private ChangeInfoDAO changeInfoDAO;

    /**
     * Simple constructor. Used by DI framework.
     */
    public ChangeMessagesTabControllerImpl() {}


    /**
     * Construct object with given DAO.
     *
     * @param changeInfoDAO {@link pl.edu.agh.mobilecodereviewer.dao.api.ChangeInfoDAO} object used by controller to obtain messages associated with change.
     */
    public ChangeMessagesTabControllerImpl(ChangeInfoDAO changeInfoDAO){
        this.changeInfoDAO = changeInfoDAO;
    }

    /**
     * Obtains  messages associated with change and informs view to show it.
     *
     * @param view View in which messages will be shown
     * @param changeId id of change for which messages will be shown
     */
    @Override
    public void updateMessages(ChangeMessagesTabView view, String changeId) {
        List<ChangeMessageInfo> changeMessageInfos = changeInfoDAO.getChangeMessages(changeId);
        view.showMessages(changeMessageInfos);
    }
}
