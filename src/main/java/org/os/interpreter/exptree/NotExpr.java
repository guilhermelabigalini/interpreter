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
public class NotExpr extends EvalExpr {

    public NotExpr(EvaluableExpr x) {
        super(x);
    }

    @Override
    public Value Eval() throws NotAllowedOperationException, ExecutionSignalException {
        return this.getX().Eval().not();
    }
    
}
