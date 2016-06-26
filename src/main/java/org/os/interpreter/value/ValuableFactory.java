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
public class ValuableFactory {

    public static StringValue fromString(String value) {
        return new StringValue(value);
    }

    public static IntValue fromInt(int value) {
        return new IntValue(value);
    }

    public static FloatValue fromFloat(float value) {
        return new FloatValue(value);
    }

    public static Valuable fromObject(Object data) {
        
        if (data instanceof Integer) {
            return fromInt((int) data);
        }
        if (data instanceof String) {
            return fromString((String) data);
        }
        if (data instanceof Float) {
            return fromFloat((float) data);
        }
        if (data instanceof Boolean) {
            return fromBoolean((boolean) data);
        }
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static Valuable fromBoolean(boolean i) {
        if (i) {
            return BooleanValue.True;
        } else {
            return BooleanValue.False;
        }
    }
}
