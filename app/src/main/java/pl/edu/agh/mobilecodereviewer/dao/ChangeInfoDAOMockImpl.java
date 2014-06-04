package pl.edu.agh.mobilecodereviewer.dao;

import com.google.inject.Singleton;

import java.util.Arrays;
import java.util.List;

import pl.edu.agh.mobilecodereviewer.dao.api.ChangeInfoDAO;
import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;

/**
 * Simple Stub for ChangeInfoDAO with hardcoded values
 * about changes. In later version should be removed and
 * replaced with professional Mock bound to ChangeInfoDAO
 * interface in Injection Module. So far its only for
 * presentation/testing measures
 * @author AGH
 * @version 0.1
 * @since 0.1
 */
@Singleton
public class ChangeInfoDAOMockImpl implements ChangeInfoDAO {

    /**
     * Method replace hardcoded values about all changes
     * @return Hardcoded some information about changes
     */
    @Override
    public List<ChangeInfo> getInfoChanges() {
        return Arrays.asList(
                ChangeInfo.valueOf("Commit0") ,
                ChangeInfo.valueOf("Commit1") ,
                ChangeInfo.valueOf("Commit2")
        );
    }

}
