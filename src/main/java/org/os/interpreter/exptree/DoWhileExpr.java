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
public class DoWhileExpr extends ConditionalExpr {

    public DoWhileExpr(Expr parent, EvaluableExpr Condition) {
        super(parent, Condition);
    }

    @Override
    public void Exec() throws ExecutionSignalException, NotAllowedOperationException {
        BeforeExec();

        try {
            EvaluableExpr condition = this.getCondition();

            do {
                try {
                    ExecInstructions();
                } catch (BreakSignalException e) {
                    return;
                } catch (ContinueSignalException e) {
                    // Do nothing... 
                }
            } while (condition.Eval().asBoolean());
        } finally {
            AfterExec();
        }
    }
}
