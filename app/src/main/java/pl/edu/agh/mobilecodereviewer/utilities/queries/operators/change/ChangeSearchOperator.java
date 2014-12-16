package pl.edu.agh.mobilecodereviewer.utilities.queries.operators.change;

import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.SearchOperator;

/**
 * Created by lee on 2014-11-01.
 */
public class ChangeSearchOperator implements SearchOperator {
    private final String value;

    public ChangeSearchOperator(String value) {
        this.value = value;
    }

    @Override
    public boolean match(ChangeInfo changeInfo) {
        if (changeInfo == null || changeInfo.getChangeId() == null) return false;
        return changeInfo.getChangeId().equals(value) ;
    }
}
