package com.kiva.kivaserverutils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileWriteAndLoadPlayerHomes {
    public static void writePlayerHomesToFile(final HashMap<String, HashMap<String, Coordinate>> hashMap, final String filename){
        try{
            FileWriter fileWriter = new FileWriter(filename);
            for (Map.Entry<String, HashMap<String, Coordinate>> player : hashMap.entrySet()) {
                for (Map.Entry<String, Coordinate> home : player.getValue().entrySet()){
                    String username = player.getKey();
                    String homeName = home.getKey();

                    // Main home
                    if (homeName.isEmpty())
                        fileWriter.write(username + "=" + home.getValue() + "\n");
                    else
                        fileWriter.write( username + "." + homeName + "=" + home.getValue() + "\n");
                }
            }
            fileWriter.close();
        } catch (IOException e) {
            System.err.println("Failed to write player homes to file: " + filename);
            e.printStackTrace();
        }
    }

    public static HashMap<String, HashMap<String, Coordinate>> loadPlayerHomesFromFile(final String filename){
        HashMap<String, HashMap<String, Coordinate>> ret = new HashMap<>();

        try {
            BufferedReader reader;
            reader = new BufferedReader(new FileReader(filename));

            String line = reader.readLine();
            while (line != null){
                int separatorIndex = line.indexOf('=');
                String playerNameAndHomeName = line.substring(0, separatorIndex).trim();
                String coordinateStr = line.substring(separatorIndex + 1).trim();

                Coordinate coordinate = new Coordinate(coordinateStr);

                int homeNameSeparatorIndex = playerNameAndHomeName.indexOf('.');
                String playerName=playerNameAndHomeName; // Backwards compatibility with KivaServerUtils versions below 1.6.3
                String homeName="";

                // A '.' indicates a home name (File format for version 1.6.3 and above)
                if (homeNameSeparatorIndex != -1){
                    playerName = playerNameAndHomeName.substring(0, homeNameSeparatorIndex);
                    homeName = playerNameAndHomeName.substring(homeNameSeparatorIndex + 1);
                }

                ret.putIfAbsent(playerName, new HashMap<>());
                ret.get(playerName).put(homeName, coordinate);

                line = reader.readLine();
            }
        } catch(IOException e){
            System.err.println("Failed to load player homes from file: " + filename);
            return new HashMap<>();
        }

        return ret;
    }
}
