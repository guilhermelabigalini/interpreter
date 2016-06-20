/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.os.interpreter;

import java.io.IOException;
import java.util.Scanner;
import org.junit.Test;
import static org.junit.Assert.*;
import org.os.interpreter.exptree.ProcExpr;
import org.os.interpreter.exptree.Value;

/**
 *
 * @author guilherme
 */
public class InterpreterTest {

    private String loadResourceAsString(String fileName) throws IOException {
        try (Scanner scanner = new Scanner(getClass().getClassLoader().getResourceAsStream(fileName))) {
            String contents = scanner.useDelimiter("\\A").next();
            scanner.close();
            return contents;
        }
    }

    private ProcExpr exec(String path) throws Exception {
        String code = loadResourceAsString(path);

        Interpreter it = new Interpreter(code);
        ProcExpr procedure = it.compile();

        procedure.Exec();

        return procedure;
    }

    @Test
    public void t001_declare_var() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/t001_declare_var.kpl");
        Value v;

        v = procedure.getVariable("a");
        assertNull(v.getValue());

        v = procedure.getVariable("b");
        assertEquals(0, v.getValue());

        v = procedure.getVariable("c");
        assertEquals(123, v.getValue());
    }

    @Test
    public void t002_math_test() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/t002_math_test.kpl");
        Value v;

        v = procedure.getVariable("a");
        assertEquals(2, v.getValue());

        v = procedure.getVariable("b");
        assertEquals(9, v.getValue());

        v = procedure.getVariable("c");
        assertEquals(12, v.getValue());

        v = procedure.getVariable("d");
        assertEquals(3, v.getValue());

        v = procedure.getVariable("e");
        assertEquals(3, v.getValue());

        v = procedure.getVariable("f");
        assertEquals(8, v.getValue());

        v = procedure.getVariable("g");
        assertEquals(3, v.getValue());

        v = procedure.getVariable("h");
        assertEquals(6, v.getValue());

        v = procedure.getVariable("x");
        assertEquals(1, v.getValue());

        v = procedure.getVariable("y");
        assertEquals(39, v.getValue());
    }

    @Test
    public void t003_plusplus_minusminutes() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/t003_plusplus_minusminutes.kpl");
        Value v;

        v = procedure.getVariable("a");
        assertEquals(2, v.getValue());

        v = procedure.getVariable("b");
        assertEquals(9, v.getValue());

        v = procedure.getVariable("c");
        assertEquals(5, v.getValue());

        v = procedure.getVariable("k");
        assertEquals(6, v.getValue());

        v = procedure.getVariable("m");
        assertEquals(4, v.getValue());

        v = procedure.getVariable("n");
        assertEquals(4, v.getValue());

        v = procedure.getVariable("z");
        assertEquals(6, v.getValue());

        v = procedure.getVariable("x");
        assertEquals(6, v.getValue());

        v = procedure.getVariable("y");
        assertEquals(7, v.getValue());

        v = procedure.getVariable("p");
        assertEquals(6, v.getValue());

        v = procedure.getVariable("o");
        assertEquals(5, v.getValue());
    }

    @Test
    public void t004_math_expr() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/t004_math_expr.kpl");
        Value v;

        v = procedure.getVariable("a");
        assertEquals(7, v.getValue());

        v = procedure.getVariable("b");
        assertEquals(8, v.getValue());

        v = procedure.getVariable("c");
        assertEquals(12, v.getValue());

        v = procedure.getVariable("d");
        assertEquals(27, v.getValue());

        v = procedure.getVariable("e");
        assertEquals(7, v.getValue());
    }

    @Test
    public void t005_boolean_operators() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/t005_boolean_operators.kpl");
        Value v;

        v = procedure.getVariable("b1");
        assertEquals(true, v.getValue());

        v = procedure.getVariable("b2");
        assertEquals(false, v.getValue());

        v = procedure.getVariable("b3");
        assertEquals(false, v.getValue());

        v = procedure.getVariable("b4");
        assertEquals(true, v.getValue());

        v = procedure.getVariable("b5");
        assertEquals(false, v.getValue());

        v = procedure.getVariable("b6");
        assertEquals(true, v.getValue());

        v = procedure.getVariable("b7");
        assertEquals(true, v.getValue());

        v = procedure.getVariable("b8");
        assertEquals(true, v.getValue());

        v = procedure.getVariable("b9");
        assertEquals(false, v.getValue());

        v = procedure.getVariable("b10");
        assertEquals(true, v.getValue());

        v = procedure.getVariable("b11");
        assertEquals(false, v.getValue());

        v = procedure.getVariable("b12");
        assertEquals(true, v.getValue());

        v = procedure.getVariable("c1");
        assertEquals(true, v.getValue());

        v = procedure.getVariable("c2");
        assertEquals(false, v.getValue());
    }

    @Test
    public void t006_boolean_expr() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/t006_boolean_expr.kpl");
        Value v;

        v = procedure.getVariable("b1");
        assertEquals(false, v.getValue());

        v = procedure.getVariable("b2");
        assertEquals(true, v.getValue());

        v = procedure.getVariable("b3");
        assertEquals(false, v.getValue());

        v = procedure.getVariable("b4");
        assertEquals(true, v.getValue());

        v = procedure.getVariable("b5");
        assertEquals(true, v.getValue());

        v = procedure.getVariable("b6");
        assertEquals(false, v.getValue());
    }

    @Test
    public void t007_if() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/t007_if.kpl");
        Value v;

        v = procedure.getVariable("a");
        assertEquals(1, v.getValue());
    }

    @Test
    public void t008_if_else() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/t008_if_else.kpl");
        Value v;

        v = procedure.getVariable("a");
        assertEquals(1, v.getValue());

        v = procedure.getVariable("b");
        assertEquals(2, v.getValue());
    }

    @Test
    public void t009_if_elseif_else() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/t009_if_elseif_else.kpl");
        Value v;

        v = procedure.getVariable("a");
        assertEquals(3, v.getValue());
    }

    @Test
    public void t010_while() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/t010_while.kpl");
        Value v;

        v = procedure.getVariable("a");
        assertEquals(0, v.getValue());

        v = procedure.getVariable("r");
        assertEquals(10, v.getValue());
    }
//
//    @Test
//    public void compile() throws Exception, ParseException {
//        ProcExpr procedure = exec("samples/helloworld.kpl");
//
//        Value v = procedure.getVariable("j");
//
//        assertEquals(9, v.getValue());
//    }
}
