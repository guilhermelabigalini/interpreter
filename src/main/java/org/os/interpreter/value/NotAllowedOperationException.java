/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.os.interpreter.value;

import org.os.interpreter.expbuilder.RuntimeInterpreterException;

/**
 *
 * @author guilherme
 */
public class NotAllowedOperationException extends RuntimeInterpreterException {

    public NotAllowedOperationException() {
        super("Operation is not allowed");
    }
    
}
