package pl.edu.agh.mobilecodereviewer.controllers;

import com.google.inject.Singleton;

import javax.inject.Inject;

import pl.edu.agh.mobilecodereviewer.controllers.api.SourceExplorerController;
import pl.edu.agh.mobilecodereviewer.dao.api.SourceCodeDAO;
import pl.edu.agh.mobilecodereviewer.model.SourceCode;
import pl.edu.agh.mobilecodereviewer.view.api.SourceExplorerView;

@Singleton
public class SourceExplorerControllerImpl implements SourceExplorerController{

    @Inject
    SourceCodeDAO sourceCodeDAO;

    @Override
    public void updateSourceCode(SourceExplorerView view) {
        SourceCode sourceCode = sourceCodeDAO.getSourceCode();

        view.showSourceCode( sourceCode );
    }

}
