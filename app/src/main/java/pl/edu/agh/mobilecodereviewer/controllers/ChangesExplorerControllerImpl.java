package pl.edu.agh.mobilecodereviewer.controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;

import pl.edu.agh.mobilecodereviewer.controllers.api.ChangesExplorerController;
import pl.edu.agh.mobilecodereviewer.dao.api.ChangeInfoDAO;
import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.view.api.ChangesExplorerView;

/**
 * Implementation for controlling action after event in change explorer
 * activity took place
 *
 * @author AGH
 * @version 0.1
 * @since 0.1
 */
@Singleton
public class ChangesExplorerControllerImpl implements ChangesExplorerController {

    /**
     * Object gives information about changes
     */
    @Inject
    private ChangeInfoDAO changeInfoDAO;

    /**
     * Simple object constructor, it doesnt initialize any
     * properties, preserve to be used with di framework
     */
    public ChangesExplorerControllerImpl() {
    }

    /**
     * Construct object with given data access Object
     *
     * @param changeInfoDAO {@link pl.edu.agh.mobilecodereviewer.controllers.ChangesExplorerControllerImpl#changeInfoDAO}
     */
    public ChangesExplorerControllerImpl(ChangeInfoDAO changeInfoDAO) {
        this.changeInfoDAO = changeInfoDAO;
    }

    /**
     * Method downloads information about changes and inform
     * view to show given information
     *
     * @param view View in which changes will be shown
     */
    @Override
    public void updateChanges(ChangesExplorerView view) {
        List<ChangeInfo> infos = changeInfoDAO.getAllChangesInfo();
        view.showChanges(infos);
    }

    /**
     * Getter for {@link pl.edu.agh.mobilecodereviewer.controllers.ChangesExplorerControllerImpl#changeInfoDAO}
     *
     * @return Change Information Data Access Object
     */
    public ChangeInfoDAO getChangeInfoDAO() {
        return changeInfoDAO;
    }

    /**
     * Setter for {@link pl.edu.agh.mobilecodereviewer.controllers.ChangesExplorerControllerImpl#changeInfoDAO}
     *
     * @param changeInfoDAO Change Information Data Access Object
     */
    public void setChangeInfoDAO(ChangeInfoDAO changeInfoDAO) {
        this.changeInfoDAO = changeInfoDAO;
    }

}
