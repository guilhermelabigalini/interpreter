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
public class UserFuncCaller extends Expr implements EvaluableExpr {

    private final InstructLstExpr Expr;
    private final UserFuncTemp UserFuncTemp;
    private final List<EvaluableExpr> args;

    public UserFuncCaller(InstructLstExpr Expr, UserFuncTemp UserFuncTemp) {
        super(Expr);
        this.Expr = Expr;
        this.UserFuncTemp = UserFuncTemp;
        this.args = new ArrayList<>();
    }

    @Override
    public Value Eval() throws NotAllowedOperationException, ExecutionSignalException {
        return UserFuncTemp.Eval(args);
    }

    public void AddArg(EvaluableExpr value) {
        this.args.add(value);
    }

    @Override
    public void Exec() throws ExecutionSignalException, NotAllowedOperationException {
        Eval();
    }

}
