/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.os.interpreter.token;

/**
 *
 * @author guilherme
 */
public final class CharHelper {

    private CharHelper() {
    }
    
    public static boolean isoctchar(char c) {
        return c >= '0' && c <= '7';
    }

    public static boolean ishexchar(char c) {
        c = Character.toUpperCase(c);
        return Character.isDigit(c) || (c >= 'A' && c <= 'F');
    }
}
