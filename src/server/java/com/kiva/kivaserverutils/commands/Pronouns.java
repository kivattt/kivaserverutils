package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.fox2code.foxloader.network.ChatColors;
import com.kiva.kivaserverutils.KivaServerUtils;

import static com.kiva.kivaserverutils.UsageMessage.sendUsageMessage;

public class Pronouns extends CommandCompat{
    public Pronouns(){
        super("pronouns", false);
    }

    public String commandSyntax(){
        return ChatColors.YELLOW + "/pronouns <pronouns>";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor){
        if (args.length != 2){
            sendUsageMessage(this.commandSyntax(), commandExecutor);
            return;
        }

        String pronouns = args[1].toLowerCase();

        if (pronouns.length() > 15){
            commandExecutor.displayChatMessage(ChatColors.RED + "Pronouns too long, max length is 15");
            return;
        }

        String allowedChars = "/abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < pronouns.length(); i++){
            if (allowedChars.indexOf(pronouns.charAt(i)) == -1){
                commandExecutor.displayChatMessage(ChatColors.RED + "Disallowed character(s), only a-z and / allowed");
                return;
            }
        }

        KivaServerUtils.playerPronouns.put(commandExecutor.getPlayerName(), pronouns);
        commandExecutor.displayChatMessage(ChatColors.GREEN + "Pronouns set! [" + ChatColors.RESET + pronouns + ChatColors.GREEN + "]");
    }
}
