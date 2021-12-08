package org.geniprojects.passwordcracker.master.utils;

import java.io.Serializable;

public class Response implements Serializable {
    public String deCryptedString;
    public long id;

    public Response() {
        this.deCryptedString = "";
    }

    public Response(String deCryptedString) {
        this.deCryptedString = deCryptedString;
    }

    public Response(String deCryptedString, long id) {
        this.deCryptedString = deCryptedString;
        this.id = id;
    }
}
