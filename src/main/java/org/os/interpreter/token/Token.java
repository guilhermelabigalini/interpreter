/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.os.interpreter.token;

/**
 *
 * @author guilherme
 */
public enum Token {

    ttUnknown,
    ttEqual,
    ttValue,
    ttName,
    ttComma,
    ttSemicolon,
    ttPlus,
    ttMinus,
    ttTimes,
    ttDiv,
    ttBigger,
    ttBEqual,
    ttSmall,
    ttSEqual,
    ttIsEqual,
    ttVar,
    /* ttVarId, */
    ttIf,
    ttElse,
    ttWhile,
    ttDo,
    ttFor,
    ttEof,
    ttBLeft,
    ttBRight,
    ttExpr,
    ttBegin,
    ttEnd,
    ttNot,
    ttMod,
    ttNotEqual,
    ttOr,
    ttAnd,
    ttSwitch,
    ttPlusEqual,
    ttMinusEqual,
    ttTimesEqual,
    ttDivEqual,
    ttModEqual,
    ttCase,
    ttDefault,
    ttTwoPoints,
    ttBreak,
    ttContinue,
    ttTry,
    ttCatch,
    ttFinally,
    ttThrow,
    /* ttInt, ttUInt,
              ttShort, ttUShort, ttLong, ttULong, ttByte, ttFloat, ttDouble,
              ttString, ttCOMVariant , */
    ttMinusMinus,
    ttPlusPlus,
    ttFunction,
    ttReturn,
    ttData;

    public boolean IsOperator() {

        Token token = this;
        
        return ((token == Token.ttPlus) || (token == Token.ttMinus)
                || (token == Token.ttTimes) || (token == Token.ttDiv)
                || (token == Token.ttBigger) || (token == Token.ttBEqual)
                || (token == Token.ttSmall) || (token == Token.ttSEqual)
                || (token == Token.ttIsEqual) || (token == Token.ttMod)
                || (token == Token.ttNotEqual) || (token == Token.ttOr)
                || (token == Token.ttAnd));
    }
}
