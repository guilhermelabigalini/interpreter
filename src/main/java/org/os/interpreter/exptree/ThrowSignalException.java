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
public class ThrowSignalException extends ExecutionSignalException {

    private final Value value;

    public ThrowSignalException(Value value) {
        this.value = value;
    }

    public Value getValue() {
        return value;
    }
}
