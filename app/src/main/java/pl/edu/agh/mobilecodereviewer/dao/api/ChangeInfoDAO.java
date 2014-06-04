package pl.edu.agh.mobilecodereviewer.dao.api;

import java.util.List;

import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;

/**
 * Change Information Data Access Object is used for obtaining
 * information about changes
 * @author AGH
 * @version 0.1
 * @since 0.1
 */
public interface ChangeInfoDAO {

    /**
     * Method return information about all changes
     * @return List of all changes
     */
    List<ChangeInfo> getInfoChanges();
}
