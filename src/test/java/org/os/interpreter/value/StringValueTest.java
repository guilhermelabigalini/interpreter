/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.os.interpreter.value;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author guilherme
 */
public class StringValueTest {

    @Test
    public void concatenate() throws NotAllowedOperationException {
        StringValue v1 = ValuableFactory.fromString("a");
        StringValue v2 = ValuableFactory.fromString("b");
        Valuable r = v1.sum(v2);

        assertTrue(r instanceof StringValue);
        assertEquals("ab", ((StringValue) r).getString());
    }

    @Test
    public void same_positive() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromString("a").same(ValuableFactory.fromString("a"));

        assertEquals(BooleanValue.True, r);
    }

    @Test
    public void same_negative() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromString("a").same(ValuableFactory.fromString("b"));

        assertEquals(BooleanValue.False, r);
    }

    @Test
    public void smaller_positive() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromString("abc").smaller(ValuableFactory.fromString("def"));

        assertEquals(BooleanValue.True, r);
    }

    @Test
    public void smaller_negative() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromString("def").smaller(ValuableFactory.fromString("abc"));

        assertEquals(BooleanValue.False, r);
    }

    @Test
    public void bigger_positive() throws NotAllowedOperationException {
        Valuable r = ValuableFactory.fromString("def").bigger(ValuableFactory.fromString("abc"));

        assertEquals(BooleanValue.True, r);
    }

    @Test
    public void bigger_negative() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromString("abc").bigger(ValuableFactory.fromString("def"));

        assertEquals(BooleanValue.False, r);
    }

    @Test
    public void smallerOrEqual_positive() throws NotAllowedOperationException {
        Valuable r = ValuableFactory.fromString("def").smallerOrEqual(ValuableFactory.fromString("abc"));

        assertEquals(BooleanValue.False, r);
    }

    @Test
    public void smallerOrEqual_negative() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromString("abc").smallerOrEqual(ValuableFactory.fromString("def"));

        assertEquals(BooleanValue.True, r);
    }

    @Test
    public void biggerOrEqual_positive() throws NotAllowedOperationException {
        Valuable r = ValuableFactory.fromString("def").biggerOrEqual(ValuableFactory.fromString("abc"));

        assertEquals(BooleanValue.True, r);
    }

    @Test
    public void biggerOrEqual_negative() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromString("abc").biggerOrEqual(ValuableFactory.fromString("def"));

        assertEquals(BooleanValue.False, r);
    }

    @Test(expected = NotAllowedOperationException.class)
    public void subtract() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromString("a").subtract(ValuableFactory.fromString("b"));

    }

    @Test(expected = NotAllowedOperationException.class)
    public void divide() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromString("a").divide(ValuableFactory.fromString("b"));

    }

    @Test(expected = NotAllowedOperationException.class)
    public void times() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromString("a").times(ValuableFactory.fromString("b"));

    }

    @Test(expected = NotAllowedOperationException.class)
    public void mod() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromString("a").mod(ValuableFactory.fromString("b"));

    }

    @Test(expected = NotAllowedOperationException.class)
    public void neg() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromString("a").neg();

    }

    @Test(expected = NotAllowedOperationException.class)
    public void not() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromString("a").not();

    }
}
