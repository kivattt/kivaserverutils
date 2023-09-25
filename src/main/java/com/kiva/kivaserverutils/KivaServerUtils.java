package com.kiva.kivaserverutils;

import com.fox2code.foxloader.loader.Mod;

import java.util.HashMap;

public class KivaServerUtils extends Mod {
    public static HashMap<String, String> playerNicknames = new HashMap<>();
    public static HashMap<String, String> playerPronouns = new HashMap<>();
    public static HashMap<String, Coordinate> playerHomes = new HashMap<>();
    public static Coordinate spawnCommandLocation = null;
    public static String version = "0.5.0";

    @Override
    public void onPreInit(){
        System.out.println("KivaServerUtils initializing...");
    }
}
