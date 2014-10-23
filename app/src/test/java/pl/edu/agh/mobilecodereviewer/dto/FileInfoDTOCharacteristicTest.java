package pl.edu.agh.mobilecodereviewer.dto;

import com.google.gson.Gson;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class FileInfoDTOCharacteristicTest {

    @Test
    public void testCheckTestIsRunning() throws Exception {
        assertEquals(true, true);
    }

    @Test
    public void testCreatedFromJsonEmptyString() throws Exception {
        Gson gson = new Gson();
        FileInfoDTO fileInfoDTO = gson.fromJson("", FileInfoDTO.class);
        assertNull( fileInfoDTO );
    }

    @Test
    public void testCreatedFromJsonNullJson() throws Exception {
        Gson gson = new Gson();
        FileInfoDTO fileInfoDTO = gson.fromJson("{}", FileInfoDTO.class);
        assertEquals(0,fileInfoDTO.getLinesInserted());
        assertNull(fileInfoDTO.getStatus());
    }

    @Test
    public void testCreatedFromJsonProperValues() throws Exception {
        Gson gson = new Gson();
        String json = "{\"status\":\"ok\",\"lines_inserted\":30}";
        FileInfoDTO fileInfoDTO = gson.fromJson(json, FileInfoDTO.class);
        assertEquals("ok",fileInfoDTO.getStatus());
        assertEquals(30,fileInfoDTO.getLinesInserted());
    }

    @Test
    public void testCreatedFromJsonWithoutStatus() throws Exception {
        Gson gson = new Gson();
        String json = "{\"lines_inserted\":30}";
        FileInfoDTO fileInfoDTO = gson.fromJson( json, FileInfoDTO.class );
        assertEquals( 30 , fileInfoDTO.getLinesInserted() );
        assertNull( fileInfoDTO.getStatus() );
    }

    @Test
    public void testCreatedFromJsonWithoutLinesInserted() throws Exception {
        Gson gson = new Gson();
        String json = "{\"status\":\"Invalidated\"}";
        FileInfoDTO fileInfoDTO = gson.fromJson( json, FileInfoDTO.class );
        assertEquals( "Invalidated" , fileInfoDTO.getStatus() );
        assertEquals( 0 , fileInfoDTO.getLinesInserted() );
    }


    @Test
    public void testCreatedFromJsonWithTooManyValues() throws Exception {
        Gson gson = new Gson();
        String json = "{\"status\":\"ok\",\"lines_inserted\":50,\"ext\":90}";
        FileInfoDTO fileInfoDTO = gson.fromJson(json, FileInfoDTO.class);
        assertEquals( "ok" , fileInfoDTO.getStatus() );
        assertEquals( 50 , fileInfoDTO.getLinesInserted() );
    }

}










