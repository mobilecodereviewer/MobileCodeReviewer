package pl.edu.agh.mobilecodereviewer.dao.gerrit;


import org.junit.Ignore;
import org.junit.Test;

import pl.edu.agh.mobilecodereviewer.dao.gerrit.api.GerritService;

import static junit.framework.Assert.assertEquals;
import static pl.edu.agh.mobilecodereviewer.dao.gerrit.utilities.GerritTestHelper.createSimpleRestServiceForTest;
import static pl.edu.agh.mobilecodereviewer.dao.gerrit.utilities.GerritTestHelper.doNothing;

@Ignore(value = "It is not needed at the moment;p")
public class ChangeInfoDAOImplTest {

    @Test
    public void testCheckTestIsRunning() throws Exception {
        assertEquals(true, true);
    }

    @Test
    public void getChangeInfoByIDFromEmptyMessage() throws Exception {
        GerritService gerritService = createSimpleRestServiceForTest(
                GerritService.class,
                doNothing,
                "{}"
        );

    }
}
