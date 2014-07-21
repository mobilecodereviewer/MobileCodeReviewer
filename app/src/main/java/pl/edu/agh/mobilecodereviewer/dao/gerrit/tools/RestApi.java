package pl.edu.agh.mobilecodereviewer.dao.gerrit.tools;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import pl.edu.agh.mobilecodereviewer.dao.gerrit.api.GerritService;
import pl.edu.agh.mobilecodereviewer.dto.ChangeInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.CommentInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.MergeableInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.RevisionInfoDTO;
import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.mime.TypedInput;

/**
 * Element responsible for getting data from gerrit instance
 */
public class RestApi {
    /**
     * Class for binding between method and url
     */
    private final GerritService gerritService;

    /**
     * Create instance of restApi with default data access service
     */
    public RestApi() {
        gerritService = createGerritService();
    }

    /**
     * Create instance of restApi from given data access service
     * @param gerritService {@link pl.edu.agh.mobilecodereviewer.dao.gerrit.api.GerritService}
     */
    public RestApi(GerritService gerritService) {
        this.gerritService = gerritService;
    }

    /**
     * Return default URL
     * @return default URL
     */
    protected String createUrl() {
        return "http://192.168.1.102:8080";
    }

    /**
     * Create default executor of tasks
     * @return {@link java.util.concurrent.Executor}
     */
    protected Executor createExecutor() {
        return Executors.newCachedThreadPool();
    }

    /**
     * Create default adapter from executor and url
     * @param executor {@link java.util.concurrent.Executor}
     * @param url URL of gerrit instance
     * @return {@link retrofit.RestAdapter}
     */
    protected RestAdapter createRestAdapter(Executor executor,String url) {
        return new RestAdapter.Builder()
                .setExecutors(executor, executor)
                .setEndpoint(url)
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .build();
    }

    /**
     * Create default gerrit service
     * @return {@link pl.edu.agh.mobilecodereviewer.dao.gerrit.api.GerritService}
     */
    protected GerritService createGerritService() {
        RestAdapter restAdapter = createRestAdapter( createExecutor() , createUrl() );
        return restAdapter.create(GerritService.class);
    }

    /**
     * Download list of changes
     * @return List of {@link pl.edu.agh.mobilecodereviewer.dto.ChangeInfoDTO}
     */
    public List<ChangeInfoDTO> getChanges(){
        return gerritService.getChanges();
    }

    /**
     * Download details of the change
     * @param id Details Identifier
     * @return {@link pl.edu.agh.mobilecodereviewer.dto.ChangeInfoDTO}
     */
    public ChangeInfoDTO getChangeDetails(String id){
        return gerritService.getChangeDetails(id);
    }


    public Pair<String, RevisionInfoDTO> getCurrentRevisionWithFiles(final String id){
        ChangeInfoDTO changeInfoDTO = gerritService.getCurrentRevisionWithFiles(id);
        return new Pair<>(changeInfoDTO.getCurrentRevision(), changeInfoDTO.getRevisions().get(changeInfoDTO.getCurrentRevision()));
    }

    public Pair<String, RevisionInfoDTO> getCurrentRevisionWithCommit(final String id) {
        ChangeInfoDTO changeInfoDTO = gerritService.getCurrentRevisionWithCommit(id);
        return new Pair<>(changeInfoDTO.getCurrentRevision(), changeInfoDTO.getRevisions().get(changeInfoDTO.getCurrentRevision()));

    }

    /**
     * Get current revision of change
     * @param id Change identifier
     * @return {@link} of Revision name and {@link pl.edu.agh.mobilecodereviewer.dto.RevisionInfoDTO}
     */
    public Pair<String, RevisionInfoDTO> getCurrentRevisionForChange(final String id) {
        ChangeInfoDTO changeInfoDTO = gerritService.getChangeWithCurrentRevision(id);
        return new Pair<>(changeInfoDTO.getCurrentRevision(),
                          changeInfoDTO.getRevisions().get(changeInfoDTO.getCurrentRevision()));
    }

    /**
     * Get content of the file in base64
     * @param change_id Change Identifier
     * @param revision_id Revision Identifier
     * @param file_id Path of file
     * @return Content compressed in base64
     */
    public String getFileContent(final String change_id, final String revision_id , final String file_id) {
        Response response = gerritService.getFileContent(change_id, revision_id, file_id);

        TypedInput responseBody = response.getBody();
        return getStringFromTypedInput( responseBody );
    }

    /**
     * Helper class for getting String value from {@link retrofit.mime.TypedInput}
     * @param typedInput {@link retrofit.mime.TypedInput}
     * @return String value from TypedInput
     */
    protected String getStringFromTypedInput(TypedInput typedInput) {
        try {
            InputStream in = typedInput.in();
            byte[] bytes = IOUtils.toByteArray(in);
            return new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get all comments for a given change and revision
     * @param change_id Change Identifier
     * @param revision_id Revision Identifier
     * @return Mapping between file and list of comments
     */
    public Map<String,List<CommentInfoDTO>> getComments( final String change_id , final String revision_id) {
        return gerritService.getComments(change_id,revision_id);
    }

    public MergeableInfoDTO getMergeableInfoForCurrentRevision(final String change_id){
        return gerritService.getMergeableInfoForCurrentRevision(change_id);
    }

    public String getChangeTopic(final String change_id) {
        return gerritService.getChangeTopic(change_id);
    }

}






