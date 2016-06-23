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
public class SmallerEqualExpr extends TwoOpsExpr {

    public SmallerEqualExpr(EvaluableExpr x, EvaluableExpr y) {
        super(x, y);
    }

    @Override
    public Value Eval() {
        int cp = this.getX().Eval().compareTo(this.getY().Eval());
        return new Value(cp <= 0);
    }

}
