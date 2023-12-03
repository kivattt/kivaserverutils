package com.kiva.kivaserverutils;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.fox2code.foxloader.registry.RegisteredItemStack;
import com.kiva.kivaserverutils.commands.*;
import net.minecraft.src.game.entity.player.EntityPlayerMP;
import net.minecraft.src.game.item.ItemFood;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

public class KivaServerUtilsServer extends KivaServerUtils implements ServerMod{
    public static final String KSUBasePath = "mods/KivaServerUtils/";
    public static final String playerNicknamesFilename                = KSUBasePath + "playernicknames.properties";
    public static final String playerPronounsFilename                 = KSUBasePath + "playerpronouns.properties";
    public static final String playerNameColorsFilename               = KSUBasePath + "playernamecolors.properties";
    public static final String playerHomesFilename                    = KSUBasePath + "playerhomes.txt";
    public static final String playersInRestrictiveModeFilename       = KSUBasePath + "restrictivemodeplayers.txt";
    public static final String playersExcludedRestrictiveModeFilename = KSUBasePath + "restrictivemodeplayersexcluded.txt";
    public static final String playersMutedFilename                   = KSUBasePath + "playersmuted.txt";
    public static final String protectedRegionsFilename               = KSUBasePath + "protectedregions.txt";
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
        KivaServerUtils.protectedRegions                   = FileWriteAndLoadStringProtectedRegionHashmap.loadStringProtectedRegionHashmapFromFile(protectedRegionsFilename);
        KivaServerUtils.spawnCommandLocation               = FileWriteAndLoadCoordinate.loadCoordinateFromFile(spawnCommandLocationFilename);
        KivaServerUtils.config                             = FileWriteAndLoadStringBooleanHashmap.loadStringBooleanHashmapFromFile(configFilename);

        if (possibleFirstRun) {
            System.out.println("+----------------------------------------------------------------------+");
            System.out.println("|                                                                      |");
            System.err.println("|        HEADS UP!!!        [KivaServerUtils]        HEADS UP!!!       |");
            System.out.println("| If you see a bunch of errors above, like \"Failed to load ...\", it is |");
            System.out.println("| perfectly normal for a first-time run, or update of KivaServerUtils. |");
            System.out.println("|                           (Just ignore it)                           |");
            System.out.println("|                                  :3                                  |");
            System.out.println("+----------------------------------------------------------------------+");
        }

        // Let me know if there's a better way to do this (Probably .toString with toLower())
        nameColorChoicesNames.put("white", ChatColors.WHITE);
        nameColorChoicesNames.put("gray", ChatColors.GRAY);
        nameColorChoicesNames.put("darkgray", ChatColors.DARK_GRAY);
        nameColorChoicesNames.put("black", ChatColors.BLACK);

        nameColorChoicesNames.put("yellow", ChatColors.YELLOW);
        nameColorChoicesNames.put("gold", ChatColors.GOLD);

        nameColorChoicesNames.put("green", ChatColors.GREEN);
        nameColorChoicesNames.put("darkgreen", ChatColors.DARK_GREEN);

        nameColorChoicesNames.put("aqua", ChatColors.AQUA); // Default color
        nameColorChoicesNames.put("darkaqua", ChatColors.DARK_AQUA);
        nameColorChoicesNames.put("blue", ChatColors.BLUE);
        nameColorChoicesNames.put("darkblue", ChatColors.DARK_BLUE);

        nameColorChoicesNames.put("pink", ChatColors.LIGHT_PURPLE);
        nameColorChoicesNames.put("purple", ChatColors.DARK_PURPLE);
        nameColorChoicesNames.put("red", ChatColors.DARK_RED);

        //colorNames.put("red", ChatColors.RED); // Reserved for operators as default color
        //colorNames.put("rainbow", ChatColors.RAINBOW); // Bugged in ReIndev 2.8.1_04

        CommandCompat.registerCommand(new Nick());
        CommandCompat.registerCommand(new NameOf());
        CommandCompat.registerCommand(new NickList());
        CommandCompat.registerCommand(new NickListAll());
        CommandCompat.registerCommand(new NickSet());
        CommandCompat.registerCommand(new NickReset());

        CommandCompat.registerCommand(new NameColor());
        CommandCompat.registerCommand(new NameColorReset());

        CommandCompat.registerCommand(new Pronouns());
        CommandCompat.registerCommand(new PronounsList());
        CommandCompat.registerCommand(new PronounsListAll());
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
        CommandCompat.registerCommand(new MuteListAll());

