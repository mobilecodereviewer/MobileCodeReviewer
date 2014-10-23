package pl.edu.agh.mobilecodereviewer.dao.gerrit.utilities;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import pl.edu.agh.mobilecodereviewer.utilities.Pair;
import pl.edu.agh.mobilecodereviewer.utilities.ConfigurationInfo;
import pl.edu.agh.mobilecodereviewer.dao.gerrit.api.GerritService;
import pl.edu.agh.mobilecodereviewer.exceptions.NetworkException;
import pl.edu.agh.mobilecodereviewer.exceptions.UnauthorizedRequestException;
import pl.edu.agh.mobilecodereviewer.dto.AccountInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.ChangeInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.CommentInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.CommentInputDTO;
import pl.edu.agh.mobilecodereviewer.dto.DiffInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.MergeableInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.ReviewInputDTO;
import pl.edu.agh.mobilecodereviewer.dto.RevisionInfoDTO;
import retrofit.ErrorHandler;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Client;
import retrofit.client.Header;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.mime.TypedInput;

import static pl.edu.agh.mobilecodereviewer.dao.gerrit.utilities.HttpDigestAuth.tryDigestAuthentication;

/**
 * Element responsible for getting data from gerrit instance
 */
public class RestApi {
    /**
     * Class for binding between method and url
     */
    private GerritService gerritService;

    private ConfigurationInfo configurationInfo;

    // private String url = "http://apps.iisg.agh.edu.pl:8081/";
    // private String login = "d00d171";
    // private String password = "SdGiS0eM3ctw";

    public RestApi(String url) {
        this.configurationInfo = new ConfigurationInfo("http://apps.iisg.agh.edu.pl:8081/",
                                                       url,
                                                       "pili",
                                                       "F9xlAF4XS4tz",
                                                       false);
        this.gerritService = createGerritService();
    }

    public RestApi(ConfigurationInfo configurationInfo){
        this.configurationInfo = configurationInfo;
        this.gerritService = createGerritService();
    }

    public RestApi(String url, Client client) {
        this.configurationInfo = new ConfigurationInfo(null, url, null, null, false);
        this.gerritService = createGerritService(client);
    }

    /**
     * Create instance of restApi from given data access service
     *
     * @param gerritService {@link pl.edu.agh.mobilecodereviewer.dao.gerrit.api.GerritService}
     */
    public RestApi(GerritService gerritService) {
        this.gerritService = gerritService;
    }

    public RestApi() {
    }

    /**
     * Return default URL
     *
     * @return default URL
     */
    protected String createUrl() {
        return this.configurationInfo.getUrl();
    }

    /**
     * Create default executor of tasks
     *
     * @return {@link java.util.concurrent.Executor}
     */
    protected Executor createExecutor() {
        return Executors.newCachedThreadPool();
    }


    protected RestAdapter createRestAdapter(Client client, Executor executor, String url) {
        return new RestAdapter.Builder()
                .setExecutors(executor, executor)
                .setEndpoint(url)
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setClient(client).build();
    }

    /**
     * Create default adapter from executor and url
     *
     * @param executor {@link java.util.concurrent.Executor}
     * @param url      URL of gerrit instance
     * @return {@link retrofit.RestAdapter}
     */
    protected RestAdapter createRestAdapter(Executor executor, String url) {
        return new RestAdapter.Builder()
                .setExecutors(executor, executor)
                .setEndpoint(url)
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setClient(new GerritClient(this.configurationInfo))
                .setErrorHandler(new GerritErrorHandler())
                .build();
    }

    /**
     * Create default gerrit service
     *
     * @return {@link pl.edu.agh.mobilecodereviewer.dao.gerrit.api.GerritService}
     */
    protected GerritService createGerritService() {
        RestAdapter restAdapter = createRestAdapter(createExecutor(), createUrl());
        setHttpAuthenticatorForVM();
        return restAdapter.create(GerritService.class);
    }

    private void setHttpAuthenticatorForVM() {
        /*Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(login, password.toCharArray());
            }
        });*/
    }

    protected GerritService createGerritService(Client client) {
        RestAdapter restAdapter = createRestAdapter(client, createExecutor(), createUrl());
        return restAdapter.create(GerritService.class);
    }

