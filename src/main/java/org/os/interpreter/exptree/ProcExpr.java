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
public class ProcExpr extends InstructLstExpr {

    public ProcExpr() {
        this(null);
    }

    public ProcExpr(Expr parent) {
        super(parent);
    }


}
