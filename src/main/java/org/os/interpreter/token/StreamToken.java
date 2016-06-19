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
public class StreamToken {

    private final Token token;
    private final Object data;

    public StreamToken(Token token) {
        this(token, null);
    }

    public StreamToken(Token token, Object data) {
        this.token = token;
        this.data = data;
    }

    public Token getToken() {
        return token;
    }

    public Object getData() {
        return data;
    }
}
