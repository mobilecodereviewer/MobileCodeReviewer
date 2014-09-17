package pl.edu.agh.mobilecodereviewer.dao.gerrit.tools;


import android.os.AsyncTask;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import pl.edu.agh.mobilecodereviewer.dto.ChangeInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.CommentInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.DiffInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.MergeableInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.RevisionInfoDTO;

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

    /**
     * Asyncrhonous execute getting changes
     * @return List of {@link pl.edu.agh.mobilecodereviewer.dto.ChangeInfoDTO}
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
     * Asynchronous execute getting details of the given changes
     * @param id Change identifier
     * @return {@link pl.edu.agh.mobilecodereviewer.dto.ChangeInfoDTO}
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
     * Asynchronous execute getting current revision changes
     * @param id Change identifier
     * @return Mapper between revision and {@link pl.edu.agh.mobilecodereviewer.dto.RevisionInfoDTO}
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
     * Get all comments for a given revision
     * @param change_id Identifier of change
     * @param revision_id Identifier of revision
     * @return Mapping between file and list of {@link pl.edu.agh.mobilecodereviewer.dto.CommentInfoDTO}
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

    @Override
    public MergeableInfoDTO getMergeableInfoForCurrentRevision(final String change_id) {
        return runAsyncTask(new AsyncTask<Object, Void, MergeableInfoDTO >() {
            @Override
            protected MergeableInfoDTO doInBackground(Object... params) {
                return restApi.getMergeableInfoForCurrentRevision(change_id);
            }
        });
    }

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

}


