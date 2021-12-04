package org.geniprojects.passwordcracker.master.workers.interaction;

import java.io.Serializable;

public class Request implements Serializable {
    public String enCryptedString;
    //public String leftBound;
    //public String rightBound;

    public Request() {
        this.enCryptedString = "";
    }

    public Request(String enCryptedString) {
        this.enCryptedString = enCryptedString;
        //this.leftBound = leftBound;
        //this.rightBound = rightBound;
    }
}
