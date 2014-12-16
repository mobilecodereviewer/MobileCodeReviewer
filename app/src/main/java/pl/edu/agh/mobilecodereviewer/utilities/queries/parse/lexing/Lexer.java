package pl.edu.agh.mobilecodereviewer.utilities.queries.parse.lexing;

import pl.edu.agh.mobilecodereviewer.utilities.queries.parse.ParseUtils;
import pl.edu.agh.mobilecodereviewer.utilities.queries.parse.tokens.Token;
import pl.edu.agh.mobilecodereviewer.utilities.queries.parse.tokens.TokenType;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Lexer {

    private List<Character> paranetheses = Arrays.asList('(', ')');
    public List<Token> createTokenStreamFromQueryString(String query) {
        List<Token> tokenStream = new LinkedList<Token>();
        int i=0;
        if (query == null)
            return tokenStream;
        while (i < query.length()) {
            if (Character.isWhitespace(query.charAt(i))) {
                i++;
            } else {
                int j = i +1;
                if ( !paranetheses.contains( query.charAt(i) ) ) {
                    while (j < query.length() && !Character.isWhitespace(query.charAt(j)) &&
                                                 !paranetheses.contains(query.charAt(j) ) ) {

                        if (ParseUtils.beginQuotingDelemeters.contains(query.charAt(j))) {
                            j++;
                            while (j < query.length() &&
                                    !ParseUtils.endQuotingDelemeters.contains(query.charAt(j))) {
                                j++;
                            }
                            j++;

                        } else j++;
                    }
                }

                if (query.substring(i, j).equals("(") ) {
                    tokenStream.add(new Token(TokenType.LEFT_PARANTHESES, "("));
                } else if (query.substring(i, j).equals(")") ) {
                    tokenStream.add(new Token(TokenType.RIGHT_PARANTHESES, ")"));
                } else if (query.substring(i, j).equals("AND")) {
                    tokenStream.add( new Token(TokenType.AND_OPERATOR,query.substring(i, j)));
                } else if (query.substring(i, j).equals("OR")) {
                    tokenStream.add(new Token(TokenType.OR_OPERATOR, query.substring(i, j)));
                } else if (query.substring(i, j).equals("NOT")) {
                    tokenStream.add(new Token(TokenType.NOT_OPERATOR, query.substring(i,j)));
                } else {
                    tokenStream.add(new Token(TokenType.QUERY_OPERATOR, query.substring(i,j) ));
                }
                i = j;
            }
        }

        return tokenStream;
    }
}











