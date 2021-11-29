package org.geniprojects.passwordcracker.worker;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WorkerLogic {
    public static void main(String[] args) {
        System.out.println("This is the worker.");
        try
        {
            ServerSocket server = new ServerSocket(50000);
            System.out.println("Server started");

            System.out.println("Waiting for a client ...");

            Socket socket = server.accept();
            System.out.println("Client accepted");

            // takes input from the client socket
            Input input = new Input(socket.getInputStream());
            System.out.println("Get Input");
            Request req = Util.serializer.readObject(input, Request.class);

            System.out.println("Successfully Read " + req.enCryptedString);


            Response resp = new Response(decrypt(req.enCryptedString));
            Output output = new Output(socket.getOutputStream());
            System.out.println("Going to write");
            Util.serializer.writeObject(output, resp);
            output.flush();


            // close connection
            input.close();
            output.close();
            socket.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }

    public static String decrypt(String input) {
        return input;
    }
}
