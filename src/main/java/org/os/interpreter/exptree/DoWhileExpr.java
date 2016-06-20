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
public class DoWhileExpr extends ConditionalExpr {

    public DoWhileExpr(Expr parent, CustomEvalExpr Condition) {
        super(parent, Condition);
    }
    
}
