package pl.edu.agh.mobilecodereviewer.utilities.queries.operators.lines;

import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.SearchOperator;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.lines.relation.IntRelation;

/**
 * Created by lee on 2014-11-01.
 */
public class DeletedLinesSearchOperator implements SearchOperator {
    private final IntRelation rel;

    public DeletedLinesSearchOperator(IntRelation rel) {
        this.rel = rel;
    }

    @Override
    public boolean match(ChangeInfo changeInfo) {
        if (changeInfo == null || changeInfo.getDeletions() == null ) return false;
        return rel.match( changeInfo.getDeletions().intValue() );
    }
}
