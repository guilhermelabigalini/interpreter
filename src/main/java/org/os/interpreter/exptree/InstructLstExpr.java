/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.os.interpreter.exptree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author guilherme
 */
public class InstructLstExpr extends Expr {

    private List<Expr> FInstructList;
    private List<Expr> FInitList;
    private HashSet<String> FAvaVaribles;
    private Stack<HashMap<String, Value>> Variables;
    private HashMap<String, Value> PreviousVariables;

    public InstructLstExpr() {
        this(null);
    }

    public InstructLstExpr(Expr parent) {
        super(parent);
        this.Variables = new Stack<>();
        this.FInstructList = new ArrayList<>();
        this.FAvaVaribles = new HashSet<>();
        this.FInitList = new ArrayList<>();
    }

    public void AddItem(Expr Data) {
        FInstructList.add(Data);
    }

    public void AddVar(String name) {
        this.FAvaVaribles.add(name);
    }

    public boolean ExitsVar(String name, boolean searchInParent) {
        if (this.FAvaVaribles.contains(name)) {
            return true;
        }

        if (searchInParent) {
            InstructLstExpr pil = this.getInstructLstExprParent();

            if (pil == null) {
                return false;
            }

            return pil.ExitsVar(name, searchInParent);
        }

        return false;
    }

    public void AddInitItem(Expr AssignExpr) {
        this.FInitList.add(AssignExpr);
    }

    public Value getPreviousVariable(String name) {
        Value result = this.PreviousVariables.get(name);

        if (result != null) {
            return result;
        }

        InstructLstExpr pil = this.getInstructLstExprParent();

        if (pil != null) {
            return pil.getPreviousVariable(name);
        }

        throw new RuntimeException("Unable to resolve run-time variable: " + name);
    }

    public Value getVariable(String name) {
        Value result = this.Variables.peek().get(name);

        if (result != null) {
            return result;
        }

        InstructLstExpr pil = this.getInstructLstExprParent();

        if (pil != null) {
            return pil.getVariable(name);
        }

        throw new RuntimeException("Unable to resolve run-time variable: " + name);
    }

    public InstructLstExpr getInstructLstExprParent() {
        Expr p = this.getParent();
        while ((p != null) && (!(p instanceof InstructLstExpr))) {
            p = p.getParent();
        }

        if (p == null) {
            return null;
        }

        return (InstructLstExpr) p;
    }

    protected void ExecInstructions() throws ExecutionSignalException {
        // init the variables 
        for (Expr e : this.FInitList) {
            e.Exec();
        }

        // run the commands
        for (Expr e : this.FInstructList) {
            e.Exec();
        }
    }

    @Override
    public void Exec() throws ExecutionSignalException {

        BeforeExec();

        try {
            ExecInstructions();
        } finally {
            AfterExec();
        }
    }

    @Override
    public void BeforeExec() {
        super.BeforeExec();

        HashMap<String, Value> currentVars = new HashMap<>(FAvaVaribles.size());
        this.Variables.push(currentVars);
        for (String name : FAvaVaribles) {
            currentVars.put(name, new Value());
        }
    }

    @Override
    public void AfterExec() {
        super.AfterExec();

        if (!Variables.empty()) {
            PreviousVariables = Variables.pop();
        } else {
            PreviousVariables = null;
        }
    }
}
