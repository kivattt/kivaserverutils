package com.kiva.kivaserverutils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriteAndLoadDouble {
    public static void writeDoubleToFile(final Double i, final String filename){
        try {
            FileWriter fileWriter = new FileWriter(filename);
            fileWriter.write(i + "\n");
            fileWriter.close();
        } catch(Throwable e){
            System.err.println("Failed to write double to file: " + filename);
            e.printStackTrace();
        }
    }

    public static Double loadDoubleFromFile(final String filename){
        try{
            BufferedReader reader;
            reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            return Double.parseDouble(line);
        } catch(IOException e){
            System.err.println("Failed to load double from file: " + filename);
        }

        return null;
    }
}
