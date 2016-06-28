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
public class ThrowExpr extends Expr {

    private final EvaluableExpr ThrowExpr;

    public ThrowExpr(Expr parent, EvaluableExpr ThrowExpr) {
        super(parent);
        this.ThrowExpr = ThrowExpr;
    }

    @Override
    public void Exec() throws ExecutionSignalException, NotAllowedOperationException {
        throw new ThrowSignalException(ThrowExpr.Eval());
    }

}
