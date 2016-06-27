/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.os.interpreter.token;

import static java.lang.Character.*;
import org.os.interpreter.expbuilder.Reference;
import static org.os.interpreter.token.CharHelper.*;

/**
 *
 * @author guilherme
 */
public class Tokenizer {

    private final BufferReader reader;
    private int currentLine;

    public Tokenizer(String source) {
        currentLine = 1;
        this.reader = new BufferReader(source);
    }

    public Bookmark GetBookmark() {
        return new Bookmark(this.reader.getPosition(), this.currentLine);
    }

    public void GotoBookmark(Bookmark bm) {
        this.currentLine = bm.getCurrentLine();
        this.reader.setPosition(bm.getPosition());
    }

    public StreamToken GetNextToken() {

        if (reader.eof()) {
            return new StreamToken(Token.ttEof);
        }

        Token t = null;
        Object data = null;

        SkipUnwanted();

        if (reader.eof()) {
            return new StreamToken(Token.ttEof);
        }

        if (isAlphabetic(reader.currentChar())) {
            //we have a varible, reserved word or function
            String name = "";

            do {
                name += reader.currentCharAndMove();

            } while (!reader.eof() && (isAlphabetic(reader.currentChar()) || reader.currentChar() == '_' || isDigit(reader.currentChar())));

            Reference<Object> refValue = new Reference<>();
            t = NameToToken(name, refValue);

            if (t == Token.ttName) {
                data = name;
            } else {
                data = refValue.getValue();
            }
        } else if (isDigit(reader.currentChar()) || reader.currentChar() == '.') {
            //we have a number
            int IntNumber = 0, i = 0;
            float FltNumber = 0.0f;

            t = Token.ttValue;

            if (reader.currentChar() == '0' && reader.nextChar() == 'x') {
                //user wrote a hex number
                int CharValue;

                //skip 'x'
                reader.next();
                reader.next();

                while (!reader.eof() && ishexchar(reader.currentChar())) {
                    if (isDigit(reader.currentChar())) {
                        CharValue = (reader.currentChar() - '0');
                    } else {
                        CharValue = 10 + (toUpperCase(reader.currentChar()) - 'A');
                    }
                    IntNumber = (IntNumber * 16) + CharValue;
                    reader.next();
                }

                return new StreamToken(Token.ttValue, IntNumber);

            } else if (reader.currentChar() == '0' && isDigit(reader.nextChar())) {
                //user wrote a oct number
                reader.next();
                while (!reader.eof() && isoctchar(reader.currentChar())) {
                    IntNumber = (IntNumber * 8) + (reader.currentChar() - '0');
                    reader.next();
                }
                data = IntNumber;
            } else {
                //user wrote a dec number
                while (!reader.eof() && isDigit(reader.currentChar())) {
                    IntNumber = (IntNumber * 10) + (reader.currentChar() - '0');
                    reader.next();
                }
                if (!reader.eof() && reader.currentChar() == '.') {
                    //this is a float number
                    reader.next();
                    while (!reader.eof() && isDigit(reader.currentChar())) {
                        FltNumber = (FltNumber * 10) + (reader.currentChar() - '0');
                        reader.next();
                        i++;
                    }
                    while (i-- > 0) {
                        FltNumber /= 10;
                    }
                    FltNumber += IntNumber;
                    data = FltNumber;
                } else {
                    data = IntNumber;
                }
            }
        } else {
            switch (reader.currentChar()) {
                case '=':
                    t = Token.ttEqual;
                    reader.next();
                    if (reader.currentChar() == '=') {
                        t = Token.ttIsEqual;
                        reader.next();
                    }
                    break;
                case '"': {
                    StringBuilder str = new StringBuilder();
                    int i = 0;

                    while (!reader.eof()) {
                        reader.next();
                        if (reader.currentChar() == '\\') {
                            reader.next();
                            if (!reader.eof()) {
                                switch (toUpperCase(reader.currentChar())) {
                                    case 'N':
                                        str.append('\n');
                                        break;
                                    case 'R':
                                        str.append('\r');
                                        break;
                                    case 'T':
                                        str.append((char) 9);
                                        break;
                                    default:
                                        str.append(reader.currentChar());
                                        break;
                                }
                            }
                        } else if (reader.currentChar() == '"') {
                            reader.next();
                            break;
                        } else {
                            str.append(reader.currentChar());
                        }
                    }

                    t = Token.ttValue;
                    data = str.toString();

                    break;
                }
                case '+':
                    t = Token.ttPlus;
                    reader.next();
                    if (!reader.eof()) {
                        if (reader.currentChar() == '=') {
                            t = Token.ttPlusEqual;
                            reader.next();
                        } else if (reader.currentChar() == '+') {
                            t = Token.ttPlusPlus;
                            reader.next();
                        }
                    }
                    break;
                case '-':
                    t = Token.ttMinus;
                    reader.next();
                    if (reader.currentChar() == '=') {
                        t = Token.ttMinusEqual;
                        reader.next();
                    } else if (reader.currentChar() == '-') {
                        t = Token.ttMinusMinus;
                        reader.next();
                    }
                    break;
                case '&':
                    reader.next();
                    if (reader.currentChar() == '&') {
                        t = Token.ttAnd;
                        reader.next();
                    }
                    break;
                case '|':
                    reader.next();
                    if (reader.currentChar() == '|') {
                        t = Token.ttOr;
                        reader.next();
                    }
                    break;
                case '!':
                    t = Token.ttNot;
                    reader.next();
                    if (reader.currentChar() == '=') {
                        t = Token.ttNotEqual;
                        reader.next();
                    }
                    break;
                case '%':
                    t = Token.ttMod;
                    reader.next();
                    if (reader.currentChar() == '=') {
                        t = Token.ttModEqual;
                        reader.next();
                    }
                    break;
                case '*':
                    t = Token.ttTimes;
                    reader.next();
                    if (reader.currentChar() == '=') {
                        t = Token.ttTimesEqual;
                        reader.next();
                    }
                    break;
                case '/':
                    t = Token.ttDiv;
                    reader.next();
                    if (reader.currentChar() == '=') {
                        t = Token.ttDivEqual;
                        reader.next();
                    }
                    break;
                case ',':
                    t = Token.ttComma;
                    reader.next();
                    break;
                case ';':
                    t = Token.ttSemicolon;
                    reader.next();
                    break;
                case '(':
                    t = Token.ttBLeft;
                    reader.next();
                    break;
                case ')':
                    t = Token.ttBRight;
                    reader.next();
                    break;
                case '>':
                    t = Token.ttBigger;
                    reader.next();
                    if (reader.currentChar() == '=') {
                        t = Token.ttBEqual;
                        reader.next();
                    }
                    break;
                case '<':
                    t = Token.ttSmall;
                    reader.next();
                    if (reader.currentChar() == '=') {
                        t = Token.ttSEqual;
                        reader.next();
                    }
                    break;
                case ':':
                    t = Token.ttTwoPoints;
                    reader.next();
                    break;
                case '{':
                    t = Token.ttBegin;
                    reader.next();
                    break;
                case '}':
                    t = Token.ttEnd;
                    reader.next();
                    break;
            }
        }
        return new StreamToken(t, data);
    }

