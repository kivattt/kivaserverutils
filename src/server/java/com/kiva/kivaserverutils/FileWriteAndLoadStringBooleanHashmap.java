package com.kiva.kivaserverutils;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class FileWriteAndLoadStringBooleanHashmap {
    public static void writeStringBooleanHashmapToFile(final HashMap<String, Boolean> hashMap, final String filename){
        Properties p = new Properties();
        //p.putAll(hashMap);
        for (Map.Entry<String, Boolean> entry : hashMap.entrySet())
            p.put(entry.getKey(), entry.getValue().toString());

        try {
            p.store(new FileOutputStream(filename), null);
        } catch(Throwable e){
            System.err.println("Failed to write string & boolean hashmap to file: " + filename);
            e.printStackTrace();
        }
    }

    public static HashMap<String, Boolean> loadStringBooleanHashmapFromFile(final String filename){
        HashMap<String, Boolean> ret = new HashMap<>();

        try {
            BufferedReader reader;
            reader = new BufferedReader(new FileReader(filename));

            String line = reader.readLine();
            while (line != null){
                if (line.startsWith("#") || line.isEmpty()) {
                    line = reader.readLine();
                    continue;
                }

                int separatorIndex = line.indexOf('=');
                String playerName = line.substring(0, separatorIndex).trim();
                Boolean value = line.substring(separatorIndex + 1).trim().equalsIgnoreCase("true");
                ret.put(playerName, value);

                line = reader.readLine();
            }
        } catch(IOException e){
            System.err.println("Failed to load string & boolean hashmap from file: " + filename);
            return new HashMap<>();
        }

        return ret;
    }
}
