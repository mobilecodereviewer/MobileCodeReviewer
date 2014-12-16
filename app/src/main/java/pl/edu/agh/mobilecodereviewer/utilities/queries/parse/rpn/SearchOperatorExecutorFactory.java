package pl.edu.agh.mobilecodereviewer.utilities.queries.parse.rpn;

import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.SearchOperator;

public interface SearchOperatorExecutorFactory {
    SearchOperator createOR(SearchOperator op1, SearchOperator op2);

    SearchOperator createAND(SearchOperator op1, SearchOperator op2);

    SearchOperator createPredicate(String command);

    SearchOperator createNOT(SearchOperator op);
}
