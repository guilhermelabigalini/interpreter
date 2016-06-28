/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.os.interpreter.exptree;

import org.os.interpreter.value.BooleanValue;
import org.os.interpreter.value.FloatValue;
import org.os.interpreter.value.IntValue;
import org.os.interpreter.value.NotAllowedOperationException;
import org.os.interpreter.value.StringValue;
import org.os.interpreter.value.Valuable;
import org.os.interpreter.value.ValuableFactory;

/**
 *
 * @author guilherme
 */
public class Value {

    public static Value Null = new Value(null);

    private Valuable value;

    public Value() {
    }

    public static Value forBoolean(boolean i) {
        return new Value(ValuableFactory.fromBoolean(i));
    }

    public static Value forInt(int i) {
        return new Value(ValuableFactory.fromInt(i));
    }

    public static Value forString(String str) {
        return new Value(ValuableFactory.fromString(str));
    }
 
    public Value(Valuable value) {
        this.value = value;
    }

    public Object getValue() {
        if (value instanceof BooleanValue) {
            return ((BooleanValue) value).boolValue();
        }

        if (value instanceof IntValue) {
            return ((IntValue) value).getInt();
        }

        if (value instanceof FloatValue) {
            return ((FloatValue) value).getFloatValue();
        }
        
        if (value instanceof StringValue) {
            return ((StringValue) value).getString();
        }
        
        return null;
    }

    public void setValue(Value value) {
        this.value = value.value;
    }

    public Value bigger(Value v) throws NotAllowedOperationException {
        return new Value(this.value.bigger(v.value));
    }

    public Value biggerOrEqual(Value v) throws NotAllowedOperationException {
        return new Value(this.value.biggerOrEqual(v.value));
    }

    public Value smaller(Value v) throws NotAllowedOperationException {
        return new Value(this.value.smaller(v.value));
    }

    public Value smallerOrEqual(Value v) throws NotAllowedOperationException {
        return new Value(this.value.smallerOrEqual(v.value));
    }

    public Value sum(Value v) throws NotAllowedOperationException {
        return new Value(this.value.sum(v.value));
    }

    public Value subtract(Value v) throws NotAllowedOperationException {
        return new Value(this.value.subtract(v.value));
    }

    public Value times(Value v) throws NotAllowedOperationException {
        return new Value(this.value.times(v.value));
    }

    public Value same(Value v) throws NotAllowedOperationException {
        return new Value(this.value.same(v.value));
    }

    public Value notsame(Value v) throws NotAllowedOperationException {
        return new Value(this.value.notsame(v.value));
    }

    public Value neg() throws NotAllowedOperationException {
        return new Value(this.value.neg());
    }

    public Value not() throws NotAllowedOperationException {
        return new Value(this.value.not());
    }

    public Value divide(Value v) throws NotAllowedOperationException {
        return new Value(this.value.divide(v.value));
    }

    public Value mod(Value v) throws NotAllowedOperationException {
        return new Value(this.value.mod(v.value));
    }

    @Override
    protected Value clone() {
        return new Value(this.value);
    }

    @Override
    public String toString() {
        return "Value{" + "value=" + value + '}';
    }

    public boolean equals(Value obj) throws NotAllowedOperationException {
        return ((BooleanValue) this.value.same(obj.value)).boolValue();
    }

    public boolean asBoolean() {
        if (value instanceof BooleanValue) {
            return ((BooleanValue) value).boolValue();
        }

        if (value instanceof IntValue) {
            return ((IntValue) value).getInt() > 0;
        }

        if (value instanceof FloatValue) {
            return ((FloatValue) value).getFloatValue() > 0;
        }

        return value != null;
    }

}
