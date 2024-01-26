package com.kiva.kivaserverutils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileWriteAndLoadWarps {
    public static void writeWarpsToFile(final HashMap<String, Coordinate> warps, final String filename){
        try {
            FileWriter fileWriter = new FileWriter(filename);
            for (Map.Entry<String, Coordinate> e : warps.entrySet())
                fileWriter.write(e.getKey() + "=" + e.getValue().toString() + "\n");
            fileWriter.close();
        } catch(IOException e){
            System.err.println("Failed to write warps to file: " + filename);
            e.printStackTrace();
        }
    }

    public static HashMap<String, Coordinate> loadWarpsFromFile(final String filename){
        HashMap<String, Coordinate> ret = new HashMap<>();

        try {
            BufferedReader reader;
            reader = new BufferedReader(new FileReader(filename));

            String line = reader.readLine();
            while (line != null){
                int separatorIndex = line.indexOf('=');
                String warpName = line.substring(0, separatorIndex).trim();
                Coordinate warpCoordinate = new Coordinate(line.substring(separatorIndex + 1).trim());

                ret.put(warpName, warpCoordinate);

                line = reader.readLine();
            }
        } catch(IOException e){
            System.err.println("Failed to load warps from file: " + filename);
            KivaServerUtilsServer.possibleFirstRun = true;
            return new HashMap<>();
        }

        return ret;
    }
}
