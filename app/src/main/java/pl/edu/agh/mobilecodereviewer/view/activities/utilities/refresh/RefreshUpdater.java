package pl.edu.agh.mobilecodereviewer.view.activities.utilities.refresh;


public interface RefreshUpdater {
    void register(Refreshable refreshable);

    void unregister(Refreshable refreshable);
}
