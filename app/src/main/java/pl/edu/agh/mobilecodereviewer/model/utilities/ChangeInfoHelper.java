package pl.edu.agh.mobilecodereviewer.model.utilities;

import pl.edu.agh.mobilecodereviewer.dto.ChangeInfoDTO;
import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.utilities.DateUtils;

public final class ChangeInfoHelper {

    public static ChangeInfo modelFromDTO(ChangeInfoDTO changeInfoDTO, boolean extractLabels){
        ChangeInfo changeInfo = ChangeInfo.valueOf(changeInfoDTO.getId(), changeInfoDTO.getChangeId(), changeInfoDTO.getSubject());

        changeInfo.setStatus(changeInfoDTO.getStatus());
        changeInfo.setOwnerName(changeInfoDTO.getOwner().getName());
        changeInfo.setProject(changeInfoDTO.getProject());
        changeInfo.setBranch(changeInfoDTO.getBranch());
        changeInfo.setUpdated(DateUtils.getPrettyDate(changeInfoDTO.getUpdated()));
        changeInfo.setCurrentRevision(changeInfoDTO.getCurrentRevision());
        changeInfo.setNumber(changeInfoDTO.getNumber());
        changeInfo.setCreated(DateUtils.getPrettyDate(changeInfoDTO.getCreated()));

        if ( changeInfoDTO.getInsertions() != null && changeInfoDTO.getDeletions() != null)
            changeInfo.setSize(changeInfoDTO.getInsertions() + changeInfoDTO.getDeletions());
        else if(changeInfoDTO.getInsertions() != null)
            changeInfo.setSize(changeInfoDTO.getInsertions());
        else if(changeInfoDTO.getDeletions() != null)
            changeInfo.setSize(changeInfoDTO.getDeletions());
        else
            changeInfo.setSize(0);

        if(extractLabels){
            changeInfo.setLabels(LabelInfoHelper.labelsFromMap(changeInfoDTO.getLabels(), true));
        }

        return changeInfo;
    }

}
