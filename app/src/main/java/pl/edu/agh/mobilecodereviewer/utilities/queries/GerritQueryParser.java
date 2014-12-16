package pl.edu.agh.mobilecodereviewer.utilities.queries;

import pl.edu.agh.mobilecodereviewer.utilities.queries.operators.SearchOperator;
import pl.edu.agh.mobilecodereviewer.utilities.queries.parse.tokens.Token;

import java.util.List;


public class GerritQueryParser {
    public static GerritQuery parse(String queryString) {
        if (queryString == null || queryString.equals("") ) {
            return new NullGerritQuery(null);
        }

        List<Token> tokenizedQuery = new DefaultQueryStringTokenizer().tokenize(queryString);
        DefaultQueryEvaluator queryEvaluator = new DefaultQueryEvaluator();
        SearchOperator resultOperator = queryEvaluator.evaluate(tokenizedQuery);

        return new GerritQuery(resultOperator);
    }

}
