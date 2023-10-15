package com.kiva.kivaserverutils;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.commands.*;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class KivaServerUtilsServer extends KivaServerUtils implements ServerMod{
    public void onInit(){
        KivaServerUtils.playerNicknames  = FileWriteAndLoadHashmap.loadHashmapFromFile("mods/KivaServerUtils/playernicknames.properties");
        KivaServerUtils.playerPronouns   = FileWriteAndLoadHashmap.loadHashmapFromFile("mods/KivaServerUtils/playerpronouns.properties");
        KivaServerUtils.playerNameColors = FileWriteAndLoadHashmap.loadHashmapFromFile("mods/KivaServerUtils/playernamecolors.properties");
        KivaServerUtils.playerHomes      = FileWriteAndLoadStringCoordinateHashmap.loadStringCoordinateHashmapFromFile("mods/KivaServerUtils/playerhomes.txt");
        KivaServerUtils.spawnCommandLocation = FileWriteAndLoadCoordinate.loadCoordinateFromFile("mods/KivaServerUtils/spawncommandlocation.txt");
        KivaServerUtils.config = FileWriteAndLoadStringBooleanHashmap.loadStringBooleanHashmapFromFile("mods/KivaServerUtils/config.txt");

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

        CommandCompat.registerCommand(new KivaVersion());
        CommandCompat.registerCommand(new KivaShowConfig());
        CommandCompat.registerCommand(new ExplosionsBreakChests());
        CommandCompat.registerCommand(new HomeCommandsDisabled());
        CommandCompat.registerCommand(new MobcapDisabled());
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
        CommandCompat.registerCommand(new Teleport());
        CommandCompat.registerCommand(new Spawn());
        CommandCompat.registerCommand(new SpawnWhere());
        CommandCompat.registerCommand(new SpawnSet());
        CommandCompat.registerCommand(new SpawnReset());
        CommandCompat.registerCommand(new Home());
        CommandCompat.registerCommand(new HomeWhere());
        CommandCompat.registerCommand(new SetHome());

        System.out.println("KivaServerUtils initialized");
    }

    @Override // TODO: Check if this is necessary or not
    public void onServerStop(NetworkPlayer.ConnectionType connectionType) {
        super.onServerStop(connectionType);

        // Make sure mods/KivaServerUtils directory exists
        Path modsPath = Paths.get("mods/KivaServerUtils");
        try {
            Files.createDirectory(modsPath);
        } catch (IOException e){
            if (!(e instanceof FileAlreadyExistsException)) {
                e.printStackTrace();
                return;
            }
        }
        FileWriteAndLoadHashmap.writeHashmapToFile(KivaServerUtils.playerNicknames, "mods/KivaServerUtils/playernicknames.properties");
        FileWriteAndLoadHashmap.writeHashmapToFile(KivaServerUtils.playerPronouns, "mods/KivaServerUtils/playerpronouns.properties");
        FileWriteAndLoadHashmap.writeHashmapToFile(KivaServerUtils.playerNameColors, "mods/KivaServerUtils/playernamecolors.properties");
        FileWriteAndLoadStringCoordinateHashmap.writeStringCoordinateHashmapToFile(KivaServerUtils.playerHomes, "mods/KivaServerUtils/playerhomes.txt");
        FileWriteAndLoadCoordinate.writeCoordinateToFile(KivaServerUtils.spawnCommandLocation, "mods/KivaServerUtils/spawncommandlocation.txt");
        FileWriteAndLoadStringBooleanHashmap.writeStringBooleanHashmapToFile(KivaServerUtils.config, "mods/KivaServerUtils/config.txt");
    }
}
