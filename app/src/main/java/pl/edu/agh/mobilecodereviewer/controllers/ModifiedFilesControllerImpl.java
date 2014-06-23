package pl.edu.agh.mobilecodereviewer.controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;

import pl.edu.agh.mobilecodereviewer.controllers.api.ModifiedFilesController;
import pl.edu.agh.mobilecodereviewer.dao.api.ChangeInfoDAO;
import pl.edu.agh.mobilecodereviewer.model.FileInfo;
import pl.edu.agh.mobilecodereviewer.view.api.ModifiedFilesView;

/**
 * Implementation for controlling action after event in modified files
 * activity took place
 *
 * @author AGH
 * @version 0.1
 * @since 0.1
 */
@Singleton
public class ModifiedFilesControllerImpl implements ModifiedFilesController {

    /**
     * Object gives information about changes.
     */
    @Inject
    private ChangeInfoDAO changeInfoDAO;

    /**
     * Simple object constructor, it doesnt initialize any
     * properties, preserve to be used with di framework
     */
    public ModifiedFilesControllerImpl() {
    }

    /**
     * Construct object with given data access Object
     *
     * @param changeInfoDAO {@link pl.edu.agh.mobilecodereviewer.controllers.ModifiedFilesControllerImpl#changeInfoDAO}
     */
    public ModifiedFilesControllerImpl(ChangeInfoDAO changeInfoDAO) {
        this.changeInfoDAO = changeInfoDAO;
    }

    /**
     * Method downloads information about modified files and inform
     * view to show given information
     *
     * @param view View in which changes will be shown
     */
    @Override
    public void updateFiles(ModifiedFilesView view, String changeId) {
        List<FileInfo> changeInfos = changeInfoDAO.getModifiedFiles(changeId);
        view.showFiles(changeInfos);
    }

    /**
     * Getter for {@link pl.edu.agh.mobilecodereviewer.controllers.ModifiedFilesControllerImpl#changeInfoDAO}
     *
     * @return Change Information Data Access Object
     */
    public ChangeInfoDAO getChangeInfoDAO() {
        return changeInfoDAO;
    }

    /**
     * Setter for {@link pl.edu.agh.mobilecodereviewer.controllers.ModifiedFilesControllerImpl#changeInfoDAO}
     *
     * @param changeInfoDAO Change Information Data Access Object
     */
    public void setChangeInfoDAO(ChangeInfoDAO changeInfoDAO) {
        this.changeInfoDAO = changeInfoDAO;
    }
}
