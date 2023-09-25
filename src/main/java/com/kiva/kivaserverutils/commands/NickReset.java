package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;

import static com.kiva.kivaserverutils.UsageMessage.sendUsageMessage;

public class NickReset extends CommandCompat{
    public NickReset(){
        super("nickreset", false);
    }

    public String commandSyntax(){
        return "§e/nickreset <nickname>";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor){
        if (commandExecutor.isOperator()){
            if (args.length >= 2){
                if (args.length > 2){
                    sendUsageMessage(commandSyntax(), commandExecutor);
                    return;
                }

                KivaServerUtils.playerNicknames.remove(args[1]);
                commandExecutor.displayChatMessage("Nickname of " + args[1] + " reset!");
                return;
            }
        }

        KivaServerUtils.playerNicknames.remove(commandExecutor.getPlayerName());
        commandExecutor.displayChatMessage("Nickname reset!");
    }
}
