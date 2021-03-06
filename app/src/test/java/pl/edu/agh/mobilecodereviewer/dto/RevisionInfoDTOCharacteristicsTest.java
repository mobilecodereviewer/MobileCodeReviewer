package pl.edu.agh.mobilecodereviewer.dto;


import com.google.gson.Gson;

import org.junit.Test;

import java.util.Collections;

import static junit.framework.Assert.assertEquals;

public class RevisionInfoDTOCharacteristicsTest {

    @Test
    public void testCheckTestIsRunning() throws Exception {
        assertEquals(true, true);
    }

    @Test
    public void testJsonViewNotInitialized() throws Exception {
        RevisionInfoDTO revisionInfoDTO = new RevisionInfoDTO();
        Gson gson = new Gson();
        String json = gson.toJson(revisionInfoDTO);
        assertEquals("{}",json);
    }


    @Test
    public void testJsonViewWithNumber() throws Exception {
        RevisionInfoDTO revisionInfoDTO = new RevisionInfoDTO();
        revisionInfoDTO.setNumber("10");
        Gson gson = new Gson();
        String json = gson.toJson(revisionInfoDTO);
        assertEquals("{\"_number\":\"10\"}", json);
    }

    @Test
    public void testJsonViewWithFile() throws Exception {
        RevisionInfoDTO revisionInfoDTO = new RevisionInfoDTO();
        revisionInfoDTO.setFiles(Collections.singletonMap("1",new FileInfoDTO("ok",15)) );
        Gson gson = new Gson();
        String json = gson.toJson(revisionInfoDTO);
        assertEquals("{\"files\":{\"1\":{\"status\":\"ok\",\"lines_inserted\":15}}}",json);
    }

}
