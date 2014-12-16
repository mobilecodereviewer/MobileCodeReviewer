package pl.edu.agh.mobilecodereviewer.utilities.queries.utils;

import pl.edu.agh.mobilecodereviewer.utilities.queries.GerritQueryParseException;
import pl.edu.agh.mobilecodereviewer.utilities.queries.parse.ParseUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lee on 2014-11-01.
 */
public class TimeUtils {

    public static final String GERRIT_TIME_SHORT = "yyyy-MM-dd";
    public static final String GERRIT_TIME_MID =   "yyyy-MM-dd HH:mm:ss";
    public static final String GERRIT_TIME_LONG =  "yyyy-MM-dd HH:mm:ss.SSS";

    public static Date parseDate(String dateText) {
        Date parsedDate = null;
        dateText = ParseUtils.parseValueFromQuotingDelimeters(dateText);

        try {
            if (dateText.length() == GERRIT_TIME_SHORT.length()) {
                parsedDate = new SimpleDateFormat(GERRIT_TIME_SHORT, Locale.ENGLISH).parse(dateText);
            } else if (dateText.length() == GERRIT_TIME_MID.length()) {
                parsedDate = new SimpleDateFormat(GERRIT_TIME_MID, Locale.ENGLISH).parse(dateText);
            } else if (dateText.length() == GERRIT_TIME_LONG.length()) {
                parsedDate = new SimpleDateFormat(GERRIT_TIME_LONG, Locale.ENGLISH).parse(dateText);
            } else throw new ParseException("",0);
        } catch (ParseException e) {
            throw new GerritQueryParseException("Time has no appropriate format :\"" + dateText + "\"");
        }

        return parsedDate;
    }
}
