package pl.edu.agh.mobilecodereviewer.controllers;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;
import java.util.Map;

import pl.edu.agh.mobilecodereviewer.controllers.api.ModifiedFilesTabController;
import pl.edu.agh.mobilecodereviewer.dao.api.ChangeInfoDAO;
import pl.edu.agh.mobilecodereviewer.model.ChangeStatus;
import pl.edu.agh.mobilecodereviewer.model.Comment;
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
    private ModifiedFilesTabView view;
    private String changeId;
    private List<FileInfo> modifiedList;
    private ChangeStatus changeStatus;

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

    @Override
    public void initializeData(ModifiedFilesTabView view, String changeId) {
        this.view = view;
        this.changeId = changeId;
    }



    /**
     * Obtains list of modified files and informs view to show it.
     *
     */
    private void updateFiles() {
        List<FileInfo> changeModifiedFiles = getModifiedFiles();
        ChangeStatus status = getChangeStatus();
        view.showFiles(changeModifiedFiles, status, getHasFilesPendingComments());
    }

    @Override
    public void refreshData() {
        updateData();
    }

    @Override
    public void refreshGui() {
        updateFiles();
    }

    private void updateData() {
        modifiedList = changeInfoDAO.getModifiedFiles(changeId);
        changeStatus = changeInfoDAO.getChangeInfoById(changeId).getStatus();
    }

    private List<FileInfo> getModifiedFiles() {
        if (modifiedList == null) {
            new UnsupportedOperationException("You should have invoked refreshData first!!");
        }
        return modifiedList;
    }

    private ChangeStatus getChangeStatus() {
        if (changeStatus == null) {
            new UnsupportedOperationException("You should have invoked refreshData first!!");
        }
        return changeStatus;
	}

    @Override
    public void checkIfFilesPendingCommentsChanged(){
        List<Boolean> filesPendingComments = getHasFilesPendingComments();
        view.refreshPendingComments(filesPendingComments);
    }

    private List<Boolean> getHasFilesPendingComments(){
        String revisionId = modifiedList.get(0).getRevisionId();
        final Map<String, List<Comment>> pendingCommentsForChange = changeInfoDAO.getPendingComments(changeId, revisionId);

        return Lists.transform(modifiedList,
                new Function<FileInfo, Boolean>() {
                    @Override
                    public Boolean apply(FileInfo from) {
                        if(pendingCommentsForChange == null){
                            return false;
                        }
                        List<Comment> fileComments = pendingCommentsForChange.get(from.getFileName());
                        return fileComments != null && fileComments.size() != 0;
                    }
                }
        );
    }
}
