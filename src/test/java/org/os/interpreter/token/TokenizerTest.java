/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.os.interpreter.token;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author guilherme
 */
public class TokenizerTest {

    private StreamToken[] getTokens(String source) {
        List<StreamToken> result = new ArrayList<>();
        Tokenizer tknzer = new Tokenizer(source);
        StreamToken st;
        do {
            st = tknzer.GetNextToken();
            result.add(st);
        } while (st.getToken() != Token.ttEof);

        return result.toArray(new StreamToken[result.size()]);
    }

    @Test
    public void parse_simboy() {
        StreamToken[] tokens = getTokens("+");

        assertEquals(tokens[0].getToken(), Token.ttPlus);
        assertEquals(tokens[1].getToken(), Token.ttEof);
    }

    @Test
    public void parse_string() {
        StreamToken[] tokens = getTokens("\"hello\"");

        assertEquals(tokens[0].getToken(), Token.ttValue);
        assertEquals(tokens[0].getData(), "hello");
        assertEquals(tokens[1].getToken(), Token.ttEof);
    }

    @Test
    public void parse_comments_one_line_integer() {
        StreamToken[] tokens = getTokens(
                " // my comments \n 123");

        assertEquals(tokens[0].getToken(), Token.ttValue);
        assertEquals(tokens[0].getData(), 123);
        assertEquals(tokens[1].getToken(), Token.ttEof);
    }

    @Test
    public void parse_comments_true_false_null() {
        StreamToken[] tokens = getTokens(" true false null ");

        assertEquals(tokens[0].getToken(), Token.ttValue);
        assertEquals(tokens[0].getData(), true);
        assertEquals(tokens[1].getToken(), Token.ttValue);
        assertEquals(tokens[1].getData(), false);
        assertEquals(tokens[2].getToken(), Token.ttValue);
        assertEquals(tokens[2].getData(), null);
    }

    @Test
    public void parse_comments_integer() {
        StreamToken[] tokens = getTokens(" /* my comments */ 123");

        assertEquals(tokens[0].getToken(), Token.ttValue);
        assertEquals(tokens[0].getData(), 123);
        assertEquals(tokens[1].getToken(), Token.ttEof);
    }

    @Test
    public void parse_integer_spaces() {
        StreamToken[] tokens = getTokens("   123   ");

        assertEquals(tokens[0].getToken(), Token.ttValue);
        assertEquals(tokens[0].getData(), 123);
        assertEquals(tokens[1].getToken(), Token.ttEof);
    }

    @Test
    public void parse_integer() {
        StreamToken[] tokens = getTokens("123");

        assertEquals(tokens[0].getToken(), Token.ttValue);
        assertEquals(tokens[0].getData(), 123);
        assertEquals(tokens[1].getToken(), Token.ttEof);
    }

    @Test
    public void parse_float() {
        StreamToken[] tokens = getTokens("123.45");

        assertEquals(tokens[0].getToken(), Token.ttValue);
        assertEquals(tokens[0].getData(), 123.45f);
        assertEquals(tokens[1].getToken(), Token.ttEof);
    }

    @Test
    public void parse_oct() {
        StreamToken[] tokens = getTokens("0123");

        assertEquals(tokens[0].getToken(), Token.ttValue);
        assertEquals(tokens[0].getData(), 0123);
        assertEquals(tokens[1].getToken(), Token.ttEof);
    }

    @Test
    public void parse_hex() {
        StreamToken[] tokens = getTokens("0xFF 0x123 ");

        assertEquals(tokens[0].getToken(), Token.ttValue);
        assertEquals(tokens[0].getData(), 0xFF);

        assertEquals(tokens[1].getToken(), Token.ttValue);
        assertEquals(tokens[1].getData(), 0x123);

        assertEquals(tokens[2].getToken(), Token.ttEof);
    }

    @Test
    public void parse_hex_lc() {
        StreamToken[] tokens = getTokens("0xff");

        assertEquals(tokens[0].getToken(), Token.ttValue);
        assertEquals(tokens[0].getData(), 0xFF);
        assertEquals(tokens[1].getToken(), Token.ttEof);
    }

    @Test
    public void parse_word() {
        StreamToken[] tokens = getTokens("hello world");

        assertEquals(tokens[0].getToken(), Token.ttName);
        assertEquals(tokens[0].getData(), "hello");
        assertEquals(tokens[1].getToken(), Token.ttName);
        assertEquals(tokens[1].getData(), "world");
    }

    @Test
    public void parse_keyword() {
        StreamToken[] tokens = getTokens("BREAK  CASE CONTINUE CATCH DO DEFAULT ELSE FOR FUNCTION FINALLY IF RETURN SWITCH THROW TRY VAR WHILE ");
        Token[] tks = new Token[]{
            Token.ttBreak, Token.ttCase,
            Token.ttContinue, Token.ttCatch, Token.ttDo, Token.ttDefault,
            Token.ttElse, Token.ttFor, Token.ttFunction, Token.ttFinally,
            Token.ttIf, Token.ttReturn, Token.ttSwitch, Token.ttThrow, Token.ttTry,
            Token.ttVar, Token.ttWhile, Token.ttEof
        };

        assertEquals(tokens.length, tks.length);
        for (int i = 0; i < tokens.length; i++) {
            assertEquals(tokens[i].getToken(), tks[i]);
        }
    }

    @Test
    public void parse_symbols() {
        StreamToken[] tokens = getTokens(" ++ -- -= += = == && || ! != % %= * *= / /= , ; ( ) > >= < <= : { } ");

        assertEquals(tokens[0].getToken(), Token.ttPlusPlus);
        assertEquals(tokens[1].getToken(), Token.ttMinusMinus);
        assertEquals(tokens[2].getToken(), Token.ttMinusEqual);
        assertEquals(tokens[3].getToken(), Token.ttPlusEqual);
        assertEquals(tokens[4].getToken(), Token.ttEqual);
        assertEquals(tokens[5].getToken(), Token.ttIsEqual);
        assertEquals(tokens[6].getToken(), Token.ttAnd);
        assertEquals(tokens[7].getToken(), Token.ttOr);
        assertEquals(tokens[8].getToken(), Token.ttNot);
        assertEquals(tokens[9].getToken(), Token.ttNotEqual);
        assertEquals(tokens[10].getToken(), Token.ttMod);
        assertEquals(tokens[11].getToken(), Token.ttModEqual);
        assertEquals(tokens[12].getToken(), Token.ttTimes);
        assertEquals(tokens[13].getToken(), Token.ttTimesEqual);
        assertEquals(tokens[14].getToken(), Token.ttDiv);
        assertEquals(tokens[15].getToken(), Token.ttDivEqual);
        assertEquals(tokens[16].getToken(), Token.ttComma);
        assertEquals(tokens[17].getToken(), Token.ttSemicolon);
        assertEquals(tokens[18].getToken(), Token.ttBLeft);
        assertEquals(tokens[19].getToken(), Token.ttBRight);
        assertEquals(tokens[20].getToken(), Token.ttBigger);
        assertEquals(tokens[21].getToken(), Token.ttBEqual);
        assertEquals(tokens[22].getToken(), Token.ttSmall);
        assertEquals(tokens[23].getToken(), Token.ttSEqual);
        assertEquals(tokens[24].getToken(), Token.ttTwoPoints);
        assertEquals(tokens[25].getToken(), Token.ttBegin);
        assertEquals(tokens[26].getToken(), Token.ttEnd);
    }
}
