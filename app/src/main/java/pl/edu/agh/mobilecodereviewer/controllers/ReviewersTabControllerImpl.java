package pl.edu.agh.mobilecodereviewer.controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;

import pl.edu.agh.mobilecodereviewer.controllers.api.ReviewersTabController;
import pl.edu.agh.mobilecodereviewer.dao.api.ChangeInfoDAO;
import pl.edu.agh.mobilecodereviewer.model.LabelInfo;
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
@Singleton
public class ReviewersTabControllerImpl implements ReviewersTabController{

    /**
     * DAO Used to access reviewers of change.
     */
    @Inject
    private ChangeInfoDAO changeInfoDAO;
    private ReviewersTabView view;
    private String changeId;
    private List<LabelInfo> labels;

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


    @Override
    public void initializeData(ReviewersTabView view, String changeId) {
        this.view = view;
        this.changeId = changeId;
    }

    /**
     * Obtains list reviewers and informs view to show it.
     *
     */
    public void updateReviewers() {
        view.showReviewers(getLabels());
    }

    @Override
    public void refreshData() {
        updateData();
    }

    @Override
    public void refreshGui() {
        updateReviewers();
    }

    private void updateData() {
        labels = changeInfoDAO.getLabels(changeId);
    }

    private List<LabelInfo> getLabels() {
        if (labels == null) {
            new UnsupportedOperationException("You should have invoked refreshData first!!");
        }
        return labels;
    }
}
