package pl.edu.agh.mobilecodereviewer.utilities.queries.operators.lines;

import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.SearchOperator;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.lines.relation.IntRelation;

/**
 * Created by lee on 2014-11-01.
 */
public class AddedLinesSearchOperator implements SearchOperator {
    private final IntRelation relation;

    public AddedLinesSearchOperator(IntRelation linesAdded) {
        this.relation = linesAdded;
    }

    @Override
    public boolean match(ChangeInfo changeInfo) {
        if (changeInfo == null || changeInfo.getInsertions() == null ) return false;
        return relation.match( changeInfo.getInsertions().intValue() );
    }
}
