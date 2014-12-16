package pl.edu.agh.mobilecodereviewer.utilities.queries.operators.logic;


import pl.edu.agh.mobilecodereviewer.model.ChangeInfo;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.SearchOperator;

public class OrSearchOperator implements SearchOperator {
    private SearchOperator op1;
    private SearchOperator op2;

    public OrSearchOperator(SearchOperator op1,SearchOperator op2) {
        this.op1 = op1;
        this.op2 = op2;
    }

    @Override
    public boolean match(ChangeInfo changeInfo) {
        if (changeInfo == null) return false;
        return op1.match(changeInfo) || op2.match(changeInfo);
    }
}
