package org.geniprojects.passwordcracker.worker;

import java.io.Serializable;

public class Request implements Serializable {
    public String enCryptedString;
    public char[] leftBound;
    public char[] rightBound;

    public Request() {
        this.enCryptedString = "";
    }
    public Request(String enCryptedString, char[] leftBound, char[] rightBound) {
        this.enCryptedString = enCryptedString;
        this.leftBound = leftBound;
        this.rightBound = rightBound;
    }


}
