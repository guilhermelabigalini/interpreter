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
public class ModExpr extends TwoOpsExpr {

    @Override
    public Value Eval() {
        return this.getX().Eval().mod(this.getY().Eval());
    }

}
