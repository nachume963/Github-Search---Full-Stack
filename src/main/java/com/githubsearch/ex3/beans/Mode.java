package com.githubsearch.ex3.beans;

import org.springframework.beans.factory.annotation.Value;

/**
 * Represents user access status
 * the scope of this Bean will be "SESSION"
 * @author Nachum Ehrlich
 */
public class Mode {
    private boolean mode;

    //Initial value = false
    public Mode() {
        this.mode = false;
    }

    //getter and setter
    public boolean isMode() {
        return mode;
    }
    public void setMode(boolean mode) {
        this.mode = mode;
    }
}
