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
public class BoolOrExpr extends TwoOpsExpr {

    public BoolOrExpr(EvaluableExpr x, EvaluableExpr y) {
        super(x, y);
    }

    @Override
    public Value Eval() throws NotAllowedOperationException, ExecutionSignalException {
        return Value.forBoolean(this.getX().Eval().asBoolean() || this.getY().Eval().asBoolean());
    }

}
