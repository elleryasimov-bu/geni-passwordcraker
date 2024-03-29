package org.geniprojects.passwordcracker.master.workers.interaction;

import com.esotericsoftware.kryo.Kryo;

public class ConnectionUtil {
    public static final Kryo serializer = initSerializer();

    private static Kryo initSerializer() {
        Kryo newSerialzier = new Kryo();
        newSerialzier.register(Request.class);
        newSerialzier.register(Response.class);
        newSerialzier.register(char[].class);
        return  newSerialzier;
    }

}
