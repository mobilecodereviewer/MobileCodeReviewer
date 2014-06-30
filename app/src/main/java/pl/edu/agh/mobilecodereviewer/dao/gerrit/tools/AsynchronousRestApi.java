package pl.edu.agh.mobilecodereviewer.dao.gerrit.tools;


import android.os.AsyncTask;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import pl.edu.agh.mobilecodereviewer.dto.ChangeInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.CommentInfoDTO;
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
        return runAsyncTask(new AsyncTask<Void, Void, List<ChangeInfoDTO>>() {

            @Override
            protected List<ChangeInfoDTO> doInBackground(Void... voids) {
                return getChanges();
            }
        });
    }

    @Override
    public ChangeInfoDTO getChangeDetails(final String id){
        return runAsyncTask(new AsyncTask<Void, Void, ChangeInfoDTO>() {

            @Override
            protected ChangeInfoDTO doInBackground(Void... voids) {
                return getChangeDetails(id);
            }
        });
    }

    @Override
    public Pair<String, RevisionInfoDTO> getCurrentRevisionForChange(final String id){
        return runAsyncTask(new AsyncTask<String, Void, Pair<String, RevisionInfoDTO>>() {

            @Override
            protected Pair<String, RevisionInfoDTO> doInBackground(String... params) {
                return getCurrentRevisionForChange(id);
            }

        });
    }

    @Override
    public String getFileContent(final String change_id, final String revision_id , final String file_id) {
        return runAsyncTask(new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                return getFileContent(change_id, revision_id, file_id);
            }
        });
    }

    @Override
    public Map<String,List<CommentInfoDTO>> getComments( final String change_id , final String revision_id) {
        return runAsyncTask(new AsyncTask<String, Void, Map<String,List<CommentInfoDTO>> >() {
            @Override
            protected Map<String,List<CommentInfoDTO>> doInBackground(String... params) {
                return getComments(change_id,revision_id);
            }
        });
    }
}


