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
    private CommitMessageTabView view;
    private String changeId;
    private String commitMessage;

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

    @Override
    public void initializeData(CommitMessageTabView view, String changeId) {
        this.view = view;
        this.changeId = changeId;
    }

    /**
     * Obtains commit message and informs view to show it.
     *
     */
    public void updateMessage() {
        view.showMessage(getCommitMessageForChange());
    }

    @Override
    public void refreshData() {
        updateData();
    }

    @Override
    public void refreshGui() {
        updateMessage();
    }

    private void updateData() {
        commitMessage = changeInfoDAO.getCommitMessageForChange(changeId);
    }


    private String getCommitMessageForChange() {
        if (commitMessage == null) {
            new UnsupportedOperationException("You should have invoked refreshData first!!");
        }
        return commitMessage;
    }
}
