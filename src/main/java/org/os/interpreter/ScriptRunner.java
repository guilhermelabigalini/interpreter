/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.os.interpreter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Scanner;
import org.os.interpreter.expbuilder.ExpressionTreeBuilder;
import org.os.interpreter.expbuilder.ParseException;
import org.os.interpreter.exptree.ExecutionSignalException;
import org.os.interpreter.exptree.ProcExpr;
import org.os.interpreter.exptree.Value;
import org.os.interpreter.value.NotAllowedOperationException;

/**
 *
 * @author guilherme
 */
public class ScriptRunner {

    private final boolean showVerbose;
    private final String sourceFile;

    public ScriptRunner(boolean showVerbose, String sourceFile) {
        this.showVerbose = showVerbose;
        this.sourceFile = sourceFile;
    }

    public void compileAndRun() throws ParseException, ExecutionSignalException, FileNotFoundException, NotAllowedOperationException {
        
        try (Scanner scanner = new Scanner(new FileInputStream(sourceFile))) {
            String contents = scanner.useDelimiter("\\A").next();

            ExpressionTreeBuilder it = new ExpressionTreeBuilder(contents);
            
            Instant tc1 = Instant.now();
            ProcExpr procedure = it.compile();
            Instant tc2 = Instant.now();

            Instant tr1 = Instant.now();
            procedure.Exec();
            Instant tr2 = Instant.now();

            // ChronoUnit.MILLIS.between(tr1, tr2)
            if (showVerbose) {
                System.out.println("Time for " + sourceFile
                        + " - parse: " + ChronoUnit.MILLIS.between(tc1, tc2) + "ms,"
                        + " execute: " + ChronoUnit.MILLIS.between(tr1, tr2) + "ms");

                System.out.println("Varibles:");

                HashMap<String, Value> vars = procedure.getPreviousVariables();
                vars.entrySet().stream().forEach((var) -> {
                    System.out.println(var.getKey() + "=" + var.getValue().getValue());
                });

            }
        }
    }
}
