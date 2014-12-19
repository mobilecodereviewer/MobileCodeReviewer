package pl.edu.agh.mobilecodereviewer.controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;

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
@Singleton
public class ChangeMessagesTabControllerImpl implements ChangeMessagesTabController {

    /**
     * DAO Used to access messages associated with change.
     */
    @Inject
    private ChangeInfoDAO changeInfoDAO;
    private List<ChangeMessageInfo> changeMessages;
    private ChangeMessagesTabView view;
    private String changeId;

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

    @Override
    public void initializeData(ChangeMessagesTabView view, String changeId) {
        this.view = view;
        this.changeId = changeId;
    }

    /**
     * Obtains  messages associated with change and informs view to show it.
     *
     */
    public void updateMessages() {
        List<ChangeMessageInfo> changeMessageInfos = getChangeMessages(changeId);
        view.showMessages(changeMessageInfos);
    }

    @Override
    public void refreshData() {
        updateData();
    }

    @Override
    public void refreshGui() {
        updateMessages();
    }

    private void updateData() {
        changeMessages = changeInfoDAO.getChangeMessages(changeId);
    }

    private List<ChangeMessageInfo> getChangeMessages(String changeId) {
        if (changeMessages == null) {
            new UnsupportedOperationException("You should have invoked refreshData first!!");
        }
        return changeMessages;
    }

}
