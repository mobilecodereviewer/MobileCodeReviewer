package pl.edu.agh.mobilecodereviewer.dao.gerrit;


import com.google.common.base.Function;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import pl.edu.agh.mobilecodereviewer.dao.api.SourceCodeDAO;
import pl.edu.agh.mobilecodereviewer.dao.gerrit.api.GerritService;
import pl.edu.agh.mobilecodereviewer.dao.gerrit.tools.RestApi;
import pl.edu.agh.mobilecodereviewer.dto.CommentInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.DiffInfoDTO;
import pl.edu.agh.mobilecodereviewer.model.Line;
import pl.edu.agh.mobilecodereviewer.model.SourceCode;
import pl.edu.agh.mobilecodereviewer.model.SourceCodeDiff;
import retrofit.RetrofitError;
import retrofit.client.Request;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.edu.agh.mobilecodereviewer.dao.gerrit.tools.GerritTestHelper.createSimpleRestServiceForTest;
import static pl.edu.agh.mobilecodereviewer.dao.gerrit.tools.GerritTestHelper.doNothing;


public class SourceCodeDAOImplTest extends SourceCodeDAOImpl {

    @Test
    public void testCheckTestIsRunning() throws Exception {
        assertEquals(true, true );
    }

    private String getDownloadedSourceCodeForSimpleTest(String serverResponse) {
        return getDownloadedSourceCodeForSimpleTest("", "", "", serverResponse, doNothing);
    }

    private String getDownloadedSourceCodeForSimpleTest(String change_id, String revision_id, String file_id,
                                                        String serverResponse, Function<Request, Void> requestValidator) {

        GerritService gerritService = createSimpleRestServiceForTest(
                GerritService.class,
                requestValidator,
                serverResponse
        );
        SourceCodeDAOImpl sourceCodeDAO = new SourceCodeDAOImpl(new RestApi(gerritService) );
        return sourceCodeDAO.downloadSourceCode(change_id,revision_id,file_id);
    }

    private List<CommentInfoDTO> getCommentsInfoForSimpleTest(String change_id, String revision_id, String file_id,
                                                              String serverResponse, Function<Request, Void> requestValidator) {

        GerritService gerritService = createSimpleRestServiceForTest(
                GerritService.class,
                requestValidator,
                serverResponse
        );
        SourceCodeDAOImpl sourceCodeDAO = new SourceCodeDAOImpl(new RestApi(gerritService) );
        return sourceCodeDAO.getComments(change_id,revision_id,file_id);
    }

    @Test
    public void testDownloadSourceCodeEmptyContent() throws Exception {
        String downloadedSource = getDownloadedSourceCodeForSimpleTest("change_id", "revision_id", "file_id", "",
                new Function<Request, Void>() {
                    @Override
                    public Void apply(Request from) {
                        assertEquals("http://0.0.0.0:8080/changes/change_id/revisions/revision_id/files/file_id/content",
                                from.getUrl());
                        return null;
                    }
                }
        );
        assertEquals( "", downloadedSource);
    }

    @Test(expected = RetrofitError.class )
    public void testDownloadSourceCodeNullContent() throws Exception {
        String downloadedSource = getDownloadedSourceCodeForSimpleTest("change_id", "revision_id", "file_id", null,
                new Function<Request, Void>() {
                    @Override
                    public Void apply(Request from) {
                        assertEquals("http://0.0.0.0:8080/changes/change_id/revisions/revision_id/files/file_id/content",
                                from.getUrl());
                        return null;
                    }
                }
        );
        assertNull(downloadedSource);
    }

    @Test(expected = RetrofitError.class )
    public void testDownloadSourceCodeFromPathWithIvalidRestCharacters() throws Exception {
        String downloadedSource = getDownloadedSourceCodeForSimpleTest("/src/java/shitPackage/change_id", "revision_id", "file_id", null,
                new Function<Request, Void>() {
                    @Override
                    public Void apply(Request from) {
                        assertEquals("http://0.0.0.0:8080/changes/%2Fsrc%2Fjava%2FshitPackage%2Fchange_id/revisions/revision_id/files/file_id/content",
                                from.getUrl());
                        return null;
                    }
                }
        );
        assertNull(downloadedSource);
    }

