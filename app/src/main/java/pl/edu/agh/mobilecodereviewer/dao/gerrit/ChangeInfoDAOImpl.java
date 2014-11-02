package pl.edu.agh.mobilecodereviewer.dao.gerrit;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import pl.edu.agh.mobilecodereviewer.dao.api.ChangeInfoDAO;
import pl.edu.agh.mobilecodereviewer.dto.CommentInputDTO;
import pl.edu.agh.mobilecodereviewer.dto.ReviewInputDTO;
import pl.edu.agh.mobilecodereviewer.model.Comment;
import pl.edu.agh.mobilecodereviewer.model.PermittedLabel;
import pl.edu.agh.mobilecodereviewer.utilities.DateUtils;
import pl.edu.agh.mobilecodereviewer.utilities.Pair;
import pl.edu.agh.mobilecodereviewer.dao.gerrit.utilities.RestApi;
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

/**
 * Data access object for information about Changes. It is
 * some kind of adapter between data from gerrit instance
 * and appropriates models
 */
@Singleton
public class ChangeInfoDAOImpl implements ChangeInfoDAO {

    /**
     * Api from information will be given
     */
    private RestApi restApi;

    private Map<Pair<String, String>, Map<String, List<Comment>>> pendingComments = new HashMap<Pair<String, String>, Map<String, List<Comment>>>();

    @Override
    public void initialize(RestApi restApi){
        this.restApi = restApi;
    }

    /**
     * Construct Object with default {@link pl.edu.agh.mobilecodereviewer.dao.gerrit.utilities.RestApi}
     */
    public ChangeInfoDAOImpl() { }

    /**
     * Construct Object from given {@link pl.edu.agh.mobilecodereviewer.dao.gerrit.utilities.RestApi}
     * @param restApi
     */
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
        changeInfoModel.setUpdated(DateUtils.getPrettyDate(changeInfoDTO.getUpdated()));
        changeInfoModel.setNumber(changeInfoDTO.getNumber());

        if (changeInfoDTO.getInsertions() != null)
            changeInfoModel.setSize(changeInfoDTO.getInsertions() - changeInfoDTO.getDeletions());
        changeInfoModel.setCreated(DateUtils.getPrettyDate(changeInfoDTO.getCreated()));

