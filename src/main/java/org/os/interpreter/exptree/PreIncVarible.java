/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.os.interpreter.exptree;

import org.os.interpreter.value.NotAllowedOperationException;

/**
 *
 * @author guilherme
 */
public class PreIncVarible extends EvalExpr {

    public PreIncVarible(EvaluableExpr x) {
        super(x);
    }

    @Override
    public Value Eval() throws NotAllowedOperationException {
        // x++
        // return the value, then descremen 
        EvaluableExpr x = this.getX();
        Value v = x.Eval();
        v.setValue(v.sum(Value.forInt(1)));
        return v;
    }

}