    @Test
    public void testDownloadSourceCodeSimpleContent() throws Exception {
        String downloadSource = getDownloadedSourceCodeForSimpleTest("ThisIsSparta");
        assertEquals( downloadSource , "ThisIsSparta" );
    }


    @Test
    public void testConversionSourceCodeEmptyContent() throws Exception {
        String convertedValue = convertSourceCode("");
        assertEquals("",convertedValue);
    }

    @Test
    public void testConversionSourceCodeNullContent() throws Exception {
        String convertedValue = convertSourceCode(null);
        assertEquals(null,convertedValue);
    }

    @Test
    public void testConversionSourceCodeFewWordsContent() throws Exception {
        String convertedValue = convertSourceCode("VGhpcyBpcyBTcGFydGE=");
        assertEquals("This is Sparta",convertedValue);
    }

    @Test
    public void testGetCommentsCheckIfRestRequestWasGood() throws Exception {
        getCommentsInfoForSimpleTest("myProject~master~I8473b95934b5732ac55d26311a706c9c2bde9940",
                "674ac754f91e64a0efb8087e59a176484bd534d1", "shit.java",
                "{}",
                new Function<Request, Void>() {
                    @Override
                    public Void apply(Request from) {
                        assertEquals("http://0.0.0.0:8080/changes/myProject%7Emaster%7EI8473b95934b5732ac55d26311a706c9c2bde9940/revisions/674ac754f91e64a0efb8087e59a176484bd534d1/comments/",
                                     from.getUrl() );
                        return null;
                    }
                });

    }

    @Test( )
    public void testGetCommentsWithEmptyRespond() throws Exception {
        List<CommentInfoDTO> commentsInfo = getCommentsInfoForSimpleTest("myProject", "revisionFirst",
                "shit.java", "{}",doNothing);

        assertEquals( 0 , commentsInfo.size() );
    }

    @Test
    public void testGetCommentsWithOneFileTwoCommentsRespond() throws Exception {
        List<CommentInfoDTO> commentInfoDTOs = getCommentsInfoForSimpleTest("projectID", "revisionID",
                "gerrit-server/src/main/java/com/google/gerrit/server/project/RefControl.java",
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
                        "  }"
                , doNothing
        );

        assertEquals( 2 , commentInfoDTOs.size() );
        CommentInfoDTO first = commentInfoDTOs.get(0);
        CommentInfoDTO second = commentInfoDTOs.get(1);

        assertEquals( "TvcXrmjM" , first.getId() );
        assertEquals(  23 , first.getLine() );
        assertEquals( "[nit] trailing whitespace" , first.getMessage() );

        assertEquals( "TveXwFiA" , second.getId() );
        assertEquals( 49 , second.getLine() );
        assertEquals( "Done" , second.getMessage() );
    }

    @Test
    public void testGetCommentsWithTwoFilesForOneCommentsRespond() throws Exception {
        List<CommentInfoDTO> commentInfoDTOs =
                getCommentsInfoForSimpleTest("projectID", "revisionID", "RefControl.java",
                        "{ \"First.java\":[ {\"id\" : 100} ] , \"RefControl.java\" : [ { \"id\" : 100} ] , \"Last.cpp\":[]}",
                        doNothing);

        assertEquals( 1, commentInfoDTOs.size() );
        CommentInfoDTO commentInfoDTO = commentInfoDTOs.get(0);
        assertEquals( "100" , commentInfoDTO.getId() );
        assertEquals(0, commentInfoDTO.getLine());
        assertNull( commentInfoDTO.getMessage() );
    }

    @Test
    public void testGetCommentsWithOneFileZeroComments() throws Exception {
        List<CommentInfoDTO> commentInfoDTOs =
                getCommentsInfoForSimpleTest("projectID", "revisionID", "First.java",
                        "{ \"First.java\":[] }",
                        doNothing);

        assertEquals( 0 , commentInfoDTOs.size() );
    }

