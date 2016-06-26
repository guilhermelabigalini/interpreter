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
public class FloatValueTest {

    @Test
    public void sum_int() throws NotAllowedOperationException {
        Valuable v1 = ValuableFactory.fromFloat(1.1f);
        Valuable v2 = ValuableFactory.fromInt(2);
        Valuable r = v1.sum(v2);

        assertTrue(r instanceof FloatValue);
        assertEquals(3.1f, ((FloatValue) r).getFloatValue(), 0.0f);
    }

    @Test
    public void sum_float() throws NotAllowedOperationException {
        Valuable v1 = ValuableFactory.fromFloat(1.1f);
        Valuable v2 = ValuableFactory.fromFloat(2.5f);
        Valuable r = v1.sum(v2);

        assertTrue(r instanceof FloatValue);
        assertEquals(3.6, ((FloatValue) r).getFloatValue(), 0.002f);
    }

    @Test
    public void sum_str() throws NotAllowedOperationException {
        Valuable v1 = ValuableFactory.fromFloat(1.5f);
        Valuable v2 = ValuableFactory.fromString("abc");
        Valuable r = v1.sum(v2);

        assertTrue(r instanceof StringValue);
        assertEquals("1.5abc", r.toString());
    }

    @Test
    public void same_positive() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromFloat(1).same(ValuableFactory.fromFloat(1));

        assertEquals(BooleanValue.True, r);
    }

    @Test
    public void same_negative() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromFloat(1).same(ValuableFactory.fromFloat(2));

        assertEquals(BooleanValue.False, r);
    }

    @Test
    public void notsame_positive() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromFloat(1).notsame(ValuableFactory.fromFloat(1));

        assertEquals(BooleanValue.False, r);
    }

    @Test
    public void notsame_negative() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromFloat(1).notsame(ValuableFactory.fromFloat(2));

        assertEquals(BooleanValue.True, r);
    }

    @Test
    public void same_positive_int() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromFloat(1).same(ValuableFactory.fromInt(1));

        assertEquals(BooleanValue.True, r);
    }

    @Test
    public void same_negative_int() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromFloat(1).same(ValuableFactory.fromInt(2));

        assertEquals(BooleanValue.False, r);
    }

    @Test
    public void smaller_positive() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromFloat(123).smaller(ValuableFactory.fromFloat(456));

        assertEquals(BooleanValue.True, r);
    }

    @Test
    public void smaller_negative() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromFloat(456).smaller(ValuableFactory.fromFloat(123));

        assertEquals(BooleanValue.False, r);
    }

    @Test
    public void smaller_negative_string() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromFloat(123).smaller(ValuableFactory.fromString("123"));

        assertEquals(BooleanValue.False, r);
    }

    @Test
    public void bigger_positive() throws NotAllowedOperationException {
        Valuable r = ValuableFactory.fromFloat(456).bigger(ValuableFactory.fromFloat(123));

        assertEquals(BooleanValue.True, r);
    }

    @Test
    public void bigger_negative() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromFloat(123).bigger(ValuableFactory.fromFloat(456));

        assertEquals(BooleanValue.False, r);
    }

    @Test
    public void smallerOrEqual_positive() throws NotAllowedOperationException {
        Valuable r = ValuableFactory.fromFloat(456).smallerOrEqual(ValuableFactory.fromFloat(123));

        assertEquals(BooleanValue.False, r);
    }

    @Test
    public void smallerOrEqual_negative() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromFloat(123).smallerOrEqual(ValuableFactory.fromFloat(456));

        assertEquals(BooleanValue.True, r);
    }

    @Test
    public void biggerOrEqual_positive() throws NotAllowedOperationException {
        Valuable r = ValuableFactory.fromFloat(456).biggerOrEqual(ValuableFactory.fromFloat(123));

        assertEquals(BooleanValue.True, r);
    }

    @Test
    public void biggerOrEqual_negative() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromFloat(123).biggerOrEqual(ValuableFactory.fromFloat(456));

        assertEquals(BooleanValue.False, r);
    }

    @Test
    public void subtract_int() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromFloat(5).subtract(ValuableFactory.fromInt(2));
        assertTrue(r instanceof FloatValue);
        assertEquals(3.0f, ((FloatValue) r).getFloatValue(), 0.0f);
    }

    @Test
    public void subtract_float() throws NotAllowedOperationException {

        Valuable r = ValuableFactory.fromFloat(5).subtract(ValuableFactory.fromFloat(2));
        assertTrue(r instanceof FloatValue);
        assertEquals(3.0f, ((FloatValue) r).getFloatValue(), 0.0f);
    }

    @Test
    public void divide_int() throws NotAllowedOperationException {
        Valuable r = ValuableFactory.fromFloat(5).divide(ValuableFactory.fromFloat(2));
        assertTrue(r instanceof FloatValue);
        assertEquals(2.5f, ((FloatValue) r).getFloatValue(), 0.0f);
    }

    @Test
    public void divide_float() throws NotAllowedOperationException {
        Valuable r = ValuableFactory.fromFloat(5).divide(ValuableFactory.fromFloat(2));
        assertTrue(r instanceof FloatValue);
        assertEquals(2.5, ((FloatValue) r).getFloatValue(), 0.0);
    }

    @Test
    public void times_int() throws NotAllowedOperationException {
        Valuable r = ValuableFactory.fromFloat(5).times(ValuableFactory.fromInt(2));
        assertTrue(r instanceof FloatValue);
        assertEquals(10.0f, ((FloatValue) r).getFloatValue(), 0.0f);
    }

    @Test
    public void times_float() throws NotAllowedOperationException {
        Valuable r = ValuableFactory.fromFloat(5).times(ValuableFactory.fromFloat(0.5f));
        assertTrue(r instanceof FloatValue);
        assertEquals(2.5, ((FloatValue) r).getFloatValue(), 0);
    }

    @Test
    public void mod_int() throws NotAllowedOperationException {
        Valuable r = ValuableFactory.fromFloat(5).mod(ValuableFactory.fromInt(2));
        assertTrue(r instanceof FloatValue);
        assertEquals(1, ((FloatValue) r).getFloatValue(), 0.0f);
    }

    @Test
    public void mod_float() throws NotAllowedOperationException {
        Valuable r = ValuableFactory.fromFloat(5).mod(ValuableFactory.fromFloat(2.5f));
        assertTrue(r instanceof FloatValue);
        assertEquals(0, ((FloatValue) r).getFloatValue(), 0);
    }

    @Test
    public void neg() throws NotAllowedOperationException {
        Valuable r = ValuableFactory.fromFloat(1).neg();
        assertTrue(r instanceof FloatValue);
        assertEquals(-1.0f, ((FloatValue) r).getFloatValue(), 0.0f);
    }

    @Test(expected = NotAllowedOperationException.class)
    public void not() throws NotAllowedOperationException {
        Valuable r = ValuableFactory.fromFloat(1).not();
    }

    @Test(expected = NotAllowedOperationException.class)
    public void times_string() throws NotAllowedOperationException {
        Valuable r = ValuableFactory.fromFloat(1).times(ValuableFactory.fromString("abc"));
    }
}
