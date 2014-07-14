package pl.edu.agh.mobilecodereviewer.dao.gerrit;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pl.edu.agh.mobilecodereviewer.dao.api.ChangeInfoDAO;
import pl.edu.agh.mobilecodereviewer.dao.gerrit.tools.AsynchronousRestApi;
import pl.edu.agh.mobilecodereviewer.dao.gerrit.tools.Pair;
import pl.edu.agh.mobilecodereviewer.dao.gerrit.tools.RestApi;
import pl.edu.agh.mobilecodereviewer.dto.ChangeInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.ChangeMessageInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.FileInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.LabelInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.MergeableInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.RevisionInfoDTO;
import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.model.ChangeMessageInfo;
import pl.edu.agh.mobilecodereviewer.model.FileInfo;
import pl.edu.agh.mobilecodereviewer.model.LabelInfo;
import pl.edu.agh.mobilecodereviewer.model.MergeableInfo;
import pl.edu.agh.mobilecodereviewer.model.utilities.LabelInfoHelper;

public class ChangeInfoDAOImpl implements ChangeInfoDAO {

    private RestApi restApi;

    public ChangeInfoDAOImpl() {
        this( new AsynchronousRestApi( new RestApi() ) );
    }

    public ChangeInfoDAOImpl(RestApi restApi) {
        this.restApi = restApi;
    }


    @Override
    public ChangeInfo getChangeInfoById(String id) {

        ChangeInfoDTO changeInfoDTO = restApi.getChangeDetails(id);

        ChangeInfo changeInfoModel = ChangeInfo.valueOf(changeInfoDTO.getId(), changeInfoDTO.getChangeId(), changeInfoDTO.getSubject());

        changeInfoModel.setStatus(changeInfoDTO.getStatus());
        changeInfoModel.setOwnerName(changeInfoDTO.getOwner().getName());
        changeInfoModel.setProject(changeInfoDTO.getProject());
        changeInfoModel.setBranch(changeInfoDTO.getBranch());
        changeInfoModel.setUpdated(changeInfoDTO.getUpdated());
        changeInfoModel.setSize(changeInfoDTO.getInsertions() - changeInfoDTO.getDeletions());
        changeInfoModel.setCreated(changeInfoDTO.getCreated());

        return changeInfoModel;
    }

    @Override
    public List<ChangeInfo> getAllChangesInfo() {

        List<ChangeInfoDTO> changeInfoDtos = restApi.getChanges();

        List<ChangeInfo> changeInfoModels = new ArrayList<ChangeInfo>();

        for(ChangeInfoDTO changeInfoDTO : changeInfoDtos){
            ChangeInfo changeInfoModel = ChangeInfo.valueOf(changeInfoDTO.getId(), changeInfoDTO.getChangeId(), changeInfoDTO.getSubject());

            changeInfoModel.setStatus(changeInfoDTO.getStatus());
            changeInfoModel.setOwnerName(changeInfoDTO.getOwner().getName());
            changeInfoModel.setProject(changeInfoDTO.getProject());
            changeInfoModel.setBranch(changeInfoDTO.getBranch());
            changeInfoModel.setUpdated(changeInfoDTO.getUpdated());
            changeInfoModel.setSize(changeInfoDTO.getInsertions() - changeInfoDTO.getDeletions());
            changeInfoModel.setCreated(changeInfoDTO.getCreated());

            changeInfoModels.add(changeInfoModel);
        }

        return changeInfoModels;
    }

    @Override
    public List<FileInfo> getModifiedFiles(String id) {

        Pair<String, RevisionInfoDTO> currentRevisionForChange = restApi.getCurrentRevisionWithFiles(id);

        Map<String, FileInfoDTO> fileInfoDTOs = currentRevisionForChange.second.getFiles();

        List<FileInfo> fileInfos = new ArrayList<FileInfo>();

        for(String fileName : fileInfoDTOs.keySet()){
            fileInfos.add(FileInfo.valueOf(id, currentRevisionForChange.first, fileName));
        }

        return fileInfos;
    }

    @Override
    public MergeableInfo getMergeableInfo(String id) {

        MergeableInfoDTO mergeableInfoDTO = restApi.getMergeableInfoForCurrentRevision(id);

        return MergeableInfo.valueOf(mergeableInfoDTO.getSubmitType(), mergeableInfoDTO.isMergeable());
    }

    @Override
    public String getChangeTopic(String id) {
        return restApi.getChangeTopic(id);
    }

    @Override
    public String getCommitMessageForChange(String id) {
        return restApi.getCurrentRevisionWithCommit(id).second.getCommit().getMessage();
    }

    @Override
    public List<ChangeMessageInfo> getChangeMessages(String id) {
        List<ChangeMessageInfoDTO> changeMessageInfoDTOs = restApi.getChangeDetails(id).getMessages();
        List<ChangeMessageInfo> changeMessageInfos = new ArrayList<ChangeMessageInfo>();

        for(ChangeMessageInfoDTO changeMessageInfoDTO : changeMessageInfoDTOs){
            changeMessageInfos.add(new ChangeMessageInfo(changeMessageInfoDTO.getAuthor().getName(), changeMessageInfoDTO.getDate().substring(0, 19), changeMessageInfoDTO.getMessage()));
        }

        return changeMessageInfos;
    }

    @Override
    public List<LabelInfo> getLabels(String id) {
        Map<String, LabelInfoDTO> labelInfoDTOs = restApi.getChangeDetails(id).getLabels();
        List<LabelInfo> labelInfos = new ArrayList<LabelInfo>();


        for(String labelName : labelInfoDTOs.keySet()){
            LabelInfoDTO labelInfoDTO = labelInfoDTOs.get(labelName);
            labelInfos.add(LabelInfoHelper.createLabelInfoFromDTO(labelName, labelInfoDTOs.get(labelName)));
        }

        return labelInfos;
    }



}