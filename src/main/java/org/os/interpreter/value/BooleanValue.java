/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.os.interpreter.value;

/**
 *
 * @author guilherme
 */
public class BooleanValue implements Valuable {

    public static final BooleanValue True = new BooleanValue(true);
    public static final BooleanValue False = new BooleanValue(false);

    private final boolean boolValue;

    private BooleanValue(boolean boolValue) {
        this.boolValue = boolValue;
    }

    @Override
    public Valuable sum(Valuable v) throws NotAllowedOperationException {
        throw new NotAllowedOperationException(); 
    }

    @Override
    public Valuable subtract(Valuable v) throws NotAllowedOperationException {
        throw new NotAllowedOperationException(); 
    }

    @Override
    public Valuable times(Valuable v) throws NotAllowedOperationException {
        throw new NotAllowedOperationException(); 
    }

    @Override
    public Valuable divide(Valuable v) throws NotAllowedOperationException {
        throw new NotAllowedOperationException(); 
    }

    @Override
    public Valuable mod(Valuable v) throws NotAllowedOperationException {
        throw new NotAllowedOperationException(); 
    }

    @Override
    public Valuable smaller(Valuable v) throws NotAllowedOperationException {
        throw new NotAllowedOperationException(); 
    }

    @Override
    public Valuable bigger(Valuable v) throws NotAllowedOperationException {
        throw new NotAllowedOperationException(); 
    }

    @Override
    public Valuable smallerOrEqual(Valuable v) throws NotAllowedOperationException {
        throw new NotAllowedOperationException(); 
    }

    @Override
    public Valuable biggerOrEqual(Valuable v) throws NotAllowedOperationException {
        throw new NotAllowedOperationException(); 
    }

    @Override
    public Valuable same(Valuable v) throws NotAllowedOperationException {
        if (this == v) {
            return True;
        } else {
            return False;
        }
    }

    @Override
    public Valuable neg() throws NotAllowedOperationException {
        throw new NotAllowedOperationException(); 
    }

    @Override
    public Valuable not() throws NotAllowedOperationException {
        if (boolValue) {
            return BooleanValue.False;
        } else {
            return BooleanValue.True;
        }
    }

}
