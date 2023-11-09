package com.kiva.kivaserverutils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class FileWriteAndLoadStringSet {
    // For strings without newlines
    public static void writeStringSetToFile(final Set<String> stringSet, final String filename){
        try{
            FileWriter f = new FileWriter(filename);
            for (String str : stringSet){
                // Failsafe to prevent any possible injection
                if (str.contains("\n"))
                    continue;

                f.write(str + "\n");
            }
            f.close();
        } catch(IOException e){
            System.err.println("Failed to write string list to file: " + filename);
            e.printStackTrace();
        }
    }

    public static Set<String> loadStringSetFromFile(final String filename){
        Set<String> ret = new HashSet<>();

        try{
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNextLine())
                ret.add(scanner.nextLine());
            scanner.close();
        } catch (FileNotFoundException e){
            System.err.println("Failed to load string list from file: " + filename);
            e.printStackTrace();
        }

        return ret;
    }
}
