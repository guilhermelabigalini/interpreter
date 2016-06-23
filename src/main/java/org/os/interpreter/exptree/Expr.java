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
public abstract class Expr {

    private final Expr parent;
    
    public Expr(Expr parent) {
        this.parent = parent;
    }

    public Expr getParent() {
        return parent;
    }

    public abstract void Exec() throws ExecutionSignalException;
    
    public void BeforeExec() {
    }
    
    public void AfterExec() {
    }

}
