package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;

import static com.kiva.kivaserverutils.UsageMessage.sendUsageMessage;

public class NickSet extends CommandCompat{
    public NickSet(){
        super("nickset", true);
    }

    public String commandSyntax(){
        return "§e/nickset <player> <nickname>";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor){
        if (args.length != 3){
            sendUsageMessage(this.commandSyntax(), commandExecutor);
            return;
        }

        String nickname = args[2];
        String nicknameLowerCase = nickname.toLowerCase();

        if (nickname.length() > 16){
            commandExecutor.displayChatMessage("Nickname too long, max length is 16");
            return;
        }

        String allowedChars = "abcdefghijklmnopqrstuvwxyz0123456789_-";

        for (int i = 0; i < nicknameLowerCase.length(); i++){
            if (allowedChars.indexOf(nicknameLowerCase.charAt(i)) == -1){
                commandExecutor.displayChatMessage("Disallowed character(s), only a-z (case-insensitive), 0-9 and symbols -_ allowed");
                return;
            }
        }

        KivaServerUtils.playerNicknames.put(args[1], nickname);
        commandExecutor.displayChatMessage("Nickname of " + args[1] + "set to " + nickname + "!");
    }
}
