package pl.edu.agh.mobilecodereviewer.dao.gerrit.tools;


import com.google.common.base.Function;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import pl.edu.agh.mobilecodereviewer.dao.gerrit.api.GerritService;
import pl.edu.agh.mobilecodereviewer.dto.ChangeInfoDTO;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Client;
import retrofit.client.Request;
import retrofit.client.Response;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

public class RestAdapterCharacteristicsTest {

    @Test
    public void testCheckTestIsRunning() throws Exception {
        assertEquals(true, true);
    }


    @Test(expected = RetrofitError.class)
    public void testRestAdapterIsOk() throws Exception {
        RestAdapter restAdapter = GerritTestHelper.createRestAdapter(new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                assertEquals("GET", request.getMethod());
                assertEquals("http://0.0.0.0:8080/changes/", request.getUrl());
                return null;
            }
        });

        GerritService gerritService = restAdapter.create(GerritService.class);
        gerritService.getChanges();
    }

    @Test
    public void testGetEmptyJson() throws Exception {
        GerritService gerritService = GerritTestHelper.createSimpleRestServiceForTest(GerritService.class,
                GerritTestHelper.doNothing,
                "");

        List<ChangeInfoDTO> changes = gerritService.getChanges();
        assertNull( changes );
    }

    @Test
    public void testGetEmptyChanges() throws Exception {
        GerritService gerritService = GerritTestHelper.createSimpleRestServiceForTest(GerritService.class,
                GerritTestHelper.doNothing,
                "[]");

        List<ChangeInfoDTO> changes = gerritService.getChanges();
        assertEquals( 0 , changes.size() );
    }

    @Test
    public void testGetRevisionEmpty() throws Exception {
        final int id = 30;
        GerritService gerritService = GerritTestHelper.createSimpleRestServiceForTest(GerritService.class,
                new Function<Request, Void>() {
                    @Override
                    public Void apply(Request from) {
                        assertEquals("http://0.0.0.0:8080/changes/" + 30 + "/detail/", from.getUrl());
                        return null;
                    }
                },
                "{}"
        );

        ChangeInfoDTO changeDetails = gerritService.getChangeDetails(Integer.toString(id));
        assertNull( changeDetails.getId() );
    }

    @Test
    public void testGetChangeWithCurrentRevisionProperSendRequest() throws Exception {
        final int id = 30;
        GerritService gerritService = GerritTestHelper.createSimpleRestServiceForTest(GerritService.class,
                new Function<Request, Void>() {
                    @Override
                    public Void apply(Request from) {
                        assertEquals("http://0.0.0.0:8080/changes/" + 30 + "/?o=CURRENT_REVISION&o=CURRENT_FILES",
                                from.getUrl());
                        return null;
                    }
                },
                "{\"id\":\"" + id + "\"}"
        );

        ChangeInfoDTO changeDetails = gerritService.getCurrentRevisionWithFiles(Integer.toString(id));
        assertEquals( changeDetails.getId() , Integer.toString(id) );
        assertNull(changeDetails.getSubject());
    }
}
















