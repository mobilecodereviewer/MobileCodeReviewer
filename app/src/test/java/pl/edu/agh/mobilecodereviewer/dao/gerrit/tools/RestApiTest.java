package pl.edu.agh.mobilecodereviewer.dao.gerrit.tools;


import com.google.common.base.Function;

import org.junit.Test;

import pl.edu.agh.mobilecodereviewer.dao.gerrit.api.GerritService;
import retrofit.client.Request;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RestApiTest {

    @Test
    public void testCheckTestIsRunning() throws Exception {
        assertEquals(true, true);
    }

    @Test
    public void testGetFileEmptyContent() throws Exception {
        GerritService gerritService = GerritTestHelper.createSimpleRestServiceForTest(GerritService.class,
                new Function<Request, Void>() {
                    @Override
                    public Void apply(Request from) {
                        assertEquals(
                                "http://0.0.0.0:8080/changes/changeid/revisions/revisionid/files/fileid/content",
                                from.getUrl()
                        );
                        return null;
                    }
                },
                "");

        RestApi restApi = new RestApi(gerritService);
        String fileContent = restApi.getFileContent("changeid", "revisionid", "fileid");
        assertNull(fileContent);
    }

    @Test
    public void testGetFileWithImproperCharacterContent() throws Exception {
        GerritService gerritService = GerritTestHelper.createSimpleRestServiceForTest(GerritService.class,
                new Function<Request, Void>() {
                    @Override
                    public Void apply(Request from) {
                        assertEquals(
                                "http://0.0.0.0:8080/changes/bin%2Fword%2Fchangeid/revisions/revisionid/files/fileid/content",
                                from.getUrl()
                        );
                        return null;
                    }
                },
                "");

        RestApi restApi = new RestApi(gerritService);
        String fileContent = restApi.getFileContent("bin/word/changeid", "revisionid", "fileid");
        assertNull(fileContent);
    }

    @Test
    public void testGetFileWithSimpleContent() throws Exception {
        GerritService gerritService = GerritTestHelper.createSimpleRestServiceForTest(GerritService.class,
                new Function<Request, Void>() {
                    @Override
                    public Void apply(Request from) {
                        assertEquals(
                                "http://0.0.0.0:8080/changes/THIS/revisions/IS/files/SPARTA/content",
                                from.getUrl()
                        );
                        return null;
                    }
                },
                "ThisIsSparta");

        RestApi restApi = new RestApi(gerritService);
        String fileContent = restApi.getFileContent("THIS", "IS", "SPARTA");
        assertEquals("ThisIsSparta",fileContent);
    }
}
