package org.geniprojects.passwordcracker.master.workers.interaction;

public class Request {
    private String enCryptedString;

    public Request(String enCryptedString) {
        this.enCryptedString = enCryptedString;
    }

    public String getEnCryptedString() {
        return enCryptedString;
    }

    public void setEnCryptedString(String enCryptedString) {
        this.enCryptedString = enCryptedString;
    }
}
