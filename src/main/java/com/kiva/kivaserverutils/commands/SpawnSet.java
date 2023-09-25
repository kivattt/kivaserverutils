package com.kiva.kivaserverutils.commands;

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
        return "§e/spawnset <x> <y> <z>";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor){
        if (args.length != 4){
            sendUsageMessage(commandSyntax(), commandExecutor);
            return;
        }

        Coordinate pos = new Coordinate();

        try {
            pos.x = Double.parseDouble(args[1]);
            pos.y = Double.parseDouble(args[2]);
            pos.z = Double.parseDouble(args[3]);

            spawnCommandLocation = pos;
            commandExecutor.displayChatMessage(ChatColors.GREEN + "Spawn location set! [" + ChatColors.RESET + pos.x + " " + pos.y + " " + pos.z + ChatColors.GREEN + "]" + ChatColors.RESET);
        } catch (NumberFormatException e){
            sendUsageMessage(commandSyntax(), commandExecutor);
        }
    }
}
