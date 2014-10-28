package pl.edu.agh.mobilecodereviewer.view.activities.utilities.refresh;

import java.sql.Ref;


public interface RefreshUpdater {
    void register(Refreshable refreshable);

    void unregister(Refreshable refreshable);
}
