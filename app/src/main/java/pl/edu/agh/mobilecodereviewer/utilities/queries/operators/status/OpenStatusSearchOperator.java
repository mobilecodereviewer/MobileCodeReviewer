package pl.edu.agh.mobilecodereviewer.utilities.queries.operators.status;

import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.model.ChangeStatus;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.SearchOperator;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by lee on 2014-10-31.
 */
public class OpenStatusSearchOperator implements SearchOperator {
    @Override
    public boolean match(ChangeInfo changeInfo) {
        if (changeInfo == null || changeInfo.getStatus() == null ) return false;
        Set<ChangeStatus> openStatus =
                new HashSet<>( Arrays.asList(ChangeStatus.NEW,ChangeStatus.SUBMITTED,ChangeStatus.DRAFT) );
        return openStatus.contains(changeInfo.getStatus());
    }
}
