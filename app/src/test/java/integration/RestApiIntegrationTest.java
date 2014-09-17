package integration;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import pl.edu.agh.mobilecodereviewer.dao.gerrit.tools.RestApi;
import pl.edu.agh.mobilecodereviewer.dto.DiffInfoDTO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Ignore
public class RestApiIntegrationTest {
    static RestApi restApi;

    @BeforeClass
    public static void connectToRestServer() throws Exception {
        restApi = new RestApi("http://192.168.163.151:8080");
    }

    @Test
    public void shouldAccessProperValues() throws Exception {
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
}


















