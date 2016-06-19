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
public class AssignExpr extends Expr {

    private final CustomEvalExpr EvalExpr;
    private final String name;

    public AssignExpr(Expr parent, CustomEvalExpr EvalExpr, String name) {
        super(parent);
        this.EvalExpr = EvalExpr;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public CustomEvalExpr getEvalExpr() {
        return EvalExpr;
    }

    protected Value getCurrentValue() {
        InstructLstExpr il = (InstructLstExpr) this.getParent();
        Value v = il.getVariable(name);
        return v;
    }

    @Override
    public void Exec() {
        Value v = this.getCurrentValue();
        v.setValue(EvalExpr.Eval());
    }

}
