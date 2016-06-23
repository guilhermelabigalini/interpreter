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
public class ModAssignExpr extends AssignExpr {

    public ModAssignExpr(Expr parent, EvaluableExpr EvalExpr, String name) {
        super(parent, EvalExpr, name);
    }

    @Override
    public void Exec() {
        Value v = this.getCurrentVar();
        v.setValue(v.mod(this.getEvalExpr().Eval()));
    }
}
