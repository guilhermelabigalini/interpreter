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
public class IntValueTest {

    @Test
    public void sum_int() throws NotAllowedOperationException {
        Valuable v1 = ValuableFactory.fromInt(1);
        Valuable v2 = ValuableFactory.fromInt(2);
        Valuable r = v1.sum(v2);

        assertTrue(r instanceof IntValue);
        assertEquals(3, ((IntValue) r).getInt());
    }

    @Test
    public void sum_float() throws NotAllowedOperationException {
        Valuable v1 = ValuableFactory.fromInt(1);
        Valuable v2 = ValuableFactory.fromFloat(2.5f);
        Valuable r = v1.sum(v2);

        assertTrue(r instanceof FloatValue);
        assertEquals(3.5, ((FloatValue) r).getFloatValue(), 0);
    }

    @Test
    public void sum_str() throws NotAllowedOperationException {
        Valuable v1 = ValuableFactory.fromInt(1);
        Valuable v2 = ValuableFactory.fromString("abc");
        Valuable r = v1.sum(v2);

        assertTrue(r instanceof StringValue);
        assertEquals("1abc", r.toString());
    }

    @Test
    public void notsame_positive() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromInt(1).notsame(ValuableFactory.fromInt(1));

        assertEquals(BooleanValue.False, r);
    }

    @Test
    public void notsame_negative() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromInt(1).notsame(ValuableFactory.fromInt(2));

        assertEquals(BooleanValue.True, r);
    }

    @Test
    public void same_positive() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromInt(1).same(ValuableFactory.fromInt(1));

        assertEquals(BooleanValue.True, r);
    }

    @Test
    public void same_negative() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromInt(1).same(ValuableFactory.fromInt(2));

        assertEquals(BooleanValue.False, r);
    }

    @Test
    public void same_positive_float() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromInt(123).same(ValuableFactory.fromFloat(123));

        assertEquals(BooleanValue.True, r);
    }

    @Test
    public void same_negative_float() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromInt(123).same(ValuableFactory.fromFloat(456));

        assertEquals(BooleanValue.False, r);
    }

    @Test
    public void same_negative_string() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromInt(123).same(ValuableFactory.fromString("abc"));

        assertEquals(BooleanValue.False, r);
    }

    @Test
    public void smaller_positive() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromInt(123).smaller(ValuableFactory.fromInt(456));

        assertEquals(BooleanValue.True, r);
    }

    @Test
    public void smaller_negative() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromInt(456).smaller(ValuableFactory.fromInt(123));

        assertEquals(BooleanValue.False, r);
    }

    @Test
    public void bigger_positive() throws NotAllowedOperationException {
        Valuable r = ValuableFactory.fromInt(456).bigger(ValuableFactory.fromInt(123));

        assertEquals(BooleanValue.True, r);
    }

    @Test
    public void bigger_negative() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromInt(123).bigger(ValuableFactory.fromInt(456));

        assertEquals(BooleanValue.False, r);
    }

    @Test
    public void smallerOrEqual_positive() throws NotAllowedOperationException {
        Valuable r = ValuableFactory.fromInt(456).smallerOrEqual(ValuableFactory.fromInt(123));

        assertEquals(BooleanValue.False, r);
    }

    @Test
    public void smallerOrEqual_negative() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromInt(123).smallerOrEqual(ValuableFactory.fromInt(456));

        assertEquals(BooleanValue.True, r);
    }

    @Test
    public void biggerOrEqual_positive() throws NotAllowedOperationException {
        Valuable r = ValuableFactory.fromInt(456).biggerOrEqual(ValuableFactory.fromInt(123));

        assertEquals(BooleanValue.True, r);
    }

    @Test
    public void biggerOrEqual_negative() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromInt(123).biggerOrEqual(ValuableFactory.fromInt(456));

        assertEquals(BooleanValue.False, r);
    }

    @Test
    public void subtract() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromInt(5).subtract(ValuableFactory.fromInt(2));
        assertTrue(r instanceof IntValue);
        assertEquals(3, ((IntValue) r).getInt());
    }

    @Test
    public void divide_int() throws NotAllowedOperationException {
        Valuable r = ValuableFactory.fromInt(5).divide(ValuableFactory.fromInt(2));
        assertTrue(r instanceof IntValue);
        assertEquals(2, ((IntValue) r).getInt());
    }

    @Test
    public void divide_float() throws NotAllowedOperationException {
        Valuable r = ValuableFactory.fromInt(5).divide(ValuableFactory.fromFloat(2));
        assertTrue(r instanceof FloatValue);
        assertEquals(2.5, ((FloatValue) r).getFloatValue(), 0.0);
    }

    @Test
    public void times_int() throws NotAllowedOperationException {
        Valuable r = ValuableFactory.fromInt(5).times(ValuableFactory.fromInt(2));
        assertTrue(r instanceof IntValue);
        assertEquals(10, ((IntValue) r).getInt());
    }

    @Test
    public void times_float() throws NotAllowedOperationException {
        Valuable r = ValuableFactory.fromInt(5).times(ValuableFactory.fromFloat(0.5f));
        assertTrue(r instanceof FloatValue);
        assertEquals(2.5, ((FloatValue) r).getFloatValue(), 0);
    }

    @Test
    public void mod_int() throws NotAllowedOperationException {
        Valuable r = ValuableFactory.fromInt(5).mod(ValuableFactory.fromInt(2));
        assertTrue(r instanceof IntValue);
        assertEquals(1, ((IntValue) r).getInt());
    }

    @Test
    public void mod_float() throws NotAllowedOperationException {
        Valuable r = ValuableFactory.fromInt(5).mod(ValuableFactory.fromFloat(2.5f));
        assertTrue(r instanceof FloatValue);
        assertEquals(0, ((FloatValue) r).getFloatValue(), 0);
    }

    @Test
    public void neg() throws NotAllowedOperationException {
        Valuable r = ValuableFactory.fromInt(1).neg();
        assertTrue(r instanceof IntValue);
        assertEquals(-1, ((IntValue) r).getInt());
    }

    @Test(expected = NotAllowedOperationException.class)
    public void not() throws NotAllowedOperationException {
        Valuable r = ValuableFactory.fromInt(1).not();
    }

    @Test(expected = NotAllowedOperationException.class)
    public void times_string() throws NotAllowedOperationException {
        Valuable r = ValuableFactory.fromInt(1).times(ValuableFactory.fromString("abc"));
    }
}
