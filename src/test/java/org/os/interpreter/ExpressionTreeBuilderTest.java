/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.os.interpreter;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
import org.junit.Test;
import static org.junit.Assert.*;
import org.os.interpreter.exptree.ProcExpr;
import org.os.interpreter.exptree.Value;

/**
 *
 * @author guilherme
 */
public class ExpressionTreeBuilderTest {

    private String loadResourceAsString(String fileName) throws IOException {
        try (Scanner scanner = new Scanner(getClass().getClassLoader().getResourceAsStream(fileName))) {
            String contents = scanner.useDelimiter("\\A").next();
            scanner.close();
            return contents;
        }
    }

    private ProcExpr exec(String path) throws Exception {
        String code = loadResourceAsString(path);

        ExpressionTreeBuilder it = new ExpressionTreeBuilder(code);
        Instant tc1 = Instant.now();
        ProcExpr procedure = it.compile();
        Instant tc2 = Instant.now();

        long tr1 = System.currentTimeMillis();
        procedure.Exec();
        long tr2 = System.currentTimeMillis();

        System.out.println("Time for " + path
                + " - compile: " + ChronoUnit.MILLIS.between(tc1, tc2) + "ms,"
                + " run: " + (tr2 - tr1) + "ms");

        return procedure;
    }

    @Test
    public void t001_declare_var() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/t001_declare_var.kpl");
        Value v;

        v = procedure.getPreviousVariable("a");
        assertNull(v.getValue());

        v = procedure.getPreviousVariable("b");
        assertEquals(0, v.getValue());

        v = procedure.getPreviousVariable("c");
        assertEquals(123, v.getValue());

        v = procedure.getPreviousVariable("d");
        assertEquals(0xff, v.getValue());

