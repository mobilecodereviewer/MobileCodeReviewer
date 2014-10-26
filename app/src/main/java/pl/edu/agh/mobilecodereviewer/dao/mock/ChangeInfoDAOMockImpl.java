package pl.edu.agh.mobilecodereviewer.dao.mock;

import com.google.inject.Singleton;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import pl.edu.agh.mobilecodereviewer.dao.api.ChangeInfoDAO;
import pl.edu.agh.mobilecodereviewer.dao.gerrit.utilities.RestApi;
import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.model.ChangeMessageInfo;
import pl.edu.agh.mobilecodereviewer.model.FileInfo;
import pl.edu.agh.mobilecodereviewer.model.LabelInfo;
import pl.edu.agh.mobilecodereviewer.model.MergeableInfo;
import pl.edu.agh.mobilecodereviewer.model.PermittedLabel;

/**
 * Simple Stub for ChangeInfoDAO with hardcoded values
 * about changes. In later version should be removed and
 * replaced with professional Mock bound to ChangeInfoDAO
 * interface in Injection Module. So far its only for
 * presentation/testing measures
 *
 * @author AGH
 * @version 0.1
 * @since 0.1
 */
@Singleton
public class ChangeInfoDAOMockImpl implements ChangeInfoDAO {

    /**
     * Information about changes used by {@link ChangeInfoDAOMockImpl}
     */
    private static List<ChangeInfo> changesInfos = Arrays.asList(
            ChangeInfo.valueOf("project1~branch1~changeId1", "changeId1", "Change1"),
            ChangeInfo.valueOf("project1~branch1~changeId2", "changeId2", "Change2"),
            ChangeInfo.valueOf("project1~branch1~changeId3", "changeId3", "Change3")
    );

    /**
     * Information about modified files used by {@link ChangeInfoDAOMockImpl}
     */
    private static List<FileInfo> filesInfos = Arrays.asList(
            FileInfo.valueOf("ThisissuperlongfileNamewhichwillbeusedtotextbehavouirasdeiojd.txt"),
            FileInfo.valueOf("file2.txt"),
            FileInfo.valueOf("file2.txt")
    );

    /**
     * Method replace hardcoded values about change given by id
     *
     * @param id id of change to retrieve
     * @return Hardcoded some information about change
     */
    @Override
    public ChangeInfo getChangeInfoById(String id) {
        for (ChangeInfo change : changesInfos) {
            if (change.getId().equals(id)) {
                return change;
            }
        }
        return changesInfos.get(0);
    }

    /**
     * Method replace hardcoded values about all changes
     *
     * @return Hardcoded some information about changes
     */
    @Override
    public List<ChangeInfo> getAllChangesInfo() {
        return changesInfos;
    }

    /**
     * Method replace hardcoded values about modified files
     *
     * @return Hardcoded some information about modified files
     */
    @Override
    public List<FileInfo> getModifiedFiles(String id) {
        return filesInfos;
    }

    @Override
    public MergeableInfo getMergeableInfo(String id) {
        return MergeableInfo.valueOf("CHERRY_PICK", true);
    }

    @Override
    public String getChangeTopic(String id) {
        return "TOPIC";
    }

    @Override
    public String getCommitMessageForChange(String id) {return "COMMIT MESSAGE";}

    @Override
    public List<ChangeMessageInfo> getChangeMessages(String id) {
        return null;
    }

    @Override
    public List<LabelInfo> getLabels(String id) {
        return null;
    }

    @Override
    public void setReview(String changeId, String revisionId, String message, Map<String, Integer> votes) {
    }

    @Override
    public void initialize(RestApi restApi) {

    }

    @Override
    public List<PermittedLabel> getPermittedLabels(String changeId) {
        return null;
    }
}
