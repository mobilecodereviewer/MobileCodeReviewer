package pl.edu.agh.mobilecodereviewer.dao.gerrit.utilities;


import android.os.AsyncTask;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import pl.edu.agh.mobilecodereviewer.dto.AccountInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.ChangeInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.CommentInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.CommentInputDTO;
import pl.edu.agh.mobilecodereviewer.dto.DiffInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.MergeableInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.NullMergeableInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.ReviewInputDTO;
import pl.edu.agh.mobilecodereviewer.dto.RevisionInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.SubmitInputDTO;
import pl.edu.agh.mobilecodereviewer.exceptions.HTTPErrorException;
import pl.edu.agh.mobilecodereviewer.model.SubmissionResult;
import pl.edu.agh.mobilecodereviewer.utilities.Pair;
import retrofit.client.Response;

/**
 * Class decorates RestApi with asynchronous task
 * menagement facility
 */
public class AsynchronousRestApi extends RestApi{
    private RestApi restApi;

    /**
     * Create object from the given restApi
     * @param restApi Appropriate restApi object
     */
    public AsynchronousRestApi(RestApi restApi) {
        this.restApi = restApi;
    }

    /**
     * Helper method to run async task given in parameter
     * @param asyncTask Task to execute
     * @param <F> Type of Parameters
     * @param <X> Type of Progress
     * @param <T> Type of Result
     * @return Result
     */
    protected <F,X,T> T runAsyncTask(AsyncTask<F,X,T> asyncTask ) {
        try {
            return asyncTask.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String getVersion(){
        return runAsyncTask(new AsyncTask<Object, Void, String>() {

            @Override
            protected String doInBackground(Object... voids) {
                return restApi.getVersion();
            }
        });
    }

    @Override
    public AccountInfoDTO getAccountInfo(){
        return runAsyncTask(new AsyncTask<Object, Void, AccountInfoDTO>() {

            @Override
            protected AccountInfoDTO doInBackground(Object... voids) {
                return restApi.getAccountInfo();
            }
        });
    }

    /**
     * Asynchronous execution of {@link RestApi#getChanges()}
     */
    @Override
    public List<ChangeInfoDTO> getChanges(){
        return runAsyncTask(new AsyncTask<Object, Void, List<ChangeInfoDTO>>() {

            @Override
            protected List<ChangeInfoDTO> doInBackground(Object... voids) {
                return restApi.getChanges();
            }
        });
    }

    /**
     * Asynchronous execution of {@link RestApi#getChangeDetails(String)}
     */
    @Override
    public ChangeInfoDTO getChangeDetails(final String id){
        return runAsyncTask(new AsyncTask<Object, Void, ChangeInfoDTO>() {

            @Override
            protected ChangeInfoDTO doInBackground(Object... voids) {
                return restApi.getChangeDetails(id);
            }
        });
    }

    /**
     * Asynchronous execution of {@link RestApi#getCurrentRevisionWithFiles}
     */
    @Override
    public Pair<String, RevisionInfoDTO> getCurrentRevisionWithFiles(final String id){
        return runAsyncTask(new AsyncTask<Object, Void, Pair<String, RevisionInfoDTO>>() {

            @Override
            protected Pair<String, RevisionInfoDTO> doInBackground(Object... params) {
                return restApi.getCurrentRevisionWithFiles(id);
            }

        });
    }

    /**
     * Asynchronous execution of {@link RestApi#getCurrentRevisionWithCommit(String)}
     */
    @Override
    public Pair<String, RevisionInfoDTO> getCurrentRevisionWithCommit(final String id){
        return runAsyncTask(new AsyncTask<Object, Void, Pair<String, RevisionInfoDTO>>() {

            @Override
            protected Pair<String, RevisionInfoDTO> doInBackground(Object... params) {
                return restApi.getCurrentRevisionWithCommit(id);
            }

        });
    }

    /**
     * Asynchronous get content of the file
     * @param change_id Indentifier of change
     * @param revision_id Identifier of revision
     * @param file_id File Path
     * @return Content of the file in Base64
     */
    @Override
    public String getFileContent(final String change_id, final String revision_id , final String file_id) {
        return runAsyncTask(new AsyncTask<Object, Void, String>() {
            @Override
            protected String doInBackground(Object... params) {
                return restApi.getFileContent(change_id, revision_id, file_id);
            }
        });
    }

    /**
     * Asynchronous execution of {@link RestApi#getComments(String, String)}
     */
    @Override
    public Map<String,List<CommentInfoDTO>> getComments( final String change_id , final String revision_id) {
        return runAsyncTask(new AsyncTask<Object, Void, Map<String,List<CommentInfoDTO>> >() {
            @Override
            protected Map<String,List<CommentInfoDTO>> doInBackground(Object... params) {
                return restApi.getComments(change_id,revision_id);
            }
        });
    }

    /**
     * Asynchronous execution of {@link RestApi#getMergeableInfoForCurrentRevision(String)}
     */
    @Override
    public MergeableInfoDTO getMergeableInfoForCurrentRevision(final String change_id) {
        return runAsyncTask(new AsyncTask<Object, Void, MergeableInfoDTO >() {
            @Override
            protected MergeableInfoDTO doInBackground(Object... params) {
                // @TODO wiem , ze jest to strasznie slabe ale mam dosc wiecznych bledow
                // z tym ,jestem juz tym zmeczony - mam nadzieje ze historia przyzna mi racje
                try {
                    return restApi.getMergeableInfoForCurrentRevision(change_id);
                } catch (Exception e) {
                    return new NullMergeableInfoDTO();
                }
            }
        });
    }

    /**
     * Asynchronous execution of {@link RestApi#getChangeTopic(String)}
     */
    @Override
    public String getChangeTopic(final String change_id) {
        return runAsyncTask(new AsyncTask<Object, Void, String >() {
            @Override
            protected String doInBackground(Object... params) {
                return restApi.getChangeTopic(change_id);
            }
        });
    }

    @Override
    public DiffInfoDTO getSourceCodeDiff(final String change_id, final String revision_id, final String file_id) {
        return runAsyncTask(new AsyncTask<Object, Void, DiffInfoDTO >() {
            @Override
            protected DiffInfoDTO doInBackground(Object... params) {
                return restApi.getSourceCodeDiff(change_id, revision_id, file_id);
            }
        });
    }

    @Override
    public void putReview(final String change_id, final String revision_id, final ReviewInputDTO reviewInputDTO){
        runAsyncTask(new AsyncTask<Object, Void, Void>() {
            @Override
            protected Void doInBackground(Object... params) {
                restApi.putReview(change_id, revision_id, reviewInputDTO);
                return null;
            }
        });
    }

    @Override
    public Map<String, List<CommentInfoDTO>> getDraftComments(final String changeId, final String revisionId){
       return  runAsyncTask(new AsyncTask<Object, Void, Map<String, List<CommentInfoDTO>>>(){
            @Override
            protected Map<String, List<CommentInfoDTO>> doInBackground(Object... objects) {
                return restApi.getDraftComments(changeId, revisionId);
            }
        });
    }

    @Override
    public CommentInfoDTO createDraftComment(final String changeId, final String revisionId, final CommentInputDTO commentInputDTO){
        return runAsyncTask(new AsyncTask<Object, Void, CommentInfoDTO>(){
            @Override
            protected CommentInfoDTO doInBackground(Object... objects) {
                return restApi.createDraftComment(changeId, revisionId, commentInputDTO);
            }
        });
    }

    @Override
    public CommentInfoDTO updateDraftComment(final String changeId, final String revisionId, final String draftId, final CommentInputDTO commentInputDTO){
        return runAsyncTask(new AsyncTask<Object, Object, CommentInfoDTO>() {
            @Override
            protected CommentInfoDTO doInBackground(Object... objects) {
                return restApi.updateDraftComment(changeId, revisionId, draftId, commentInputDTO);
            }
        });
    }

    @Override
    public void deleteDraftComment(final String changeId, final String revisionId, final String draftId){
        runAsyncTask(new AsyncTask<Object, Void, Void>() {
            @Override
            protected Void doInBackground(Object... objects) {
                restApi.deleteDraftComment(changeId, revisionId, draftId);
                return null;
            }
        });
    }

    @Override
    public SubmissionResult submitChange(final String changeId, final SubmitInputDTO submitInputDTO){
        return runAsyncTask(new AsyncTask<Object, Void, SubmissionResult>() {
            @Override
            protected SubmissionResult doInBackground(Object... objects) {
                return  restApi.submitChange(changeId, submitInputDTO);
            }
        });
    }
}


