package pl.edu.agh.mobilecodereviewer.controllers;

import com.google.inject.Inject;

import pl.edu.agh.mobilecodereviewer.controllers.api.CommitMessageTabController;
import pl.edu.agh.mobilecodereviewer.dao.api.ChangeInfoDAO;
import pl.edu.agh.mobilecodereviewer.view.api.CommitMessageTabView;

/**
 * Implementation of the CommitMessageTabController interface.
 * <p/>
 *  Used for controlling actions after event in {@link pl.edu.agh.mobilecodereviewer.view.activities.CommitMessageTab} activity took place.
 *
 * @author AGH
 * @version 0.1
 * @since 0.3
 */
public class CommitMessageTabControllerImpl implements CommitMessageTabController{

    /**
     * DAO Used to access commit message associated with change.
     */
    @Inject
    private ChangeInfoDAO changeInfoDAO;

    /**
     * Simple constructor. Used by DI framework.
     */
    public CommitMessageTabControllerImpl() {}

    /**
     * Construct object with given DAO.
     *
     * @param changeInfoDAO {@link pl.edu.agh.mobilecodereviewer.dao.api.ChangeInfoDAO} object used by controller to obtain commit message.
     */
    public CommitMessageTabControllerImpl(ChangeInfoDAO changeInfoDAO){
        this.changeInfoDAO = changeInfoDAO;
    }

    /**
     * Obtains commit message and informs view to show it.
     *
     * @param view View in which commit message will be shown
     * @param changeId id of change for which commit message will be shown
     */
    @Override
    public void updateMessage(CommitMessageTabView view, String changeId) {
        view.showMessage(changeInfoDAO.getCommitMessageForChange(changeId));
    }
}
