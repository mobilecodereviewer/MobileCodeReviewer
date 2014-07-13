package pl.edu.agh.mobilecodereviewer.dao.api;

import java.util.List;

import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.model.ChangeMessageInfo;
import pl.edu.agh.mobilecodereviewer.model.FileInfo;
import pl.edu.agh.mobilecodereviewer.model.LabelInfo;
import pl.edu.agh.mobilecodereviewer.model.MergeableInfo;

/**
 * Change Information Data Access Object is used for obtaining
 * information about changes
 *
 * @author AGH
 * @version 0.1
 * @since 0.1
 */
public interface ChangeInfoDAO {

    /**
     * Method returns information about specific change given by its id.
     *
     * @param id id of change to retrieve
     * @return {@link pl.edu.agh.mobilecodereviewer.model.ChangeInfo} object with given id
     */
    ChangeInfo getChangeInfoById(String id);

    /**
     * Method returns information about all changes.
     *
     * @return List of all changes
     */
    List<ChangeInfo> getAllChangesInfo();

    /**
     * Method returns information about files modified within change given by id.
     *
     * @param id id of change which files will be retrieved
     * @return list of files modified within change
     */
    List<FileInfo> getModifiedFiles(String id);

    MergeableInfo getMergeableInfo(String id);

    String getChangeTopic(String id);

    String getCommitMessageForChange(String id);

    List<ChangeMessageInfo> getChangeMessages(String id);

    List<LabelInfo> getLabels(String id);
}
