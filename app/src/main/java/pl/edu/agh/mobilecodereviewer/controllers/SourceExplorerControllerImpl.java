package pl.edu.agh.mobilecodereviewer.controllers;

import com.google.inject.Singleton;

import javax.inject.Inject;

import pl.edu.agh.mobilecodereviewer.controllers.api.SourceExplorerController;
import pl.edu.agh.mobilecodereviewer.dao.api.SourceCodeDAO;
import pl.edu.agh.mobilecodereviewer.model.SourceCode;
import pl.edu.agh.mobilecodereviewer.model.SourceCodeDiff;
import pl.edu.agh.mobilecodereviewer.view.api.SourceExplorerView;

/**
 * Implementation for controlling action after event in source explorer
 * activity took place
 *
 * @author AGH
 * @version 0.1
 * @since 0.1
 */
@Singleton
public class SourceExplorerControllerImpl implements SourceExplorerController{

    /**
     * Object gives information about source code
     */
    @Inject
    SourceCodeDAO sourceCodeDAO;

    /**
     * Simple object constructor, it doesnt initialize any
     * properties, preserve to be used with di framework
     */
    public SourceExplorerControllerImpl() {
    }

    /**
     * Construct object with given data access Object
     *
     * @param sourceCodeDAO {@link pl.edu.agh.mobilecodereviewer.dao.api.SourceCodeDAO}
     */
    public SourceExplorerControllerImpl(SourceCodeDAO sourceCodeDAO) {
        this.sourceCodeDAO = sourceCodeDAO;
    }

    /**
     * Method downloads source code for a given file with comments
     * and line number
     * @param view View which will be show information aboud model
     */
    @Override
    public void updateSourceCode(SourceExplorerView view,String change_id,String revision_id,String file_id) {
        SourceCode sourceCode = sourceCodeDAO.getSourceCode(change_id,revision_id,file_id);

        view.showSourceCode(sourceCode);
    }

    @Override
    public void updateSourceCodeDiff(SourceExplorerView view, String change_id, String revision_id, String file_id) {
        SourceCodeDiff sourceCodeDiff = sourceCodeDAO.getSourceCodeDiff(change_id, revision_id, file_id);

        view.showSourceCodeDiff(sourceCodeDiff );
    }

}
















