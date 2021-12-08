package org.geniprojects.passwordcracker.master.workers.interaction;

import com.esotericsoftware.kryo.Kryo;
import org.geniprojects.passwordcracker.master.utils.Request;
import org.geniprojects.passwordcracker.master.utils.Response;

public class ConnectionUtil {
    public static final Kryo serializer = initSerializer();
    public static final Kryo inputSerializer = initSerializer();

    private static Kryo initSerializer() {
        Kryo newSerialzier = new Kryo();
        newSerialzier.register(Request.class);
        newSerialzier.register(Response.class);
        newSerialzier.register(char[].class);
        return  newSerialzier;
    }

}
