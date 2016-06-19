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
public class Bookmark {
    private int position;
    private int currentLine;

    public Bookmark(int position, int currentLine) {
        this.position = position;
        this.currentLine = currentLine;
    }

    public int getPosition() {
        return position;
    }

    public int getCurrentLine() {
        return currentLine;
    }
    
}
