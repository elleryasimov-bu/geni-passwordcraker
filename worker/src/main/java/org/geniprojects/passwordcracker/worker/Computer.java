package org.geniprojects.passwordcracker.worker;

import java.security.NoSuchAlgorithmException;
import java.util.concurrent.BlockingQueue;

public class Computer implements Runnable{
    private BlockingQueue<Response> resultQueue;
    private Request request;
    //private int id;

    public Computer(Request request, BlockingQueue<Response> resultQueue) {
        //this.id = id;
        this.resultQueue = resultQueue;
        this.request = request;
    }

    public void run() {
        String decryptedString = "";
        if (Util.md5Map.containsKey(request.enCryptedString)) {
            decryptedString = Util.md5Map.get(request.enCryptedString);
        } else {
            try {
                decryptedString = WorkerLogic.decrypt(request.enCryptedString, request.leftBound, request.rightBound);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        Response response = new Response(decryptedString);
        response.id = request.majorId;
        try {
            if (resultQueue != null) resultQueue.put(response);
        } catch (InterruptedException e) {
            System.out.println("Interrupted");
            e.printStackTrace();
        }
    }
}
