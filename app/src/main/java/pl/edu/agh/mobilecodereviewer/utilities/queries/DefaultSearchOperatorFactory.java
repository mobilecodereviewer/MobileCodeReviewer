package pl.edu.agh.mobilecodereviewer.utilities.queries;

import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.SearchOperator;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.logic.AndSearchOperator;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.logic.NotSearchOperator;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.logic.OrSearchOperator;
import pl.edu.agh.mobilecodereviewer.utilities.queries.parse.predicates.SearchPredicateParser;
import pl.edu.agh.mobilecodereviewer.utilities.queries.parse.rpn.SearchOperatorExecutorFactory;

class DefaultSearchOperatorFactory implements SearchOperatorExecutorFactory {
    @Override
    public SearchOperator createOR(SearchOperator op1, SearchOperator op2) {
        return new OrSearchOperator(op1, op2);
    }

    @Override
    public SearchOperator createAND(SearchOperator op1, SearchOperator op2) {
        return new AndSearchOperator(op1,op2);
    }

    @Override
    public SearchOperator createPredicate(String command) {
        return SearchPredicateParser.parseCommand(command);
    }

    @Override
    public SearchOperator createNOT(SearchOperator op) {
        return new NotSearchOperator(op);
    }
}
