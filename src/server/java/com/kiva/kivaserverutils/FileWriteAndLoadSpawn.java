package com.kiva.kivaserverutils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileWriteAndLoadSpawn {
    public static void writeSpawnToFile(final Coordinate coordinate, final String filename){
        if (coordinate == null) {
            File f = new File(filename);
            if (!f.exists())
                return;

            System.out.println("No spawn set, deleting " + filename);

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
            System.err.println("Failed to write spawn coordinate to file: " + filename);
            e.printStackTrace();
        }
    }

    public static Coordinate loadSpawnFromFile(final String filename){
        Coordinate ret = new Coordinate();

        try {
            ret.fromString(Files.readAllLines(Paths.get(filename)).get(0));
        } catch(IOException | IndexOutOfBoundsException e) {
            // No error msg since it's expected when no spawn is set, to avoid confusion
            return null;
        }

        return ret;
    }
}
