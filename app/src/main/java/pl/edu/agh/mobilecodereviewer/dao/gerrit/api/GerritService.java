package pl.edu.agh.mobilecodereviewer.dao.gerrit.api;

import java.util.List;
import java.util.Map;

import pl.edu.agh.mobilecodereviewer.dto.ChangeInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.CommentInfoDTO;
import retrofit.http.GET;
import retrofit.http.Path;


public interface GerritService {
    @GET("/changes/")
    List<ChangeInfoDTO> getChanges();

    @GET("/changes/{id}/detail/")
    ChangeInfoDTO getChangeDetails(@Path("id") String id);

    @GET("/changes/{id}/?o=CURRENT_REVISION&o=CURRENT_FILES")
    ChangeInfoDTO getChangeWithCurrentRevision(@Path("id") String id);

    @GET("/changes/{change_id}/revisions/{revision_id}/files/{file_id}/content")
    String getFileContent(@Path("change_id") String change_id,
                          @Path("revision_id") String revision_id ,
                          @Path("file_id") String file_id);

    @GET("/changes/{change_id}/revisions/{revision_id}/comments/")
    Map<String,List<CommentInfoDTO>> getComments(@Path("change_id") String change_id ,
                                                 @Path("revision_id") String revision_id);

}
