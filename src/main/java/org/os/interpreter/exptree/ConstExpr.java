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
public class ConstExpr extends CustomEvalExpr {

    private final Object value;

    public ConstExpr(Object value) {
        this.value = value;
    }
    
    @Override
    public Value Eval() {
        return new Value(value);
    }
    
}
