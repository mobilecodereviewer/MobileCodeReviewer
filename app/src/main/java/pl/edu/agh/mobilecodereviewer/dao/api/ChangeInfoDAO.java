package pl.edu.agh.mobilecodereviewer.dao.api;

import java.util.List;
import java.util.Map;

import pl.edu.agh.mobilecodereviewer.dao.gerrit.utilities.RestApi;
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
     * @return {@link pl.edu.agh.mobilecodereviewer.model.ChangeInfo}
     */
    ChangeInfo getChangeInfoById(String id);

    /**
     * Method returns information about all changes.
     *
     * @return list of {@link pl.edu.agh.mobilecodereviewer.model.ChangeInfo}
     */
    List<ChangeInfo> getAllChangesInfo();

    /**
     * Method returns information about files modified within change given by id.
     *
     * @param id id of change which files will be retrieved
     * @return list of files {@link pl.edu.agh.mobilecodereviewer.model.FileInfo}
     */
    List<FileInfo> getModifiedFiles(String id);

    /**
     * Method returns information about mergeability of change with given id.
     *
     * @param id id of change for which mergeablitiy information will be retrieved
     * @return {@link pl.edu.agh.mobilecodereviewer.model.MergeableInfo}
     */
    MergeableInfo getMergeableInfo(String id);

    /**
     * Method returns topic of change.
     *
     * @param id id of change for which topic will be retrieved
     * @return retrieved topic
     */
    String getChangeTopic(String id);

    /**
     * Method returns commit message for change with given id.
     *
     * @param id id of change for which commit message will be retrieved
     * @return commit message
     */
    String getCommitMessageForChange(String id);

    /**
     * Method returns messages associated with change with given id.
     *
     * @param id id of change for which messages will be retrieved
     * @return list of {@link pl.edu.agh.mobilecodereviewer.model.ChangeMessageInfo}
     */
    List<ChangeMessageInfo> getChangeMessages(String id);

    /**
     * Method returns detailed information about labels associated with change.
     *
     * @param id id of change for which information will be retrieved
     * @return list of {@link pl.edu.agh.mobilecodereviewer.model.LabelInfo}
     */
    List<LabelInfo> getLabels(String id);

    void setReview(String changeId, String revisionId, String message, Map<String, Integer> votes);

    void initialize(RestApi restApi);
}
