package com.kiva.kivaserverutils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileWriteAndLoadStringCoordinateHashmap {
    public static void writeStringCoordinateHashmapToFile(final HashMap<String, Coordinate> hashMap, final String filename){
        try{
            FileWriter fileWriter = new FileWriter(filename);
            for (Map.Entry<String, Coordinate> entry : hashMap.entrySet()) {
                fileWriter.write(entry.getKey() + "=" + entry.getValue() + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            System.err.println("Failed to write string & coordinate hashmap to file: " + filename);
            e.printStackTrace();
        }
    }

    public static HashMap<String, Coordinate> loadStringCoordinateHashmapFromFile(final String filename){
        HashMap<String, Coordinate> ret = new HashMap<>();

        try {
            BufferedReader reader;
            reader = new BufferedReader(new FileReader(filename));

            String line = reader.readLine();
            while (line != null){
                int separatorIndex = line.indexOf('=');
                String playerName = line.substring(0, separatorIndex).trim();
                String coordinateStr = line.substring(separatorIndex + 1).trim();

                Coordinate coordinate = new Coordinate(coordinateStr);
                ret.put(playerName, coordinate);

                line = reader.readLine();
            }
        } catch(IOException e){
            System.err.println("Failed to load string & coordinate hashmap from file: " + filename);
            return new HashMap<>();
        }

        return ret;
    }
}
