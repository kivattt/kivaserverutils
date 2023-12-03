package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.launcher.ServerMain;
import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.Coordinate;

import static com.kiva.kivaserverutils.KivaServerUtils.spawnCommandLocation;

public class Spawn extends CommandCompat{
    public Spawn(){
        super("spawn", false);
    }

    public String commandSyntax(){
        return ChatColors.YELLOW + "/spawn";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor){
        if (spawnCommandLocation == null){
            commandExecutor.displayChatMessage(ChatColors.RED + "No spawn has been set");
            return;
        }

        if (spawnCommandLocation.dimension != ServerMod.toEntityPlayerMP(commandExecutor).dimension)
            commandExecutor.sendPlayerThroughPortalRegistered();

        commandExecutor.teleportRegistered(spawnCommandLocation.x, spawnCommandLocation.y, spawnCommandLocation.z);
        commandExecutor.displayChatMessage(ChatColors.GREEN + "Teleported to spawn!");
    }
}
