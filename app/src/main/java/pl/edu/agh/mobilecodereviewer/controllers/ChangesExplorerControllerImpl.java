package pl.edu.agh.mobilecodereviewer.controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import pl.edu.agh.mobilecodereviewer.controllers.api.ChangesExplorerController;
import pl.edu.agh.mobilecodereviewer.controllers.utilities.ChangesFilter;
import pl.edu.agh.mobilecodereviewer.dao.api.ChangeInfoDAO;
import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.model.ChangeStatus;
import pl.edu.agh.mobilecodereviewer.utilities.PreferencesAccessor;
import pl.edu.agh.mobilecodereviewer.utilities.queries.GerritQuery;
import pl.edu.agh.mobilecodereviewer.utilities.queries.GerritQueryParseException;
import pl.edu.agh.mobilecodereviewer.utilities.queries.GerritQueryParser;
import pl.edu.agh.mobilecodereviewer.view.api.ChangesExplorerView;

/**
 * Implementation of the ChangesExplorerController interface.
 * <p/>
 *  Used for controlling actions after event in {@link pl.edu.agh.mobilecodereviewer.view.activities.ChangesExplorer} activity took place.
 *
 * @author AGH
 * @version 0.1
 * @since 0.1
 */
@Singleton
public class ChangesExplorerControllerImpl implements ChangesExplorerController {

    public static final String CANNOT_REMOVE_BUILTIN_FILTER = "Cannot remove built-in status change filters";
    public static final String PARSING_EXCEPTION = "Problem with parsing ";
    /**
     * DAO Used to access information about changes.
     */
    @Inject
    private ChangeInfoDAO changeInfoDAO;

    private ChangesExplorerView view;

    private List<ChangeInfo> changeInfos;

    private List<ChangesFilter> builtInChangeFilters;

    private List<ChangesFilter> customChangeFilters;

    private ChangesFilter currentChangeFilter;


    private List<ChangeInfo> allChangesInfo;

    /**
     * Simple constructor. Used by DI framework.
     */
    public ChangesExplorerControllerImpl() {
    }

    /**
     * Construct object with given DAO.
     *
     * @param changeInfoDAO {@link pl.edu.agh.mobilecodereviewer.dao.api.ChangeInfoDAO} object used by controller to obtain information about changes.
     */
    public ChangesExplorerControllerImpl(ChangeInfoDAO changeInfoDAO) {
        this.changeInfoDAO = changeInfoDAO;
    }

    @Override
    public void initializeData(ChangesExplorerView changesExplorerView) {
        this.view = changesExplorerView;
        this.currentChangeFilter = ChangesFilter.createChangeFilterFromChangeStatus(ChangeStatus.NEW);
        this.customChangeFilters = new ArrayList<ChangesFilter>();
        builtInChangeFilters = getBuiltInStatusSearchFilters();
        customChangeFilters = PreferencesAccessor.getChangesFilters() ;
    }

    private List<ChangeInfo> filterChanges(List<ChangeInfo> changeInfos) {
        GerritQuery query = null;
        try {
            query = GerritQueryParser.parse(currentChangeFilter.getQuery());
            return query.execute(changeInfos);
        } catch (GerritQueryParseException parseException) {
            view.showMessage(PARSING_EXCEPTION + currentChangeFilter.getName() + ":\n" + parseException.getMessage());
        }
        return Collections.emptyList();
    }


    @Override
    public void updateChanges() {
        List<ChangeInfo> infos = getChangeInfos();
        if(infos.size() != 0){
            view.showChanges(infos);
        } else {
            view.showNoChangesToDisplay();
        }

    }

    @Override
    public void search(String query) {
        view.clearChangesList();
        List<ChangeInfo> allInfos = getChangeInfos();
        List<ChangeInfo> searchedInfos = new LinkedList<ChangeInfo>();
        if (query == "" || query == null)
            view.showChanges(allInfos);
        else {
            for (ChangeInfo info : allInfos) {
                if ( doesChangeInfoMatchQuery(info, query) )
                    searchedInfos.add(info);
            }
            if (searchedInfos.size() == 0) {
                view.showNoChangesToDisplay();
            } else view.showFoundChanges(query, searchedInfos);
        }
    }

    @Override
    public void chooseChangeFilter() {
        List<ChangesFilter> allFilters = joinFilters(builtInChangeFilters, customChangeFilters);
        view.showListOfAvalaibleFilters(currentChangeFilter, allFilters);
    }

    public List<ChangesFilter> getBuiltInStatusSearchFilters() {
        ChangeStatus[] allChangeStatuses = ChangeStatus.values();
        List<ChangesFilter> changesFilters = new LinkedList<ChangesFilter>();
        for (ChangeStatus changeStatus : allChangeStatuses) {
            changesFilters.add(ChangesFilter.createChangeFilterFromChangeStatus(changeStatus));
        }
        return changesFilters;
    }

    private List<ChangesFilter> joinFilters(List<ChangesFilter> changesFilters, List<ChangesFilter> customChangeFilters) {
        List<ChangesFilter> joinList = new LinkedList<ChangesFilter>();
        joinList.addAll(changesFilters);
        joinList.addAll(customChangeFilters);
        return joinList;
    }

    @Override
    public void setChangeFilter(ChangesFilter changesFilter) {
        currentChangeFilter = changesFilter;
        updateChanges();
    }

    @Override
    public void addChangeFilter(ChangesFilter changesFilter) {
        PreferencesAccessor.saveChangeFilter(changesFilter);
        customChangeFilters.add(changesFilter);
    }

    @Override
    public boolean removeChangeFilter(ChangesFilter filterToRemove) {
        if (builtInChangeFilters.contains(filterToRemove)) {
            view.showMessage(CANNOT_REMOVE_BUILTIN_FILTER);
            return false;
        } else {
            PreferencesAccessor.removeChangeFilter(filterToRemove);
            customChangeFilters.remove(filterToRemove);
            return true;
        }
    }

    @Override
    public void refreshChanges() {
        updateData();
        updateChanges();
    }

    private boolean doesChangeInfoMatchQuery(ChangeInfo info, String query) {
        if (query == null) {
            return false;
        } else return info.getSubject().toLowerCase().contains(query.toLowerCase());
    }

    private List<ChangeInfo> getChangeInfos() {
        changeInfos = getAllChangesInfo();
        return filterChanges(changeInfos);
    }

    private void updateData() {
        allChangesInfo = changeInfoDAO.getAllChangesInfo();
    }

    private List<ChangeInfo> getAllChangesInfo() {
        if (allChangesInfo == null) {
            allChangesInfo = changeInfoDAO.getAllChangesInfo();
        }
        return allChangesInfo;
    }

}
