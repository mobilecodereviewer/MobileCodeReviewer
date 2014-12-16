package pl.edu.agh.mobilecodereviewer.utilities.queries.parse.rpn;


import pl.edu.agh.mobilecodereviewer.utilities.queries.GerritQueryParseException;
import pl.edu.agh.mobilecodereviewer.utilities.queries.parse.tokens.Token;
import pl.edu.agh.mobilecodereviewer.utilities.queries.parse.tokens.TokenType;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class InfixToRPNConvertor {

    public void addMissingJoinANDOperator(List<Token> tokenStream) {
        for (int i = 0; i < tokenStream.size()-1; i++) {
            if (tokenStream.get(i).getType() == TokenType.QUERY_OPERATOR &&
                    tokenStream.get(i + 1).getType() == TokenType.QUERY_OPERATOR) {
                tokenStream.add(i+1,new Token(TokenType.AND_OPERATOR,"AND"));
            } else if (tokenStream.get(i).getType() == TokenType.QUERY_OPERATOR &&
                    tokenStream.get(i + 1).getType() == TokenType.LEFT_PARANTHESES) {
                tokenStream.add(i+1,new Token(TokenType.AND_OPERATOR,"AND"));
            } else if (tokenStream.get(i).getType() == TokenType.RIGHT_PARANTHESES &&
                    tokenStream.get(i + 1).getType() == TokenType.QUERY_OPERATOR) {
                tokenStream.add(i+1,new Token(TokenType.AND_OPERATOR,"AND"));
            }

        }
    }

    public List<Token> convert(List<Token> tokenStream) {
        List<Token> convertedOutput = new LinkedList<>();
        Stack<Token> operatorsToAdd = new Stack<>();

        for (int i = 0; i < tokenStream.size(); i++) {
            Token currToken = tokenStream.get(i);
            if (currToken.getType() == TokenType.QUERY_OPERATOR) {
                convertedOutput.add(currToken);
            }else if (isCurrentTokenOperator(currToken)) {
                while ( isCurrentTokenHaveHigherPredenceThanTopOfOperatorStack(currToken, operatorsToAdd) ) {
                    convertedOutput.add( operatorsToAdd.pop() );
                }

                operatorsToAdd.add( currToken );
            }else if (currToken.getType() == TokenType.LEFT_PARANTHESES) {
                operatorsToAdd.add(currToken);
            } else if (currToken.getType() == TokenType.RIGHT_PARANTHESES) {
                while (operatorsToAdd.size() > 0 && operatorsToAdd.peek().getType() != TokenType.LEFT_PARANTHESES) {
                    convertedOutput.add( operatorsToAdd.pop() );
                }
                if (operatorsToAdd.size() == 0)
                    throw new GerritQueryParseException("Query parsing error: Parantheses in expression is not valid");
                operatorsToAdd.pop();
            }

        }

        while (operatorsToAdd.size() > 0) {
            if (isCurrentTokenOperator(operatorsToAdd.peek())) {

                convertedOutput.add( operatorsToAdd.pop() );
            } else throw new GerritQueryParseException("Query parsing error: Query string not valid");
        }

        return convertedOutput;
    }

    private boolean isCurrentTokenOperator(Token currToken) {
        return currToken.getType() == TokenType.AND_OPERATOR ||
               currToken.getType() == TokenType.OR_OPERATOR  ||
               currToken.getType() == TokenType.NOT_OPERATOR;
    }

    private boolean isCurrentTokenHaveHigherPredenceThanTopOfOperatorStack(Token currToken, Stack<Token> operatorsToAdd) {
        return operatorsToAdd.size() > 0 &&
                (
                            ( (currToken.getType() == TokenType.OR_OPERATOR &&
                                (operatorsToAdd.peek().getType() == TokenType.AND_OPERATOR) || operatorsToAdd.peek().getType() == TokenType.NOT_OPERATOR) )
                        ||
                            ( (currToken.getType() == TokenType.AND_OPERATOR &&
                                    (operatorsToAdd.peek().getType() == TokenType.NOT_OPERATOR) ) )
                );
    }

}







