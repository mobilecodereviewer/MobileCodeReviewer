package pl.edu.agh.mobilecodereviewer.controllers;

import com.google.inject.Singleton;

import javax.inject.Inject;

import pl.edu.agh.mobilecodereviewer.controllers.api.SourceExplorerController;
import pl.edu.agh.mobilecodereviewer.dao.api.SourceCodeDAO;
import pl.edu.agh.mobilecodereviewer.model.SourceCode;
import pl.edu.agh.mobilecodereviewer.view.api.SourceExplorerView;

/**
 * Implementation of the SourceExplorerController interface.
 * <p/>
 *  Used for controlling actions after event in {@link pl.edu.agh.mobilecodereviewer.view.activities.SourceExplorer} activity took place.
 *
 * @author AGH
 * @version 0.1
 * @since 0.2
 */
@Singleton
public class SourceExplorerControllerImpl implements SourceExplorerController{

    /**
     * DAO Used to access source code.
     */
    @Inject
    SourceCodeDAO sourceCodeDAO;

    /**
     * Simple constructor. Used by DI framework.
     */
    public SourceExplorerControllerImpl() {
    }

    /**
     * Construct object with given DAO.
     *
     * @param sourceCodeDAO {@link pl.edu.agh.mobilecodereviewer.dao.api.SourceCodeDAO} object used by controller to obtain source code.
     */
    public SourceExplorerControllerImpl(SourceCodeDAO sourceCodeDAO) {
        this.sourceCodeDAO = sourceCodeDAO;
    }

    /**
     * Obtains source code with comments and line numbers and informs view to show it.
     *
     * @param view View in which source code will be shown
     * @param change_id id of change containing revision with file
     * @param revision_id id of revision containing file
     * @param file_id id of file for which source code will be shown
     */
    @Override
    public void updateSourceCode(SourceExplorerView view,String change_id,String revision_id,String file_id) {
        SourceCode sourceCode = sourceCodeDAO.getSourceCode(change_id,revision_id,file_id);

        view.showSourceCode(sourceCode);
    }

}
