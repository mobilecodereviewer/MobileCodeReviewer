package pl.edu.agh.mobilecodereviewer.view.api;

import java.util.List;

import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;

/**
 * Represents the view which will show information about changes
 * @author AGH
 * @version 0.1
 * @since 0.1
 */
public interface ChangesExplorerView {

    /**
     * Shows given list of changes
     * @param changes List of information about changes
     */
    void showChanges(List<ChangeInfo> changes);
}
