package org.geniprojects.passwordcracker.master.workers.interaction;

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
