package pl.edu.agh.mobilecodereviewer.utilities.queries.utils;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static junit.framework.TestCase.assertEquals;

public class TimeUtilsTest {

    @Test
    public void shouldSimpleShortFormatOFGerritTimeBeProperlyParsed() throws Exception {
        Date date = TimeUtils.parseDate("\'2006-01-15\'");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date );
        assertEquals(2006, cal.get(Calendar.YEAR));
        assertEquals(0, cal.get(Calendar.MONTH));
        assertEquals(15, cal.get(Calendar.DAY_OF_MONTH) );
        assertEquals(0, cal.get(Calendar.HOUR));
        assertEquals(0, cal.get(Calendar.MINUTE ) );
        assertEquals(0, cal.get(Calendar.SECOND) );
    }

    @Test
    public void shouldSimpleMidFormatOFGerritTimeBeProperlyParsed() throws Exception {
        Date date = TimeUtils.parseDate("\'2006-01-02 15:04:05\'");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date );
        assertEquals(2006, cal.get(Calendar.YEAR) );
        assertEquals(0, cal.get(Calendar.MONTH));
        assertEquals(2, cal.get(Calendar.DAY_OF_MONTH) );
        assertEquals(15, cal.get(Calendar.HOUR_OF_DAY));
        assertEquals(4, cal.get(Calendar.MINUTE ) );
        assertEquals(5, cal.get(Calendar.SECOND) );
    }

    @Test
    public void shouldSimpleLongFormatOFGerritTimeBeProperlyParsed() throws Exception {
        Date date = TimeUtils.parseDate("\'2006-01-02 15:04:05.890\'");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date );
        assertEquals(2006, cal.get(Calendar.YEAR) );
        assertEquals(0, cal.get(Calendar.MONTH));
        assertEquals(2, cal.get(Calendar.DAY_OF_MONTH) );
        assertEquals(15, cal.get(Calendar.HOUR_OF_DAY));
        assertEquals(4, cal.get(Calendar.MINUTE ) );
        assertEquals(5, cal.get(Calendar.SECOND) );
        assertEquals(890, cal.get(Calendar.MILLISECOND) );
    }


}