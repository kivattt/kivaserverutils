package com.kiva.kivaserverutils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileWriteAndLoadStringProtectedRegionHashmap {
    public static void writeStringProtectedRegionHashmapToFile(final HashMap<String, ProtectedRegion> hashMap, final String filename){
        try{
            FileWriter fileWriter = new FileWriter(filename);
            for (Map.Entry<String, ProtectedRegion> entry : hashMap.entrySet()) {
                fileWriter.write(entry.getKey() + "=" + entry.getValue().toWriteableDataString() + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            System.err.println("Failed to write string & protected region hashmap to file: " + filename);
            e.printStackTrace();
        }
    }

    public static HashMap<String, ProtectedRegion> loadStringProtectedRegionHashmapFromFile(final String filename){
        HashMap<String, ProtectedRegion> ret = new HashMap<>();

        try {
            BufferedReader reader;
            reader = new BufferedReader(new FileReader(filename));

            String line = reader.readLine();
            while (line != null){
                int separatorIndex = line.indexOf('=');
                String regionName = line.substring(0, separatorIndex).trim();
                String regionStr = line.substring(separatorIndex + 1).trim();

                ProtectedRegion protectedRegion = new ProtectedRegion(regionStr);
                ret.put(regionName, protectedRegion);

                line = reader.readLine();
            }
        } catch(IOException e){
            System.err.println("Failed to load string & protected region hashmap from file: " + filename);
            KivaServerUtilsServer.possibleFirstRun = true;
            return new HashMap<>();
        }

        return ret;
    }
}
