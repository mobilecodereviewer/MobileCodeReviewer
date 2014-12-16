package pl.edu.agh.mobilecodereviewer.utilities.queries.operators.starred;

import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.SearchOperator;

public class StarredSearchOperator implements SearchOperator {

    @Override
    public boolean match(ChangeInfo changeInfo) {
        if (changeInfo == null || changeInfo.hasStarred() == null) return false;
        return changeInfo.hasStarred().booleanValue();
    }
}
