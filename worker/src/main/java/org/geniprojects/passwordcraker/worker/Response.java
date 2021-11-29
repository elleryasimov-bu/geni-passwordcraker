package org.geniprojects.passwordcraker.worker;

public class Response {
    private String deCryptedString;

    public Response(String deCryptedString) {
        this.deCryptedString = deCryptedString;
    }

    public String getDeCryptedString() {
        return deCryptedString;
    }

    public void setDeCryptedString(String deCryptedString) {
        this.deCryptedString = deCryptedString;
    }
}