    public String getVersion(){

        try {
            return gerritService.getVersion();
        } catch (NetworkException e) {
            return null;
        }
    }

    public AccountInfoDTO getAccountInfo(){
        try {
            return gerritService.getAccountInfo();
        } catch(Exception ex) {
            return null;
        }
    }

    /**
     * Download list of changes
     *
     * @return List of {@link pl.edu.agh.mobilecodereviewer.dto.ChangeInfoDTO}
     */
    public List<ChangeInfoDTO> getChanges() {
        List<List<ChangeInfoDTO>> allChanges = gerritService.getChanges();
        return Lists.newArrayList(Iterables.concat(allChanges));
    }

    /**
     * Download details of the change
     *
     * @param id Details Identifier
     * @return {@link pl.edu.agh.mobilecodereviewer.dto.ChangeInfoDTO}
     */
    public ChangeInfoDTO getChangeDetails(String id) {
        return gerritService.getChangeDetails(id);
    }

    /**
     * Get current revision with list of files.
     * @param id identifier of change
     * @return {@link pl.edu.agh.mobilecodereviewer.utilities.Pair} of changeId, and {@link pl.edu.agh.mobilecodereviewer.dto.RevisionInfoDTO} object representing revision
     */
    public Pair<String, RevisionInfoDTO> getCurrentRevisionWithFiles(final String id){
        ChangeInfoDTO changeInfoDTO = gerritService.getCurrentRevisionWithFiles(id);
        return new Pair<>(changeInfoDTO.getCurrentRevision(), changeInfoDTO.getRevisions().get(changeInfoDTO.getCurrentRevision()));
    }

    /**
     * Get current revision with commit message.
     * @param id identifier of change
     * @return {@link pl.edu.agh.mobilecodereviewer.utilities.Pair} of changeId, and {@link pl.edu.agh.mobilecodereviewer.dto.RevisionInfoDTO} object representing revision
     */
    public Pair<String, RevisionInfoDTO> getCurrentRevisionWithCommit(final String id) {
        ChangeInfoDTO changeInfoDTO = gerritService.getCurrentRevisionWithCommit(id);
        return new Pair<>(changeInfoDTO.getCurrentRevision(), changeInfoDTO.getRevisions().get(changeInfoDTO.getCurrentRevision()));

    }

    /**
     * Get content of the file in base64
     *
     * @param change_id   Change Identifier
     * @param revision_id Revision Identifier
     * @param file_id     Path of file
     * @return Content compressed in base64
     */
    public String getFileContent(final String change_id, final String revision_id, final String file_id) {
        Response response = gerritService.getFileContent(change_id, revision_id, file_id);

        TypedInput responseBody = response.getBody();
        return getStringFromTypedInput(responseBody);
    }

    /**
     * Helper class for getting String value from {@link retrofit.mime.TypedInput}
     *
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
     *
     * @param change_id   Change Identifier
     * @param revision_id Revision Identifier
     * @return Mapping between file and list of comments
     */
    public Map<String, List<CommentInfoDTO>> getComments(final String change_id, final String revision_id) {
        return gerritService.getComments(change_id, revision_id);
    }

    /**
     * Get mergeability info of current revision.
     * @param change_id identifier of change
     * @return {@link MergeableInfoDTO}
     */
    public MergeableInfoDTO getMergeableInfoForCurrentRevision(final String change_id){
        return gerritService.getMergeableInfoForCurrentRevision(change_id);
    }

    /**
     * Get topic of change.
     * @param change_id identifier of change
     * @return topic of change
     */
    public String getChangeTopic(final String change_id) {
        return gerritService.getChangeTopic(change_id);
    }

    public DiffInfoDTO getSourceCodeDiff(String change_id, String revision_id, String file_id) {
        return gerritService.getDiffInfo(change_id, revision_id, file_id);
    }

    public void putFileComment(String change_id, String revision_id,
                               int line, String message, String path) throws RetrofitError {
        ReviewInputDTO reviewInputDTO = ReviewInputDTO.createFromSingleComment(path,
                new CommentInputDTO(line, message, path));
        gerritService.putReview(change_id, revision_id, reviewInputDTO);
    }

