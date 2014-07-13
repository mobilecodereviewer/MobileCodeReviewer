package pl.edu.agh.mobilecodereviewer.configuration;

import com.google.inject.Binder;
import com.google.inject.Module;

import pl.edu.agh.mobilecodereviewer.controllers.ChangeInfoTabControllerImpl;
import pl.edu.agh.mobilecodereviewer.controllers.ChangeMessagesTabControllerImpl;
import pl.edu.agh.mobilecodereviewer.controllers.ChangesExplorerControllerImpl;
import pl.edu.agh.mobilecodereviewer.controllers.CommitMessageTabControllerImpl;
import pl.edu.agh.mobilecodereviewer.controllers.ModifiedFilesTabControllerImpl;
import pl.edu.agh.mobilecodereviewer.controllers.ReviewersTabControllerImpl;
import pl.edu.agh.mobilecodereviewer.controllers.SourceExplorerControllerImpl;
import pl.edu.agh.mobilecodereviewer.controllers.api.ChangeInfoTabController;
import pl.edu.agh.mobilecodereviewer.controllers.api.ChangeMessagesTabController;
import pl.edu.agh.mobilecodereviewer.controllers.api.ChangesExplorerController;
import pl.edu.agh.mobilecodereviewer.controllers.api.CommitMessageTabController;
import pl.edu.agh.mobilecodereviewer.controllers.api.ModifiedFilesTabController;
import pl.edu.agh.mobilecodereviewer.controllers.api.ReviewersTabController;
import pl.edu.agh.mobilecodereviewer.controllers.api.SourceExplorerController;
import pl.edu.agh.mobilecodereviewer.dao.api.ChangeInfoDAO;
import pl.edu.agh.mobilecodereviewer.dao.api.SourceCodeDAO;
import pl.edu.agh.mobilecodereviewer.dao.gerrit.ChangeInfoDAOImpl;
import pl.edu.agh.mobilecodereviewer.dao.mock.SourceCodeDAOMockImpl;

/**
 * Class configure assosiaciations between interfaces and classes
 * to be injected by di framework.
 *
 * @author AGH
 * @version 0.1
 * @since 0.1
 */
public class InjectionModule implements Module {
    /**
     * Method bind interfaces to associated classes
     *
     * @param binder Object to inform about associations
     */
    @Override
    public void configure(Binder binder) {
        binder.bind(ChangeInfoDAO.class).to(ChangeInfoDAOImpl.class);
        binder.bind(ChangesExplorerController.class).to(ChangesExplorerControllerImpl.class);
        binder.bind(ModifiedFilesTabController.class).to(ModifiedFilesTabControllerImpl.class);
        binder.bind(ChangeInfoTabController.class).to(ChangeInfoTabControllerImpl.class);
        binder.bind(CommitMessageTabController.class).to(CommitMessageTabControllerImpl.class);
        binder.bind(ChangeMessagesTabController.class).to(ChangeMessagesTabControllerImpl.class);
        binder.bind(ReviewersTabController.class).to(ReviewersTabControllerImpl.class);
        binder.bind(SourceCodeDAO.class).to(SourceCodeDAOMockImpl.class);
        binder.bind(SourceExplorerController.class).to(SourceExplorerControllerImpl.class);
    }
}
