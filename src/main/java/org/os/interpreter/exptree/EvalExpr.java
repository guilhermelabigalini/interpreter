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
public abstract class EvalExpr extends CustomEvalExpr {

    private CustomEvalExpr x;

    public EvalExpr() {

    }

    public EvalExpr(CustomEvalExpr x) {
        this.x = x;
    }

    public void setX(CustomEvalExpr x) {
        this.x = x;
    }

    public CustomEvalExpr getX() {
        return x;
    }
}
