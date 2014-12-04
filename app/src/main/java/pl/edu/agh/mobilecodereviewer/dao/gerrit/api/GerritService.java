package pl.edu.agh.mobilecodereviewer.dao.gerrit.api;

import java.util.List;
import java.util.Map;

import pl.edu.agh.mobilecodereviewer.dto.CommentInputDTO;
import pl.edu.agh.mobilecodereviewer.exceptions.NetworkException;
import pl.edu.agh.mobilecodereviewer.exceptions.UnauthorizedRequestException;
import pl.edu.agh.mobilecodereviewer.dto.AccountInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.ChangeInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.CommentInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.DiffInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.MergeableInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.ReviewInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.ReviewInputDTO;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Tool class which annotated data access method with
 * appropriate url paths.
 *
 * Adding /--u-- prefix to endpoint indicates RestApi not to add
 * /a/ prefix when executing method on given endpoint for authenticated user.
 *
 * /a/ prefix is responsible for authentication on server side
 *
 * As a result method with /--u-- prefix will be executed as for anonymous
 * user no matter if user works as authenticated or not.
 */
public interface GerritService {

    @GET("/--u--/config/server/version")
    String getVersion() throws NetworkException;

    @GET("/accounts/self")
    AccountInfoDTO getAccountInfo() throws UnauthorizedRequestException, NetworkException;

    /**
     * Get all changes from file
     * @return List of {@link pl.edu.agh.mobilecodereviewer.dto.ChangeInfoDTO}
     */
    // @TODO TO query jest popieprzone ale bez tego nie dziala - moze w przyslyszhc wersjach nie bedzie
    // @TODO potrzeby zeby ono bylo, a dodatkowo ten potworek zwraca liste otwartych i liste zamknietych
    // @TODO w dwoch listach...
    @GET("/changes/?q=is:open+&q=is:closed&o=CURRENT_REVISION")
    List<List<ChangeInfoDTO>> getChanges();

    /**
     * Get detailed information about change
     * @param id Identifier of the change
     * @return {@link pl.edu.agh.mobilecodereviewer.dto.ChangeInfoDTO}
     */
    @GET("/changes/{id}/detail/")
    ChangeInfoDTO getChangeDetails(@Path("id") String id);

    /**
     * Get change info with detailed information about list of files in most recent revision.
     * @param id identifier of the change
     * @return {@link pl.edu.agh.mobilecodereviewer.dto.ChangeInfoDTO}
     */
    @GET("/changes/{id}/?o=CURRENT_REVISION&o=CURRENT_FILES")
    ChangeInfoDTO getCurrentRevisionWithFiles(@Path("id") String id);

    /**
     * Get change info with detailed information about most recent commit.
     * @param id
     * @return
     */
    @GET("/changes/{id}/?o=CURRENT_REVISION&o=CURRENT_COMMIT")
    ChangeInfoDTO getCurrentRevisionWithCommit(@Path("id") String id);

    /**
     * Get raw file content from the server
     * @param change_id identifier of the change
     * @param revision_id identifier of the revision
     * @param file_id identifier of the file
     * @return {@link retrofit.client.Response}
     */
    @GET("/changes/{change_id}/revisions/{revision_id}/files/{file_id}/content")
    Response getFileContent( @Path("change_id") String change_id,
                             @Path("revision_id") String revision_id ,
                             @Path("file_id") String file_id);

    /**
     * Get all comments for a given change and its revision
     * @param change_id identifier of the change
     * @param revision_id identifier of the revision
     * @return Assosciation between filename and comments added in the given revision
     */
    @GET("/changes/{change_id}/revisions/{revision_id}/comments/")
    Map<String,List<CommentInfoDTO>> getComments( @Path("change_id") String change_id ,
                                                  @Path("revision_id") String revision_id);

    /**
     * Get information about mergeability of change.
     * @param change_id identifier of the change
     * @return {@link pl.edu.agh.mobilecodereviewer.dto.MergeableInfoDTO}
     */
    @GET("/changes/{change_id}/revisions/current/mergeable")
    MergeableInfoDTO getMergeableInfoForCurrentRevision(@Path("change_id") String change_id);

    /**
     * Get topic of change.
     * @param change_id identifier of the change
     * @return topic of change
     */
    @GET("/changes/{change_id}/topic")
    String getChangeTopic(@Path("change_id") String change_id);

    @GET("/changes/{change-id}/revisions/{revision-id}/files/{file-id}/diff")
    DiffInfoDTO getDiffInfo(@Path("change-id") String change_id, @Path("revision-id") String revision_id,
                            @Path("file-id") String file_id);

    @POST("/changes/{change-id}/revisions/{revision-id}/review")
    ReviewInfoDTO putReview(@Path("change-id") String change_id, @Path("revision-id") String revision_id,
                            @Body ReviewInputDTO reviewInputDTO);

    @GET("/changes/{change_id}/revisions/{revision_id}/drafts")
    Map<String, List<CommentInfoDTO>> getDraftComments( @Path("change_id") String change_id,
                                                        @Path("revision_id") String revision_id);

    @PUT("/changes/{change_id}/revisions/{revision_id}/drafts/")
    CommentInfoDTO createDraftComment(@Path("change_id") String change_id,
                                      @Path("revision_id") String revision_id,
                                      @Body CommentInputDTO commentInputDTO);

    @PUT("/changes/{change_id}/revisions/{revision_id}/drafts/{draft_id}")
    CommentInfoDTO updateDraftComment(@Path("change_id") String change_id,
                                      @Path("revision_id") String revision_id,
                                      @Path("draft_id") String draft_id,
                                      @Body CommentInputDTO commentInputDTO);

    @DELETE("/changes/{change_id}/revisions/{revision_id}/drafts/{draft_id}")
    Response deleteDraftComment(@Path("change_id") String change_id,
                            @Path("revision_id") String revision_id,
                            @Path("draft_id") String draft_id);




}























