package pl.edu.agh.mobilecodereviewer.utilities.queries.parse;

import org.junit.Test;
import pl.edu.agh.mobilecodereviewer.utilities.queries.parse.lexing.Lexer;
import pl.edu.agh.mobilecodereviewer.utilities.queries.parse.rpn.InfixToRPNConvertor;
import pl.edu.agh.mobilecodereviewer.utilities.queries.parse.tokens.Token;
import pl.edu.agh.mobilecodereviewer.utilities.queries.parse.tokens.TokenType;

import java.util.List;

import static junit.framework.TestCase.assertEquals;


public class ConvertingToRPNFunctionalTest {

    private static final Token AND_OPERATOR = new Token(TokenType.AND_OPERATOR, "AND");
    private static final Token OR_OPERATOR = new Token(TokenType.OR_OPERATOR, "OR");
    private static final Token LEFT_PARAM = new Token(TokenType.LEFT_PARANTHESES, "(");
    private static final Token RIGHT_PARAM = new Token(TokenType.RIGHT_PARANTHESES, ")");

    private Token createSearchQueryToken(String value) {
        return new Token(TokenType.QUERY_OPERATOR, value);
    }

    @Test
    public void shouldProperlyParseTestCase0() throws Exception {
        Lexer lexer = new Lexer();
        List<Token> queryTokens = lexer.createTokenStreamFromQueryString(
                "   status:open         ");
        InfixToRPNConvertor rpnConvertor = new InfixToRPNConvertor();
        rpnConvertor.addMissingJoinANDOperator(queryTokens);
        List<Token> converted = rpnConvertor.convert(queryTokens);
        assertEquals(1, converted.size() );
        assertEquals(createSearchQueryToken("status:open"), converted.get(0) );
    }

    @Test
    public void shouldProperlyParseTestCase0_5() throws Exception {
        Lexer lexer = new Lexer();
        List<Token> queryTokens = lexer.createTokenStreamFromQueryString(
                "status:open");
        InfixToRPNConvertor rpnConvertor = new InfixToRPNConvertor();
        rpnConvertor.addMissingJoinANDOperator(queryTokens);
        List<Token> converted = rpnConvertor.convert(queryTokens);
        assertEquals(1, converted.size() );
        assertEquals(createSearchQueryToken("status:open"), converted.get(0) );
    }

    @Test
    public void shouldProperlyParseTestCase1() throws Exception {
        Lexer lexer = new Lexer();
        List<Token> queryTokens = lexer.createTokenStreamFromQueryString(
                "(status:open OR status:closed AND before:'yesterday') OR (owner:'PI LI' conflicts:'  15  ')");
        InfixToRPNConvertor rpnConvertor = new InfixToRPNConvertor();
        rpnConvertor.addMissingJoinANDOperator(queryTokens);
        List<Token> converted = rpnConvertor.convert(queryTokens);
        assertEquals(9, converted.size() );
        assertEquals(createSearchQueryToken("status:open"), converted.get(0) );
        assertEquals(createSearchQueryToken("status:closed"), converted.get(1) );
        assertEquals(createSearchQueryToken("before:'yesterday'"), converted.get(2) );
        assertEquals(AND_OPERATOR, converted.get(3) );
        assertEquals(OR_OPERATOR, converted.get(4) );
        assertEquals(createSearchQueryToken("owner:'PI LI'"), converted.get(5) );
        assertEquals(createSearchQueryToken("conflicts:'  15  '"), converted.get(6) );
        assertEquals(AND_OPERATOR, converted.get(7) );
        assertEquals(OR_OPERATOR, converted.get(8) );
    }

    @Test
    public void shouldProperlyParseTestCase2() throws Exception {
        Lexer lexer = new Lexer();
        List<Token> queryTokens = lexer.createTokenStreamFromQueryString(
                "(status:open AND ( before:'yesterday' OR owner:'PI LI'    )) conflicts:'  15  '");
        InfixToRPNConvertor rpnConvertor = new InfixToRPNConvertor();
        rpnConvertor.addMissingJoinANDOperator(queryTokens);
        List<Token> converted = rpnConvertor.convert(queryTokens);
        assertEquals(7, converted.size() );
        assertEquals(createSearchQueryToken("status:open"), converted.get(0) );
        assertEquals(createSearchQueryToken("before:'yesterday'"), converted.get(1) );
        assertEquals(createSearchQueryToken("owner:'PI LI'"), converted.get(2) );
        assertEquals(OR_OPERATOR, converted.get(3) );
        assertEquals(AND_OPERATOR, converted.get(4) );
        assertEquals(createSearchQueryToken("conflicts:'  15  '"), converted.get(5) );
        assertEquals(AND_OPERATOR, converted.get(6) );
    }

    @Test
    public void shouldProperlyParseTestCase3() throws Exception {
        Lexer lexer = new Lexer();
        List<Token> queryTokens = lexer.createTokenStreamFromQueryString(
                "  status:open OR before:'yest  erday   '   conflicts:'  15  '   ");
        InfixToRPNConvertor rpnConvertor = new InfixToRPNConvertor();
        rpnConvertor.addMissingJoinANDOperator(queryTokens);
        List<Token> converted = rpnConvertor.convert(queryTokens);
        assertEquals(5, converted.size() );
        assertEquals(createSearchQueryToken("status:open"), converted.get(0) );
        assertEquals(createSearchQueryToken("before:'yest  erday   '"), converted.get(1) );
        assertEquals(createSearchQueryToken("conflicts:'  15  '"), converted.get(2) );
        assertEquals(AND_OPERATOR, converted.get(3) );
        assertEquals(OR_OPERATOR, converted.get(4) );
    }

    @Test
    public void shouldProperlyParseTestCase4() throws Exception {
        Lexer lexer = new Lexer();
        List<Token> queryTokens = lexer.createTokenStreamFromQueryString(
                "  status:open OR before:'yest  erday   '");
        InfixToRPNConvertor rpnConvertor = new InfixToRPNConvertor();
        rpnConvertor.addMissingJoinANDOperator(queryTokens);
        List<Token> converted = rpnConvertor.convert(queryTokens);
        assertEquals(3, converted.size() );
        assertEquals(createSearchQueryToken("status:open"), converted.get(0) );
        assertEquals(createSearchQueryToken("before:'yest  erday   '"), converted.get(1) );
        assertEquals(OR_OPERATOR, converted.get(2) );
    }

    @Test
    public void shouldProperlyParseTestCase5() throws Exception {
        Lexer lexer = new Lexer();
        List<Token> queryTokens = lexer.createTokenStreamFromQueryString(
                "  status:open AND (    before:'yest  erday   ' OR age:'15s'  )");
        InfixToRPNConvertor rpnConvertor = new InfixToRPNConvertor();
        rpnConvertor.addMissingJoinANDOperator(queryTokens);
        List<Token> converted = rpnConvertor.convert(queryTokens);
        assertEquals(5, converted.size() );
        assertEquals(createSearchQueryToken("status:open"), converted.get(0) );
        assertEquals(createSearchQueryToken("before:'yest  erday   '"), converted.get(1) );
        assertEquals(createSearchQueryToken("age:'15s'"), converted.get(2) );
        assertEquals(OR_OPERATOR, converted.get(3) );
        assertEquals(AND_OPERATOR, converted.get(4) );
    }


}



































