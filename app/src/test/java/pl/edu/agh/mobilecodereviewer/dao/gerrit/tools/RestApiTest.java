package pl.edu.agh.mobilecodereviewer.dao.gerrit.utilities;


import com.google.common.base.Function;

import org.junit.Test;

import java.util.List;
import java.util.Map;

import pl.edu.agh.mobilecodereviewer.dao.gerrit.api.GerritService;
import pl.edu.agh.mobilecodereviewer.dto.CommentInfoDTO;
import pl.edu.agh.mobilecodereviewer.dto.DiffContentDTO;
import pl.edu.agh.mobilecodereviewer.dto.DiffInfoDTO;
import retrofit.client.Request;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

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

    @Test
    public void shouldGetProperDiffAndSendAppropriateDiffRequest() throws Exception {
        GerritService gerritService = GerritTestHelper.createSimpleRestServiceForTest(GerritService.class,
                new Function<Request, Void>() {
                    @Override
                    public Void apply(Request from) {
                        assertEquals(
                                "http://0.0.0.0:8080/changes/myProjectmasterI847/revisions/674ac/files/RefControl.java/diff",
                                from.getUrl()
                        );
                        return null;
                    }
                }
                , "{\n" +
                        "    \"meta_a\": {\n" +
                        "      \"name\": \"gerrit-server/src/main/java/com/google/gerrit/server/project/RefControl.java\",\n" +
                        "      \"content_type\": \"text/x-java-source\",\n" +
                        "      \"lines\": 372\n" +
                        "    },\n" +
                        "    \"meta_b\": {\n" +
                        "      \"name\": \"gerrit-server/src/main/java/com/google/gerrit/server/project/RefControl.java\",\n" +
                        "      \"content_type\": \"text/x-java-source\",\n" +
                        "      \"lines\": 578\n" +
                        "    },\n" +
                        "    \"change_type\": \"MODIFIED\",\n" +
                        "    \"diff_header\": [\n" +
                        "      \"diff --git a/gerrit-server/src/main/java/com/google/gerrit/server/project/RefControl.java b/gerrit-server/src/main/java/com/google/gerrit/server/project/RefControl.java\",\n" +
                        "      \"index 59b7670..9faf81c 100644\",\n" +
                        "      \"--- a/gerrit-server/src/main/java/com/google/gerrit/server/project/RefControl.java\",\n" +
                        "      \"+++ b/gerrit-server/src/main/java/com/google/gerrit/server/project/RefControl.java\"\n" +
                        "    ],\n" +
                        "    \"content\": [\n" +
                        "      {\n" +
                        "        \"ab\": [\n" +
                        "          \"// Copyright (C) 2010 The Android Open Source Project\",\n" +
                        "          \"//\",\n" +
                        "          \"// Licensed under the Apache License, Version 2.0 (the \\\"License\\\");\",\n" +
                        "          \"// you may not use this file except in compliance with the License.\",\n" +
                        "          \"// You may obtain a copy of the License at\",\n" +
                        "          \"//\",\n" +
                        "          \"// http://www.apache.org/licenses/LICENSE-2.0\",\n" +
                        "          \"//\",\n" +
                        "          \"// Unless required by applicable law or agreed to in writing, software\",\n" +
                        "          \"// distributed under the License is distributed on an \\\"AS IS\\\" BASIS,\",\n" +
                        "          \"// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\",\n" +
                        "          \"// See the License for the specific language governing permissions and\",\n" +
                        "          \"// limitations under the License.\"\n" +
                        "        ]\n" +
                        "      },\n" +
                        "      {\n" +
                        "        \"b\": [\n" +
                        "          \"//\",\n" +
                        "          \"// Add some more lines in the header.\"\n" +
                        "        ]\n" +
                        "      },\n" +
                        "      {\n" +
                        "        \"ab\": [\n" +
                        "          \"\",\n" +
                        "          \"package com.google.gerrit.server.project;\",\n" +
                        "          \"\",\n" +
                        "          \"import com.google.common.collect.Maps;\"\n" +
                        "        ]\n" +
                        "      }\n" +
                        "    ]\n" +
                        "  }"
        );
        RestApi restApi = new RestApi(gerritService);
        DiffInfoDTO sourceCodeDiff = restApi.getSourceCodeDiff("myProjectmasterI847", "674ac", "RefControl.java");
        List<DiffContentDTO> diffContent = sourceCodeDiff.getDiffContent();
        assertNotNull(diffContent);
        assertEquals( 3, diffContent.size() );

        DiffContentDTO firstDiff = diffContent.get(0);
        List<String> firstDiffLinesUnchanged = firstDiff.getLinesUnchanged();
        assertEquals(13, firstDiffLinesUnchanged.size());
        assertEquals("// Copyright (C) 2010 The Android Open Source Project", firstDiffLinesUnchanged.get(0));
        assertEquals("// http://www.apache.org/licenses/LICENSE-2.0", firstDiffLinesUnchanged.get(6));
        assertEquals("// limitations under the License.", firstDiffLinesUnchanged.get(12));
        assertNull(firstDiff.getLinesAfterChange());
        assertNull( firstDiff.getLinesBeforeChange());

        DiffContentDTO secondDiff = diffContent.get(1);
        List<String> secondDiffLinesAfterChange = secondDiff.getLinesAfterChange();
        assertEquals(2, secondDiffLinesAfterChange.size());
        assertEquals("//", secondDiffLinesAfterChange.get(0));
        assertEquals("// Add some more lines in the header.", secondDiffLinesAfterChange.get(1));
        assertNull(secondDiff.getLinesBeforeChange());
        assertNull( secondDiff.getLinesUnchanged() );

        DiffContentDTO thirdDiff = diffContent.get(2);
        List<String> thirdDiffLinesUnchanged = thirdDiff.getLinesUnchanged();
        assertEquals(4, thirdDiffLinesUnchanged.size());
        assertEquals("", thirdDiffLinesUnchanged.get(0));
        assertEquals("package com.google.gerrit.server.project;", thirdDiffLinesUnchanged.get(1) );
        assertEquals("", thirdDiffLinesUnchanged.get(2));
        assertEquals("import com.google.common.collect.Maps;", thirdDiffLinesUnchanged.get(3));

        assertNull(thirdDiff.getLinesBeforeChange());
        assertNull(thirdDiff.getLinesAfterChange());
    }

}









