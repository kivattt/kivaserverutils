package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;

import static com.kiva.kivaserverutils.UsageMessage.sendUsageMessage;

public class Teleport extends CommandCompat{
    public Teleport(){
        super("teleport", true);
    }

    public String commandSyntax(){
        return ChatColors.YELLOW + "/teleport <x> <y> <z>";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor){
        if (args.length != 4){
            sendUsageMessage(commandSyntax(), commandExecutor);
            return;
        }

        double x, y, z;

        try {
            x = Double.parseDouble(args[1]);
            y = Double.parseDouble(args[2]);
            z = Double.parseDouble(args[3]);
        } catch (NumberFormatException e){
            sendUsageMessage(commandSyntax(), commandExecutor);
            return;
        }

        commandExecutor.teleportRegistered(x, y, z);
    }
}
