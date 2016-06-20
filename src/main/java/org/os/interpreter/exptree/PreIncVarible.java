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
public class PreIncVarible extends CustomIncVarible {

    @Override
    public Value Eval() {
        // x++
        // return the value, then descremen 
        CustomEvalExpr x = this.getX();
        Value v = x.Eval();
        v.setValue(v.sum(new Value(1)));
        return v;
    }

}
