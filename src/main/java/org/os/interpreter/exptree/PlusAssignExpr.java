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
public class PlusAssignExpr extends AssignExpr {

    public PlusAssignExpr(Expr parent, EvaluableExpr EvalExpr, String name) {
        super(parent, EvalExpr, name);
    }

    @Override
    public void Exec() throws NotAllowedOperationException {
        Value v = this.getCurrentVar();
        v.setValue(v.sum(this.getEvalExpr().Eval()));
    }
    
}
