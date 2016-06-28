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
public class CatchBlockExpr extends InstructLstExpr {

    private final String catchVarName;
    private Value error;

    public CatchBlockExpr(InstructLstExpr parent, String catchVarName) {
        super(parent);
        this.catchVarName = catchVarName;
        
        this.AddVar(catchVarName);
    }

    @Override
    public void BeforeExec() throws NotAllowedOperationException, ExecutionSignalException {
        // alloc the current varaibles
        super.BeforeExec();

        // assign values to the parameters
        Value var = this.getVariable(catchVarName);
        var.setValue(error);
    }

    public void Exec(Value arg) throws NotAllowedOperationException, ExecutionSignalException {

        this.error = arg;

        this.Exec();
    }
}
