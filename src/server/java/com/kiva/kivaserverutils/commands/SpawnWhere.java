package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.Coordinate;

import static com.kiva.kivaserverutils.KivaServerUtils.spawnCommandLocation;

public class SpawnWhere extends CommandCompat{
    public SpawnWhere(){
        super("spawnwhere", false);
    }

    public String commandSyntax(){
        return "§e/spawnwhere";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor){
        if (spawnCommandLocation == null){
            commandExecutor.displayChatMessage(ChatColors.RED + "No spawn has been set");
            return;
        }

        commandExecutor.displayChatMessage(ChatColors.GREEN + "Spawn is at [" + ChatColors.RESET + spawnCommandLocation.toStringXYZInt() + ChatColors.GREEN + "] in the " + ChatColors.RESET + Coordinate.dimensionToString(spawnCommandLocation.dimension));
    }
}
