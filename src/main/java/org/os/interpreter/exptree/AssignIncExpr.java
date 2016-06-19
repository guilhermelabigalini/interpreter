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
public class AssignIncExpr extends Expr {

    CustomIncVarible IncExpr;
            
    public AssignIncExpr(Expr parent, CustomIncVarible IncExpr) {
        super(parent);
        this.IncExpr = IncExpr;
    }

    @Override
    public void Exec() {
        
        IncExpr.Eval();
    }
    
}
