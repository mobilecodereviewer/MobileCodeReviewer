package pl.edu.agh.mobilecodereviewer.utilities.queries.operators.lines.parser;

import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.lines.relation.IntRelation;

/**
 * Created by lee on 2014-11-01.
 */
public class LessEqualsIntRelation implements IntRelation {
    private final int val;

    public LessEqualsIntRelation(int val) {
        this.val = val;
    }

    @Override
    public boolean match(int val) {
        return val <= this.val;
    }
}
