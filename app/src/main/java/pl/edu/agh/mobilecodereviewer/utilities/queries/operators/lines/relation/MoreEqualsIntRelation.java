package pl.edu.agh.mobilecodereviewer.utilities.queries.operators.lines.relation;

/**
 * Created by lee on 2014-11-01.
 */
public class MoreEqualsIntRelation implements IntRelation {
    private final int val;

    public MoreEqualsIntRelation(int val) {
        this.val = val;
    }

    @Override
    public boolean match(int val) {
        return val >= this.val;
    }
}
