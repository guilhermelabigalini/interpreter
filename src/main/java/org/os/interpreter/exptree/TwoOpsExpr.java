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

    public EvaluableExpr getY() {
        return y;
    }

    public void setY(EvaluableExpr y) {
        this.y = y;
    }

}
