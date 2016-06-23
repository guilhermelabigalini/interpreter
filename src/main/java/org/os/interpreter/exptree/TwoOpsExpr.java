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
public abstract class TwoOpsExpr extends EvalExpr {

    private EvaluableExpr y;

    public TwoOpsExpr(EvaluableExpr x, EvaluableExpr y) {
        super(x);
        this.y = y;
    }

    public EvaluableExpr getY() {
        return y;
    }
}