    @Test
    public void testSplitEmptySource() throws Exception {
        List<String> lines = splitSourceIntoLines("");
        assertEquals( 0 , lines.size() );
    }

    @Test(expected = NullPointerException.class)
    public void testSplitNullSource() throws Exception {
        splitSourceIntoLines(null);
    }

    @Test
    public void testSplitSourceIntoUnixLines() throws Exception {
        List<String> lines = splitSourceIntoLines(
                " \n" +
                "import java.util.Map;\n" +
                " public class ChangeInfoDTO {\n" +
                " \n" +
                "    private String subject;}"
        );
        assertEquals(" " , lines.get(0) );
        assertEquals("import java.util.Map;" , lines.get(1) );
        assertEquals(" public class ChangeInfoDTO {",lines.get(2) );
        assertEquals(" ", lines.get(3) );
        assertEquals("    private String subject;}" , lines.get(4) );
    }

    @Test
    public void testSplitSourceIntoWindowLines() throws Exception {
        List<String> lines = splitSourceIntoLines(
                " \r\n" +
                        "import java.util.Map;\r\n" +
                        " public class ChangeInfoDTO {\r\n" +
                        " \r\n" +
                        "    private String subject;}"
        );
        assertEquals(" " , lines.get(0) );
        assertEquals("import java.util.Map;" , lines.get(1) );
        assertEquals(" public class ChangeInfoDTO {",lines.get(2) );
        assertEquals(" ", lines.get(3) );
        assertEquals("    private String subject;}" , lines.get(4) );
    }

    @Test
    public void testSplitSourceIntoMacLines() throws Exception {
        List<String> lines = splitSourceIntoLines(
                " \r" +
                        "import java.util.Map;\r" +
                        " public class ChangeInfoDTO {\r" +
                        " \r" +
                        "    private String subject;}"
        );
        assertEquals(" " , lines.get(0) );
        assertEquals("import java.util.Map;" , lines.get(1) );
        assertEquals(" public class ChangeInfoDTO {",lines.get(2) );
        assertEquals(" ", lines.get(3)  );
        assertEquals("    private String subject;}" , lines.get(4) );
    }

    @Test
    public void testMappedZeroComment() throws Exception {
        Map<Integer, List<CommentInfoDTO>> commentsInLines = mapCommentsIntoLines( new LinkedList<CommentInfoDTO>() );

        assertEquals( 0 , commentsInLines.entrySet().size() );
    }

    @Test
    public void testMappedTwoComent() throws Exception {
        Map<Integer, List<CommentInfoDTO>> commentsInLines = mapCommentsIntoLines(
                Arrays.asList(
                        new CommentInfoDTO("1", 0, ""),
                        new CommentInfoDTO("2", 1, "")
                )
        );
        assertEquals(  2 , commentsInLines.size() );

        assertNotNull( commentsInLines.get(0) );
        assertEquals(  1, commentsInLines.get(0).size());
        assertEquals( "1" , commentsInLines.get(0).get(0).getId() );

        assertNotNull( commentsInLines.get(1) );
        assertEquals( 1  ,commentsInLines.get(1).size() );
        assertEquals( "2", commentsInLines.get(1).get(0).getId() );

        assertEquals( 2, commentsInLines.entrySet().size() );
    }

    @Test
    public void testCreateSourceCodeWithCommentNotAssociatedWithLines() throws Exception {
        Map<Integer,List<CommentInfoDTO>> commentsInLines = new HashMap<>();
        commentsInLines.put(10, Arrays.asList( CommentInfoDTO.valueOf(10,"") ) );
        commentsInLines.put(5, Collections.singletonList(CommentInfoDTO.valueOf(5, "HiHo")) );
        List<String> lines = splitSourceIntoLines(
                " \r" +
                        "import java.util.Map;\r" +
                        " public class ChangeInfoDTO {\r" +
                        " \r" +
                        "    private String subject;}"
        );
        SourceCode sourceCode = createSourceCode("file", lines, commentsInLines);
        assertEquals( 5 , sourceCode.getLines().size() );
        assertEquals( 5 , sourceCode.getLines().get(4).getLineNumber() );
        for (int i=0;i<4;i++)
            assertEquals( 0 , sourceCode.getLines().get(i).getComments().size() );
        assertEquals( 1 , sourceCode.getLines().get(4).getComments().size() );
        assertEquals( "HiHo" , sourceCode.getLines().get(4).getComments().get(0).getContent() );
    }

