package org.geniprojects.passwordcracker.master.workers;

import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.geniprojects.passwordcracker.master.server.ServerUtil;
import org.geniprojects.passwordcracker.master.service.ServiceUtil;
import org.geniprojects.passwordcracker.master.workers.interaction.ConnectionUtil;
import org.geniprojects.passwordcracker.master.utils.Request;
import org.geniprojects.passwordcracker.master.utils.Response;
import org.geniprojects.passwordcracker.master.workers.management.ManagementUtil;
import org.geniprojects.passwordcracker.master.workers.management.WorkerPool;

import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class Worker {
    private String ipAddress;
    private int port;
    private Socket socket;
    private WorkerPool workerPool;
    public int id;
    public AtomicBoolean availability;
    private Worker previousWorker;
    private Thread receivingThread;

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

    public synchronized boolean asyncAssign(Request req){
        if (!availability.get()) return false;
        try {
            if (!socket.isConnected() || socket.isClosed()) {
                socket = new Socket(ipAddress, port);
                ManagementUtil.backgroundThreadPool.submit(this::runReceiver);
            }

            Output output = new Output(socket.getOutputStream());
            System.out.println("Going to write");
            ConnectionUtil.serializer.writeObject(output, req);
            System.out.println("Going to send " + req.enCryptedString);
            output.flush();
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void runReceiver() {
        try {
            System.out.println("Waiting for input");
            Input input = new Input(socket.getInputStream());
            Response resp = ConnectionUtil.serializer.readObject(input, Response.class);
            System.out.println("Response received");
            // TODO: publish response to some place.
            ServiceUtil.md5DecryptExecutor.queueCatalog.get(resp.id).put(resp.deCryptedString);
        } catch (Exception e) {
            availability.set(false);

        }

    }

    public Worker getSubsituteWorker() {
        return previousWorker;
    }

}
