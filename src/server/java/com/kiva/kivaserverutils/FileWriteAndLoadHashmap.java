package com.kiva.kivaserverutils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class FileWriteAndLoadHashmap {
    public static void writeHashmapToFile(final HashMap<String, String> hashMap, final String filename){
        Properties p = new Properties();
        p.putAll(hashMap);
        try {
            p.store(new FileOutputStream(filename), null);
        } catch(Throwable e){
            System.err.println("Failed to write hashmap to file: " + filename);
            e.printStackTrace();
        }
    }

    public static HashMap<String, String> loadHashmapFromFile(final String filename){
        HashMap<String, String> ret = new HashMap<>();

        try (InputStream file = new FileInputStream(filename)) {
            Properties p = new Properties();
            p.load(file);

            for (final String key : p.stringPropertyNames())
                ret.put(key, p.getProperty(key));
        } catch(IOException e){
            System.err.println("Failed to load hashmap from file: " + filename);
            KivaServerUtilsServer.possibleFirstRun = true;
        }

        return ret;
    }
}
