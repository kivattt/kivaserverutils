package com.kiva.kivaserverutils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileWriteAndLoadCoordinate {
    public static void writeCoordinateToFile(final Coordinate coordinate, final String filename){
        if (coordinate == null) {
            File f = new File(filename);
            if (!f.exists())
                return;

            System.out.println("No spawn set, attempting to delete " + filename);

            if (f.delete())
                System.out.println("Deleted " + filename + " successfully");
            else
                System.err.println("Failed to delete " + filename);

            return;
        }

        try {
            FileWriter fileWriter = new FileWriter(filename);
            fileWriter.write(coordinate.toString());
            fileWriter.close();
        } catch(IOException e){
            System.err.println("Failed to write coordinate to file: " + filename);
            e.printStackTrace();
        }
    }

    public static Coordinate loadCoordinateFromFile(final String filename){
        Coordinate ret = new Coordinate();

        try {
            ret.fromString(Files.readAllLines(Paths.get(filename)).get(0));
        } catch(IOException | IndexOutOfBoundsException e) {
            // Removed since this is expected when no spawn is set, to avoid confusion
            //System.err.println("Failed to load coordinate from file: " + filename);
            return null;
        }

        return ret;
    }
}
