package pl.edu.agh.mobilecodereviewer.utilities.queries.parse;

import com.google.common.collect.Sets;
import pl.edu.agh.mobilecodereviewer.utilities.queries.GerritQueryParseException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ParseUtils {

    public static final Set<Character> beginQuotingDelemeters =
            new HashSet<>(Arrays.asList('\'','\"','{'));

    public static final Set<Character> endQuotingDelemeters =
            new HashSet<>(Arrays.asList('\'','\"','}'));

    public static final Set<Character> quotingDelemeters = Sets.union(beginQuotingDelemeters, endQuotingDelemeters);


    public static String parseValueFromQuotingDelimeters(String value) {
        if (value.length() < 2 || !( beginQuotingDelemeters.contains(value.charAt(0)) &&
                                     endQuotingDelemeters.contains( value.charAt(value.length()-1))
                                   ) )
            throw new GerritQueryParseException("Unrecognized value, value should be enclosed in quoting delimeters");
        return value.substring(1, value.length() - 1);
    }

}



















