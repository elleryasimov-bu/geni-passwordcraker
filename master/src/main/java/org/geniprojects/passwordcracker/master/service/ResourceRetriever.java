package org.geniprojects.passwordcracker.master.service;

import java.io.IOException;

import java.io.File;

import java.net.URL;
import java.nio.file.Files;

import java.nio.file.Path;
import java.nio.file.Paths;

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

        try {
            //content = new String(Files.readAllBytes(Paths.get(path)));
            URL url  = ResourceRetriever.class.getResource(path);
            content = new String(Files.readAllBytes(Paths.get(url.toURI())));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return content;
    }

    public static void main(String args[]) throws IOException {
        String filePath = "C:/Users/Zimou Sun/Desktop/laioffer.txt";

        System.out.println(retrieveResourceText("/client.html"));
    }
}