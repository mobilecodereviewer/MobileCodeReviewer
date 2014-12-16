package pl.edu.agh.mobilecodereviewer.utilities.queries.operators.lines.parser;

import pl.edu.agh.mobilecodereviewer.utilities.queries.GerritQueryParseException;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.lines.relation.*;

/**
 * Created by lee on 2014-11-01.
 */
public class RelationLines {
    public static IntRelation parse(String value) {
        int val = -1;
        if (value.length() == 0)
            throw new GerritQueryParseException("Unrecognized line relation in string :" + value);
        if (value.length() == 1) {
            try {
                val = Integer.parseInt(value);
                return new EqualIntRelation(val);
            } catch (NumberFormatException e) {
                throw new GerritQueryParseException("Unrecognized line relation number in string :" + value);
            }
        } else {
            if (value.charAt(0) == '>' && value.charAt(1) == '=') {
                try {
                    val = Integer.parseInt( value.substring(2) );
                    return new MoreEqualsIntRelation(val);
                } catch (NumberFormatException e) {
                    throw new GerritQueryParseException("Unrecognized line relation number in string :" + value);
                }
            } else if (value.charAt(0) == '>') {
                try {
                    val = Integer.parseInt( value.substring(1) );
                    return new MoreIntRelation(val);
                } catch (NumberFormatException e) {
                    throw new GerritQueryParseException("Unrecognized line relation number in string :" + value);
                }
            } else if (value.charAt(0) == '<' && value.charAt(1) == '=') {
                try {
                    val = Integer.parseInt( value.substring(2) );
                    return new LessEqualsIntRelation(val);
                } catch (NumberFormatException e) {
                    throw new GerritQueryParseException("Unrecognized line relation number in string :" + value);
                }
            } else if (value.charAt(0) == '<') {
                try {
                    val = Integer.parseInt( value.substring(1) );
                    return new LessIntRelation(val);
                } catch (NumberFormatException e) {
                    throw new GerritQueryParseException("Unrecognized line relation number in string :" + value);
                }
            } else {
                try {
                    val = Integer.parseInt( value );
                    return new EqualIntRelation(val);
                } catch (NumberFormatException e) {
                    throw new GerritQueryParseException("Unrecognized line relation number in string :" + value);
                }
            }
        }
    }


}
