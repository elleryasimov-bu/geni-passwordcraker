package org.geniprojects.passwordcracker.master.workers.interaction;

import java.io.Serializable;

public class Response implements Serializable {
    public String deCryptedString;

    public Response() {
        this.deCryptedString = "";
    }

    public Response(String deCryptedString) {
        this.deCryptedString = deCryptedString;
    }
}

