package org.geniprojects.passwordcracker.master.utils;

import java.io.Serializable;
import java.util.Date;

public class Request implements Serializable {
    public String enCryptedString;
    public char[] leftBound;
    public char[] rightBound;
    public long id;

    public Request() {
        this.enCryptedString = "";
        this.id = (new Date()).getTime();
    }

    public Request(String enCryptedString, String leftBound, String rightBound) {
        this.enCryptedString = enCryptedString;
        this.leftBound = leftBound.toCharArray();
        this.rightBound = rightBound.toCharArray();
        this.id = (new Date()).getTime();
    }
}
