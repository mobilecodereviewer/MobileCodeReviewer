package pl.edu.agh.mobilecodereviewer.utilities.queries;


import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.SearchOperator;
import pl.edu.agh.mobilecodereviewer.utilities.queries.parse.rpn.RPNExecutor;
import pl.edu.agh.mobilecodereviewer.utilities.queries.parse.tokens.Token;

import java.util.List;

public class DefaultQueryEvaluator {

    private final RPNExecutor executor;

    public DefaultQueryEvaluator() {
        executor = new RPNExecutor(new DefaultSearchOperatorFactory());
    }

    public SearchOperator evaluate(List<Token> tokenizedQuery) {
        return executor.calculateSearchOperator(tokenizedQuery);
    }
}
