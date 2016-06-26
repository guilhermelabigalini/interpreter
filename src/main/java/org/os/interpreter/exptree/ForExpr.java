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
public class ForExpr extends ConditionalExpr {

    private final Expr InitExpr;
    private final Expr IncExpr;

    public ForExpr(InstructLstExpr parent, Expr InitExpr, EvaluableExpr Condition, Expr IncExpr) {
        super(parent, Condition);
        this.InitExpr = InitExpr;
        this.IncExpr = IncExpr;
    }

    @Override
    public void Exec() throws ExecutionSignalException, NotAllowedOperationException {
        BeforeExec();

        try {

            if (InitExpr != null) {
                InitExpr.Exec();
            }

            EvaluableExpr condition = getCondition();
            while (condition == null || condition.Eval().asBoolean()) {

                try {
                    ExecInstructions();
                } catch (BreakSignalException e) {
                    return;
                } catch (ContinueSignalException e) {
                    // Do nothing... 
                }

                if (IncExpr != null) {
                    IncExpr.Exec();
                }
            }
        } finally {
            AfterExec();
        }
    }

}
