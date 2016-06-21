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
    
    private CustomEvalExpr condition;

    public ConditionalExpr(Expr parent, CustomEvalExpr condition) {
        super(parent);
        this.condition = condition;
    }

    public CustomEvalExpr getCondition() {
        return condition;
    }

    public void setCondition(CustomEvalExpr condition) {
        this.condition = condition;
    }
    
}
