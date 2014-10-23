package integration;

import org.junit.Before;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import pl.edu.agh.mobilecodereviewer.dao.gerrit.utilities.RestApi;
import pl.edu.agh.mobilecodereviewer.dto.AccountInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.ChangeInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.DiffInfoDTO;
import retrofit.client.Client;
import retrofit.client.Request;
import retrofit.client.Response;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RestApiIntegrationTest {
    static RestApi restApi;

    @BeforeClass
    public static void connectToRestServer() throws Exception {
        //restApi = new RestApi("http://apps.iisg.agh.edu.pl:8081/");
        restApi = new RestApi("http://gerrit.ovirt.org");
    }

    @Ignore
    @Test
    public void shouldGetSourceDiffAccessProperValues() throws Exception {
        String change_id = "Ie6fa2f384caa58b5cae6256d9e8041f2981c8832";
        String revision_id = "1";
        String file_id = "README.md";
        DiffInfoDTO sourceCodeDiff = restApi.getSourceCodeDiff(change_id, revision_id, file_id);
        assertNotNull(sourceCodeDiff);
        assertNotNull(sourceCodeDiff.getDiffContent());
        assertEquals( 2, sourceCodeDiff.getDiffContent().size() );
        assertEquals("Initial review readme file", sourceCodeDiff.getDiffContent().get(0).getLinesUnchanged().get(0));
        assertEquals("\\nLOOOOL" , sourceCodeDiff.getDiffContent().get(1).getLinesBeforeChange().get(0) );
        assertEquals("LOOOOL",sourceCodeDiff.getDiffContent().get(1).getLinesAfterChange().get(0));
        assertEquals("" , sourceCodeDiff.getDiffContent().get(1).getLinesAfterChange().get(1) );
        assertEquals("" , sourceCodeDiff.getDiffContent().get(1).getLinesAfterChange().get(2) );
        assertEquals("give yourself some free time with this stuff..." , sourceCodeDiff.getDiffContent().get(1).getLinesAfterChange().get(3) );
    }

    @Ignore
    @Test
    public void shouldPutCommentSaveValuesToServer() throws Exception {
        String change_id = "I4b2ca228d25393ed58eee97bbaa46d5939d62767";
        String revision_id = "1";
        restApi.putFileComment(change_id, revision_id,
                               5,"According to RFC RestApiTest 14:37:40/02/10/2014","file1.java");

    }

    @Ignore
    @Test
    public void learnWhatGetChangesPassToClient() throws Exception {
        Client client = new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                assertEquals( "GET" , request.getMethod() );
                return null;
            }
        };
        RestApi restApi = new RestApi("http://apps.iisg.agh.edu.pl:8081/",client);
        restApi.getChanges();
    }


    @Ignore
    @Test
    public void shouldGetChangesFromServer() throws Exception {
        List<ChangeInfoDTO> changeInfoDTOs = restApi.getChanges();
        assertEquals(2, changeInfoDTOs.size());
        assertEquals( "Add file1.java" , changeInfoDTOs.get(0).getChangeId() );
    }

    @Ignore
    @Test
    public void shouldGetAccountSelfFromServer() throws Exception {
        AccountInfoDTO accountInfo = restApi.getAccountInfo();
        assertNotNull(accountInfo);
        assertEquals("", accountInfo.toString());
    }

}


