        return changeInfoModel;
    }

    @Override
    public List<ChangeInfo> getAllChangesInfo() {

        List<ChangeInfoDTO> changeInfoDtos = restApi.getChanges();

        List<ChangeInfo> changeInfoModels = new ArrayList<ChangeInfo>();

        for(ChangeInfoDTO changeInfoDTO : changeInfoDtos){
            ChangeInfo changeInfoModel = ChangeInfo.valueOf(changeInfoDTO.getId(), changeInfoDTO.getChangeId(), changeInfoDTO.getSubject());

            changeInfoModel.setStatus(changeInfoDTO.getStatus().toString());
            changeInfoModel.setOwnerName(changeInfoDTO.getOwner().getName());
            changeInfoModel.setProject(changeInfoDTO.getProject());
            changeInfoModel.setBranch(changeInfoDTO.getBranch());
            changeInfoModel.setUpdated(DateUtils.getPrettyDate(changeInfoDTO.getUpdated()));
            changeInfoModel.setCurrentRevision(changeInfoDTO.getCurrentRevision());
            changeInfoModel.setNumber(changeInfoDTO.getNumber());

            if ( changeInfoDTO.getInsertions() != null )
                changeInfoModel.setSize(changeInfoDTO.getInsertions() - changeInfoDTO.getDeletions());
            else
                changeInfoModel.setSize(0);
            changeInfoModel.setCreated(DateUtils.getPrettyDate(changeInfoDTO.getCreated()));

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
            fileInfos.add(FileInfo.valueOf(id, currentRevisionForChange.first, fileName, fileInfoDTOs.get(fileName).getLinesInserted(), fileInfoDTOs.get(fileName).getLinesDeleted()));
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
            changeMessageInfos.add(new ChangeMessageInfo(changeMessageInfoDTO.getAuthor().getName(), DateUtils.getPrettyDate(changeMessageInfoDTO.getDate()), changeMessageInfoDTO.getMessage()));
        }

        return changeMessageInfos;
    }

    @Override
    public List<PermittedLabel> getPermittedLabels(String changeId) {
        Map<String, List<Integer>> permittedLabels = restApi.getChangeDetails(changeId).getPermittedLabels();

        List<PermittedLabel> result = new ArrayList<PermittedLabel>();
        for(String labelName : permittedLabels.keySet()){
            result.add(new PermittedLabel(labelName, permittedLabels.get(labelName)));
        }

        return result;
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

    @Override
    public void setReview(String changeId, String revisionId, String message, Map<String, Integer> votes) {

        ReviewInputDTO review = ReviewInputDTO.createVoteReview(message, votes);;

        Pair<String, String> reviewIdentifier =  getReviewIdentifier(changeId, revisionId);

        if(pendingComments.containsKey(reviewIdentifier)) {
            review.setComments(translatePendingCommentsToDTOs(reviewIdentifier));
        }

        restApi.putReview(changeId, revisionId, review);

    }

    private Map<String, List<CommentInputDTO>> translatePendingCommentsToDTOs(Pair<String, String> reviewIdentifier) {
        Map<String, List<Comment>> filesCommentsModels = pendingComments.get(reviewIdentifier);
        Map<String, List<CommentInputDTO>> filesCommentsDTOs = new HashMap<>();

        for(String path : filesCommentsModels.keySet()) {

            List<Comment> commentsModels = filesCommentsModels.get(path);
            List<CommentInputDTO> commentsDTOs = new LinkedList<CommentInputDTO>();

            for(Comment comment : commentsModels){
                commentsDTOs.add(new CommentInputDTO(comment.getLine(), comment.getContent()));
            }

            filesCommentsDTOs.put(path, commentsDTOs);
        }

        pendingComments.remove(reviewIdentifier);

        return filesCommentsDTOs;
    }

    @Override
    public void putFileComment(String changeId, String revisionId,Comment comment) {
        String path = comment.getPath();
        comment.setPending(true);

        Pair<String, String> reviewIdentifier = getReviewIdentifier(changeId, revisionId);

        if(pendingComments.containsKey(reviewIdentifier)) {
            List<Comment> comments = pendingComments.get(reviewIdentifier).get(path);
            if(comments != null){
                comments.add(comment);
            } else {
                List<Comment> commentsList = new LinkedList<>();
                commentsList.add(comment);
                pendingComments.get(reviewIdentifier).put(path, commentsList);
            }
        } else {
            Map<String, List<Comment>> comments = new HashMap<>();
            List<Comment> commentsList = new LinkedList<>();
            commentsList.add(comment);
            comments.put(path, commentsList);
            pendingComments.put(reviewIdentifier, comments);
        }

    }

    @Override
    public Map<String, List<Comment>> deleteFileComment(String changeId, String revisionId, String path, Comment comment) {
        Pair<String, String> reviewIdentifier = getReviewIdentifier(changeId, revisionId);

        if(pendingComments.containsKey(reviewIdentifier)){

            List<Comment> comments = pendingComments.get(reviewIdentifier).get(path);
            comments.remove(comment);

            if(comments.isEmpty()){
                pendingComments.get(reviewIdentifier).remove(path);
            }
        }

        return pendingComments.get(reviewIdentifier);
    }

    @Override
    public Map<String, List<Comment>> updateFileComment(String changeId, String revisionId, String path, Comment comment, String content) {
        Pair<String, String> reviewIdentifier = getReviewIdentifier(changeId, revisionId);

        if(pendingComments.containsKey(reviewIdentifier)){

            List<Comment> fileComments = pendingComments.get(reviewIdentifier).get(path);

            for(Comment checkedComment : fileComments){
                if(checkedComment.equals(comment)){
                    comment.setContent(content);
                    break;
                }
            }

        }

        return pendingComments.get(reviewIdentifier);
    }

    @Override
    public Map<String, List<Comment>> getPendingComments(String changeId, String revisionId) {
        return pendingComments.get(getReviewIdentifier(changeId, revisionId));
    }

    private Pair<String, String> getReviewIdentifier(String changeId, String revisionId){
        return new Pair<String, String>(changeId, revisionId);
    }

}
