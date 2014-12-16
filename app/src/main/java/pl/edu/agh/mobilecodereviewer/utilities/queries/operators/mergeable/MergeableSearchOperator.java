package pl.edu.agh.mobilecodereviewer.utilities.queries.operators.mergeable;

import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.SearchOperator;


public class MergeableSearchOperator implements SearchOperator {
    @Override
    public boolean match(ChangeInfo changeInfo) {
        if (changeInfo == null || changeInfo.getMergeable() == null) return false;
        return changeInfo.getMergeable().booleanValue();
    }
}
