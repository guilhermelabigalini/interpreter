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

/**
 *
 * @author guilherme
 */
public class InstructLstExpr extends Expr {

    protected List<Expr> FInstructList;
    protected List<Expr> FInitList;
    protected HashSet<String> FAvaVaribles;
    protected HashMap<String, Value> Variables;

    public InstructLstExpr() {
        this(null);
    }

    public InstructLstExpr(Expr parent) {
        super(parent);
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

    public Value getVariable(String name) {
        Value result = this.Variables.get(name);

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

        this.Variables = new HashMap<>(FAvaVaribles.size());
        for (String name : FAvaVaribles) {
            this.Variables.put(name, new Value());
        }
    }

    @Override
    public void AfterExec() {
        super.AfterExec();

        // this.Variables.clear();
    }
}
