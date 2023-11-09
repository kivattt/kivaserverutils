package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.loader.ServerMod;
import com.fox2code.foxloader.network.ChatColors;
import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;

import static com.kiva.kivaserverutils.UsageMessage.sendUsageMessage;

public class PronounsSet extends CommandCompat{
    public PronounsSet(){
        super("pronounsset", true);
    }

    public String commandSyntax(){
        return "§e/pronounsset <player> <pronouns>";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor){
        if (args.length != 3){
            sendUsageMessage(this.commandSyntax(), commandExecutor);
            return;
        }

        String pronouns = args[2].toLowerCase();

        if (pronouns.length() > 15){
            commandExecutor.displayChatMessage("Pronouns too long, max length is 15");
            return;
        }

        String allowedChars = "/abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < pronouns.length(); i++){
            if (allowedChars.indexOf(pronouns.charAt(i)) == -1){
                commandExecutor.displayChatMessage("Disallowed character(s), only a-z and / allowed");
                return;
            }
        }

        KivaServerUtils.playerPronouns.put(args[1], pronouns);
        ServerMod.getGameInstance().configManager.sendChatMessageToAllOps("Pronouns of " + args[1] + " set! [" + ChatColors.GREEN + pronouns + ChatColors.RESET + "]");
    }
}
