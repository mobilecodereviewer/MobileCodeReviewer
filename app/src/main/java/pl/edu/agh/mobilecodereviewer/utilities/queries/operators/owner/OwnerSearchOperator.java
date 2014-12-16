package pl.edu.agh.mobilecodereviewer.utilities.queries.operators.owner;

import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.SearchOperator;

/**
 * Created by lee on 2014-11-01.
 */
public class OwnerSearchOperator implements SearchOperator {
    private final String owner;

    public OwnerSearchOperator(String valWithoutParantheses) {
        this.owner = valWithoutParantheses;
    }

    @Override
    public boolean match(ChangeInfo changeInfo) {
        if (changeInfo == null || changeInfo.getOwnerName() == null ) return false;
        return changeInfo.getOwnerName().equals(owner);
    }
}
