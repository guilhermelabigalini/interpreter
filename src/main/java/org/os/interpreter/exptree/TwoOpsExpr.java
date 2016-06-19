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

    private CustomEvalExpr y;

    public CustomEvalExpr getY() {
        return y;
    }

    public void setY(CustomEvalExpr y) {
        this.y = y;
    }

}
