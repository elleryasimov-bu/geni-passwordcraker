package org.geniprojects.passwordcracker.worker;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.net.Socket;

public class PerConnection implements Runnable{
    private Socket socket;

    public PerConnection(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            while (true) {
                Input input = new Input(socket.getInputStream());
                Output output = new Output(socket.getOutputStream());
                System.out.println("Get Input");
                Request req = Util.serializer.readObject(input, Request.class);
                //input.close();


                System.out.println("Successfully Read " + req.enCryptedString);


                Response resp = new Response(WorkerLogic.decrypt(req.enCryptedString, req.leftBound, req.rightBound));
                resp.id = req.majorId;
                System.out.println("Going to write");
                Util.serializer.writeObject(output, resp);
                output.flush();
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
}
