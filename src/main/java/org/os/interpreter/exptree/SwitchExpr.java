/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.os.interpreter.exptree;

import java.util.ArrayList;
import java.util.List;
import org.os.interpreter.value.NotAllowedOperationException;

/**
 *
 * @author guilherme
 */
public class SwitchExpr extends Expr {

    public static class SwitchItem {

        private final List<EvaluableExpr> conditions;
        private final ProcExpr procExpr;

        public SwitchItem(SwitchExpr parent) {
            this.conditions = new ArrayList<>();
            this.procExpr = new ProcExpr(parent);
        }

        public List<EvaluableExpr> getConditions() {
            return conditions;
        }

        public ProcExpr getProcExpr() {
            return procExpr;
        }
    }

    private final EvaluableExpr condition;
    private final List<SwitchItem> cases;
    private ProcExpr defaultProc;

    public SwitchExpr(Expr parent, EvaluableExpr condition) {
        super(parent);
        this.condition = condition;
        this.cases = new ArrayList<>();
        this.defaultProc = null;
    }

    public boolean isDefaultBlockDefined() {
        return defaultProc != null;
    }

    public ProcExpr getDefaultProc() {
        if (defaultProc == null) {
            defaultProc = new ProcExpr(this);
        }

        return defaultProc;
    }

    public List<SwitchItem> getCases() {
        return cases;
    }

    @Override
    public void Exec() throws ExecutionSignalException, NotAllowedOperationException {
        boolean singleCasePassed = false;
        boolean aborted = false;

        Value r = this.condition.Eval();
        for (SwitchItem swtchitem : this.cases) {

            if (!singleCasePassed) {
                for (EvaluableExpr caseItem : swtchitem.conditions) {
                    if (r.equals(caseItem.Eval())) {
                        singleCasePassed = true;
                        break;
                    }
                }
            }

            if (singleCasePassed) {
                try {
                    swtchitem.getProcExpr().Exec();
                } catch (ContinueSignalException e) {
                    // do, nothing !
                } catch (BreakSignalException e) {
                    aborted = true;
                    break;
                }
            }
        }
        if (this.isDefaultBlockDefined() && (!aborted)) {
            defaultProc.Exec();
        }

    }

}
