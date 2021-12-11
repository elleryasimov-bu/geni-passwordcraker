package org.geniprojects.passwordcracker.master.workers;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.geniprojects.passwordcracker.master.server.ServerUtil;
import org.geniprojects.passwordcracker.master.service.ServiceUtil;
import org.geniprojects.passwordcracker.master.workers.interaction.ConnectionUtil;
import org.geniprojects.passwordcracker.master.utils.Request;
import org.geniprojects.passwordcracker.master.utils.Response;
import org.geniprojects.passwordcracker.master.workers.management.ManagementUtil;
import org.geniprojects.passwordcracker.master.workers.management.WorkerPool;

import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Worker {
    private String ipAddress;
    private int port;
    private Socket socket;
    private WorkerPool workerPool;
    public int id;
    public AtomicBoolean availability = new AtomicBoolean();
    private Worker previousWorker;
    private Thread receivingThread;
    private Kryo serializer = ConnectionUtil.initSerializer();
    private Kryo inputSerializer = ConnectionUtil.initSerializer();
    //private Output output;
    //private Input input;

    public Worker(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public Response assign(Request req) throws Exception {
        Socket socket = new Socket(ipAddress, port);
        System.out.println("Connected");


        Output output = new Output(socket.getOutputStream());
        System.out.println("Going to write");
        serializer.writeObject(output, req);
        System.out.println("Going to send " + req.enCryptedString);
        output.flush();
        System.out.println("Write Successfully");

        System.out.println("Waiting for input");
        Input input = new Input(socket.getInputStream());
        Response resp = serializer.readObject(input, Response.class);
        System.out.println("Response received");


        output.close();
        input.close();
        socket.close();
        return resp;
    }

    public synchronized boolean asyncAssign(Request req){
        //if (!availability.get()) return false;
        try {
            if (socket == null) {
                socket = new Socket(ipAddress, port);
                //output = new Output(socket.getOutputStream());
                //input = new Input(socket.getInputStream());
                ManagementUtil.backgroundThreadPool.submit(this::runReceiver);
                availability.set(true);
            }

            Output output = new Output(socket.getOutputStream());
            System.out.println("Going to write");
            serializer.writeObject(output, req);
            System.out.println("Going to send " + req.enCryptedString);
            output.flush();
            //output.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void runReceiver() {
        //Input input;
        //Input input = null;
        try {
            System.out.println("Waiting for input");
            //input = new Input(socket.getInputStream());
            while (true) {
                Input input = new Input(socket.getInputStream());
                Response resp = inputSerializer.readObject(input, Response.class);
                //input.close();
                System.out.println("Response received: " + resp.id);
                // Fix: Not thread-safe.
                BlockingQueue<String> assignedQueue = ServiceUtil.md5DecryptExecutor.queueCatalog.get(resp.id);
                if (assignedQueue != null) {
                    assignedQueue.put(resp.deCryptedString);
                }
            }
        } catch (Exception e) {
            availability.set(false);
        } finally {
            try {
                //if (input != null) input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Worker getSubsituteWorker() {
        return previousWorker;
    }

    public void setPreviousWorker(Worker worker) {
        previousWorker = worker;
    }

}
