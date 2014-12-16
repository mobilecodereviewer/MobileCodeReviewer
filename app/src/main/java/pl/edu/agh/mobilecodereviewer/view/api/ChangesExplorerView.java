package pl.edu.agh.mobilecodereviewer.view.api;

import java.util.List;

import pl.edu.agh.mobilecodereviewer.controllers.utilities.ChangesFilter;
import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.model.ChangeStatus;

/**
 * Represents the view which will show information about changes
 *
 * @author AGH
 * @version 0.1
 * @since 0.1
 */
public interface ChangesExplorerView {

    /**
     * Shows given list of changes
     *
     * @param changes List of information about changes
     */
    void showChanges(List<ChangeInfo> changes);

    void clearChangesList();

    void hideSearchPanel();

    void showFoundChanges(String query, List<ChangeInfo> searchedInfos);

    void showMessage(String message);

    void showListOfAvalaibleFilters(ChangesFilter currentStatus, List<ChangesFilter> changeStatuses);

    void showNoChangesToDisplay();
}
