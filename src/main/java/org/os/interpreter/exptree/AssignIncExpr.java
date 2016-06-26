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
public class AssignIncExpr extends Expr {

    EvalExpr IncExpr;
            
    public AssignIncExpr(Expr parent, EvalExpr IncExpr) {
        super(parent);
        this.IncExpr = IncExpr;
    }

    @Override
    public void Exec() throws NotAllowedOperationException {
        
        IncExpr.Eval();
    }
    
}
