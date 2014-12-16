package pl.edu.agh.mobilecodereviewer.utilities.queries.operators.status;

import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.SearchOperator;

public class ReviewedStatusSearchOperator implements SearchOperator {
    @Override
    public boolean match(ChangeInfo changeInfo) {
        if (changeInfo == null || changeInfo.getReviewed() == null) return false;
        return changeInfo.getReviewed().booleanValue();
    }
}
