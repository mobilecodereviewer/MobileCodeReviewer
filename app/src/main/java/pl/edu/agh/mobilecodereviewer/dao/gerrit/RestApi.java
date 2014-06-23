package pl.edu.agh.mobilecodereviewer.dao.gerrit;

import android.os.AsyncTask;
import android.util.Pair;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import pl.edu.agh.mobilecodereviewer.dto.ChangeInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.RevisionInfoDTO;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;

public class RestApi {

    private interface GerritService {

        @GET("/changes/")
        List<ChangeInfoDTO> getChanges();

        @GET("/changes/{id}/detail/")
        ChangeInfoDTO getChangeDetails(@Path("id") String id);

        @GET("/changes/{id}/?o=CURRENT_REVISION&o=CURRENT_FILES")
        ChangeInfoDTO getChangeWithCurrentRevision(@Path("id") String id);

    }

    private static final String API_URL = "http://192.168.194.161:8080";

    private static final Executor EXECUTOR = Executors.newCachedThreadPool();

    private static final RestAdapter REST_ADAPTER = new RestAdapter.Builder()
            .setExecutors(EXECUTOR, EXECUTOR)
            .setEndpoint(API_URL)
            .setLogLevel(RestAdapter.LogLevel.BASIC)
            .build();

    private static final GerritService GERRIT_SERVICE = REST_ADAPTER.create(GerritService.class);

    public static List<ChangeInfoDTO> getChanges(){

        try {
            return new AsyncTask<Void, Void, List<ChangeInfoDTO>>(){

                @Override
                protected List<ChangeInfoDTO> doInBackground(Void... params) {
                    return GERRIT_SERVICE.getChanges();
                }

            }.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ChangeInfoDTO getChangeDetails(String id){

        try {
            return new AsyncTask<String, Void, ChangeInfoDTO>() {

                @Override
                protected ChangeInfoDTO doInBackground(String... params) {
                    return GERRIT_SERVICE.getChangeDetails(params[0]);
                }

            }.execute(id).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Pair<String, RevisionInfoDTO> getCurrentRevisionForChange(String id){

        try {
            return new AsyncTask<String, Void, Pair<String, RevisionInfoDTO>>() {

                @Override
                protected Pair<String, RevisionInfoDTO> doInBackground(String... params) {
                    ChangeInfoDTO changeInfoDTO = GERRIT_SERVICE.getChangeWithCurrentRevision(params[0]);

                    return new Pair<String, RevisionInfoDTO>(changeInfoDTO.getCurrentRevision(), changeInfoDTO.getRevisions().get(changeInfoDTO.getCurrentRevision()));
                }

            }.execute(id).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }
}
