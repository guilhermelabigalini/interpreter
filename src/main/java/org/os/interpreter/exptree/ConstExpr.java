/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.os.interpreter.exptree;

import org.os.interpreter.value.Valuable;

/**
 *
 * @author guilherme
 */
public class ConstExpr implements EvaluableExpr {

    private final Valuable value;

    public ConstExpr(Valuable value) {
        this.value = value;
    }
    
    @Override
    public Value Eval() {
        return new Value(value);
    }
    
}
