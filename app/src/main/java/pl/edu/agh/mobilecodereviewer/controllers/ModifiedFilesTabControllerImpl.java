package pl.edu.agh.mobilecodereviewer.controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;

import pl.edu.agh.mobilecodereviewer.controllers.api.ModifiedFilesTabController;
import pl.edu.agh.mobilecodereviewer.dao.api.ChangeInfoDAO;
import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.model.FileInfo;
import pl.edu.agh.mobilecodereviewer.view.api.ModifiedFilesTabView;

/**
 * Implementation of the ModifiedFilesTabController interface.
 * <p/>
 *  Used for controlling actions after event in {@link pl.edu.agh.mobilecodereviewer.view.activities.ModifiedFilesTab} activity took place.
 *
 * @author AGH
 * @version 0.1
 * @since 0.2
 */
@Singleton
public class ModifiedFilesTabControllerImpl implements ModifiedFilesTabController {

    /**
     * DAO Used to access files modified within change.
     */
    @Inject
    private ChangeInfoDAO changeInfoDAO;

    /**
     * Simple constructor. Used by DI framework.
     */
    public ModifiedFilesTabControllerImpl() {
    }

    /**
     * Construct object with given DAO.
     *
     * @param changeInfoDAO
     */
    public ModifiedFilesTabControllerImpl(ChangeInfoDAO changeInfoDAO) {
        this.changeInfoDAO = changeInfoDAO;
    }

    /**
     * Obtains list of modified files and informs view to show it.
     *
     * @param view View in which modified files will be shown
     * @param changeId id of change for which modified files will be shown
     */
    @Override
    public void updateFiles(ModifiedFilesTabView view, String changeId) {
        List<FileInfo> changeModifiedFiles = changeInfoDAO.getModifiedFiles(changeId);
        ChangeInfo changeInfo = changeInfoDAO.getChangeInfoById(changeId);
        view.showFiles(changeModifiedFiles, changeInfo.getStatus() );
    }
}