        v = procedure.getPreviousVariable("e");
        assertEquals(035, v.getValue());
    }

    @Test
    public void t002_math_test() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/t002_math_test.kpl");
        Value v;

        v = procedure.getPreviousVariable("a");
        assertEquals(2, v.getValue());

        v = procedure.getPreviousVariable("b");
        assertEquals(9, v.getValue());

        v = procedure.getPreviousVariable("c");
        assertEquals(12, v.getValue());

        v = procedure.getPreviousVariable("d");
        assertEquals(3, v.getValue());

        v = procedure.getPreviousVariable("e");
        assertEquals(3, v.getValue());

        v = procedure.getPreviousVariable("f");
        assertEquals(8, v.getValue());

        v = procedure.getPreviousVariable("g");
        assertEquals(3, v.getValue());

        v = procedure.getPreviousVariable("h");
        assertEquals(6, v.getValue());

        v = procedure.getPreviousVariable("x");
        assertEquals(1, v.getValue());

        v = procedure.getPreviousVariable("y");
        assertEquals(39, v.getValue());
    }

    @Test
    public void t003_plusplus_minusminutes() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/t003_plusplus_minusminutes.kpl");
        Value v;

        v = procedure.getPreviousVariable("a");
        assertEquals(2, v.getValue());

        v = procedure.getPreviousVariable("b");
        assertEquals(9, v.getValue());

        v = procedure.getPreviousVariable("c");
        assertEquals(5, v.getValue());

        v = procedure.getPreviousVariable("k");
        assertEquals(6, v.getValue());

        v = procedure.getPreviousVariable("m");
        assertEquals(4, v.getValue());

        v = procedure.getPreviousVariable("n");
        assertEquals(4, v.getValue());

        v = procedure.getPreviousVariable("z");
        assertEquals(6, v.getValue());

        v = procedure.getPreviousVariable("x");
        assertEquals(6, v.getValue());

        v = procedure.getPreviousVariable("y");
        assertEquals(7, v.getValue());

        v = procedure.getPreviousVariable("p");
        assertEquals(6, v.getValue());

        v = procedure.getPreviousVariable("o");
        assertEquals(5, v.getValue());
    }

    @Test
    public void t004_math_expr() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/t004_math_expr.kpl");
        Value v;

        v = procedure.getPreviousVariable("a");
        assertEquals(7, v.getValue());

        v = procedure.getPreviousVariable("b");
        assertEquals(8, v.getValue());

        v = procedure.getPreviousVariable("c");
        assertEquals(12, v.getValue());

        v = procedure.getPreviousVariable("d");
        assertEquals(27, v.getValue());

        v = procedure.getPreviousVariable("e");
        assertEquals(7, v.getValue());

        v = procedure.getPreviousVariable("f");
        assertEquals(-1, v.getValue());

        v = procedure.getPreviousVariable("g");
        assertEquals(-9, v.getValue());
    }

    @Test
    public void t005_boolean_operators() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/t005_boolean_operators.kpl");
        Value v;

        v = procedure.getPreviousVariable("b1");
        assertEquals(true, v.getValue());

        v = procedure.getPreviousVariable("b2");
        assertEquals(false, v.getValue());

        v = procedure.getPreviousVariable("b3");
        assertEquals(false, v.getValue());

        v = procedure.getPreviousVariable("b4");
        assertEquals(true, v.getValue());

        v = procedure.getPreviousVariable("b5");
        assertEquals(false, v.getValue());

        v = procedure.getPreviousVariable("b6");
        assertEquals(true, v.getValue());

        v = procedure.getPreviousVariable("b7");
        assertEquals(true, v.getValue());

        v = procedure.getPreviousVariable("b8");
        assertEquals(true, v.getValue());

        v = procedure.getPreviousVariable("b9");
        assertEquals(false, v.getValue());

        v = procedure.getPreviousVariable("b10");
        assertEquals(true, v.getValue());

        v = procedure.getPreviousVariable("b11");
        assertEquals(false, v.getValue());

        v = procedure.getPreviousVariable("b12");
        assertEquals(true, v.getValue());

        v = procedure.getPreviousVariable("c1");
        assertEquals(true, v.getValue());

        v = procedure.getPreviousVariable("c2");
        assertEquals(false, v.getValue());
    }

    @Test
    public void t006_boolean_expr() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/t006_boolean_expr.kpl");
        Value v;

        v = procedure.getPreviousVariable("b1");
        assertEquals(false, v.getValue());

        v = procedure.getPreviousVariable("b2");
        assertEquals(true, v.getValue());

        v = procedure.getPreviousVariable("b3");
        assertEquals(false, v.getValue());

        v = procedure.getPreviousVariable("b4");
        assertEquals(true, v.getValue());

        v = procedure.getPreviousVariable("b5");
        assertEquals(true, v.getValue());

        v = procedure.getPreviousVariable("b6");
        assertEquals(false, v.getValue());

        v = procedure.getPreviousVariable("b7");
        assertEquals(true, v.getValue());
    }

    @Test
    public void t007_if() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/t007_if.kpl");
        Value v;

        v = procedure.getPreviousVariable("a");
        assertEquals(1, v.getValue());
    }

    @Test
    public void t008_if_else() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/t008_if_else.kpl");
        Value v;

        v = procedure.getPreviousVariable("a");
        assertEquals(1, v.getValue());

        v = procedure.getPreviousVariable("b");
        assertEquals(2, v.getValue());
    }

    @Test
    public void t009_if_elseif_else() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/t009_if_elseif_else.kpl");
        Value v;

        v = procedure.getPreviousVariable("a");
        assertEquals(3, v.getValue());
    }

    @Test
    public void t010_while() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/t010_while.kpl");
        Value v;

        v = procedure.getPreviousVariable("a");
        assertEquals(0, v.getValue());

        v = procedure.getPreviousVariable("r");
        assertEquals(10, v.getValue());
    }

    @Test
    public void t011_while_break() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/t011_while_break.kpl");
        Value v;

        v = procedure.getPreviousVariable("a");
        assertEquals(5, v.getValue());

        v = procedure.getPreviousVariable("r");
        assertEquals(4, v.getValue());
    }

    @Test
    public void t012_while_continue() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/t012_while_continue.kpl");
        Value v;

        v = procedure.getPreviousVariable("a");
        assertEquals(0, v.getValue());

        v = procedure.getPreviousVariable("r");
        assertEquals(5, v.getValue());
    }

    @Test
    public void t013_dowhile() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/t013_dowhile.kpl");
        Value v;

        v = procedure.getPreviousVariable("a");
        assertEquals(0, v.getValue());

        v = procedure.getPreviousVariable("r");
        assertEquals(15, v.getValue());
    }

    @Test
    public void t014_dowhile_break() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/t014_dowhile_break.kpl");
        Value v;

        v = procedure.getPreviousVariable("a");
        assertEquals(6, v.getValue());

        v = procedure.getPreviousVariable("r");
        assertEquals(3, v.getValue());
    }

    @Test
    public void t015_dowhile_continue() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/t015_dowhile_continue.kpl");
        Value v;

        v = procedure.getPreviousVariable("a");
        assertEquals(0, v.getValue());

        v = procedure.getPreviousVariable("r");
        assertEquals(5, v.getValue());
    }

    @Test
    public void t016_for() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/t016_for.kpl");
        Value v;

        v = procedure.getPreviousVariable("n");
        assertEquals(5, v.getValue());

        v = procedure.getPreviousVariable("r");
        assertEquals(120, v.getValue());
    }

    @Test
    public void t017_for_break() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/t017_for_break.kpl");
        Value v;

        v = procedure.getPreviousVariable("i");
        assertEquals(5, v.getValue());

        v = procedure.getPreviousVariable("r");
        assertEquals(4, v.getValue());
    }

    @Test
    public void t018_for_continue() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/t018_for_continue.kpl");
        Value v;

        v = procedure.getPreviousVariable("i");
        assertEquals(11, v.getValue());

        v = procedure.getPreviousVariable("r");
        assertEquals(5, v.getValue());
    }

    @Test
    public void t019_swtich() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/t019_swtich.kpl");
        Value v;

        v = procedure.getPreviousVariable("r");
        assertEquals(2, v.getValue());

        v = procedure.getPreviousVariable("v");
        assertEquals("abc", v.getValue());
    }

    @Test
    public void t020_swtich_and_continue() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/t020_swtich_and_continue.kpl");
        Value v;

        v = procedure.getPreviousVariable("r");
        assertEquals(5, v.getValue());
    }

    @Test
    public void t021_swtich_and_force_continue() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/t021_swtich_and_force_continue.kpl");
        Value v;

        v = procedure.getPreviousVariable("r");
        assertEquals(5, v.getValue());

        v = procedure.getPreviousVariable("x");
        assertEquals(0, v.getValue());
    }

    @Test
    public void t022_swtich_default() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/t022_swtich_default.kpl");
        Value v;

        v = procedure.getPreviousVariable("r");
        assertEquals(3, v.getValue());
    }

    @Test
    public void t023_swtich_default_case() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/t023_swtich_default_case.kpl");
        Value v;

        v = procedure.getPreviousVariable("r");
        assertEquals(5, v.getValue());
    }

    @Test
    public void t024_swtich_multiple_cases() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/t024_swtich_multiple_cases.kpl");
        Value v;

        v = procedure.getPreviousVariable("r");
        assertEquals(2, v.getValue());
    }

    @Test
    public void t025_function() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/t025_function.kpl");
        Value v;

        v = procedure.getPreviousVariable("x");
        assertEquals(3, v.getValue());

        v = procedure.getPreviousVariable("y");
        assertEquals(4, v.getValue());
    }

    @Test
    public void t026_function_uses_function() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/t026_function_uses_function.kpl");
        Value v;

        v = procedure.getPreviousVariable("x");
        assertEquals(7, v.getValue());

        v = procedure.getPreviousVariable("y");
        assertEquals(14, v.getValue());

        v = procedure.getPreviousVariable("z");
        assertEquals(36, v.getValue());
    }

    @Test
    public void t027_function_recursive() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/t027_function_recursive.kpl");
        Value v;

        v = procedure.getPreviousVariable("x");
        assertEquals(720, v.getValue());
    }

    @Test
    public void t028_type_system() throws Exception, ParseException {
        ProcExpr procedure = exec("samples/t028_type_system.kpl");
        Value v;

        v = procedure.getPreviousVariable("z");
        assertEquals("abc2", v.getValue());

        v = procedure.getPreviousVariable("y");
        assertEquals(8.6f, (float) v.getValue(), 0.001f);

        v = procedure.getPreviousVariable("x");
        assertEquals("abc8.6", v.getValue());

        v = procedure.getPreviousVariable("k");
        assertEquals("concatenateabc2", v.getValue());
    }

    @Test(expected = org.os.interpreter.value.NotAllowedOperationException.class)
    public void t029_type_system_not_allowed() throws Exception, ParseException {
        exec("samples/t029_type_system_not_allowed.kpl");
    }
}
