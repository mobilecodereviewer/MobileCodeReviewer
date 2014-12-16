package pl.edu.agh.mobilecodereviewer.utilities.queries.parse.rpn;

import pl.edu.agh.mobilecodereviewer.utilities.queries.GerritQueryParseException;
import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.SearchOperator;
import pl.edu.agh.mobilecodereviewer.utilities.queries.parse.tokens.Token;
import pl.edu.agh.mobilecodereviewer.utilities.queries.parse.tokens.TokenType;

import java.util.List;
import java.util.Stack;

public class RPNExecutor {

    private final SearchOperatorExecutorFactory searchOperatorExecutorFactory;

    public RPNExecutor(SearchOperatorExecutorFactory searchOperatorExecutorFactory) {
        this.searchOperatorExecutorFactory = searchOperatorExecutorFactory;
    }

    public SearchOperator calculateSearchOperator(List<Token> tokenInRPNForm) {
        Stack<SearchOperator> resultStack = new Stack<>();

        for (int i = 0; i < tokenInRPNForm.size(); i++) {
            Token currToken = tokenInRPNForm.get(i);

            if (currToken.getType() == TokenType.QUERY_OPERATOR) {
                SearchOperator predicate = searchOperatorExecutorFactory.createPredicate(currToken.getValue());
                resultStack.push(predicate);
            } else if (currToken.getType() == TokenType.AND_OPERATOR) {
                if (resultStack.size()<2) throw new GerritQueryParseException("Query parse exception");
                SearchOperator op1 = resultStack.pop();
                SearchOperator op2 = resultStack.pop();
                SearchOperator andOp = searchOperatorExecutorFactory.createAND(op1, op2);
                resultStack.push(andOp);
            } else if (currToken.getType() == TokenType.OR_OPERATOR) {
                if (resultStack.size()<2) throw new GerritQueryParseException("Query parse exception");
                SearchOperator op1 = resultStack.pop();
                SearchOperator op2 = resultStack.pop();
                SearchOperator orOp = searchOperatorExecutorFactory.createOR(op1, op2);
                resultStack.push(orOp);
            } else if (currToken.getType() == TokenType.NOT_OPERATOR){
                if (resultStack.size()<1) throw new GerritQueryParseException("Query parse exception");
                SearchOperator op = resultStack.pop();
                SearchOperator notOp = searchOperatorExecutorFactory.createNOT(op);
                resultStack.push(notOp);
            } else throw new GerritQueryParseException("Query parse exception");
        }

        if (resultStack.size() != 1) throw new GerritQueryParseException("Query parse exception");

        return resultStack.pop();
    }
}






















