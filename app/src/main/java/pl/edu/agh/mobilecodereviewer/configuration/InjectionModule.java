package pl.edu.agh.mobilecodereviewer.configuration;

import com.google.inject.Binder;
import com.google.inject.Module;

import pl.edu.agh.mobilecodereviewer.controllers.ChangesExplorerControllerImpl;
import pl.edu.agh.mobilecodereviewer.controllers.ModifiedFilesControllerImpl;
import pl.edu.agh.mobilecodereviewer.controllers.api.ChangesExplorerController;
import pl.edu.agh.mobilecodereviewer.controllers.api.ModifiedFilesController;
import pl.edu.agh.mobilecodereviewer.dao.ChangeInfoDAOMockImpl;
import pl.edu.agh.mobilecodereviewer.dao.api.ChangeInfoDAO;

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
        binder.bind(ChangeInfoDAO.class).to(ChangeInfoDAOMockImpl.class);
        binder.bind(ChangesExplorerController.class).to(ChangesExplorerControllerImpl.class);
        binder.bind(ModifiedFilesController.class).to(ModifiedFilesControllerImpl.class);
    }
}
