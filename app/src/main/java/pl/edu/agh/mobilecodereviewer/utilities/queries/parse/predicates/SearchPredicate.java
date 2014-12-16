package pl.edu.agh.mobilecodereviewer.utilities.queries.parse.predicates;

import pl.edu.agh.mobilecodereviewer.utilities.queries.GerritQueryParseException;

public class SearchPredicate {
    private String command;
    private String options;
    private boolean is_negated;

    public SearchPredicate(String predicate) {
        assert predicate != null && predicate.length() > 1;
        if (predicate.charAt(0) == '-' ) {
            is_negated = true;
            predicate = predicate.substring(1);
        } else
            is_negated = false;

        splitCommandAndOption(predicate);
    }

    private void splitCommandAndOption(String predicate) {
        int separatorIndex = predicate.indexOf(':');
        if (separatorIndex == -1) {
            throw new GerritQueryParseException("Cant find name/value seperator \':\' at predicate : \"" + predicate + "\"");
        }

        this.command = predicate.substring(0,separatorIndex);
        this.options = predicate.substring(separatorIndex+1);
    }

    public String getBase() {
        return command;
    }

    public String getExtension() {
        return options;
    }

    public boolean isNegated() {
        return is_negated;
    }
}
