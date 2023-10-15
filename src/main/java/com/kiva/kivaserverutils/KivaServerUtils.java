package com.kiva.kivaserverutils;

import com.fox2code.foxloader.loader.Mod;
import com.fox2code.foxloader.network.ChatColors;

import java.util.HashMap;

public class KivaServerUtils extends Mod {
    public static HashMap<String, String> playerNicknames = new HashMap<>();
    public static HashMap<String, String> playerPronouns = new HashMap<>();
    public static HashMap<String, String> playerNameColors = new HashMap<>();
    public static String defaultPlayerNameColor = ChatColors.AQUA;

    public static HashMap<String, String> colorNames = new HashMap<>();

    public static HashMap<String, Coordinate> playerHomes = new HashMap<>();
    public static Coordinate spawnCommandLocation = null;
    public static String version = "1.1.0";
    public static HashMap<String, Boolean> config = new HashMap<>();
    public static String handleWindowClickLatestPlayerUsername;

    public static String getPlayerNameColor(final String playerName){
        if (playerNameColors == null)
            return defaultPlayerNameColor;

        String color = playerNameColors.get(playerName);
        return color == null ? defaultPlayerNameColor : color;
    }

    public static Boolean getConfigValue(String key){
        Boolean value = config.get(key);
        return value == null ? false : value;
    }

    @Override
    public void onPreInit(){
        System.out.println("KivaServerUtils initializing...");
    }
}
