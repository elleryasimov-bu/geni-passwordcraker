package org.geniprojects.passwordcracker.worker;

import com.esotericsoftware.kryo.Kryo;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Util {
    public static final Kryo serializer = initSerializer();
    private static Kryo initSerializer() {
        Kryo kryo = new Kryo();
        kryo.register(Request.class);
        kryo.register(Response.class);
        kryo.register(char[].class);
        return kryo;
    }
    public static final ExecutorService connThreadPool = Executors.newCachedThreadPool();

    public static final ConcurrentHashMap<String, String> md5Map = new ConcurrentHashMap<>();
}
