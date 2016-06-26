/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.os.interpreter.value;

/**
 *
 * @author guilherme
 */
public interface Valuable {
    Valuable sum(Valuable v) throws NotAllowedOperationException;
    Valuable subtract(Valuable v) throws NotAllowedOperationException;
    Valuable times(Valuable v) throws NotAllowedOperationException;
    Valuable divide(Valuable v) throws NotAllowedOperationException;
    Valuable mod(Valuable v) throws NotAllowedOperationException;
    Valuable neg() throws NotAllowedOperationException;
    Valuable not() throws NotAllowedOperationException;
    Valuable same(Valuable v) throws NotAllowedOperationException;
    Valuable notsame(Valuable v) throws NotAllowedOperationException;
    Valuable smaller(Valuable v) throws NotAllowedOperationException;
    Valuable bigger(Valuable v) throws NotAllowedOperationException;
    Valuable smallerOrEqual(Valuable v) throws NotAllowedOperationException;
    Valuable biggerOrEqual(Valuable v) throws NotAllowedOperationException;
}
