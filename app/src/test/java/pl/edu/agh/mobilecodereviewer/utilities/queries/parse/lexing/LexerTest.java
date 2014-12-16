package pl.edu.agh.mobilecodereviewer.utilities.queries.parse.lexing;

import org.junit.Test;
import pl.edu.agh.mobilecodereviewer.utilities.queries.parse.tokens.Token;
import pl.edu.agh.mobilecodereviewer.utilities.queries.parse.tokens.TokenType;

import java.util.List;

import static org.junit.Assert.*;

public class LexerTest {

    @Test
    public void shouldReturnEmptyListFromNullQueryString() throws Exception {
        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.createTokenStreamFromQueryString(null);
        assertEquals( 0 , tokens.size() );
    }

    @Test
    public void shouldReturnEmptyListFromEmptyQueryString() throws Exception {
        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.createTokenStreamFromQueryString("");
        assertEquals( 0 , tokens.size() );
    }

    @Test
    public void shouldReturnEmptyListFromEmptyWhiteSpacesQueryString() throws Exception {
        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.createTokenStreamFromQueryString("      \t    \t       \t          ");
        assertEquals( 0 , tokens.size() );
    }

    @Test
    public void shouldReturnEmptyListFromOneSearchOperatorQueryString() throws Exception {
        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.createTokenStreamFromQueryString("status:open");
        assertEquals( 1 , tokens.size() );
        assertEquals(new Token(TokenType.QUERY_OPERATOR,  "status:open") , tokens.get(0));
    }

    @Test
    public void shouldReturnEmptyListFromOneSearchWithWhitespacesOperatorQueryString() throws Exception {
        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.createTokenStreamFromQueryString("      status:open          ");
        assertEquals( 1 , tokens.size() );
        assertEquals(new Token(TokenType.QUERY_OPERATOR,  "status:open") , tokens.get(0));
    }

    @Test
    public void shouldReturnEmptyListFromTwoSearchOperatorQueryString() throws Exception {
        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.createTokenStreamFromQueryString("      status:open          status:closed");
        assertEquals( 2 , tokens.size() );
        assertEquals(new Token(TokenType.QUERY_OPERATOR,  "status:open") , tokens.get(0));
        assertEquals(new Token(TokenType.QUERY_OPERATOR,  "status:closed") , tokens.get(1));
    }

    @Test
    public void shouldReturnEmptyListFromOneSearchOperatorWithDelimeterValueQueryString() throws Exception {
        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.createTokenStreamFromQueryString("after:\'2006-01-02   15:04:05.890   -0700\'");
        assertEquals( 1 , tokens.size() );
        assertEquals(new Token(TokenType.QUERY_OPERATOR,  "after:'2006-01-02   15:04:05.890   -0700'") , tokens.get(0));
    }

    @Test
    public void shouldReturnEmptyListFromTwoSearchOperatorWithDelimeterValueTwoManyWhiteSpacesQueryString() throws Exception {
        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.createTokenStreamFromQueryString("after:\'2006-01-02   15:04:05.890\' status:open");
        assertEquals( 2 , tokens.size() );
        assertEquals(new Token(TokenType.QUERY_OPERATOR,  "after:'2006-01-02   15:04:05.890'") , tokens.get(0));
        assertEquals(new Token(TokenType.QUERY_OPERATOR,  "status:open") , tokens.get(1));
    }

    @Test
    public void shouldReturnEmptyListFromOneSearchOperatorWIthParamsQueryString() throws Exception {
        Lexer lexer = new Lexer();
        List<Token> tokens = lexer.createTokenStreamFromQueryString("(status:open)");
        assertEquals( 3 , tokens.size() );
        assertEquals(new Token(TokenType.LEFT_PARANTHESES,  "(") , tokens.get(0));
        assertEquals(new Token(TokenType.QUERY_OPERATOR,  "status:open") , tokens.get(1));
        assertEquals(new Token(TokenType.RIGHT_PARANTHESES,  ")") , tokens.get(2));
    }

