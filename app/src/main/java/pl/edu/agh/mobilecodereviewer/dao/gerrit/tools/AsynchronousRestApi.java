package pl.edu.agh.mobilecodereviewer.dao.gerrit.tools;


import android.os.AsyncTask;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import pl.edu.agh.mobilecodereviewer.dto.ChangeInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.CommentInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.MergeableInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.RevisionInfoDTO;

public class AsynchronousRestApi extends RestApi{
    private RestApi restApi;

    public AsynchronousRestApi(RestApi restApi) {
        this.restApi = restApi;
    }

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
    public List<ChangeInfoDTO> getChanges(){
        return runAsyncTask(new AsyncTask<Object, Void, List<ChangeInfoDTO>>() {

            @Override
            protected List<ChangeInfoDTO> doInBackground(Object... voids) {
                return restApi.getChanges();
            }
        });
    }

    @Override
    public ChangeInfoDTO getChangeDetails(final String id){
        return runAsyncTask(new AsyncTask<Object, Void, ChangeInfoDTO>() {

            @Override
            protected ChangeInfoDTO doInBackground(Object... voids) {
                return restApi.getChangeDetails(id);
            }
        });
    }

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


    @Override
    public String getFileContent(final String change_id, final String revision_id , final String file_id) {
        return runAsyncTask(new AsyncTask<Object, Void, String>() {
            @Override
            protected String doInBackground(Object... params) {
                return restApi.getFileContent(change_id, revision_id, file_id);
            }
        });
    }

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

}


