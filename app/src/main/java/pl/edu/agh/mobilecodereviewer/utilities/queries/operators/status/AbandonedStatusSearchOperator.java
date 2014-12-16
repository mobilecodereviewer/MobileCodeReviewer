package pl.edu.agh.mobilecodereviewer.utilities.queries.operators.status;

import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.model.ChangeStatus;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.SearchOperator;

/**
 * Created by lee on 2014-10-31.
 */
public class AbandonedStatusSearchOperator implements SearchOperator {

    @Override
    public boolean match(ChangeInfo changeInfo) {
        if (changeInfo == null || changeInfo.getStatus() == null ) return false;
        return changeInfo.getStatus() == ChangeStatus.ABANDONED;
    }
}
