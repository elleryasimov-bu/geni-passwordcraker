package org.geniprojects.passwordcracker.master.workers;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.geniprojects.passwordcracker.master.workers.interaction.ConnectionUtil;
import org.geniprojects.passwordcracker.master.workers.interaction.Request;
import org.geniprojects.passwordcracker.master.workers.interaction.Response;

import java.net.Socket;

public class Worker {
    private String ipAddress;
    private int port;
    private Socket socket;

    public Worker(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public Response assign(Request req) throws Exception {
        Socket socket = new Socket(ipAddress, port);
        System.out.println("Connected");


        Output output = new Output(socket.getOutputStream());
        System.out.println("Going to write");
        ConnectionUtil.serializer.writeObject(output, req);
        System.out.println("Going to send " + req.enCryptedString);
        output.flush();
        System.out.println("Write Successfully");

        System.out.println("Waiting for input");
        Input input = new Input(socket.getInputStream());
        Response resp = ConnectionUtil.serializer.readObject(input, Response.class);
        System.out.println("Response received");


        output.close();
        input.close();
        socket.close();
        return resp;
    }
}
