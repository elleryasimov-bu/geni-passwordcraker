package org.geniprojects.passwordcracker.master.workers.interaction;

import java.io.Serializable;

public class Request implements Serializable {
    public String enCryptedString;
    public char[] leftBound;
    public char[] rightBound;

    public Request() {
        this.enCryptedString = "";
    }

    public Request(String enCryptedString, String leftBound, String rightBound) {
        this.enCryptedString = enCryptedString;
        this.leftBound = leftBound.toCharArray();
        this.rightBound = rightBound.toCharArray();
    }
}
