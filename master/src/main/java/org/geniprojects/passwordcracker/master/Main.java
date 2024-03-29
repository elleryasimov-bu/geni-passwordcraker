package org.geniprojects.passwordcracker.master;

import org.geniprojects.passwordcracker.master.server.HttpServer;
import org.geniprojects.passwordcracker.master.workers.Worker;
import org.geniprojects.passwordcracker.master.workers.management.ManagementUtil;

public class Main {
    public static void main(String[] args) throws Exception{
        System.out.println("This is the master.");
        int port = 8080;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        ManagementUtil.workerPool.addWorker(new Worker("127.0.0.1", 50000));

        try{
            new HttpServer().run(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
