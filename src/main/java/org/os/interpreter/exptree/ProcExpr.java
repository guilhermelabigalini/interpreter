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

    @Override
    public void Exec() {

        BeforeExec();
        
        try {
            // init the variables 
            for (Expr e : this.FInitList) {
                e.Exec();
            }

            // run the commands
            for (Expr e : this.FInstructList) {
                e.Exec();
            }

        } finally {
            AfterExec();
        }
    }

}