    private void SkipUnwanted() {
        while (!reader.eof() && ((reader.currentChar() <= ' ')
                || ((reader.currentChar() == '/') && ((reader.nextChar() == '/') || (reader.nextChar() == '*'))))) {
            do {
                SkipComments();
                SkipSpaces();
            } while ((!reader.eof() && reader.currentChar() == '/') && ((reader.nextChar() == '/') || (reader.nextChar() == '*')));
        }
    }

    private void SkipComments() {
        SkipSpaces();

        if (reader.eof() || reader.currentChar() != '/') {
            return;
        }

        reader.next();

        if (reader.currentChar() == '/') {
            while (!reader.eof() && reader.currentChar() != '\n') {
                reader.next();
            }
            if (!reader.eof() && reader.currentChar() == '\n') {
                CheckLine(reader.currentChar());
                reader.next();
            }
        } else if (reader.currentChar() == '*') {
            do {
                reader.next();
                if (reader.currentChar() == '*') {
                    reader.next();
                    if (reader.currentChar() == '/') {
                        reader.next();
                        reader.next();
                        break;
                    }
                } else {
                    CheckLine(reader.currentChar());
                }
            } while (!reader.eof());
        } else {
            reader.previous();
        }

        SkipSpaces();
    }

    private void SkipSpaces() {
        while (!reader.eof() && reader.currentChar() <= ' ') {
            CheckLine(reader.currentChar());
            reader.next();
        }
    }

