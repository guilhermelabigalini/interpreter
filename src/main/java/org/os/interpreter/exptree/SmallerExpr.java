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
public class SmallerExpr extends TwoOpsExpr {

    public SmallerExpr(EvaluableExpr x, EvaluableExpr y) {
        super(x, y);
    }

    @Override
    public Value Eval() throws NotAllowedOperationException {
        return this.getX().Eval().smaller(this.getY().Eval());
    }
    
    
}
