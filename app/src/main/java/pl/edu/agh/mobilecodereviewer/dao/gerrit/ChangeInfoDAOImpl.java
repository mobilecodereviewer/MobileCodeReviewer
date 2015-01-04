package pl.edu.agh.mobilecodereviewer.dao.gerrit;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import pl.edu.agh.mobilecodereviewer.dao.api.ChangeInfoDAO;
import pl.edu.agh.mobilecodereviewer.dto.CommentInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.CommentInputDTO;
import pl.edu.agh.mobilecodereviewer.dto.ReviewInputDTO;
import pl.edu.agh.mobilecodereviewer.dto.SubmitInputDTO;
import pl.edu.agh.mobilecodereviewer.exceptions.HTTPErrorException;
import pl.edu.agh.mobilecodereviewer.model.Comment;
import pl.edu.agh.mobilecodereviewer.model.PermittedLabel;
import pl.edu.agh.mobilecodereviewer.model.SubmissionResult;
import pl.edu.agh.mobilecodereviewer.model.utilities.ChangeInfoHelper;
import pl.edu.agh.mobilecodereviewer.model.utilities.CommentHelper;
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
import retrofit.client.Response;

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

        return ChangeInfoHelper.modelFromDTO(changeInfoDTO, false);
    }

    @Override
    public List<ChangeInfo> getAllChangesInfo() {
        List<ChangeInfoDTO> changeInfoDtos = restApi.getChanges();
        List<ChangeInfo> changeInfoModels = new ArrayList<ChangeInfo>();

        for(ChangeInfoDTO changeInfoDTO : changeInfoDtos){
            ChangeInfo changeInfoModel = ChangeInfoHelper.modelFromDTO(changeInfoDTO, true);
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
            String authorName = changeMessageInfoDTO.getAuthor() != null ? changeMessageInfoDTO.getAuthor().getName() : "";
            changeMessageInfos.add(new ChangeMessageInfo(authorName, DateUtils.getPrettyDate(changeMessageInfoDTO.getDate()), changeMessageInfoDTO.getMessage()));
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
        return LabelInfoHelper.labelsFromMap(labelInfoDTOs, false);
    }

    @Override
    public void setReview(String changeId, String revisionId, String message, Map<String, Integer> votes) {

        ReviewInputDTO review = ReviewInputDTO.createVoteReview(message, votes);

        Map<String, List<CommentInfoDTO>> pendingComments = restApi.getDraftComments(changeId, revisionId);
        if(pendingComments != null && pendingComments.size() != 0) {
            review.setComments(translateDraftCommentsToCommentInputDTOs(pendingComments));
        }

        restApi.putReview(changeId, revisionId, review);

    }

    private Map<String, List<CommentInputDTO>> translateDraftCommentsToCommentInputDTOs(Map<String, List<CommentInfoDTO>> draftComments) {
        Map<String, List<CommentInputDTO>> filesCommentInputsDTOs = new HashMap<>();

        for(String path : draftComments.keySet()){
            List<CommentInfoDTO> commentInfoDTOs = draftComments.get(path);

            List<CommentInputDTO> commentInputDTOs = new LinkedList<>();
            for(CommentInfoDTO commentInfoDTO : commentInfoDTOs){
                commentInputDTOs.add(new CommentInputDTO(commentInfoDTO.getLine(), commentInfoDTO.getMessage()));
            }

            filesCommentInputsDTOs.put(path, commentInputDTOs);
        }

        return filesCommentInputsDTOs;
    }

    @Override
    public void putDraftComment(String changeId, String revisionId, Comment comment) {
        restApi.createDraftComment(changeId, revisionId, new CommentInputDTO(comment.getLine(), comment.getContent(), comment.getPath()));
    }

    @Override
    public Map<String, List<Comment>> deleteFileComment(String changeId, String revisionId, String path, Comment comment) {
        restApi.deleteDraftComment(changeId, revisionId, comment.getDraftId());
        return getPendingComments(changeId, revisionId);
    }

    @Override
    public Map<String, List<Comment>> updateFileComment(String changeId, String revisionId, String path, Comment comment, String content) {
        restApi.updateDraftComment(changeId, revisionId, comment.getDraftId(), new CommentInputDTO(comment.getLine(), content));
        return getPendingComments(changeId, revisionId);
    }

    @Override
    public Map<String, List<Comment>> getPendingComments(String changeId, String revisionId) {
        return translateCommentInfoDTOsToComments(restApi.getDraftComments(changeId, revisionId));
    }

    @Override
    public SubmissionResult submitChange(String changeId) {
        return restApi.submitChange(changeId, new SubmitInputDTO(true));
    }

    private Map<String, List<Comment>> translateCommentInfoDTOsToComments(Map<String, List<CommentInfoDTO>> draftComments){
        Map<String, List<Comment>> filesCommentsDTOs = new HashMap<>();

        if(draftComments != null) {
            for (String path : draftComments.keySet()) {
                List<CommentInfoDTO> commentInfoDTOs = draftComments.get(path);

                List<Comment> commentInputDTOs = new LinkedList<>();
                for (CommentInfoDTO commentInfoDTO : commentInfoDTOs) {
                    commentInputDTOs.add(CommentHelper.commentFromDTO(commentInfoDTO, path, true));
                }

                filesCommentsDTOs.put(path, commentInputDTOs);
            }
        }

        return filesCommentsDTOs;
    }
}
