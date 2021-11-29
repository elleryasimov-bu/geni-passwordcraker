package org.geniprojects.passwordcracker.master;

import org.geniprojects.passwordcracker.master.server.HttpServer;

public class Main {
    public static void main(String[] args) throws Exception{
        System.out.println("This is the master.");
        int port = 8080;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        try{
            new HttpServer().run(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
