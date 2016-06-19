/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.os.interpreter;

import java.util.Collection;
import java.util.List;

/**
 *
 * @author guilherme
 */
public class ParseException extends Exception {

    private final Collection<Error> errors;

    public Collection<Error> getErrors() {
        return errors;
    }
    
    public ParseException(Collection<Error> errors){
        super("Total errors is: " + errors.size());
        this.errors = errors;
    }
}
