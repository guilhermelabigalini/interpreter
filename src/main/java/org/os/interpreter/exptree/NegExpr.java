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
public class NegExpr extends EvalExpr {

    public NegExpr(EvaluableExpr x) {
        super(x);
    }

    @Override
    public Value Eval() {
        return this.getX().Eval().times(new Value(-1));
    }
    
}
