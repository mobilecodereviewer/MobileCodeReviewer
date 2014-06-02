package pl.edu.agh.mobilecodereviewer.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.edu.agh.mobilecodereviewer.controllers.MainApplicationController;
import pl.edu.agh.mobilecodereviewer.controllers.changes.explorer.ChangesExplorerController;

@Module(
    injects =
        {
            MainApplicationController.class,
            ChangesExplorerController.class
        }

)
public class ControllersModule {

    @Provides
    @Singleton
    ChangesExplorerController provideChangesExplorerController(){
        return new ChangesExplorerController();
    }
}
