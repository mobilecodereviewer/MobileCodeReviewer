package pl.edu.agh.mobilecodereviewer.utilities.queries.operators.lines.relation;

/**
 * Created by lee on 2014-11-01.
 */
public class LessIntRelation implements IntRelation {
    private final int val;

    public LessIntRelation(int val) {
        this.val = val;
    }

    @Override
    public boolean match(int val) {
        return val < this.val;
    }
}
