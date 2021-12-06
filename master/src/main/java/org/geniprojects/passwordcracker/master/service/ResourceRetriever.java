package org.geniprojects.passwordcracker.master.service;

import org.geniprojects.passwordcracker.master.workers.management.WorkerPool;

import java.io.*;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class ResourceRetriever {

    public static File retrieveResourceFile(String path){
        File f = null;
        try {
            f = new File(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

    public static String retrieveResourceText(String path) {
        String content = "";

        try (InputStream in = ResourceRetriever.class.getResourceAsStream(path)) {
            content = new BufferedReader(
                    new InputStreamReader(in, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return content;
    }

    public static void main(String args[]) throws IOException {

        System.out.println(retrieveResourceText("/client.html"));
    }
}