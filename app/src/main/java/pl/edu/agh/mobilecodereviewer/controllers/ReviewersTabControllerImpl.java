package pl.edu.agh.mobilecodereviewer.controllers;

import com.google.inject.Inject;

import pl.edu.agh.mobilecodereviewer.controllers.api.ReviewersTabController;
import pl.edu.agh.mobilecodereviewer.dao.api.ChangeInfoDAO;
import pl.edu.agh.mobilecodereviewer.view.api.ReviewersTabView;

/**
 * Implementation of the ReviewersTabController interface.
 * <p/>
 *  Used for controlling actions after event in {@link pl.edu.agh.mobilecodereviewer.view.activities.ReviewersTab} activity took place.
 *
 * @author AGH
 * @version 0.1
 * @since 0.2
 */
public class ReviewersTabControllerImpl implements ReviewersTabController{

    /**
     * DAO Used to access reviewers of change.
     */
    @Inject
    private ChangeInfoDAO changeInfoDAO;

    /**
     * Simple constructor. Used by DI framework.
     */
    public ReviewersTabControllerImpl(){}

    /**
     * Construct object with given DAO.
     *
     * @param changeInfoDAO {@link pl.edu.agh.mobilecodereviewer.dao.api.ChangeInfoDAO} object used by controller to obtain reviewers.
     */
    public ReviewersTabControllerImpl(ChangeInfoDAO changeInfoDAO) {
        this.changeInfoDAO = changeInfoDAO;
    }

    /**
     * Obtains list reviewers and informs view to show it.
     *
     * @param view View in which modified files will be shown
     * @param changeId id of change for which modified files will be shown
     */
    @Override
    public void updateReviewers(ReviewersTabView view, String changeId) {
        view.showReviewers(changeInfoDAO.getLabels(changeId));
    }
}
