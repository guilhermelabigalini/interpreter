/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.os.interpreter;

import java.io.FileNotFoundException;
import org.os.interpreter.expbuilder.ParseException;
import org.os.interpreter.exptree.ExecutionSignalException;
import org.os.interpreter.value.NotAllowedOperationException;

/**
 *
 * @author guilherme
 */
public class Runner {

    public static void main(String[] args) throws
            ParseException,
            ExecutionSignalException,
            FileNotFoundException,
            NotAllowedOperationException {
        /*
        args=new String[] {
            "-file", "source.kpl",
            "-verbose"
        };*/

        RunnerStartupInfo rsi = new RunnerStartupInfo(args);

        if (rsi.isValidOptions(true)) {
            ScriptRunner runner = new ScriptRunner(rsi.isVerbose(), rsi.getFileName());

            runner.compileAndRun();
        }
    }
}
