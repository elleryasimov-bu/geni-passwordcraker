package org.geniprojects.passwordcracker.worker;

import com.esotericsoftware.kryo.Kryo;

public class Util {
    public static final Kryo serializer = initSerializer();
    private static Kryo initSerializer() {
        Kryo kryo = new Kryo();
        kryo.register(Request.class);
        kryo.register(Response.class);
        kryo.register(char[].class);
        return kryo;
    }
}
