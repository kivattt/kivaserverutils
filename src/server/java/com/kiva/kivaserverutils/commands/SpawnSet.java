package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.Coordinate;

import static com.kiva.kivaserverutils.UsageMessage.sendUsageMessage;
import static com.kiva.kivaserverutils.KivaServerUtils.spawnCommandLocation;

public class SpawnSet extends CommandCompat{
    public SpawnSet(){
        super("spawnset", true);
    }

    public String commandSyntax(){
        return "§e/spawnset";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor){
        Coordinate pos = new Coordinate();

        pos.x = commandExecutor.getRegisteredX();
        pos.y = commandExecutor.getRegisteredY();
        pos.z = commandExecutor.getRegisteredZ();
        pos.dimension = ServerMod.toEntityPlayerMP(commandExecutor).dimension;

        spawnCommandLocation = pos;
        commandExecutor.displayChatMessage(ChatColors.GREEN + "Spawn location set! [" + ChatColors.RESET + pos.toStringXYZInt() + ChatColors.GREEN + "] in the " + ChatColors.RESET + Coordinate.dimensionToString(pos.dimension));
    }
}