    @Test
    public void testCreateSourceCodeWithFewLinesWithoutComments() throws Exception{
        Map<Integer,List<CommentInfoDTO>> commentsInLines = new HashMap<>();
        commentsInLines.put(
            1 ,
            new LinkedList<CommentInfoDTO>( Arrays.asList(
                CommentInfoDTO.valueOf(1,"LOL"),CommentInfoDTO.valueOf(1,"NOOB")
            ))
        );
        commentsInLines.put(
            5,
            new LinkedList<CommentInfoDTO>( Arrays.asList(
                    CommentInfoDTO.valueOf(5, "LOL2"), CommentInfoDTO.valueOf(5, "NOOB2")
            ))
        );
        List<String> lines = splitSourceIntoLines(
                " \r" +
                        "import java.util.Map;\r" +
                        " public class ChangeInfoDTO {\r" +
                        " \r" +
                        "    private String subject;}"
        );
        SourceCode sourceCode = createSourceCode("file", lines, commentsInLines);
        assertEquals(5,sourceCode.getLines().size() );
        assertEquals(2,sourceCode.getLines().get(0).getComments().size() );
        assertEquals("LOL" , sourceCode.getLines().get(0).getComments().get(0).getContent() );
        assertEquals(" public class ChangeInfoDTO {", sourceCode.getLines().get(2).getContent() );
        assertEquals(3 , sourceCode.getLines().get(2).getLineNumber() );
        assertEquals("    private String subject;}",sourceCode.getLines().get(4).getContent() );
        assertEquals("NOOB2" , sourceCode.getLines().get(4).getComments().get(1).getContent() );
    }

    @Test
    public void getSimpleSourceCode() throws Exception {
        RestApi restApi = mock(RestApi.class);
        when( restApi.getFileContent(anyString(),anyString(),anyString() ) )
                     .thenReturn("aW1wb3J0IGphdmEudXRpbC5NYXA7DQpwdWJsaWMgY2xhc3MgQ2hhbmdlSW5mb0RUTyB7DQogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICANCiAgICBwcml2YXRlIFN0cmluZyBzdWJqZWN0Ow==");

        when( restApi.getComments(anyString(), anyString()) )
                     .thenReturn(
                             Collections.singletonMap("/bin/batch.java",
                                                      Arrays.asList(
                                                              CommentInfoDTO.valueOf(1, "First Line"),
                                                              CommentInfoDTO.valueOf(4, "Best class declared"))
                             )
                     );

        SourceCodeDAOImpl sourceCodeDAO = new SourceCodeDAOImpl( restApi );
        SourceCode sourceCode = sourceCodeDAO.getSourceCode("", "", "/bin/batch.java");

        assertEquals( 4 , sourceCode.getLines().size() );
        assertEquals( "import java.util.Map;" , sourceCode.getLineContent(1) );
        assertEquals( 1 , sourceCode.getLineComments(1).size() );
        assertEquals( "    private String subject;" , sourceCode.getLineContent(4) );
        assertEquals( 4 , sourceCode.getLine(4).getLineNumber() );
        assertEquals( "First Line" , sourceCode.getLines().get(0).getComments().get(0).getContent() );
        assertEquals( "Best class declared" , sourceCode.getLines().get(3).getComments().get(0).getContent() );
    }

