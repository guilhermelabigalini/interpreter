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
public abstract class EvalExpr extends EvaluableExpr {

    private EvaluableExpr x;

    public EvalExpr() {

    }

    public EvalExpr(EvaluableExpr x) {
        this.x = x;
    }

    public void setX(EvaluableExpr x) {
        this.x = x;
    }

    public EvaluableExpr getX() {
        return x;
    }
}
