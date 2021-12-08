package org.geniprojects.passwordcracker.master.utils;

import java.io.Serializable;

public class Response implements Serializable {
    public String deCryptedString;
    public int id;

    public Response() {
        this.deCryptedString = "";
    }

    public Response(String deCryptedString) {
        this.deCryptedString = deCryptedString;
    }

    public Response(String deCryptedString, int id) {
        this.deCryptedString = deCryptedString;
        this.id = id;
    }
}