    public void putReview(String change_id,String revision_id,String message,Map<String, Integer> votes) {
        ReviewInputDTO reviewInputDTO = ReviewInputDTO.createVoteReview(message, votes);
        gerritService.putReview(change_id, revision_id, reviewInputDTO);
    }

    private class GerritErrorHandler implements ErrorHandler{

        @Override
        public Throwable handleError(RetrofitError retrofitError) {

            if(retrofitError.getCause() instanceof FileNotFoundException){
                return new UnauthorizedRequestException();
            }

            if(retrofitError.isNetworkError()){
                return new NetworkException();
            }

            return retrofitError;
        }
    }

    private class GerritClient implements Client {

        private final ConfigurationInfo configurationInfo;

        public GerritClient(ConfigurationInfo configurationInfo){
            this.configurationInfo = configurationInfo;
        }

        private HttpURLConnection tryAuth(HttpURLConnection connection, String username, String password)
                throws IOException
        {
            int responseCode = connection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_UNAUTHORIZED){
                connection = tryDigestAuthentication(connection, username, password);
                if(connection == null){
                    throw new HttpDigestAuth.AuthenticationException();
                }
            }
            return connection;
        }

        @Override
        public Response execute(Request request) throws IOException {

            Request preProcessedRequest = new Request(request.getMethod(), prepareUrl(request.getUrl()), request.getHeaders(), request.getBody());

            HttpURLConnection urlConnection = (HttpURLConnection) new URL(preProcessedRequest.getUrl()).openConnection();

            setConnectionProperties(preProcessedRequest, urlConnection);
            urlConnection.connect();

            HttpURLConnection authConnection = tryAuth(urlConnection, this.configurationInfo.getLogin(), this.configurationInfo.getPassword());
            if (authConnection != urlConnection) {
                urlConnection = authConnection;
                setConnectionProperties(preProcessedRequest,urlConnection);
                urlConnection.connect();
            }
            return createResponseFromConnection(urlConnection);
        }

        private String prepareUrl(String initialUrl){

            String serverUrl = configurationInfo.getUrl();

            String initialEndpoint = initialUrl.split(configurationInfo.getUrl())[1];
            String preparedEndpoint = "";

            if(initialEndpoint.startsWith("/--u--")) {
                preparedEndpoint = initialEndpoint.substring("/--u--".length());
            } else if(configurationInfo.isAuthenticatedUser()){
                preparedEndpoint += "/a";
                preparedEndpoint += initialEndpoint;
            } else {
                preparedEndpoint = initialEndpoint;
            }

            return serverUrl + preparedEndpoint;
        }

        private Response createResponseFromConnection(final HttpURLConnection urlConnection) throws IOException {
            String received_url = urlConnection.getURL().toString();
            int received_status = urlConnection.getResponseCode();
            String received_reason = urlConnection.getResponseMessage();
            List<Header> received_headers = new LinkedList<Header>();
            for (Map.Entry<String, List<String>> header : urlConnection.getHeaderFields().entrySet()) {
                for (String headerValue : header.getValue()) {
                    received_headers.add(new Header(header.getKey(), headerValue));
                }
            }
            TypedInput received_body = new TypedInput() {
                @Override
                public String mimeType() {
                    return urlConnection.getContentType();
                }

                @Override
                public long length() {
                    return -1;
                }

                @Override
                public InputStream in() throws IOException {
                    return urlConnection.getInputStream();
                }
            };
            return new Response(received_url, received_status, received_reason, received_headers, received_body);
        }

        private void setConnectionProperties(Request request, HttpURLConnection urlConnection) throws IOException {
            urlConnection.setRequestMethod(request.getMethod());
            for (Header header : request.getHeaders()) {
                urlConnection.addRequestProperty(header.getName(), header.getValue());
            }

            if (!request.getMethod().toLowerCase().equals("get")) {
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("charset", "UTF-8");
                urlConnection.setDoOutput(true);
                OutputStream outputStream = urlConnection.getOutputStream();
                if (request.getBody() != null)
                    request.getBody().writeTo(outputStream);
            }
        }


    }
}
