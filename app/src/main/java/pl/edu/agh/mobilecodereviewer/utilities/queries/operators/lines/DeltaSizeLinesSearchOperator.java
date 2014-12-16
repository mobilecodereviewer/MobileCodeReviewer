package pl.edu.agh.mobilecodereviewer.utilities.queries.operators.lines;

import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.SearchOperator;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.lines.relation.IntRelation;

/**
 * Created by lee on 2014-11-01.
 */
public class DeltaSizeLinesSearchOperator implements SearchOperator {
    private final IntRelation rel;

    public DeltaSizeLinesSearchOperator(IntRelation rel) {
        this.rel = rel;
    }

    @Override
    public boolean match(ChangeInfo changeInfo) {
        int delta = Math.abs(changeInfo.getInsertions() - changeInfo.getDeletions());
        int size = changeInfo.getSize();
        return rel.match( delta/size );
    }
}
