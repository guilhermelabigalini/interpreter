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
public class IntValue implements Valuable {

    private final int integer;

    public IntValue(int value) {
        this.integer = value;
    }

    public int getInt() {
        return integer;
    }

    @Override
    public Valuable sum(Valuable v) throws NotAllowedOperationException {
        return TryOperation(v,
                i -> new IntValue(this.integer + i),
                f -> new FloatValue(this.integer + f),
                s -> new StringValue(this.toString() + s));
    }

    @Override
    public Valuable subtract(Valuable v) throws NotAllowedOperationException {
        return TryOperation(v,
                i -> new IntValue(this.integer - i),
                f -> new FloatValue(this.integer - f));
    }

    @Override
    public Valuable times(Valuable v) throws NotAllowedOperationException {
        return TryOperation(v,
                i -> new IntValue(this.integer * i),
                f -> new FloatValue(this.integer * f));
    }

    @Override
    public Valuable divide(Valuable v) throws NotAllowedOperationException {
        return TryOperation(v,
                i -> new IntValue(this.integer / i),
                f -> new FloatValue(this.integer / f));
    }

    @Override
    public Valuable mod(Valuable v) throws NotAllowedOperationException {
        return TryOperation(v,
                i -> new IntValue(this.integer % i),
                f -> new FloatValue(this.integer % f));
    }

    @Override
    public Valuable neg() throws NotAllowedOperationException {
        return new IntValue(-this.integer);
    }

    @Override
    public Valuable same(Valuable v) throws NotAllowedOperationException {
        return TryCmpInteger(v, (cmp) -> cmp == 0);
    }

    @Override
    public Valuable smaller(Valuable v) throws NotAllowedOperationException {
        return TryCmpInteger(v, (cmp) -> cmp < 0);
    }

    @Override
    public Valuable notsame(Valuable v) throws NotAllowedOperationException {
        return TryCmpInteger(v, (cmp) -> cmp != 0);
    }

    @Override
    public Valuable bigger(Valuable v) throws NotAllowedOperationException {
        return TryCmpInteger(v, (cmp) -> cmp > 0);
    }

    @Override
    public Valuable smallerOrEqual(Valuable v) throws NotAllowedOperationException {
        return TryCmpInteger(v, (cmp) -> cmp <= 0);
    }

    @Override
    public Valuable biggerOrEqual(Valuable v) throws NotAllowedOperationException {
        return TryCmpInteger(v, (cmp) -> cmp >= 0);
    }

    private Valuable TryCmpInteger(
            Valuable v,
            Function<Integer, Boolean> checkCmp) {

        if (v instanceof IntValue) {
            int intV = ((IntValue) v).integer;
            if (checkCmp.apply(Integer.compare(this.integer, intV))) {
                return BooleanValue.True;
            } else {
                return BooleanValue.False;
            }
        }

        if (v instanceof FloatValue) {
            float floatV = ((FloatValue) v).getFloatValue();
            if (checkCmp.apply(Float.compare(this.integer, floatV))) {
                return BooleanValue.True;
            } else {
                return BooleanValue.False;
            }
        }

        return BooleanValue.False;
    }

    private Valuable TryOperation(
            Valuable v,
            Function<Integer, Valuable> intOp,
            Function<Float, Valuable> floatOp,
            Function<String, Valuable> strOp) throws NotAllowedOperationException {

        if (v instanceof StringValue) {
            return strOp.apply(v.toString());
        }

        return TryOperation(v, intOp, floatOp);
    }

    private Valuable TryOperation(
            Valuable v,
            Function<Integer, Valuable> intOp,
            Function<Float, Valuable> floatOp) throws NotAllowedOperationException {

        if (v instanceof IntValue) {
            return intOp.apply(((IntValue) v).integer);
        }

        if (v instanceof FloatValue) {
            return floatOp.apply(((FloatValue) v).getFloatValue());
        }

        // Math operatorons are not supported with other types
        // User may be trying to perform int * string
        throw new NotAllowedOperationException();
    }

    @Override
    public String toString() {
        return Integer.toString(this.integer);
    }

    @Override
    public Valuable not() throws NotAllowedOperationException {
        throw new NotAllowedOperationException();
    }

}
