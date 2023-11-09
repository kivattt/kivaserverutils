package com.kiva.kivaserverutils;

import com.fox2code.foxloader.loader.Mod;
import com.fox2code.foxloader.network.ChatColors;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class KivaServerUtils extends Mod {
    public static HashMap<String, String> playerNicknames = new HashMap<>();
    public static HashMap<String, String> playerPronouns = new HashMap<>();
    public static HashMap<String, String> playerNameColors = new HashMap<>();
    public static Set<String> playersInRestrictiveMode;
    public static Set<String> playersExcludedFromRestrictiveMode;
    public static Set<String> playersMuted;
    public static String defaultPlayerNameColor = ChatColors.AQUA;

    public static HashMap<String, String> colorNames = new HashMap<>();

    public static HashMap<String, Coordinate> playerHomes = new HashMap<>();
    public static Coordinate spawnCommandLocation = null;
    public static String version = "1.3.0";
    public static String KSUBroadcastPrefix = ChatColors.DARK_GRAY + "[" + ChatColors.GRAY + "KSU" + ChatColors.DARK_GRAY + "] " + ChatColors.RESET;
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

    public static String notifyPlayerIsInRestrictiveMode = ChatColors.RED + "You are currently in restrictive mode";

    public static Boolean isPlayerInRestrictiveMode(final String username){
        if (!KivaServerUtils.getConfigValue("restrictbydefault"))
            return KivaServerUtils.playersInRestrictiveMode.contains(username);
        else
            return !KivaServerUtils.playersExcludedFromRestrictiveMode.contains(username);
    }

    public static Boolean togglePlayerRestrictiveMode(final String username){
        if (isPlayerInRestrictiveMode(username))
            KivaServerUtils.playersInRestrictiveMode.remove(username);
        else
            KivaServerUtils.playersInRestrictiveMode.add(username);

        return isPlayerInRestrictiveMode(username);
    }

    public static Boolean isPlayerExcludedFromRestrictiveMode(final String username){
        return KivaServerUtils.playersExcludedFromRestrictiveMode.contains(username);
    }

    public static Boolean toggleExcludePlayerFromRestrictiveMode(final String username){
        if (isPlayerExcludedFromRestrictiveMode(username))
            KivaServerUtils.playersExcludedFromRestrictiveMode.remove(username);
        else
            KivaServerUtils.playersExcludedFromRestrictiveMode.add(username);

        return isPlayerExcludedFromRestrictiveMode(username);
    }

    public static Boolean isPlayerMuted(final String username){
        return KivaServerUtils.playersMuted.contains(username);
    }

    public static Boolean togglePlayerMuted(final String username){
        if (isPlayerMuted(username))
            KivaServerUtils.playersMuted.remove(username);
        else
            KivaServerUtils.playersMuted.add(username);

        return isPlayerMuted(username);
    }

    @Override
    public void onPreInit(){
        System.out.println("KivaServerUtils initializing...");
    }
}
