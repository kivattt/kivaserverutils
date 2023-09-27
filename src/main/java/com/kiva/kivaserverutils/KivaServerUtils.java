package com.kiva.kivaserverutils;

import com.fox2code.foxloader.loader.Mod;

import java.util.HashMap;

public class KivaServerUtils extends Mod {
    public static HashMap<String, String> playerNicknames = new HashMap<>();
    public static HashMap<String, String> playerPronouns = new HashMap<>();
    public static HashMap<String, Coordinate> playerHomes = new HashMap<>();
    public static Coordinate spawnCommandLocation = null;
    public static String version = "0.6.0";
    public static HashMap<String, Boolean> config = new HashMap<>();

    public static Boolean getConfigValue(String key){
        if (!config.containsKey(key))
            return false;
        return config.get(key);
    }

    @Override
    public void onPreInit(){
        System.out.println("KivaServerUtils initializing...");
    }
}
