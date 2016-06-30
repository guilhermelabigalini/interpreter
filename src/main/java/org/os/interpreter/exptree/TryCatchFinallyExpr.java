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
public class TryCatchFinallyExpr extends Expr {

    ProcExpr FDangerBlock, FFinnalyBlock;
    CatchBlockExpr FExceptionBlock;

    public TryCatchFinallyExpr(Expr parent,
            ProcExpr FDangerBlock,
            CatchBlockExpr FExceptionBlock,
            ProcExpr FFinnalyBlock) {
        super(parent);
        this.FDangerBlock = FDangerBlock;
        this.FExceptionBlock = FExceptionBlock;
        this.FFinnalyBlock = FFinnalyBlock;
    }

    @Override
    public void Exec() throws ExecutionSignalException, NotAllowedOperationException {
        try {
            this.FDangerBlock.Exec();
        } catch (ThrowSignalException e) {
            if (this.FExceptionBlock != null) {
                this.FExceptionBlock.Exec(e.getValue());
            }
        } catch (ExecutionSignalException e) {
            throw e;
        } catch (Throwable e) {
            if (this.FExceptionBlock != null) {
                this.FExceptionBlock.Exec(Value.forString(e.getMessage()));
            }
        } finally {
            if (this.FFinnalyBlock != null) {
                this.FFinnalyBlock.Exec();
            }
        }
    }
}
