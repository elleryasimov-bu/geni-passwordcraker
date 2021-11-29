package org.geniprojects.passwordcraker.worker;

import com.esotericsoftware.kryo.Kryo;

public class Util {
    public static final Kryo serializer = initSerializer();
    private static Kryo initSerializer() {
        Kryo kryo = new Kryo();
        kryo.register(Request.class);
        kryo.register(Response.class);
        return kryo;
    }
}
