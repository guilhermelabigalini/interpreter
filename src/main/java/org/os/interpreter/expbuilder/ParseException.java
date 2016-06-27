/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.os.interpreter.expbuilder;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author guilherme
 */
public class ParseException extends Exception {

    private final Collection<Error> errors;

    public Collection<Error> getErrors() {
        return errors;
    }

    public ParseException(List<Error> errors) {

        super("Total errors is: " + errors.size() + "\n" + String.join("", errors.stream().map(e -> e.toString()).collect(Collectors.toList())));;
        
        this.errors = errors;
    }
}
