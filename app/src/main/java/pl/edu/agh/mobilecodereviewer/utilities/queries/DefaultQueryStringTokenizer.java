package pl.edu.agh.mobilecodereviewer.utilities.queries;

import pl.edu.agh.mobilecodereviewer.utilities.queries.parse.lexing.Lexer;
import pl.edu.agh.mobilecodereviewer.utilities.queries.parse.rpn.InfixToRPNConvertor;
import pl.edu.agh.mobilecodereviewer.utilities.queries.parse.tokens.Token;

import java.util.List;

public class DefaultQueryStringTokenizer {

    public List<Token> tokenize(String queryString) {
        Lexer lexer = new Lexer();
        List<Token> queryStringTokenized = lexer.createTokenStreamFromQueryString(queryString);

        InfixToRPNConvertor infixToRPNConvertor = new InfixToRPNConvertor();
        infixToRPNConvertor.addMissingJoinANDOperator(queryStringTokenized);
        return infixToRPNConvertor.convert(queryStringTokenized);
    }
}
