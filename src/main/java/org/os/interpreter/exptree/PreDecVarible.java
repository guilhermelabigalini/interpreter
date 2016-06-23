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
public class PreDecVarible extends EvalExpr {

    public PreDecVarible(EvaluableExpr x) {
        super(x);
    }

    @Override
    public Value Eval() {
        // x++
        // return the value, then descremen 
        EvaluableExpr x = this.getX();
        Value v = x.Eval();
        v.setValue(v.sum(new Value(-1)));
        return v;
    }
    
}
