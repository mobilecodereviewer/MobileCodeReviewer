package pl.edu.agh.mobilecodereviewer.utilities.queries.operators.status;

import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.SearchOperator;

/**
 * Created by lee on 2014-10-31.
 */
public class AllStatusSearchOperator implements SearchOperator{

    @Override
    public boolean match(ChangeInfo changeInfo) {
        if (changeInfo == null) return false;
        return true;
    }
}
