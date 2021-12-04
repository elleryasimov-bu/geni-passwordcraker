package org.geniprojects.passwordcracker.worker;

import java.io.Serializable;

public class Request implements Serializable {
    public String enCryptedString;
    //AAAAA
    public String leftBound;
    //zzzzz
    public String rightBound;

    public Request() {
        this.enCryptedString = "";
    }
    public Request(String enCryptedString, String leftBound, String rightBound) {
        this.enCryptedString = enCryptedString;
        this.leftBound = leftBound;
        this.rightBound = rightBound;
    }


}
