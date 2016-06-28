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
public class UserFuncTemp extends InstructLstExpr {

    private List<String> parameters;
    private List<EvaluableExpr> args;
    
    public UserFuncTemp(InstructLstExpr parent) {
        super(parent);
        this.parameters = new ArrayList<>();
    }
    
    public void addParameter(String name) {
        parameters.add(name);
        this.AddVar(name);
    }
    
    public int getTotalParameters() {
        return parameters.size();
    }

    public InstructLstExpr getBody() {
        return this;
    }

    @Override
    public void BeforeExec() throws NotAllowedOperationException, ExecutionSignalException {
        // resolve the parameter values
        Value[] values = new Value[this.parameters.size()];
        for (int p = 0; p < this.parameters.size(); p++) {
            values[p] = args.get(p).Eval();
        }
        
        // alloc the current varaibles
        super.BeforeExec(); 
        
        // assign values to the parameters
        for (int p = 0; p < this.parameters.size(); p++) {
            Value var = this.getVariable(this.parameters.get(p));
            var.setValue(values[p]);
        }
    }
    
    public Value Eval(List<EvaluableExpr> args) throws NotAllowedOperationException, ExecutionSignalException {
        try {
            this.args = args;
            
            this.Exec();
            
            return Value.Null;
            
        } catch (ReturnSignalException ex) {
            return ex.getValue();
        } 
    }
}
