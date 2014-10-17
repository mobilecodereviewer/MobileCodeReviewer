package pl.edu.agh.mobilecodereviewer.controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.LinkedList;
import java.util.List;

import pl.edu.agh.mobilecodereviewer.R;
import pl.edu.agh.mobilecodereviewer.controllers.api.ChangesExplorerController;
import pl.edu.agh.mobilecodereviewer.dao.api.ChangeInfoDAO;
import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.model.ChangeStatus;
import pl.edu.agh.mobilecodereviewer.view.api.ChangesExplorerView;
import roboguice.inject.InjectResource;

/**
 * Implementation of the ChangesExplorerController interface.
 * <p/>
 *  Used for controlling actions after event in {@link pl.edu.agh.mobilecodereviewer.view.activities.ChangesExplorer} activity took place.
 *
 * @author AGH
 * @version 0.1
 * @since 0.1
 */
@Singleton
public class ChangesExplorerControllerImpl implements ChangesExplorerController {

    /**
     * DAO Used to access information about changes.
     */
    @Inject
    private ChangeInfoDAO changeInfoDAO;

    private ChangesExplorerView view;

    private List<ChangeInfo> changeInfos;

    @InjectResource(R.string.pl_agh_edu_mobilecodereviewer_ChangesExplorer_no_search_results_found)
    private String NO_SEARCH_RESULTS_FOUND;

    private ChangeStatus currentStatus;

    /**
     * Simple constructor. Used by DI framework.
     */
    public ChangesExplorerControllerImpl() {
    }

    /**
     * Construct object with given DAO.
     *
     * @param changeInfoDAO {@link pl.edu.agh.mobilecodereviewer.dao.api.ChangeInfoDAO} object used by controller to obtain information about changes.
     */
    public ChangesExplorerControllerImpl(ChangeInfoDAO changeInfoDAO) {
        this.changeInfoDAO = changeInfoDAO;
    }

    @Override
    public void initializeData(ChangesExplorerView changesExplorerView) {
        this.view = changesExplorerView;
        this.currentStatus = ChangeStatus.ALL;
    }

    public List<ChangeInfo> getChangeInfos() {
        if (changeInfos == null) { // perform lazy initialization
            changeInfos = changeInfoDAO.getAllChangesInfo();
        }
        return filterWithAppropriateStatus(changeInfos);
    }

    private List<ChangeInfo> filterWithAppropriateStatus(List<ChangeInfo> changeInfos) {
        List<ChangeInfo> changes = new LinkedList<ChangeInfo>();
        for (ChangeInfo change : changeInfos) {
            if (currentStatus.matchStatus(change.getStatus())) {
                changes.add(change);
            }
        }
        return changes;
    }


    @Override
    public void updateChanges() {
        List<ChangeInfo> infos = getChangeInfos();
        view.showChanges(infos);
    }

    @Override
    public void search(String query) {
        view.clearChangesList();
        List<ChangeInfo> allInfos = getChangeInfos();
        List<ChangeInfo> searchedInfos = new LinkedList<ChangeInfo>();
        if (query == "" || query == null)
            view.showChanges(allInfos);
        else {
            for (ChangeInfo info : allInfos) {
                if ( doesChangeInfoMatchQuery(info, query) )
                    searchedInfos.add(info);
            }
            if (searchedInfos.size() == 0) {
                view.showMessage(NO_SEARCH_RESULTS_FOUND);
            } else view.showFoundChanges(query, searchedInfos);
        }
    }

    @Override
    public void chooseStatus() {
        ChangeStatus[] values = ChangeStatus.values();
        view.showListOfAvalaibleStatus(currentStatus,values);
    }

    @Override
    public void changeStatus(ChangeStatus status) {
        currentStatus = status;
        updateChanges();
    }

    private boolean doesChangeInfoMatchQuery(ChangeInfo info, String query) {
        if (query == null) {
            return false;
        } else return info.toString().toLowerCase().contains(query.toLowerCase());
    }


}
