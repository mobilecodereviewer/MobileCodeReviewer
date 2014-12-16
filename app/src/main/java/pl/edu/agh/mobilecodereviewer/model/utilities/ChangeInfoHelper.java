package pl.edu.agh.mobilecodereviewer.model.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import pl.edu.agh.mobilecodereviewer.dto.ChangeInfoDTO;
import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.utilities.DateUtils;

public final class ChangeInfoHelper {

    public static final String GERRIT_TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static Date gerritTimeToDate(String updated) {
        try {
            updated = updated.substring( 0,updated.indexOf('.') );
            return new SimpleDateFormat(GERRIT_TIMESTAMP_FORMAT, Locale.ENGLISH).parse(updated);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Date assigned in updated must be in Gerrit Format: " +
                                               GERRIT_TIMESTAMP_FORMAT +
                                               " but was " + updated);
        }
    }


    public static ChangeInfo modelFromDTO(ChangeInfoDTO changeInfoDTO, boolean extractLabels){
        ChangeInfo changeInfo = ChangeInfo.valueOf(changeInfoDTO.getId(), changeInfoDTO.getChangeId(), changeInfoDTO.getSubject());

        changeInfo.setStatus(changeInfoDTO.getStatus());
        changeInfo.setOwnerName(changeInfoDTO.getOwner().getName());
        changeInfo.setProject(changeInfoDTO.getProject());
        changeInfo.setBranch(changeInfoDTO.getBranch());
        changeInfo.setUpdated(changeInfoDTO.getUpdated());
        changeInfo.setCurrentRevision(changeInfoDTO.getCurrentRevision());
        changeInfo.setNumber(changeInfoDTO.getNumber());

        changeInfo.setInsertions(changeInfoDTO.getInsertions());
        changeInfo.setDeletions(changeInfoDTO.getDeletions());
        changeInfo.setStarred(changeInfoDTO.getStarred() );
        changeInfo.setHasDraftComments(changeInfoDTO.getHas_draft_comments());
        changeInfo.setMergeable(changeInfoDTO.getMergeable());
        changeInfo.setReviewed(changeInfoDTO.getReviewed());
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

    public static String gerritDateToString(Date date) {
        DateFormat df = new SimpleDateFormat(GERRIT_TIMESTAMP_FORMAT);
        return df.format(date);
    }

}
