package pl.edu.agh.mobilecodereviewer.utilities.queries.operators.logic;


import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.SearchOperator;

public class NotSearchOperator implements SearchOperator {
    private SearchOperator op;

    public NotSearchOperator(SearchOperator op) {
        this.op = op;
    }

    @Override
    public boolean match(ChangeInfo changeInfo) {
        if (changeInfo == null) return false;
        return !op.match(changeInfo);
    }
}
