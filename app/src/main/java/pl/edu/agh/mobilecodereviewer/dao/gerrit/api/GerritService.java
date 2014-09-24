package pl.edu.agh.mobilecodereviewer.dao.gerrit.api;

import java.util.List;
import java.util.Map;

import pl.edu.agh.mobilecodereviewer.dto.ChangeInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.CommentInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.DiffInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.MergeableInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.ReviewInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.ReviewInputDTO;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Tool class which annotated data access method with
 * appropriate url paths.
 */
public interface GerritService {

    @GET("/changes/")
    /**
     * Download All Changes from file
     * @return List of {@link pl.edu.agh.mobilecodereviewer.dto.ChangeInfoDTO}
     */
    List<ChangeInfoDTO> getChanges();

    @GET("/changes/{id}/detail/")
    /**
     * Download information about change
     * @param id Identifier of the change
     * @return {@link pl.edu.agh.mobilecodereviewer.dto.ChangeInfoDTO}
     */
    ChangeInfoDTO getChangeDetails(@Path("id") String id);


    @GET("/changes/{id}/?o=CURRENT_REVISION&o=CURRENT_FILES")
    ChangeInfoDTO getCurrentRevisionWithFiles(@Path("id") String id);

    @GET("/changes/{id}/?o=CURRENT_REVISION&o=CURRENT_COMMIT")
    ChangeInfoDTO getCurrentRevisionWithCommit(@Path("id") String id);
    /**
     * Get all changes withing given revision
     * @param id identifier of the revision
     * @return {@link pl.edu.agh.mobilecodereviewer.dto.ChangeInfoDTO}
     */
    ChangeInfoDTO getChangeWithCurrentRevision(@Path("id") String id);

    @GET("/changes/{change_id}/revisions/{revision_id}/files/{file_id}/content")
    /**
     * Get raw file content from the server
     * @param change_id identifier of the change
     * @param revision_id identifier of the revision
     * @param file_id identifier of the file
     * @return {@link retrofit.client.Response}
     */
    Response getFileContent( @Path("change_id") String change_id,
                             @Path("revision_id") String revision_id ,
                             @Path("file_id") String file_id);

    @GET("/changes/{change_id}/revisions/{revision_id}/comments/")
    /**
     * Get all comment for a given change and its revision
     * @param change_id identifier of the change
     * @param revision_id identifier of the revision
     * @return Assosciation between filename and comments added in the given revision
     */
    Map<String,List<CommentInfoDTO>> getComments( @Path("change_id") String change_id ,
                                                  @Path("revision_id") String revision_id);

    @GET("/changes/{change_id}/revisions/current/mergeable")
    MergeableInfoDTO getMergeableInfoForCurrentRevision(@Path("change_id") String change_id);

    @GET("/changes/{change_id}/topic")
    String getChangeTopic(@Path("change_id") String change_id);

    @GET("/changes/{change-id}/revisions/{revision-id}/files/{file-id}/diff")
    DiffInfoDTO getDiffInfo(@Path("change-id") String change_id, @Path("revision-id") String revision_id,
                            @Path("file-id") String file_id);

    @POST("/a/changes/{change-id}/revisions/{revision-id}/review")
    ReviewInfoDTO putFileComment(@Path("change-id") String change_id, @Path("revision-id") String revision_id,
                                 @Body ReviewInputDTO reviewInputDTO);


}























