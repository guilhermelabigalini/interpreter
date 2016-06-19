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
public class BufferReader {

    private String buffer;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public BufferReader(String buffer) {
        this.buffer = buffer;
        this.position = 0;
    }

    public char moveAndGetChar() {
        next();
        return currentChar();
    }

    public char currentCharAndMove() {
        char c = buffer.charAt(position);
        next();
        return c;
    }

    public boolean eof()
    {
        return position >= buffer.length();
    }
    
    public char currentChar() {
        return buffer.charAt(position);
    }

    public char nextChar() {
        return buffer.charAt(position + 1);
    }

    public void next() {
        position++;
    }

    void previous() {
        position--;
    }
}
