package pl.edu.agh.mobilecodereviewer.dto;


import com.google.gson.Gson;

import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

public class ChangeInfoDTOCharacteristicTest {

    private String prepareJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    @Test
    public void testCheckTestIsRunning() throws Exception {
        assertEquals(true, true);
    }

    @Test
    public void testJsonViewWithNullValues() throws Exception {
        ChangeInfoDTO changeInfoDTO = new ChangeInfoDTO();
        String json = prepareJson(changeInfoDTO);
        assertEquals("{}" , json );
    }

    @Test
    public void testJsonViewWithId() throws Exception {
        ChangeInfoDTO changeInfoDTO = new ChangeInfoDTO();
        changeInfoDTO.setId("1");
        String json = prepareJson( changeInfoDTO );
        assertEquals("{\"id\":\"1\"}", json);
    }

    @Test
    public void testJsonViewWithIdAndChangeId() throws Exception {
        ChangeInfoDTO changeInfoDTO = new ChangeInfoDTO();
        changeInfoDTO.setId("1");
        changeInfoDTO.setChangeId("1");
        String json = prepareJson(changeInfoDTO);
        assertEquals("{\"id\":\"1\",\"change_id\":\"1\"}" , json );
    }

    @Test
    public void testJsonViewWithChangeId() throws Exception {
        ChangeInfoDTO changeInfoDTO = new ChangeInfoDTO();
        changeInfoDTO.setChangeId("1");
        String json = prepareJson(changeInfoDTO);
        assertEquals("{\"change_id\":\"1\"}",json);
    }

    @Test
    public void testJsonViewWithCurrentRevision() throws Exception {
        ChangeInfoDTO changeInfoDTO = new ChangeInfoDTO();
        changeInfoDTO.setCurrentRevision("1");
        String json = prepareJson(changeInfoDTO);
        assertEquals("{\"current_revision\":\"1\"}" , json);
    }

    @Test
    public void testJsonViewWithIdAndOneRevisionInfoDTO() throws Exception {
        ChangeInfoDTO changeInfoDTO = new ChangeInfoDTO();
        changeInfoDTO.setId("1");
        Map<String, RevisionInfoDTO> revisions = new HashMap<>();
        revisions.put("1",new RevisionInfoDTO("1", Collections.singletonMap("1",new FileInfoDTO("ok",1) ) ) );
        revisions.put("2",new RevisionInfoDTO("2", Collections.singletonMap("1",new FileInfoDTO("ok",1) ) ) );
        changeInfoDTO.setRevisions( revisions );
        String json = prepareJson(changeInfoDTO);
        assertEquals("{\"id\":\"1\",\"revisions\":{" +
                        "\"1\":{\"_number\":\"1\",\"files\":{\"1\":{\"status\":\"ok\",\"lines_inserted\":1}}}," +
                        "\"2\":{\"_number\":\"2\",\"files\":{\"1\":{\"status\":\"ok\",\"lines_inserted\":1}}}" +
                     "}}",json);
    }

}



















