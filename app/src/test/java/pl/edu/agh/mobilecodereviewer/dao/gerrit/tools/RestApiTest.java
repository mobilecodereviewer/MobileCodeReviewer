package pl.edu.agh.mobilecodereviewer.dao.gerrit.tools;


import com.google.common.base.Function;

import org.apache.commons.io.input.NullInputStream;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

import pl.edu.agh.mobilecodereviewer.dao.gerrit.api.GerritService;
import pl.edu.agh.mobilecodereviewer.dto.CommentInfoDTO;
import retrofit.client.Request;
import retrofit.mime.TypedInput;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RestApiTest extends RestApi{

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
        assertEquals( "",fileContent );
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
        assertEquals("", fileContent);
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

    @Test
    public void testGetStringFromTypicalTypedInfo() throws Exception {
        RestApi restApi = new RestApi(null);
        TypedInput typedInput = mock(TypedInput.class);
        String text = "ThisIsSparta";
        when( typedInput.in() ).thenReturn( new ByteArrayInputStream( text.getBytes() ) );
        String stringFromTypedInput = restApi.getStringFromTypedInput(typedInput);
        assertEquals("ThisIsSparta" , stringFromTypedInput );
    }

    @Test
    public void testGetStringFromNullTypicalTypedInfo() throws Exception {
        RestApi restApi = new RestApi(null);
        TypedInput typedInput = mock(TypedInput.class);
        when( typedInput.in() ).thenReturn( new NullInputStream(0) );
        String stringFromTypedInput = restApi.getStringFromTypedInput(typedInput);
        assertEquals( "" , stringFromTypedInput );
    }

    @Test
    public void testGetTypicalComment() throws Exception {
        GerritService gerritService = GerritTestHelper.createSimpleRestServiceForTest(GerritService.class,
                new Function<Request, Void>() {
                    @Override
                    public Void apply(Request from) {
                        assertEquals(
                                "http://0.0.0.0:8080/changes/0/revisions/0/comments/",
                                from.getUrl()
                        );
                        return null;
                    }
                },
                        "{\n" +
                        "    \"gerrit-server/src/main/java/com/google/gerrit/server/project/RefControl.java\": [\n" +
                        "      {\n" +
                        "        \"id\": \"TvcXrmjM\",\n" +
                        "        \"line\": 23,\n" +
                        "        \"message\": \"[nit] trailing whitespace\",\n" +
                        "        \"updated\": \"2013-02-26 15:40:43.986000000\",\n" +
                        "        \"author\": {\n" +
                        "          \"_account_id\": 1000096,\n" +
                        "          \"name\": \"John Doe\",\n" +
                        "          \"email\": \"john.doe@example.com\"\n" +
                        "        }\n" +
                        "      },\n" +
                        "      {\n" +
                        "        \"id\": \"TveXwFiA\",\n" +
                        "        \"line\": 49,\n" +
                        "        \"in_reply_to\": \"TfYX-Iuo\",\n" +
                        "        \"message\": \"Done\",\n" +
                        "        \"updated\": \"2013-02-26 15:40:45.328000000\",\n" +
                        "        \"author\": {\n" +
                        "          \"_account_id\": 1000097,\n" +
                        "          \"name\": \"Jane Roe\",\n" +
                        "          \"email\": \"jane.roe@example.com\"\n" +
                        "        }\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  }");

        RestApi restApi = new RestApi(gerritService);
        Map<String, List<CommentInfoDTO>> comments = restApi.getComments("0", "0");
        List<CommentInfoDTO> commentInfoDTOs =
                comments.get("gerrit-server/src/main/java/com/google/gerrit/server/project/RefControl.java");
        assertNotNull( commentInfoDTOs.get(0) );
        assertEquals("TvcXrmjM",commentInfoDTOs.get(0).getId());
        assertEquals("[nit] trailing whitespace",commentInfoDTOs.get(0).getMessage());

    }


}











