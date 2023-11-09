package com.kiva.kivaserverutils;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.fox2code.foxloader.registry.RegisteredItemStack;
import com.kiva.kivaserverutils.commands.*;
import net.minecraft.src.game.item.ItemFood;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class KivaServerUtilsServer extends KivaServerUtils implements ServerMod{
    public static final String KSUBasePath = "mods/KivaServerUtils/";
    public static final String playerNicknamesFilename                = KSUBasePath + "playernicknames.properties";
    public static final String playerPronounsFilename                 = KSUBasePath + "playerpronouns.properties";
    public static final String playerNameColorsFilename               = KSUBasePath + "playernamecolors.properties";
    public static final String playerHomesFilename                    = KSUBasePath + "playerhomes.txt";
    public static final String playersInRestrictiveModeFilename       = KSUBasePath + "restrictivemodeplayers.txt";
    public static final String playersExcludedRestrictiveModeFilename = KSUBasePath + "restrictivemodeplayersexcluded.txt";
    public static final String playersMutedFilename                   = KSUBasePath + "playersmuted.txt";
    public static final String spawnCommandLocationFilename           = KSUBasePath + "spawncommandlocation.txt";
    public static final String configFilename                         = KSUBasePath + "config.txt";

    public static boolean possibleFirstRun = false;

    public void onInit(){
        KivaServerUtils.playerNicknames                    = FileWriteAndLoadHashmap.loadHashmapFromFile(playerNicknamesFilename);
        KivaServerUtils.playerPronouns                     = FileWriteAndLoadHashmap.loadHashmapFromFile(playerPronounsFilename);
        KivaServerUtils.playerNameColors                   = FileWriteAndLoadHashmap.loadHashmapFromFile(playerNameColorsFilename);
        KivaServerUtils.playerHomes                        = FileWriteAndLoadStringCoordinateHashmap.loadStringCoordinateHashmapFromFile(playerHomesFilename);
        KivaServerUtils.playersInRestrictiveMode           = FileWriteAndLoadStringSet.loadStringSetFromFile(playersInRestrictiveModeFilename);
        KivaServerUtils.playersExcludedFromRestrictiveMode = FileWriteAndLoadStringSet.loadStringSetFromFile(playersExcludedRestrictiveModeFilename);
        KivaServerUtils.playersMuted                       = FileWriteAndLoadStringSet.loadStringSetFromFile(playersMutedFilename);
        KivaServerUtils.spawnCommandLocation               = FileWriteAndLoadCoordinate.loadCoordinateFromFile(spawnCommandLocationFilename);
        KivaServerUtils.config                             = FileWriteAndLoadStringBooleanHashmap.loadStringBooleanHashmapFromFile(configFilename);

        if (possibleFirstRun) {
            System.out.println("+----------------------------------------------------------------------+");
            System.out.println("|                                                                      |");
            System.err.println("|        HEADS UP!!!        [KivaServerUtils]        HEADS UP!!!       |");
            System.out.println("| If you see a bunch of errors above, like \"Failed to load ...\",       |");
            System.out.println("| It is perfectly normal for a first-time run of KivaServerUtils       |");
            System.out.println("|                           (Just ignore it)                           |");
            System.out.println("|                                  :3                                  |");
            System.out.println("+----------------------------------------------------------------------+");
        }

        // Let me know if there's a better way to do this
        colorNames.put("black", ChatColors.BLACK);
        colorNames.put("darkblue", ChatColors.DARK_BLUE);
        colorNames.put("darkgreen", ChatColors.DARK_GREEN);
        colorNames.put("darkaqua", ChatColors.DARK_AQUA);
        colorNames.put("darkred", ChatColors.DARK_RED);
        colorNames.put("darkpurple", ChatColors.DARK_PURPLE);
        colorNames.put("gold", ChatColors.GOLD);
        colorNames.put("gray", ChatColors.GRAY);
        colorNames.put("darkgray", ChatColors.DARK_GRAY);
        colorNames.put("blue", ChatColors.BLUE);
        colorNames.put("aqua", ChatColors.AQUA); // Default color
        //colorNames.put("red", ChatColors.RED); // Reserved for operators as default color
        colorNames.put("lightpurple", ChatColors.LIGHT_PURPLE);
        colorNames.put("yellow", ChatColors.YELLOW);
        colorNames.put("white", ChatColors.WHITE);
        //colorNames.put("rainbow", ChatColors.RAINBOW); // Bugged in ReIndev 2.8.1_04

        CommandCompat.registerCommand(new Nick());
        CommandCompat.registerCommand(new NickList());
        CommandCompat.registerCommand(new NickSet());
        CommandCompat.registerCommand(new NickReset());

        CommandCompat.registerCommand(new NameColor());
        CommandCompat.registerCommand(new NameColorReset());

        CommandCompat.registerCommand(new Pronouns());
        CommandCompat.registerCommand(new PronounsList());
        CommandCompat.registerCommand(new PronounsSet());
        CommandCompat.registerCommand(new PronounsReset());

        CommandCompat.registerCommand(new Spawn());
        CommandCompat.registerCommand(new SpawnWhere());
        CommandCompat.registerCommand(new SpawnSet());
        CommandCompat.registerCommand(new SpawnReset());

        CommandCompat.registerCommand(new Home());
        CommandCompat.registerCommand(new HomeWhere());
        CommandCompat.registerCommand(new SetHome());

        CommandCompat.registerCommand(new Mute());
        CommandCompat.registerCommand(new MuteList());

        CommandCompat.registerCommand(new Restrict());
        CommandCompat.registerCommand(new RestrictList());
        CommandCompat.registerCommand(new RestrictExclude());
        CommandCompat.registerCommand(new RestrictExcludeList());
        CommandCompat.registerCommand(new RestrictByDefault());

        CommandCompat.registerCommand(new KivaShowConfig());
        CommandCompat.registerCommand(new ExplosionsBreakChests());
        CommandCompat.registerCommand(new HomeCommandsDisabled());
        CommandCompat.registerCommand(new MobcapDisabled());

        CommandCompat.registerCommand(new Teleport());
        CommandCompat.registerCommand(new KivaVersion());

        System.out.println("KivaServerUtils initialized");
    }

    @Override
    public void onServerStop(NetworkPlayer.ConnectionType connectionType) {
        super.onServerStop(connectionType);

        // Make sure mods/KivaServerUtils directory exists
        Path modsPath = Paths.get(KSUBasePath);
        try {
            Files.createDirectory(modsPath);
        } catch (IOException e){
            if (!(e instanceof FileAlreadyExistsException)) {
                System.err.println(KSUBasePath + " was not found, unable to store mod data");
                e.printStackTrace();
                return;
            }
        }
        FileWriteAndLoadHashmap.writeHashmapToFile(KivaServerUtils.playerNicknames, playerNicknamesFilename);
        FileWriteAndLoadHashmap.writeHashmapToFile(KivaServerUtils.playerPronouns, playerPronounsFilename);
        FileWriteAndLoadHashmap.writeHashmapToFile(KivaServerUtils.playerNameColors, playerNameColorsFilename);
        FileWriteAndLoadStringCoordinateHashmap.writeStringCoordinateHashmapToFile(KivaServerUtils.playerHomes, playerHomesFilename);
        FileWriteAndLoadStringSet.writeStringSetToFile(KivaServerUtils.playersInRestrictiveMode, playersInRestrictiveModeFilename);
        FileWriteAndLoadStringSet.writeStringSetToFile(KivaServerUtils.playersExcludedFromRestrictiveMode, playersExcludedRestrictiveModeFilename);
        FileWriteAndLoadStringSet.writeStringSetToFile(KivaServerUtils.playersMuted, playersMutedFilename);
        FileWriteAndLoadCoordinate.writeCoordinateToFile(KivaServerUtils.spawnCommandLocation, spawnCommandLocationFilename);
        FileWriteAndLoadStringBooleanHashmap.writeStringBooleanHashmapToFile(KivaServerUtils.config, configFilename);
    }

    // Prevents breaking blocks while in restrictive mode
    @Override
    public boolean onPlayerStartBreakBlock(NetworkPlayer networkPlayer, RegisteredItemStack itemStack, int x, int y, int z, int facing, boolean cancelled) {
        boolean isRestrictiveMode = KivaServerUtils.isPlayerInRestrictiveMode(networkPlayer.getPlayerName());

        if (isRestrictiveMode)
            networkPlayer.displayChatMessage(KivaServerUtils.notifyPlayerIsInRestrictiveMode);

        return isRestrictiveMode;
    }

    // Prevents placing blocks while in restrictive mode
    @Override
    public boolean onPlayerUseItemOnBlock(NetworkPlayer networkPlayer, RegisteredItemStack itemStack, int x, int y, int z, int facing, float xOffset, float yOffset, float zOffset, boolean cancelled) {
        boolean isRestrictiveMode = KivaServerUtils.isPlayerInRestrictiveMode(networkPlayer.getPlayerName());

        if (isRestrictiveMode)
            networkPlayer.displayChatMessage(KivaServerUtils.notifyPlayerIsInRestrictiveMode);

        return isRestrictiveMode;
    }

    // Prevents using items like buckets to place water/lava when in restrictive mode
    @Override
    public boolean onPlayerUseItem(NetworkPlayer networkPlayer, RegisteredItemStack itemStack, boolean cancelled) {
        // Still allow players in restrictive mode to eat food
        if (itemStack.getRegisteredItem() instanceof ItemFood)
            return false;

        boolean isRestrictiveMode = KivaServerUtilsServer.isPlayerInRestrictiveMode(networkPlayer.getPlayerName());

        if (isRestrictiveMode)
            networkPlayer.displayChatMessage(KivaServerUtils.notifyPlayerIsInRestrictiveMode);

        return isRestrictiveMode;
    }
}
