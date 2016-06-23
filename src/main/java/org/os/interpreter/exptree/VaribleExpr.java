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
public class VaribleExpr implements EvaluableExpr {

    private final String name;
    private final InstructLstExpr parent;

    public VaribleExpr(String name, InstructLstExpr parent) {
        this.name = name;
        this.parent = parent;
    }

    @Override
    public Value Eval() {

        Value v = parent.getVariable(name);
        return v;
    }

}
