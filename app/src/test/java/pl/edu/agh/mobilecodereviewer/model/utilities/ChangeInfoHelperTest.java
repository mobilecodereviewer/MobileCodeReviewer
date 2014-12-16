package pl.edu.agh.mobilecodereviewer.model.utilities;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class ChangeInfoHelperTest {

    @Test
    public void testGerritTimeToDate() throws Exception {
        Date date = ChangeInfoHelper.gerritTimeToDate("2014-10-21 22:21:07.851000000");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        assertEquals(2014, cal.get(Calendar.YEAR));
        assertEquals(9, cal.get(Calendar.MONTH));
        assertEquals(21, cal.get(Calendar.DAY_OF_MONTH));
    }
}