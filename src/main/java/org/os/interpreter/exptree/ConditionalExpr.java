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
public class ConditionalExpr extends InstructLstExpr {
    
    private EvaluableExpr condition;

    public ConditionalExpr(Expr parent, EvaluableExpr condition) {
        super(parent);
        this.condition = condition;
    }

    public EvaluableExpr getCondition() {
        return condition;
    }

    public void setCondition(EvaluableExpr condition) {
        this.condition = condition;
    }
    
}
