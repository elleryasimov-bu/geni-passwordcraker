package org.geniprojects.passwordcraker.worker;

import java.io.Serializable;

public class Request implements Serializable {
    public String enCryptedString;

    public Request() {
        this.enCryptedString = "";
    }
    public Request(String enCryptedString) {
        this.enCryptedString = enCryptedString;
    }


}
