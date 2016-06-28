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
public class AssignExpr extends Expr {

    private final EvaluableExpr EvalExpr;
    private final String name;

    public AssignExpr(Expr parent, EvaluableExpr EvalExpr, String name) {
        super(parent);
        this.EvalExpr = EvalExpr;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public EvaluableExpr getEvalExpr() {
        return EvalExpr;
    }

    protected Value getCurrentVar() {
        InstructLstExpr il = (InstructLstExpr) this.getParent();
        Value v = il.getVariable(name);
        return v;
    }

    @Override
    public void Exec() throws NotAllowedOperationException, ExecutionSignalException {
        Value v = this.getCurrentVar();
        v.setValue(EvalExpr.Eval());
    }

}
