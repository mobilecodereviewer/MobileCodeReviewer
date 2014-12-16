package pl.edu.agh.mobilecodereviewer.utilities.queries.parse.rpn;

import org.junit.Test;
import pl.edu.agh.mobilecodereviewer.utilities.queries.parse.tokens.Token;
import pl.edu.agh.mobilecodereviewer.utilities.queries.parse.tokens.TokenType;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class InfixToRPNConvertorTest {

    private static final Token AND_OPERATOR = new Token(TokenType.AND_OPERATOR, "AND");
    private static final Token OR_OPERATOR = new Token(TokenType.OR_OPERATOR, "OR");
    private static final Token LEFT_PARAM = new Token(TokenType.LEFT_PARANTHESES, "(");
    private static final Token RIGHT_PARAM = new Token(TokenType.RIGHT_PARANTHESES, ")");


    @Test
    public void shouldNotAddMissingAndOPForOneOperator() throws Exception {
        InfixToRPNConvertor infixToRPNConvertor = new InfixToRPNConvertor();
        List<Token> tokens = Arrays.asList(new Token(TokenType.QUERY_OPERATOR,"status:open"));
        infixToRPNConvertor.addMissingJoinANDOperator(tokens);
        assertEquals(1,tokens.size());
    }

    @Test
    public void shouldAddMissingAndOPForTwoMissANDOperator() throws Exception {
        InfixToRPNConvertor infixToRPNConvertor = new InfixToRPNConvertor();
        List<Token> tokens = new LinkedList<>(Arrays.asList(new Token(TokenType.QUERY_OPERATOR,"status:open"),
                                                            new Token(TokenType.QUERY_OPERATOR,"status:close"))
                                              );
        infixToRPNConvertor.addMissingJoinANDOperator(tokens);
        assertEquals(3,tokens.size());
        assertEquals(new Token(TokenType.QUERY_OPERATOR,"status:open") ,tokens.get(0));
        assertEquals(AND_OPERATOR,tokens.get(1));
        assertEquals(new Token(TokenType.QUERY_OPERATOR,"status:close") ,tokens.get(2));
    }

    @Test
    public void shouldNotAddMissingAndOPForTwoNOTMissANDOperator() throws Exception {
        InfixToRPNConvertor infixToRPNConvertor = new InfixToRPNConvertor();
        List<Token> tokens = new LinkedList<>(Arrays.asList(new Token(TokenType.QUERY_OPERATOR,"status:open"),
                                                            AND_OPERATOR,
                                                            new Token(TokenType.QUERY_OPERATOR,"status:close"))
        );
        infixToRPNConvertor.addMissingJoinANDOperator(tokens);
        assertEquals(3,tokens.size());
        assertEquals(new Token(TokenType.QUERY_OPERATOR,"status:open") ,tokens.get(0));
        assertEquals(new Token(TokenType.QUERY_OPERATOR,"status:close") ,tokens.get(2));
    }

    @Test
    public void shouldNotAddMissingAndOPForTwoJoinedOROperator() throws Exception {
        InfixToRPNConvertor infixToRPNConvertor = new InfixToRPNConvertor();
        List<Token> tokens = new LinkedList<>(Arrays.asList(new Token(TokenType.QUERY_OPERATOR,"status:open"),
                OR_OPERATOR,
                new Token(TokenType.QUERY_OPERATOR,"status:close"))
        );
        infixToRPNConvertor.addMissingJoinANDOperator(tokens);
        assertEquals(3,tokens.size());
        assertEquals(new Token(TokenType.QUERY_OPERATOR,"status:open") ,tokens.get(0));
        assertEquals(OR_OPERATOR ,tokens.get(1));
        assertEquals(new Token(TokenType.QUERY_OPERATOR,"status:close") ,tokens.get(2));
    }

    @Test
    public void shouldAddMissingAndOPForTwoNotJoinedWithBeforeParamsOperator() throws Exception {
        InfixToRPNConvertor infixToRPNConvertor = new InfixToRPNConvertor();
        List<Token> tokens = new LinkedList<>(Arrays.asList(
                LEFT_PARAM,
                new Token(TokenType.QUERY_OPERATOR,"status:open"),
                RIGHT_PARAM,
                new Token(TokenType.QUERY_OPERATOR,"status:close")
        ));
        infixToRPNConvertor.addMissingJoinANDOperator(tokens);
        assertEquals(5,tokens.size());
        assertEquals(AND_OPERATOR ,tokens.get(3));
        assertEquals(new Token(TokenType.QUERY_OPERATOR,"status:close") ,tokens.get(4));
    }

    @Test
    public void shouldAddMissingAndOPForTwoNotJoinedWithAfterParamsOperator() throws Exception {
        InfixToRPNConvertor infixToRPNConvertor = new InfixToRPNConvertor();
        List<Token> tokens = new LinkedList<>(Arrays.asList(
                new Token(TokenType.QUERY_OPERATOR,"status:close"),
                LEFT_PARAM,
                new Token(TokenType.QUERY_OPERATOR,"status:open"),
                RIGHT_PARAM
        ));
        infixToRPNConvertor.addMissingJoinANDOperator(tokens);
        assertEquals(5,tokens.size());
        assertEquals(new Token(TokenType.QUERY_OPERATOR,"status:close") ,tokens.get(0 ));
        assertEquals(AND_OPERATOR, tokens.get(1));
        assertEquals(LEFT_PARAM,tokens.get(2));
    }

    @Test
    public void shouldNotConvertEmptyTokenList() throws Exception {
        InfixToRPNConvertor infixToRPNConvertor = new InfixToRPNConvertor();
        List<Token> tokenList = new LinkedList<Token>();
        List<Token> convertedToken = infixToRPNConvertor.convert(tokenList);
        assertEquals( new LinkedList<Token>() , convertedToken );
    }

    @Test
    public void shouldNotConvertSingleTokenList() throws Exception {
        InfixToRPNConvertor infixToRPNConvertor = new InfixToRPNConvertor();
        List<Token> tokenList = new LinkedList<>(Arrays.asList(
                new Token(TokenType.QUERY_OPERATOR,"status:close")
        ));
        List<Token> convertedToken = infixToRPNConvertor.convert(tokenList);
        assertEquals(1, convertedToken.size());
        assertEquals(new Token(TokenType.QUERY_OPERATOR,"status:close"), convertedToken.get(0));
    }

    @Test
    public void shouldConvertTwoOperatorConnectedWithBooleanTokenList() throws Exception {
        InfixToRPNConvertor infixToRPNConvertor = new InfixToRPNConvertor();
        List<Token> tokenList = new LinkedList<>(Arrays.asList(
                new Token(TokenType.QUERY_OPERATOR,"status:close"),
                OR_OPERATOR,
                new Token(TokenType.QUERY_OPERATOR,"status:open")
        ));
        List<Token> convertedToken = infixToRPNConvertor.convert(tokenList);
        assertEquals(3, convertedToken.size());
        assertEquals(new Token(TokenType.QUERY_OPERATOR,"status:close"), convertedToken.get(0));
        assertEquals(new Token(TokenType.QUERY_OPERATOR,"status:open"), convertedToken.get(1));
        assertEquals(OR_OPERATOR, convertedToken.get(2));
    }

    @Test
    public void shouldConvertThreeOperatorConnectedWithORANDWithMakingGoodOrderTokenList() throws Exception {
        InfixToRPNConvertor infixToRPNConvertor = new InfixToRPNConvertor();
        List<Token> tokenList = new LinkedList<>(Arrays.asList(
                new Token(TokenType.QUERY_OPERATOR,"status:close"),
                OR_OPERATOR,
                new Token(TokenType.QUERY_OPERATOR,"status:open"),
                AND_OPERATOR,
                new Token(TokenType.QUERY_OPERATOR,"status:merged")
        ));
        List<Token> convertedToken = infixToRPNConvertor.convert(tokenList);
        assertEquals(5, convertedToken.size());
        assertEquals(new Token(TokenType.QUERY_OPERATOR,"status:close"), convertedToken.get(0));
        assertEquals(new Token(TokenType.QUERY_OPERATOR,"status:open"), convertedToken.get(1));
        assertEquals(new Token(TokenType.QUERY_OPERATOR,"status:merged"), convertedToken.get(2));
        assertEquals(AND_OPERATOR, convertedToken.get(3));
        assertEquals(OR_OPERATOR, convertedToken.get(4));
    }

    @Test
    public void shouldConvertThreeOperatorConnectedWithORANDAndParamsWithMakingGoodOrderTokenList() throws Exception {
        InfixToRPNConvertor infixToRPNConvertor = new InfixToRPNConvertor();
        List<Token> tokenList = new LinkedList<>(Arrays.asList(
                LEFT_PARAM,
                new Token(TokenType.QUERY_OPERATOR,"status:close"),
                OR_OPERATOR,
                new Token(TokenType.QUERY_OPERATOR,"status:open"),
                RIGHT_PARAM,
                AND_OPERATOR,
                new Token(TokenType.QUERY_OPERATOR,"status:merged")
        ));
        List<Token> convertedToken = infixToRPNConvertor.convert(tokenList);
        assertEquals(5, convertedToken.size());
        assertEquals(new Token(TokenType.QUERY_OPERATOR,"status:close"), convertedToken.get(0));
        assertEquals(new Token(TokenType.QUERY_OPERATOR,"status:open"), convertedToken.get(1));
        assertEquals(OR_OPERATOR, convertedToken.get(2));
        assertEquals(new Token(TokenType.QUERY_OPERATOR,"status:merged"), convertedToken.get(3));
        assertEquals(AND_OPERATOR, convertedToken.get(4));
    }





}























