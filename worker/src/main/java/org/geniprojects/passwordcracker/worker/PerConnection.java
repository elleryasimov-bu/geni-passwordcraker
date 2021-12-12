package org.geniprojects.passwordcracker.worker;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.net.Socket;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class PerConnection{
    private Socket socket;

    private BlockingQueue<Response> resultQueue = new LinkedBlockingQueue<>();

    private Kryo serializer = Util.initSerializer();

    public PerConnection(Socket socket) {
        this.socket = socket;
    }

    public void receive() {
        try {
            while (true) {
                Input input = new Input(socket.getInputStream());
                System.out.println("Get Input");
                Request req = serializer.readObject(input, Request.class);
                //input.close();


                System.out.println("Successfully Read " + req.enCryptedString);


                //Response resp = new Response(WorkerLogic.decrypt(req.enCryptedString, req.leftBound, req.rightBound));
                Util.computingThreadPool.submit(new Computer(req, resultQueue));

                //output.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try{
                System.out.println("Closing the socket");
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void response() {
        try {
            Output output = new Output(socket.getOutputStream());
            while (!socket.isClosed()) {
                Response response = resultQueue.take();
                System.out.println("Going to write");
                serializer.writeObject(output, response);
                output.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
