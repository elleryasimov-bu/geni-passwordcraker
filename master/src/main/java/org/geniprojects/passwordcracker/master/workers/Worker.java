package org.geniprojects.passwordcracker.master.workers;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.geniprojects.passwordcracker.master.workers.interaction.ConnectionUtil;
import org.geniprojects.passwordcracker.master.workers.interaction.Request;
import org.geniprojects.passwordcracker.master.workers.interaction.Response;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Worker {
    private String ipAddress;
    private int port;

    public Worker(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public Response assign(Request req) throws Exception {
        Socket socket = new Socket(ipAddress, port);
        System.out.println("Connected");


        Output output = new Output(socket.getOutputStream());
        ConnectionUtil.serializer.writeObject(output, req);
        output.close();

        Input input = new Input(socket.getInputStream());
        Response resp = ConnectionUtil.serializer.readObject(input, Response.class);
        return resp;
    }
}
