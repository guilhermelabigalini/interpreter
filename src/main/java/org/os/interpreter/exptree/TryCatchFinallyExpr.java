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
public class TryCatchFinallyExpr extends Expr {

    public TryCatchFinallyExpr(Expr parent) {
        super(parent);
    }

    @Override
    public void Exec() throws ExecutionSignalException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
