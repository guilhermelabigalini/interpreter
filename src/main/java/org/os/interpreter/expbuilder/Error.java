package org.os.interpreter.expbuilder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author guilherme
 */
public class Error {

    public static final String VARNOTFOUND = "Error (%d): unknown varible: \"s\"."; //passar o nome da variavel
    public static final String FUNCNOTFOUND = "Error (%d): unknown function: \"s\"."; //passar o nome da variavel
    public static final String EVAREXISTS = "Error (%d): re-declared indentifier."; //passar o nome da variavel
    public static final String ESTMNT = "Error (%d): invalid expression.";
    public static final String EVARNAMENTFND = "Error (%d): name for indentifier expected but not found."; //passar o nome da variavel
    public static final String ESEMICOLON = "Error (%d): \";\" expected but not found.";
    public static final String EWHILE = "Error (%d): \"while\" expected but not found.";
    public static final String EREFVAR = "Error (%d): varible in referece parameter but not found.";
    public static final String ECOMMA = "Error (%d): comma (\",\") expected but not found.";
    public static final String EENDPARAM = "Error (%d): Reference parameter need to be followed by \",\" or \")\" but it isn't not found.";
    public static final String EBLEFT = "Error (%d): \"while\" statement must to be followed by \"(\".";
    public static final String EBLEFTFOR = "Error (%d): \"for\" statement must to be followed by \"(\".";
    public static final String EFUNCNOELEFT = "Error (%d): functions must to be followed by \"(\".";
    public static final String ENMBROFPARAMS = "Error (%d): invalid number of parameter for \"s\"."; //passar o nome da funcao
    public static final String EBLEFTIF = "Error (%d): \"if\" statement must to be folowed by \"(\".";
    public static final String EBLEFTSWITCH = "Error (%d): \"switch\" statement must to be folowed by \"(\".";
    public static final String EERRHNDNGOP = "Error (%d): Operator expected but not found.";
    public static final String EOPEXPBLRGHT = "Error (%d): Operator expected but \")\" found.";
    public static final String EOPEXPCOMMA = "Error (%d): Operator expected but \",\" found.";
    public static final String EOPEXP2PNTS = "Error (%d): Operator expected but \":\" found.";
    public static final String ESWITCHBGN = "Error (%d): \"{\" after condition expression expected but found.";
    public static final String ECASESTMNT = "Error (%d): \"case\" or \"default\" expected but found.";
    public static final String EDEFEXISTS = "Error (%d): \"default\" statement already exists.";
    public static final String E2POINTS = "Error (%d): Operator expected but \":\" found.";
    public static final String EENDTRY = "Error (%d): \"catch\" or \"finally\" expected but not found.";
    public static final String ECATCHWITHOUTVAR = "Error (%d): \"catch\" does not have variable definition.";
    public static final String EINVTYPE = "Error (%d): invalid varible type";
    public static final String EPREDEC = "Error (%d): pre-decrement operator is only avaiable for varibles.";
    public static final String EPREINC = "Error (%d): pre-increment operator is only avaiable for varibles.";
    public static final String EPOSDEC = "Error (%d): pos-decrement operator is only avaiable for varibles.";
    public static final String EPOSINC = "Error (%d): pos-increment operator is only avaiable for varibles.";
    public static final String EFUNCINFUNC = "Error (%d): function statement inside function.";
    public static final String EFUNCBLEFT = "Error (%d): left-bracket (\"(\") expected but not found.";
    public static final String EFUNCBRIGHT = "Error (%d): right-bracket (\")\") expected but not found.";
    public static final String EBEGIN = "Error (%d): \"{\" expected but not found.";
    public static final String EFUNCEXISTS = "Error (%d): this function alrealy exists.";
    public static final String EFUNCNAME = "Error (%d): function name expected but not found.";
    public static final String ERETURNFUNC = "Error (%d): return statement must be inside an function.";
    public static final String EINVBREAKPOS = "Error (%d): \"break\" statement must to be inside a loop.";
    public static final String EINVCONTPOS = "Error (%d): \"continue\" statement must to be inside a loop.";

    private final int lineNumber;
    private final String message;

    public Error(int lineNumber, String message) {
        this.lineNumber = lineNumber;
        this.message = message;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return String.format(this.message, this.lineNumber);
    }

}
