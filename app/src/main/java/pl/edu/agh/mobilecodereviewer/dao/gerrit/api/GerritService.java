package pl.edu.agh.mobilecodereviewer.dao.gerrit.api;

import java.util.List;

import pl.edu.agh.mobilecodereviewer.dto.ChangeInfoDTO;
import retrofit.http.GET;
import retrofit.http.Path;


public interface GerritService {
    @GET("/changes/")
    List<ChangeInfoDTO> getChanges();

    @GET("/changes/{id}/detail/")
    ChangeInfoDTO getChangeDetails(@Path("id") String id);

    @GET("/changes/{id}/?o=CURRENT_REVISION&o=CURRENT_FILES")
    ChangeInfoDTO getChangeWithCurrentRevision(@Path("id") String id);
}
