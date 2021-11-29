package org.geniprojects.passwordcraker.worker;

import com.esotericsoftware.kryo.io.Input;

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

            String line = "";

            // close connection
            socket.close();
            input.close();
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
