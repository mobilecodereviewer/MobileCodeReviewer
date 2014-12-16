package pl.edu.agh.mobilecodereviewer.utilities.queries.operators.age.parser;

import pl.edu.agh.mobilecodereviewer.utilities.queries.GerritQueryParseException;
import pl.edu.agh.mobilecodereviewer.utilities.queries.parse.ParseUtils;

/**
 * Created by lee on 2014-10-31.
 */
public class AgeValueParser {
    public static AgeValue parse(String ageToParse) {
        int i= 0;
        ageToParse = ParseUtils.parseValueFromQuotingDelimeters(ageToParse);
        while ( Character.isDigit( ageToParse.charAt(i) ) ) i++;
        if (i == 0)
            throw new GerritQueryParseException("Unrecognized age value , first part of age value should be number");
        int value = Integer.parseInt( ageToParse.substring(0,i) );
        AgeType type;
        try {
            type = AgeType.createTypeFromString( ageToParse.substring(i, ageToParse.length() ) );
        } catch (Exception e) {
            throw new GerritQueryParseException("Unrecognized age type version:\"" + ageToParse +
                                                 "\" should be one of s,sec,seconds,h,m etc");
        }
        return new AgeValue(type,value);
    }
}
