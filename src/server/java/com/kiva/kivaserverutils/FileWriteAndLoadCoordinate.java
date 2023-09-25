package com.kiva.kivaserverutils;

import java.io.*;
import java.util.Scanner;

public class FileWriteAndLoadCoordinate {
    public static void writeCoordinateToFile(final Coordinate coordinate, final String filename){
        if (coordinate == null)
            return;

        try {
            FileWriter fileWriter = new FileWriter(filename);
            fileWriter.write(coordinate.x + " " + coordinate.y + " " + coordinate.z);
            fileWriter.close();
        } catch(IOException e){
            System.err.println("Failed to write coordinate to file: " + filename);
            e.printStackTrace();
        }
    }

    public static Coordinate loadCoordinateFromFile(final String filename){
        Coordinate ret = new Coordinate();

        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            ret.x = scanner.nextDouble();
            ret.y = scanner.nextDouble();
            ret.z = scanner.nextDouble();
            scanner.close();
        } catch(FileNotFoundException e){
            System.out.println("Failed to load coordinate from file: " + filename);
            return null;
        }

        return ret;
    }
}
