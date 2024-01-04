package com.kiva.kivaserverutils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriteAndLoadInteger {
    public static void writeIntegerToFile(final Integer i, final String filename){
        try {
            FileWriter fileWriter = new FileWriter(filename);
            fileWriter.write(i + "\n");
            fileWriter.close();
        } catch(Throwable e){
            System.err.println("Failed to write integer to file: " + filename);
            e.printStackTrace();
        }
    }

    public static Integer loadIntegerFromFile(final String filename){
        try{
            BufferedReader reader;
            reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            return Integer.parseInt(line);
        } catch(IOException e){
            System.err.println("Failed to load integer from file: " + filename);
        }

        return null;
    }
}