    @Test
    public void shouldReturnEmptyListFromOneSearchOperatorWIthParamsAndDelimetersQueryString() throws Exception {
        Lexer lexer = new Lexer();
        List<Token> tokens =
            lexer.createTokenStreamFromQueryString("(after:\"2006-01-02   15:04:05.890\") status:open (before:{2006 -01 -02})");
        assertEquals( 7 , tokens.size() );
        assertEquals(new Token(TokenType.LEFT_PARANTHESES,  "(") , tokens.get(0));
        assertEquals(new Token(TokenType.QUERY_OPERATOR,  "after:\"2006-01-02   15:04:05.890\"") , tokens.get(1));
        assertEquals(new Token(TokenType.RIGHT_PARANTHESES,  ")") , tokens.get(2));
        assertEquals(new Token(TokenType.QUERY_OPERATOR,  "status:open") , tokens.get(3));
        assertEquals(new Token(TokenType.LEFT_PARANTHESES,  "(") , tokens.get(4));
        assertEquals(new Token(TokenType.QUERY_OPERATOR,  "before:{2006 -01 -02}") , tokens.get(5));
        assertEquals(new Token(TokenType.RIGHT_PARANTHESES,  ")") , tokens.get(6));
    }

    @Test
    public void shouldReturnEmptyListFromOneSearchOperatorWIthBooleanAndOPAndDelimetersQueryString() throws Exception {
        Lexer lexer = new Lexer();
        List<Token> tokens =
                lexer.createTokenStreamFromQueryString("status:open AND status:closed");
        assertEquals( 3 , tokens.size() );
        assertEquals(new Token(TokenType.QUERY_OPERATOR,  "status:open") , tokens.get(0));
        assertEquals(new Token(TokenType.AND_OPERATOR,  "AND") , tokens.get(1));
        assertEquals(new Token(TokenType.QUERY_OPERATOR,  "status:closed") , tokens.get(2));

    }

    @Test
    public void shouldReturnEmptyListFromOneSearchOperatorWIthBooleanOrOPAndDelimetersQueryString() throws Exception {
        Lexer lexer = new Lexer();
        List<Token> tokens =
                lexer.createTokenStreamFromQueryString("status:open OR status:closed");
        assertEquals( 3 , tokens.size() );
        assertEquals(new Token(TokenType.QUERY_OPERATOR,  "status:open") , tokens.get(0));
        assertEquals(new Token(TokenType.OR_OPERATOR,  "OR") , tokens.get(1));
        assertEquals(new Token(TokenType.QUERY_OPERATOR,  "status:closed") , tokens.get(2));
    }

    @Test
    public void shouldReturnEmptyListFromOneSearchOperatorWIthParanthessAndBooleanOrOPAndDelimetersQueryString() throws Exception {
        Lexer lexer = new Lexer();
        List<Token> tokens =
                lexer.createTokenStreamFromQueryString(
                        "   (status:open OR status:closed) AND    (age:'15 s'   OR   (added:' > 30 ' OR added:'<5'))   "
                );
        assertEquals( 15 , tokens.size() );
        assertEquals(new Token(TokenType.LEFT_PARANTHESES,  "(") , tokens.get(0));
        assertEquals(new Token(TokenType.QUERY_OPERATOR,  "status:open") , tokens.get(1));
        assertEquals(new Token(TokenType.OR_OPERATOR,  "OR") , tokens.get(2));
        assertEquals(new Token(TokenType.QUERY_OPERATOR,  "status:closed") , tokens.get(3));
        assertEquals(new Token(TokenType.RIGHT_PARANTHESES,  ")") , tokens.get(4));
        assertEquals(new Token(TokenType.AND_OPERATOR,  "AND") , tokens.get(5));
        assertEquals(new Token(TokenType.LEFT_PARANTHESES,  "(") , tokens.get(6));
        assertEquals(new Token(TokenType.QUERY_OPERATOR,  "age:'15 s'") , tokens.get(7));
        assertEquals(new Token(TokenType.OR_OPERATOR,  "OR") , tokens.get(8));
        assertEquals(new Token(TokenType.LEFT_PARANTHESES,  "(") , tokens.get(9));
        assertEquals(new Token(TokenType.QUERY_OPERATOR, "added:' > 30 '"), tokens.get(10));
        assertEquals(new Token(TokenType.OR_OPERATOR,  "OR") , tokens.get(11));
        assertEquals(new Token(TokenType.QUERY_OPERATOR, "added:'<5'"), tokens.get(12));
        assertEquals(new Token(TokenType.RIGHT_PARANTHESES,  ")") , tokens.get(13));
        assertEquals(new Token(TokenType.RIGHT_PARANTHESES,  ")") , tokens.get(14));
    }




}
















