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
public class ReturnExpr extends Expr {

    private final EvaluableExpr returnExp;

    public ReturnExpr(Expr parent, EvaluableExpr returnExp) {
        super(parent);
        this.returnExp = returnExp;
    }

    @Override
    public void Exec() throws ExecutionSignalException, NotAllowedOperationException {
        Value result = null;
        
        if (returnExp != null) 
            result = returnExp.Eval();
        
        throw new ReturnSignalException(result);
    }
    
}
