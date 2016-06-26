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
public class BooleanValueTest {

    @Test(expected = NotAllowedOperationException.class)
    public void sum() throws NotAllowedOperationException {

        Valuable r = BooleanValue.True.sum(BooleanValue.True);

        assertTrue(r instanceof StringValue);
        assertEquals("ab", ((StringValue) r).getString());
    }
    
    @Test
    public void notsame_positive() throws NotAllowedOperationException {

        Valuable r = BooleanValue.True.notsame(BooleanValue.True);

        assertEquals(BooleanValue.False, r);
    }

    @Test
    public void notsame_negative() throws NotAllowedOperationException {

        Valuable r = BooleanValue.True.notsame(BooleanValue.False);

        assertEquals(BooleanValue.True, r);
    }
    
    @Test
    public void same_positive() throws NotAllowedOperationException {

        Valuable r = BooleanValue.True.same(BooleanValue.True);

        assertEquals(BooleanValue.True, r);
    }

    @Test
    public void same_negative() throws NotAllowedOperationException {

        Valuable r = BooleanValue.True.same(BooleanValue.False);

        assertEquals(BooleanValue.False, r);
    }

    @Test(expected = NotAllowedOperationException.class)
    public void smaller_positive() throws NotAllowedOperationException {

        Valuable r = BooleanValue.True.smaller(BooleanValue.False);
    }

    @Test(expected = NotAllowedOperationException.class)
    public void bigger() throws NotAllowedOperationException {
        Valuable r = BooleanValue.True.bigger(BooleanValue.False);
    }

    @Test(expected = NotAllowedOperationException.class)
    public void smallerOrEqual() throws NotAllowedOperationException {
        Valuable r = BooleanValue.True.smallerOrEqual(BooleanValue.False);
    }

    @Test(expected = NotAllowedOperationException.class)
    public void biggerOrEqual() throws NotAllowedOperationException {
        Valuable r = BooleanValue.True.biggerOrEqual(BooleanValue.False);
    }

    @Test(expected = NotAllowedOperationException.class)
    public void subtract() throws NotAllowedOperationException {
        Valuable r = BooleanValue.True.subtract(BooleanValue.False);
    }

    @Test(expected = NotAllowedOperationException.class)
    public void divide() throws NotAllowedOperationException {
        Valuable r = BooleanValue.True.divide(BooleanValue.False);
    }

    @Test(expected = NotAllowedOperationException.class)
    public void times() throws NotAllowedOperationException {
        Valuable r = BooleanValue.True.times(BooleanValue.False);
    }

    @Test(expected = NotAllowedOperationException.class)
    public void mod() throws NotAllowedOperationException {
        Valuable r = BooleanValue.True.mod(BooleanValue.False);
    }

    @Test(expected = NotAllowedOperationException.class)
    public void neg() throws NotAllowedOperationException {
        Valuable r = BooleanValue.True.neg();
    }

    @Test
    public void neg_true() throws NotAllowedOperationException {
        assertEquals(BooleanValue.True.not(), BooleanValue.False);
    }
    
    @Test
    public void neg_false() throws NotAllowedOperationException {
        assertEquals(BooleanValue.False.not(), BooleanValue.True);
    }
}
