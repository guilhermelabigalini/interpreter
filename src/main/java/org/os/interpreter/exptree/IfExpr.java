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
public class IfExpr extends ConditionalExpr {

    private ProcExpr ElseExpr;

    public IfExpr(Expr parent, EvaluableExpr Condition) {
        super(parent, Condition);
    }

    public ProcExpr getElseExpr() {
        if (ElseExpr == null) {
            ElseExpr = new ProcExpr(this);
        }

        return ElseExpr;
    }

    @Override
    public void Exec() throws ExecutionSignalException {

        BeforeExec();

        try {

            Value contitionResult = this.getCondition().Eval();

            if (contitionResult.asBoolean()) {
                super.Exec();
            } else if (ElseExpr != null) {
                ElseExpr.Exec();
            }

        } finally {
            AfterExec();
        }
    }

}
