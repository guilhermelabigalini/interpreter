/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.os.interpreter.value;

import java.util.function.Function;

/**
 *
 * @author guilherme
 */
public class StringValue implements Valuable {

    private final String string;

    public StringValue(String value) {
        this.string = value;
    }

    public String getString() {
        return string;
    }

    @Override
    public Valuable sum(Valuable v) {
        return new StringValue(this.string + v.toString());
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
    public Valuable neg() throws NotAllowedOperationException {
        throw new NotAllowedOperationException();
    }

    @Override
    public Valuable same(Valuable v) throws NotAllowedOperationException {
        return TryCmdString(v, (cmp) -> cmp == 0);
    }

    @Override
    public Valuable notsame(Valuable v) throws NotAllowedOperationException {
        return TryCmdString(v, (cmp) -> cmp != 0);
    }

    @Override
    public Valuable smaller(Valuable v) throws NotAllowedOperationException {
        return TryCmdString(v, (cmp) -> cmp < 0);
    }

    @Override
    public Valuable bigger(Valuable v) throws NotAllowedOperationException {
        return TryCmdString(v, (cmp) -> cmp > 0);
    }

    @Override
    public Valuable smallerOrEqual(Valuable v) throws NotAllowedOperationException {
        return TryCmdString(v, (cmp) -> cmp <= 0);
    }

    @Override
    public Valuable biggerOrEqual(Valuable v) throws NotAllowedOperationException {
        return TryCmdString(v, (cmp) -> cmp >= 0);
    }

    private Valuable TryCmdString(
            Valuable v,
            Function<Integer, Boolean> checkCmp) {
        if (v instanceof StringValue) {
            String str = ((StringValue) v).string;
            if (checkCmp.apply(this.string.compareTo(str))) {
                return BooleanValue.True;
            }
        }

        return BooleanValue.False;
    }

    @Override
    public String toString() {
        return this.string;
    }

    @Override
    public Valuable not() throws NotAllowedOperationException {
        throw new NotAllowedOperationException();
    }

}
