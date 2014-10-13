package pl.edu.agh.mobilecodereviewer.controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.LinkedList;
import java.util.List;

import pl.edu.agh.mobilecodereviewer.controllers.api.ChangesExplorerController;
import pl.edu.agh.mobilecodereviewer.dao.api.ChangeInfoDAO;
import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.view.api.ChangesExplorerView;

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
    }

    public List<ChangeInfo> getChangeInfos() {
        if (changeInfos == null) { // perform lazy initialization
            changeInfos = changeInfoDAO.getAllChangesInfo();
        }
        return changeInfos;
    }

    /**
     * Obtains information about all changes and informs view to show it.
     *
     * @param view View in which messages will be shown
     */
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
                if ( info.toString().contains(query) )
                    searchedInfos.add(info);
            }
            view.showChanges(searchedInfos);
        }
    }



}
