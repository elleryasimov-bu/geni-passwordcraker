package org.geniprojects.passwordcracker.master.utils;

import java.io.Serializable;
import java.util.Date;

public class Request implements Serializable {
    public String enCryptedString;
    public char[] leftBound;
    public char[] rightBound;
    public int majorId;
    public long minorId;

    public Request() {
        this.enCryptedString = "";
        this.minorId = (new Date()).getTime();
    }

    public Request(int majorId, String enCryptedString, String leftBound, String rightBound) {
        this.enCryptedString = enCryptedString;
        this.leftBound = leftBound.toCharArray();
        this.rightBound = rightBound.toCharArray();
        this.minorId = (new Date()).getTime();
        this.majorId = majorId;
    }
}
