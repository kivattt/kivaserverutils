package com.kiva.kivaserverutils.commands;

import com.fox2code.foxloader.network.NetworkPlayer;
import com.fox2code.foxloader.registry.CommandCompat;
import com.kiva.kivaserverutils.KivaServerUtils;
import com.kiva.kivaserverutils.NicknameAllowed;

import static com.kiva.kivaserverutils.UsageMessage.sendUsageMessage;

public class Nick extends CommandCompat{
    public Nick(){
        super("nick", false);
    }

    public String commandSyntax(){
        return "§e/nick <nickname>";
    }

    public void onExecute(final String[] args, final NetworkPlayer commandExecutor){
        if (args.length != 2){
            sendUsageMessage(this.commandSyntax(), commandExecutor);
            return;
        }

        String nickname = args[1];
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

        if (!commandExecutor.isOperator() && !NicknameAllowed.nicknameIsAllowed(nickname)){
            commandExecutor.displayChatMessage("Someone already has that nickname/username, ask a mod to force change it?");
            return;
        }

        KivaServerUtils.playerNicknames.put(commandExecutor.getPlayerName(), nickname);
        commandExecutor.displayChatMessage("Nickname set to " + nickname + "!");
    }
}