        CommandCompat.registerCommand(new Restrict());
        CommandCompat.registerCommand(new RestrictList());
        CommandCompat.registerCommand(new RestrictListAll());

        CommandCompat.registerCommand(new RestrictExclude());
        CommandCompat.registerCommand(new RestrictExcludeList());
        CommandCompat.registerCommand(new RestrictExcludeListAll());

        CommandCompat.registerCommand(new RestrictByDefault());

        CommandCompat.registerCommand(new KivaShowConfig());
        CommandCompat.registerCommand(new ExplosionsBreakChests());
        CommandCompat.registerCommand(new HomeCommandsDisabled());
        CommandCompat.registerCommand(new MobcapDisabled());

        CommandCompat.registerCommand(new Teleport());
        CommandCompat.registerCommand(new KivaVersion());

        CommandCompat.registerCommand(new Protect());

        CommandCompat.registerCommand(new TPA());
        CommandCompat.registerCommand(new TPARevoke());
        CommandCompat.registerCommand(new TPAccept());
        CommandCompat.registerCommand(new TPDeny());
        CommandCompat.registerCommand(new TPACommandsDisabled());

        System.out.println("KivaServerUtils initialized");
    }

    // Let's revoke all tpa requests from the player on disconnect
    @Override
    public boolean onNetworkPlayerDisconnected(NetworkPlayer networkPlayer, String kickMessage, boolean cancelled) {
        for (Map.Entry<String, ArrayList<String>> entry : KivaServerUtils.tpaRequests.entrySet())
            entry.getValue().remove(networkPlayer.getPlayerName());
        return false;
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
        FileWriteAndLoadStringProtectedRegionHashmap.writeStringProtectedRegionHashmapToFile(KivaServerUtils.protectedRegions, protectedRegionsFilename);
        FileWriteAndLoadCoordinate.writeCoordinateToFile(KivaServerUtils.spawnCommandLocation, spawnCommandLocationFilename);
        FileWriteAndLoadStringBooleanHashmap.writeStringBooleanHashmapToFile(KivaServerUtils.config, configFilename);
    }

    public boolean handleRestrictiveModeAndRegionProtection(NetworkPlayer networkPlayer, final int x, final int y, final int z){
        return handleRestrictiveModeAndRegionProtection(networkPlayer, x, y, z, true);
    }
    public boolean handleRestrictiveModeAndRegionProtection(NetworkPlayer networkPlayer, final int x, final int y, final int z, boolean sendNotifyMsg){
        boolean isRestrictiveMode = KivaServerUtils.isPlayerInRestrictiveMode(networkPlayer.getPlayerName());
        Pair<String, Boolean> protectedRegion = KivaServerUtils.inProtectedRegion(x, y, z, ((EntityPlayerMP)networkPlayer).dimension);

        if (sendNotifyMsg) {
            if (isRestrictiveMode)
                networkPlayer.displayChatMessage(KivaServerUtils.notifyPlayerIsInRestrictiveMode);
            else if (protectedRegion.second)
                networkPlayer.displayChatMessage(KivaServerUtils.notifyProtectedRegion + protectedRegion.first);
        }

        return isRestrictiveMode | protectedRegion.second;
    }

    // Prevents breaking blocks while in restrictive mode
    @Override
    public boolean onPlayerStartBreakBlock(NetworkPlayer networkPlayer, RegisteredItemStack itemStack, int x, int y, int z, int facing, boolean cancelled) {
        // We don't output notify message while in survival, would be too much output in chat with it
        // Has to be enabled for creative mode, since this event actually cancels properly in creative mode
        return handleRestrictiveModeAndRegionProtection(networkPlayer, x, y, z, ((EntityPlayerMP)networkPlayer).gamemode != 0);
    }

    @Override
    public boolean onPlayerBreakBlock(NetworkPlayer networkPlayer, RegisteredItemStack itemStack, int x, int y, int z, int facing, boolean cancelled) {
        return handleRestrictiveModeAndRegionProtection(networkPlayer, x, y, z);
    }

    // Prevents placing blocks while in restrictive mode
    @Override
    public boolean onPlayerUseItemOnBlock(NetworkPlayer networkPlayer, RegisteredItemStack itemStack, int x, int y, int z, int facing, float xOffset, float yOffset, float zOffset, boolean cancelled) {
        return handleRestrictiveModeAndRegionProtection(networkPlayer, x, y, z);
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
