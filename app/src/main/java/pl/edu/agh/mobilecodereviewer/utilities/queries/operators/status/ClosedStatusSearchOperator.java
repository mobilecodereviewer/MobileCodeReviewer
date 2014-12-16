package pl.edu.agh.mobilecodereviewer.utilities.queries.operators.status;

import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.model.ChangeStatus;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.SearchOperator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lee on 2014-11-01.
 */
public class ClosedStatusSearchOperator implements SearchOperator {
    @Override
    public boolean match(ChangeInfo changeInfo) {
        if (changeInfo == null || changeInfo.getStatus() == null ) return false;
        Set<ChangeStatus> closeStatus =
                new HashSet<>( Arrays.asList( ChangeStatus.MERGED, ChangeStatus.ABANDONED) );
        return closeStatus.contains(changeInfo.getStatus());
    }
}
