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
public class BreakExpr extends Expr {

    public BreakExpr(Expr parent) {
        super(parent);
    }

    @Override
    public void Exec() throws ExecutionSignalException {
        throw new BreakSignalException();
    }

}
