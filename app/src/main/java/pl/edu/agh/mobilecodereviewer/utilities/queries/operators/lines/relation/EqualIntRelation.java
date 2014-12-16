package pl.edu.agh.mobilecodereviewer.utilities.queries.operators.lines.relation;

/**
 * Created by lee on 2014-11-01.
 */
public class EqualIntRelation implements IntRelation {
    private final int val;

    public EqualIntRelation(int val) {
        this.val = val;
    }

    @Override
    public boolean match(int val) {
        return this.val == val;
    }
}
