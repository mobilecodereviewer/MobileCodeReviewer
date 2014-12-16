package pl.edu.agh.mobilecodereviewer.utilities.queries.operators;

import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;

/**
 * Created by lee on 2014-10-31.
 */
public interface SearchOperator {
    boolean match(ChangeInfo changeInfo);
}