    @Test
    public void getSimpleSourceCodeWihoutComments() throws Exception {
        RestApi restApi = mock(RestApi.class);
        when( restApi.getFileContent(anyString(),anyString(),anyString() ) )
                .thenReturn("aW1wb3J0IGphdmEudXRpbC5NYXA7DQpwdWJsaWMgY2xhc3MgQ2hhbmdlSW5mb0RUTyB7DQogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICANCiAgICBwcml2YXRlIFN0cmluZyBzdWJqZWN0Ow==");

        when( restApi.getComments( anyString(),anyString() ) )
                .thenReturn(
                        Collections.<String, List<CommentInfoDTO>>singletonMap("/bin/batch2.java", new LinkedList<CommentInfoDTO>() )
                );

        SourceCodeDAOImpl sourceCodeDAO = new SourceCodeDAOImpl( restApi );
        SourceCode sourceCode = sourceCodeDAO.getSourceCode("", "", "/bin/batch.java");

        assertEquals( 4 , sourceCode.getLines().size() );
        assertEquals( "import java.util.Map;" , sourceCode.getLineContent(1) );
        assertEquals( "    private String subject;" , sourceCode.getLineContent(4) );
        assertEquals( 3 , sourceCode.getLine(3).getLineNumber() );
        int commentCount = 0;
        for (Line line : sourceCode.getLines() ) commentCount += line.getComments().size();
        assertEquals( 0 , commentCount );
    }

    @Test
    public void getSimpleSourceCodeWithEmptySourceWithoutComments() throws Exception {
        RestApi restApi = mock(RestApi.class);
        when( restApi.getFileContent(anyString(),anyString(),anyString() ) )
                .thenReturn("");

        when( restApi.getComments( anyString(),anyString() ) )
                .thenReturn(
                        Collections.<String, List<CommentInfoDTO>>singletonMap("/bin/batch2.java", new LinkedList<CommentInfoDTO>() )
                );

        SourceCodeDAOImpl sourceCodeDAO = new SourceCodeDAOImpl( restApi );
        SourceCode sourceCode = sourceCodeDAO.getSourceCode("", "", "/bin/batch.java");

        assertEquals( 0 , sourceCode.getLines().size() );
        int commentCount=0;
        for (Line line : sourceCode.getLines() ) commentCount += line.getComments().size();
        assertEquals(0,commentCount);
    }

    @Test
    public void getSimpleSourceCodeWithEmptySourceWithTwoComments() throws Exception {
        RestApi restApi = mock(RestApi.class);
        when( restApi.getFileContent(anyString(),anyString(),anyString() ) )
                .thenReturn("");

        when( restApi.getComments( anyString(),anyString() ) )
                .thenReturn(
                        Collections.singletonMap("/bin/batch.java",
                                Arrays.asList(
                                        CommentInfoDTO.valueOf(1, "First Line"),
                                        CommentInfoDTO.valueOf(4, "Best class declared"))
                        )
                );

        SourceCodeDAOImpl sourceCodeDAO = new SourceCodeDAOImpl( restApi );
        SourceCode sourceCode = sourceCodeDAO.getSourceCode("", "", "/bin/batch.java");

        assertEquals( 0 , sourceCode.getLines().size() );
        int commentCount=0;
        for (Line line : sourceCode.getLines() ) commentCount += line.getComments().size();
        assertEquals(0,commentCount);
    }

    @Test
    public void shouldGetDiffedSourceCode() throws Exception {
        RestApi restApi = mock(RestApi.class);
        String change_id = "change";
        String revision_id = "revision";
        String file_id = "file";
        DiffInfoDTO diffInfoDTO = new DiffInfoDTO(null);
        when ( restApi.getSourceCodeDiff(change_id,revision_id,file_id) ).
                thenReturn( diffInfoDTO );

        SourceCodeDAO sourceCodeDAO = new SourceCodeDAOImpl(restApi);
        SourceCodeDiff sourceCodeDiff = sourceCodeDAO.getSourceCodeDiff(change_id, revision_id, file_id);
        verify(restApi).getSourceCodeDiff(change_id, revision_id, file_id);
        assertEquals(0, sourceCodeDiff.getLinesCount());
    }



}
























