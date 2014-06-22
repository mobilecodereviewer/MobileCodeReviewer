package pl.edu.agh.mobilecodereviewer.dao.gerrit;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.mobilecodereviewer.dao.api.ChangeInfoDAO;
import pl.edu.agh.mobilecodereviewer.dto.ChangeInfoDTO;
import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.model.FileInfo;

public class ChangeInfoDAOImpl implements ChangeInfoDAO {

    @Override
    public ChangeInfo getChangeInfoById(String id) {

        ChangeInfoDTO changeInfoDTO = RestApi.getChangeDetails(id);

        ChangeInfo changeInfo = ChangeInfo.valueOf(changeInfoDTO.getId(), changeInfoDTO.getChangeId(), changeInfoDTO.getSubject());

        return changeInfo;
    }

    @Override
    public List<ChangeInfo> getAllChangesInfo() {

        List<ChangeInfoDTO> changeInfoDtos = RestApi.getChanges();

        List<ChangeInfo> changeInfoModels = new ArrayList<ChangeInfo>();

        for(ChangeInfoDTO changeInfoDTO : changeInfoDtos){
            ChangeInfo changeInfoModel = ChangeInfo.valueOf(changeInfoDTO.getId(), changeInfoDTO.getChangeId(), changeInfoDTO.getSubject());
            changeInfoModels.add(changeInfoModel);
        }

        return changeInfoModels;
    }

    @Override
    public List<FileInfo> getModifiedFiles(String id) {
        return null;
    }
}
