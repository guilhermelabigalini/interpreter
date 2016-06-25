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
}