    public int getCurrentLine() {
        return currentLine;
    }

    private void CheckLine(char c) {
        if (c == '\n') {
            currentLine++;
        }
    }

    private Token NameToToken(String Name, Reference<Object> data) {

        Name = Name.toUpperCase();

        switch (Name.charAt(0)) {
            case 'B':
                if ("BREAK".equals(Name)) {
                    return Token.ttBreak;
                }
                //if (Name == "BYTE")
                //  return ttByte;
                break;
            case 'C':
                if ("CASE".equals(Name)) {
                    return Token.ttCase;
                }
                if ("CONTINUE".equals(Name)) {
                    return Token.ttContinue;
                }
                if ("CATCH".equals(Name)) {
                    return Token.ttCatch;
                }
                //if (Name == "COMVARIANT")
                //  return ttCOMVariant;
                break;
            case 'D':
                if ("DO".equals(Name)) {
                    return Token.ttDo;
                }
                if ("DEFAULT".equals(Name)) {
                    return Token.ttDefault;
                }
                //if (Name == "DOUBLE")
                //  return ttDouble;
                break;
            case 'E':
                if ("ELSE".equals(Name)) {
                    return Token.ttElse;
                }
                break;
            case 'F':
                if ("FALSE".equals(Name)) {
                    data.setValue(false);
                    return Token.ttValue;
                }
                if ("FOR".equals(Name)) {
                    return Token.ttFor;
                }
                if ("FUNCTION".equals(Name)) {
                    return Token.ttFunction;
                }
                if ("FINALLY".equals(Name)) {
                    return Token.ttFinally;
                }
                //if (Name == "FLOAT")
                //  return ttFloat;
                break;
            case 'I':
                if ("IF".equals(Name)) {
                    return Token.ttIf;
                }
                //if (Name == "INT")
                //  return ttInt;
                break;
            //case 'L':
            //  if (Name == "LONG")
            //    return ttIf;
            //  break;
            case 'R':
                if ("RETURN".equals(Name)) {
                    return Token.ttReturn;
                }
                break;
            case 'S':
                if ("SWITCH".equals(Name)) {
                    return Token.ttSwitch;
                }
                //if (Name == "SHORT")
                //  return ttShort;
                //if (Name == "STRING")
                //  return ttString;
                break;
            case 'T':
                if ("THROW".equals(Name)) {
                    return Token.ttThrow;
                }
                if ("TRUE".equals(Name)) {
                    data.setValue(true);
                    return Token.ttValue;
                }
                if ("TRY".equals(Name)) {
                    return Token.ttTry;
                }
                break;
            case 'N':
                if ("NULL".equals(Name)) {
                    data.setValue(null);
                    return Token.ttValue;
                }
                break;
            //case 'U':
            //  if (Name == "UINT")
            //    return ttUInt;
            // if (Name == "USHORT")
            //    return ttUShort;
            //  if (Name == "ULONG")
            //    return ttULong;
            //  break;
            case 'V':
                if ("VAR".equals(Name)) {
                    return Token.ttVar;
                }
                break;
            case 'W':
                if ("WHILE".equals(Name)) {
                    return Token.ttWhile;
                }
                break;
        }
        return Token.ttName;
    }
}
