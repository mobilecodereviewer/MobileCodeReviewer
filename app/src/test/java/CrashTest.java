import org.junit.Test;

import pl.edu.agh.mobilecodereviewer.dao.gerrit.utilities.RestApi;
import pl.edu.agh.mobilecodereviewer.dto.CommentInputDTO;
import pl.edu.agh.mobilecodereviewer.utilities.ConfigurationInfo;

/**
 * Created by lee on 2015-01-04.
 */
public class CrashTest {

    @Test
    public void shouldCheckWhatTheFuckHappened() throws Exception {
        ConfigurationInfo configurationInfo = new ConfigurationInfo(
                "agh",
                "http://apps.iisg.agh.edu.pl:10002",
                "pili",
                "F9xlAF4XS4tz",
                true
                );
        RestApi restApi = new RestApi(configurationInfo);
        CommentInputDTO commentInputDTO = new CommentInputDTO(72,"19:00 koment from test","predictor.py");
        restApi.createDraftComment(
                "I3ba1930b42f4348862bc750bfe18490043c6c1b5",
                "current",
                commentInputDTO);
    }

}




























