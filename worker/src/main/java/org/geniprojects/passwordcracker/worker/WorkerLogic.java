package org.geniprojects.passwordcracker.worker;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class WorkerLogic {
    public static void main(String[] args) {
        System.out.println("This is the worker.");
        try
        {
            ServerSocket server = new ServerSocket(50000);
            System.out.println("Server started");

            System.out.println("Waiting for a client ...");

            Socket socket = server.accept();
            Util.connThreadPool.submit(new PerConnection(socket));
            System.out.println("Client accepted");

//            // takes input from the client socket
//            Input input = new Input(socket.getInputStream());
//            System.out.println("Get Input");
//            Request req = Util.serializer.readObject(input, Request.class);
//
//            System.out.println("Successfully Read " + req.enCryptedString);
//
//
//            Response resp = new Response(decrypt(req.enCryptedString, req.leftBound, req.rightBound));
//            Output output = new Output(socket.getOutputStream());
//            System.out.println("Going to write");
//            Util.serializer.writeObject(output, resp);
//            output.flush();
//
//
//            // close connection
//            input.close();
//            output.close();
            //socket.close();

            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static String decrypt(String input, char[] leftBound, char[] rightBound) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        StringBuilder testpsw = new StringBuilder();
        for (char c1 = leftBound[0]; c1 <= rightBound[0]; c1++) {
            testpsw.append(c1);
            for (char c2 = leftBound[1]; c2 <= rightBound[1]; c2++) {
                testpsw.append(c2);
                for (char c3 = leftBound[2]; c3 <= rightBound[2]; c3++) {
                    testpsw.append(c3);
                    for (char c4 = leftBound[3]; c4 <= rightBound[3]; c4++) {
                        testpsw.append(c4);
                        for (char c5 = leftBound[4]; c5 <= rightBound[4]; c5++) {
                            testpsw.append(c5);
                            md.update(testpsw.toString().getBytes());
                            String hash = new BigInteger(1, md.digest()).toString(16);
                            if (input.equals(hash)) {
                                return testpsw.toString();
                            }
                            testpsw.deleteCharAt(4);
                        }
                        testpsw.deleteCharAt(3);
                    }
                    testpsw.deleteCharAt(2);
                }
                testpsw.deleteCharAt(1);
            }
            testpsw.deleteCharAt(0);
        }
        return "";
    }
}
