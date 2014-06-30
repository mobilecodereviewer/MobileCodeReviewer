package pl.edu.agh.mobilecodereviewer.dao.gerrit.tools;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import pl.edu.agh.mobilecodereviewer.dao.gerrit.api.GerritService;
import pl.edu.agh.mobilecodereviewer.dto.ChangeInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.CommentInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.RevisionInfoDTO;
import retrofit.RestAdapter;

public class RestApi {
    private final GerritService gerritService;

    public RestApi() {
        gerritService = createGerritService();
    }

    public RestApi(GerritService gerritService) {
        this.gerritService = gerritService;
    }

    protected String createUrl() {
        return "http://192.168.194.161:8080";
    }

    protected Executor createExecutor() {
        return Executors.newCachedThreadPool();
    }

    protected RestAdapter createRestAdapter(Executor executor,String url) {
        return new RestAdapter.Builder()
                .setExecutors(executor, executor)
                .setEndpoint(url)
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .build();
    }

    protected GerritService createGerritService() {
        RestAdapter restAdapter = createRestAdapter( createExecutor() , createUrl() );
        return restAdapter.create(GerritService.class);
    }

    public List<ChangeInfoDTO> getChanges(){
        return gerritService.getChanges();
    }

    public ChangeInfoDTO getChangeDetails(String id){
        return gerritService.getChangeDetails(id);
    }

    public Pair<String, RevisionInfoDTO> getCurrentRevisionForChange(final String id){
        ChangeInfoDTO changeInfoDTO = gerritService.getChangeWithCurrentRevision(id);
        return new Pair<>(changeInfoDTO.getCurrentRevision(), changeInfoDTO.getRevisions().get(changeInfoDTO.getCurrentRevision()));
    }

    public String getFileContent(final String change_id, final String revision_id , final String file_id) {
        return gerritService.getFileContent(change_id, revision_id, file_id);
    }

    public Map<String,List<CommentInfoDTO>> getComments( final String change_id , final String revision_id) {
        return gerritService.getComments(change_id,revision_id);
    }
}






