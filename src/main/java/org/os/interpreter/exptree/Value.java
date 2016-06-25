/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.os.interpreter.exptree;

/**
 *
 * @author guilherme
 */
public class Value implements Comparable<Value> {

    public static Value Null = new Value(null);
    
    private Object value;

    public Value() {
    }

    public Value(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value.value;
    }

    public Value sum(Value v) {
        Object r = (Integer) this.value + (Integer) v.value;
        return new Value(r);
    }

    public Value subtract(Value v) {
        Object r = (Integer) this.value - (Integer) v.value;
        return new Value(r);
    }

    public Value times(Value v) {
        Object r = (Integer) this.value * (Integer) v.value;
        return new Value(r);
    }

    public Value divide(Value v) {
        Object r = (Integer) this.value / (Integer) v.value;
        return new Value(r);
    }

    public Value mod(Value v) {
        Object r = (Integer) this.value % (Integer) v.value;
        return new Value(r);
    }

    @Override
    protected Value clone() {
        return new Value(this.value);
    }

    @Override
    public String toString() {
        return "Value{" + "value=" + value + '}';
    }

    public boolean equals(Value obj) {
        return this.value.equals(obj.value);
    }

    boolean asBoolean() {
        if (value instanceof Boolean) {
            return (boolean) value;
        }

        if (value instanceof Number) {
            return ((Number) value).intValue() > 0;
        }

        return value != null;
    }

    @Override
    public int compareTo(Value o) {
        return ((Comparable) this.value).compareTo(o.getValue());
    }

    public Value not() {
        if (value instanceof Boolean) {
            return new Value(!(boolean) value);
        }

        throw new RuntimeException("Not only supported in BOOLEAN.");
    }

}
