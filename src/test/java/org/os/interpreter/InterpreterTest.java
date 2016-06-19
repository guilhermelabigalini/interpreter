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
    }
@Test
    public void t003_plusplus_minusminutes() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/t003_plusplus_minusminutes.kpl");
        Value v;

        v = procedure.getVariable("a");
        assertEquals(2, v.getValue());

        v = procedure.getVariable("b");
        assertEquals(9, v.getValue());
    }
    @Test
    public void compile() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/helloworld.kpl");

        Value v = procedure.getVariable("j");

        assertEquals(9, v.getValue());
    }
}
